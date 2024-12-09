const { UserProgress, Module, Level, Quiz, QuizOption, Sequelize, User } = require('../models');
const { successResponse, errorResponse } = require('../utils/responseConsistency');

const { Op, where } = require('sequelize');

// Fungsi untuk mendapatkan modul dengan level dan status penyelesaian
const getModules = async (req, res) => {
    const userId = req.user.id; // ID pengguna dari token

    try {
        // Ambil data pengguna berdasarkan ID
        const user = await User.findByPk(userId);
        if (!user) {
            return errorResponse(res, 'User not found', 'User not found', 401);
        }
        if (!user.userLevel) {
            return errorResponse(res,  'user level not set','Access denied', 403);
        }

        // Ambil semua modul dari database
        const modules = await Module.findAll({
            order: [['level', 'ASC']], // Urutkan berdasarkan level secara ascending
            where: {
                level: {
                    [Op.gt]: 0, // Level module must be greater than 0
                },
            },
            include: [
                {
                    model: Level, // Sertakan semua level terkait untuk setiap modul
                    order: [['order', 'ASC']], // Urutkan level berdasarkan urutan (order)
                    include: [
                        {
                            model: UserProgress,
                            where: { userId }, // Ambil hanya progres untuk pengguna tertentu
                            required: false, // Left join untuk memastikan level tanpa progres juga dimasukkan
                            attributes: ['id', 'completed', 'score'], // Ambil atribut yang relevan
                        },
                    ],
                },
            ],
        });

        // Transformasikan data ke format respons yang diinginkan
        const response = modules.map((module) => {
            // Hitung jumlah level dan level yang telah diselesaikan pengguna
            const totalLevels = module.Levels.length;
            const completedLevels = module.Levels.filter(
                (level) => level.UserProgresses?.[0]?.completed
            ).length;

            return {
                id: module.id,
                name: module.name,
                level: module.level,
                isAccessible: user.userLevel >= module.level, // Cek apakah modul dapat diakses
                lessonsCompleted: `${completedLevels}/${totalLevels}`, // Format penyelesaian modul
                levels: module.Levels.map((level) => ({
                    id: level.id,
                    title: level.title,
                    order: level.order,
                    score: level.UserProgresses?.[0]?.score || null, // Sertakan skor jika ada
                    isCompleted: !!level.UserProgresses?.[0]?.completed, // Boolean status penyelesaian
                })),
            };
        });

        // Kirim respons ke klien menggunakan successResponse
        successResponse(res, response, 'Successful get module');
    } catch (error) {
        console.error('Error fetching modules and levels:', error);
        errorResponse(res, error, 'Gagal mengambil data modul dan level', 500);
    }
};



const getLevel = async (req, res) => {
    const { moduleId, levelId } = req.params;
    const userId = req.user.id;

    try {
        // Fetch user level
        const user = await User.findByPk(userId, {
            attributes: ['userLevel'],
        });
        if (!user) {
            return errorResponse(res, 'User not found', 'Pengguna tidak ditemukan', 404);
        }
        const { userLevel } = user;

        // Fetch module level
        const module = await Module.findByPk(moduleId, {
            attributes: ['level'],
        });
        if (!module) {
            return errorResponse(res, 'Module not found', 'Modul tidak ditemukan', 404);
        }
        const { level: moduleLevel } = module;

        // Check level access
        if (userLevel < moduleLevel) {
            return errorResponse(res, `Your level (${userLevel?? 0}) is not sufficient to access this module. Required level: ${moduleLevel}.`, 'Access denied', 403);
        }

        // Fetch levels with quizzes and options
        const levels = await Level.findAll({
            where: { moduleId, id: levelId },
            order: [['order', 'ASC']],
            include: [
                {
                    model: Quiz,
                    include: [{ model: QuizOption }],
                },
            ],
        });

        // Handle empty levels
        if (!levels || levels.length < 1) {
            return errorResponse(res, 'Level not available', 'Level tidak tersedia', 404);
        }

        // Transform levels data into a cleaner response format
        const response = levels.map((level) => ({
            id: level.id,
            title: level.title,
            quizzes: level.Quizzes.map((quiz) => ({
                id: quiz.id,
                type: quiz.type,
                question: quiz.question,
                answer: quiz.answer,
                imageUrl: quiz.imageUrl,
                quizOptions: quiz.QuizOptions.map((quizOption) => ({
                    option: quizOption.option,
                })),
            })),
        }));

        // Send success response
        return successResponse(res, response, 'Level content fetched successfully');
    } catch (error) {
        console.error('Error fetching level:', error);
        return errorResponse(res, error.message || 'Internal Server Error', 'Error fetching level content', 500);
    }
};




module.exports = { getModules, getLevel };
