const { Recipe } = require ('../../models')
const { successResponse, errorResponse } = require('../../utils/responseConsistency');
const cheerio = require('cheerio');
const { Op } = require('sequelize');

const getRecipe = async (req, res) => {
    const { search = '' } = req.query;
    try {
        const RecipeData = await Recipe.findAll({
            where: {
                title: {
                    [Op.like]: `%${search}%`,
                },
            },
        });

        const result = RecipeData.map((item) => {
            const plainTextContent = cheerio.load(item.content).text();

            const words = plainTextContent.split(/\s+/);
            const overview = words.slice(0, 30).join(' ');
            
            const imageName = item.title.replace(/\s+/g, '_');

            const imageUrl = `${process.env.BUCKET_URL}/recipe/${encodeURIComponent(item.title)}/${imageName}.png`;

            return {
                ...item.toJSON(),
                imageUrl: imageUrl, // Dynamic image URL based on item.title
                content: overview, // Shortened content preview
            };
        });

        return successResponse(res, result, 'Recipes fetched successfully');
    } catch (error) {
        return errorResponse(res, error, 'Error when fetching recipes', 500);
    }
};
const getRecipeDetail = async (req, res) => {
    const { id } = req.params; // Menggunakan req.params
    try {
        const RecipeDetail = await Recipe.findByPk(id);
        if (!RecipeDetail) {
            return errorResponse(res, null, 'Recipe detail not found', 404);
        }

        const cleanData = RecipeDetail.dataValues; // Mengambil semua atribut

        return successResponse(res, cleanData, 'Recipe detail fetched successfully');
    } catch (error) {
        return errorResponse(res, null, 'Error when fetching Recipe Detail', 500);
    }
};


module.exports = {getRecipe, getRecipeDetail }