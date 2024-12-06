const { sequelize } = require('../models');
const {
    fetchUser,
    fetchModule,
    validateAccess,
    updateOrCreateProgress,
} = require('../services/progress/index');
const { updateOrCreateStreak, checkAndUpdateUserLevel } = require('../services/progress/index');
const { successResponse, errorResponse } = require('../utils/responseConsistency');

const putUserProgress = async (req, res) => {
    const transaction = await sequelize.transaction();

    try {
        const { moduleId, levelId, score } = req.body;
        const userId = req.user.id;

        // Fetch and validate user
        const user = await fetchUser(userId, transaction);
        user.point += score;
        await user.save({ transaction });

        // Fetch and validate module
        const module = await fetchModule(moduleId, transaction);
        const levelExists = module.Levels.some((level) => level.id === levelId);
        if (!levelExists) {
            throw { statusCode: 404, message: `Level ID ${levelId} not found in module.` };
        }

        // Validate access
        await validateAccess(user.userLevel, module.level);

        // Update or create user progress
        const userProgress = await updateOrCreateProgress({ userId, moduleId, levelId, score }, transaction);

        // Check and update user level
        const userProgressLevel = await UserProgress.findAll({ where: { userId, moduleId }, transaction });
        const levelUp = await checkAndUpdateUserLevel(userId, moduleId, userProgressLevel, transaction);

        // Update or create streak
        await updateOrCreateStreak(userId, transaction);

        // Commit transaction
        await transaction.commit();

        // Send success response
        successResponse(res, { ...userProgress.toJSON(), levelUp }, 'User progress updated successfully.');
    } catch (error) {
        if (!transaction.finished) await transaction.rollback();
        console.error('Error processing user progress:', error);

        const statusCode = error.statusCode || 500;
        const message = error.message || 'Internal Server Error';
        errorResponse(res, message, 'Error processing user progress', statusCode);
    }
};

const setUserLevel = async (req, res) => {
    try {
        const { score } = req.body;
        const userId = req.user.id;

        // Validate score
        if (score < 0 || score > 10) {
            return errorResponse(res, 'Invalid score', 'Score must be between 0 and 10', 400);
        }

        const userLevel = score > 7 ? 3 : score >= 5 ? 2 : 1;

        // Update user level
        const user = await User.findByPk(userId);
        if (user.userLevel !== null) {
            return errorResponse(res, 'Invalid operation', 'User level already set', 400);
        }
        user.userLevel = userLevel;
        await user.save();

        successResponse(res, {}, 'User level successfully set.');
    } catch (error) {
        console.error('Error setting user level:', error);
        errorResponse(res, error.message, 'Error setting user level', 500);
    }
};

module.exports = { putUserProgress, setUserLevel };
