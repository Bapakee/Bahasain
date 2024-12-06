const { User, Streak } = require('../models')
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
            point : parseInt(user.point),
            streak : user.Streak.streak
        }
        successResponse(res, response, 'Head fetched successfully');
    } catch (error) {
        errorResponse(res, null, 'Failed to fetch head', 500);
    }
};


module.exports = {getHead}