const { UserProgress } = require('../../models');


async function updateOrCreateProgress({ userId, moduleId, levelId, score }, transaction) {
    const userProgress = await UserProgress.findOne({
        where: { userId, moduleId, levelId },
        transaction,
    });

    if (userProgress) {
        userProgress.score = Math.max(score, userProgress.score);
        userProgress.completed = true;
        userProgress.lastAccessed = new Date();
        await userProgress.save({ transaction });
        return userProgress;
    }

    return await UserProgress.create(
        {
            userId,
            moduleId,
            levelId,
            score,
            completed: true,
            lastAccessed: new Date(),
        },
        { transaction }
    );
}

module.exports = {
    updateOrCreateProgress,
};