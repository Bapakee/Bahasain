const { test } = require('../config/config');
const { UserProgress, User, Module,Level, sequelize } = require('../models');
const { updateOrCreateStreak, checkAndUpdateUserLevel } = require('../services/progress/index');
const { successResponse, errorResponse } = require('../utils/responseConsistency'); // Import utility functions

/**
 * Handle PUT user progress (create or update progress)
 */
const putUserProgress = async (req, res) => {
    const transaction = await sequelize.transaction(); // Start transaction

    try {
        const { moduleId, levelId, score } = req.body;
        const userId = req.user.id;

        // Fetch user level
        const user = await User.findByPk(userId, {
            attributes: ['userLevel'],
            transaction,
        });
        if (!user) {
            await transaction.rollback();
            return errorResponse(res, 'User not found', 'Pengguna tidak ditemukan', 404);
        }
        const { userLevel } = user;

        // Fetch module level
        const module = await Module.findByPk(moduleId, {
            attributes: ['level'], // You can keep this if needed for other logic
            include: [{
                model: Level,  // Assuming 'Level' is the model that holds the actual level information
                attributes: ['id'], // Attributes you want to retrieve from the Level model
            }],
            transaction,
        });
        if (!module) {
            await transaction.rollback();
            return errorResponse(res, 'Module not found', 'Modul tidak ditemukan', 404);
        }
        const levelExists = module.Levels.some(level => level.id === levelId);
        if (!levelExists) {
            await transaction.rollback();
            return errorResponse(
                res,
                'Level not found in module',
                `Level ID ${levelId} tidak ditemukan dalam modul ini.`,
                404
            );
        }

        const { level: moduleLevel } = module;
        // Check if user level is sufficient
        if (userLevel < moduleLevel) {
            await transaction.rollback();
            return errorResponse(
                res,
                `Access denied`,
                `Level Anda (${userLevel}) belum cukup untuk mengakses modul ini. Level yang dibutuhkan: ${moduleLevel}.`,
                403
            );
        }

        // Get all progress for the user in the given module
        let userProgressLevel = await UserProgress.findAll({
            where: { userId, moduleId },
            transaction,
        });

        // Find progress for the given levelId
        let userProgress = userProgressLevel.find((progress) => progress.levelId === levelId);

        let message;
        if (userProgress) {
            // Update existing progress
            userProgress.score = score || userProgress.score;
            userProgress.completed = true;
            userProgress.lastAccessed = new Date();
            await userProgress.save({ transaction });
            message = 'Successful update user progress';
        } else {
            // Create new progress
            userProgress = await UserProgress.create(
                {
                    userId,
                    moduleId,
                    levelId,
                    completed: true,
                    lastAccessed: new Date(),
                    score,
                },
                { transaction }
            );
            message = 'Successful save user progress';
        }
        userProgressLevel = await UserProgress.findAll({
            where: { userId, moduleId },
            transaction,
        });

        // Check and update user level if all levels in the module are completed
        const levelUp = await checkAndUpdateUserLevel(userId, moduleId, userProgressLevel, transaction);
        
        // Update or create streak
        await updateOrCreateStreak(userId, transaction);

        // Prepare response, excluding unnecessary fields
        const { user_id, level_id, module_id, updated_at, created_at, ...response } = userProgress.toJSON();

        // Commit transaction
        await transaction.commit();

        // Send success response
        successResponse(res, {...response,levelUp}, message);
    } catch (error) {
        if (!transaction.finished) await transaction.rollback(); // Rollback on error
        console.error('Error processing user progress:', error);

        // Handle specific errors or fallback to default error response
        const statusCode = error.statusCode || 500;
        const errorMessage = error.message || 'Internal Server Error';
        errorResponse(res, errorMessage, 'Error processing user progress', statusCode);
    }
};

const setUserLevel = async (req, res) => {
    try {
        const { score } = req.body;
        const userId = req.user.id;

        // Validate score range
        if (score < 0 || score > 10) {
            return errorResponse(res, 'Score must be between 0 and 10.', 'Invalid Score', 400);
        }

        // Determine user level based on score
        let userLevel;
        if (score > 7) {
            userLevel = 3;
        } else if (score >= 5) {
            userLevel = 2;
        } else {
            userLevel = 1;
        }

        // Check if user level is already set
        const user = await User.findByPk(userId);
        if (user.userLevel !== null) {
            return errorResponse(res, 'User level already set.', 'Invalid User', 400);
        }

        // Update user level
        user.userLevel = userLevel;
        await user.save();

        // Send success response
        return successResponse(res, [], 'User level successfully set.');

    } catch (error) {
        return errorResponse(res, error, 'Error setting user level', 500);
    }
}


module.exports = { putUserProgress, setUserLevel };
