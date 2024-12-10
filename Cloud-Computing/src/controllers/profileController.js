const { User, Streak } = require('../models')
const { getAllModule } = require('../services/getModule')
const { successResponse, errorResponse } = require('../utils/responseConsistency');

const getHead = async (req, res) => {
    const userId = req.user.id;
    try {
        const user = await User.findByPk(userId, {
            include: [
                {
                    model: Streak,
                    attributes: ['streak'],
                },
            ],
            attributes: ['point'],
        });
        const response = {
            point: parseInt(user.point),
            streak: user.Streak.streak
        }
        successResponse(res, response, 'Head fetched successfully');
    } catch (error) {
        errorResponse(res, null, 'Failed to fetch head', 500);
    }
};

const getLevelDescription = (userLevel) => {
    if (userLevel === 1) return 'Beginner';
    if (userLevel === 2) return 'Intermediate';
    if (userLevel === 3) return 'Expert';
    return 'Unknown Level'; // Jika level tidak sesuai
};

const getProfile = async (req, res) => {
    const userId = req.user.id;
    try {
        // Ambil data user
        const user = await User.findByPk(userId);
        const { name, userLevel } = user.toJSON();

        // Ambil semua modul dengan level yang sama dengan userLevel
        const modules = await getAllModule(userId);
        const sameLevelModules = modules.filter((module) => module.level === userLevel);

        // Filter modul yang sudah selesai dengan level yang sama dengan userLevel
        const completedModules = sameLevelModules.filter((module) => {
            const totalLevels = module.Levels.length;
            const completedLevels = module.Levels.filter(
                (level) => level.UserProgresses?.[0]?.completed
            ).length;

            // Modul dianggap selesai jika semua levelnya sudah diselesaikan
            return completedLevels === totalLevels;
        });

        // Hitung jumlah modul yang selesai
        const completedModulesCount = completedModules.length;

        // Hitung total modul dengan level yang sama
        const totalModulesSameLevel = sameLevelModules.length;

        // Hitung persentase modul yang selesai
        const percent =
            totalModulesSameLevel > 0
                ? ((completedModulesCount / totalModulesSameLevel) * 100).toFixed(2)
                : 0;

        // Ambil sertifikat modul yang selesai
        const certiLink = completedModules.map(
            (module) => `${process.env.BUCKET_URL}/Certificate/${encodeURIComponent(module.name)}.png`
        );
        const avatarLink = `${process.env.BUCKET_URL}/Avatar/${user.avatar}.png`


        // Response
        const response = {
            name,
            avatar:avatarLink,
            level: userLevel,
            percent:parseFloat(percent), // Persentase modul yang selesai
            certiLink, // Link sertifikat untuk modul yang selesai
        };

        successResponse(res, response, 'Profile fetched successfully');
    } catch (error) {
        console.error(error);
        errorResponse(res, error, 'Failed to fetch profile');
    }
};






module.exports = { getHead, getProfile }