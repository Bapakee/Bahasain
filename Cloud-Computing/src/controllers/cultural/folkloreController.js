const { Folklore } = require('../../models');
const { successResponse, errorResponse } = require('../../utils/responseConsistency');
const cheerio = require('cheerio');
const { Op } = require('sequelize');

const getFolklore = async (req, res) => {
    const { search = '' } = req.query;
    try {
        const folkloreData = await Folklore.findAll({
            where: {
                title: {
                    [Op.like]: `%${search}%`,
                },
            },
        });

        const result = folkloreData.map((item) => {
            const plainTextContent = cheerio.load(item.content).text();

            const words = plainTextContent.split(/\s+/);
            const overview = words.slice(0, 30).join(' ');

            return {
                ...item.toJSON(),
                content: overview, // Shortened content preview
            };
        });

        return successResponse(res, result, 'Folklore fetched successfully');
    } catch (error) {
        return errorResponse(res, error, 'Error when fetching Folklore', 500);
    }
};

const getFolkloreDetail = async (req, res) => {
    const { id } = req.params; // Use req.params for the id
    try {
        const folkloreDetail = await Folklore.findByPk(id);
        if (!folkloreDetail) {
            return errorResponse(res, null, 'Folklore detail not found', 404);
        }

        return successResponse(res, folkloreDetail, 'Folklore detail fetched successfully');
    } catch (error) {
        return errorResponse(res, null, 'Error when fetching Folklore Detail', 500);
    }
};

module.exports = { getFolklore, getFolkloreDetail };
