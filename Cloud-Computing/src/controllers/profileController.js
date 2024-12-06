const { User, Streak } = require('../models')
const { successResponse, errorResponse } = require('../utils/responseConsistency');

const getHead = async (req, res) => {
    const userId = req.user.id;
    try {
        const user = await User.findByPk(userId, {
            include: [
                {
                    model: Streak,
                    attributes: ['streaK'],
                },
            ],
            attributes: ['point'],
        });
        successResponse(res, user, 'Head fetched successfully');
    } catch (error) {
        errorResponse(res, null, 'Failed to fetch head', 500);
    }
};


module.exports = {getHead}