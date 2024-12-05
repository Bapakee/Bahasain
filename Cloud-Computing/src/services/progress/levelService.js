const { Level, Module, User, UserProgress } = require('../../models');

/**
 * Cek dan update level user jika semua level pada modul diselesaikan
 */
const checkAndUpdateUserLevel = async (userId, moduleId, userProgressLevel, transaction) => {
    console.log(userProgressLevel,'test');
    
    try {
        // Ambil semua level yang terkait dengan modul ini
        const allLevels = await Level.findAll({
            where: { moduleId },
            attributes: ['id'],
            transaction,
        });

        // Debug log untuk memeriksa level yang ada di modul
        console.log('All Levels in module:', allLevels.map(level => level.id));

        // Ambil level pengguna saat ini
        const user = await User.findOne({
            where: { id: userId },
            transaction,
        });

        if (!user) {
            throw new Error('User not found');
        }

        // Debug log untuk memeriksa level pengguna
        console.log('User level before update:', user.userLevel);

        // Ambil level modul
        const module = await Module.findOne({
            where: { id: moduleId },
            attributes: ['level'],
            transaction,
        });

        if (!module) {
            throw new Error('Module not found');
        }

        // Debug log untuk memeriksa level modul
        console.log('Module level:', module.level);

        // Ambil level yang sudah diselesaikan oleh pengguna
        const completedLevels = userProgressLevel
            .filter((progress) => progress.completed)
            .map((progress) => progress.levelId);

        // Debug log untuk memeriksa level yang diselesaikan
        console.log('Completed levels by user:', completedLevels);

        // Cek jika semua level dalam modul telah selesai
        const allCompleted = allLevels.every((level) => completedLevels.includes(level.id));

        // Debug log untuk memeriksa status selesai semua level
        console.log('All levels completed:', allCompleted);

        if (allCompleted) {
            // Jika semua level sudah selesai, naikkan level user jika perlu
            if (user.userLevel <= module.level) {
                user.userLevel += 1; // Menaikkan level user
                await user.save({ transaction }); // Simpan perubahan level user
                console.log('User level after update:', user.userLevel);
                return true
            } else {
                console.log('User level already at max level for this module');
                return false
            }
        }
        return false

    } catch (error) {
        console.error('Error in checkAndUpdateUserLevel:', error);
        throw error; // Throw error untuk menangani kesalahan di luar fungsi ini
    }
};

module.exports = { checkAndUpdateUserLevel };
