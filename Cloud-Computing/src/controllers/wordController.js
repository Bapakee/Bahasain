const { Word,WordCategory } = require('../models');
const { Op, Model, where } = require('sequelize'); // Sequelize operators
const { successResponse, errorResponse } = require('../utils/responseConsistency'); // Import utility functions


const getWord = async (req, res) => {
    const { page = 1, limit = 10, search = '', categories = '' } = req.query;

    try {
        // Prepare categories for filtering if provided
        let categoryFilter = {};
        if (categories) {
            // Split categories by commas and trim extra spaces
            categoryFilter = {
                category: {
                    [Op.in]: categories.split(',').map(category => category.trim())
                }
            };
        }

        // Build query conditions based on search and category filter
        const conditions = {
            where: {
                word: {
                    [Op.like]: `%${search}%`, // Search by word
                },

            },
            order: [['word', 'ASC']], 
            include: [
                {
                    model: WordCategory,
                    attributes:['category'],
                    where : {
                        ...categoryFilter
                    }
                }
            ],
            offset: (page - 1) * limit, // For pagination
            limit: parseInt(limit, 10),
        };

        // Fetch the words from the database
        const words = await Word.findAll(conditions);

        // Format the response
        const response = words.map(word => ({
            id: word.id,
            word: word.word,
        }));

        // Send the success response
        successResponse(res, response, 'Words fetched successfully');
    } catch (error) {
        // Send error response in case of failure
        errorResponse(res, error, 'Error fetching data', 500);
    }
};


const getWordById = async (req, res) => {
    const { id } = req.params;

    try {
        // Fetch the word by primary key along with associated WordCategory
        const word = await Word.findByPk(id, {
            include: [
                {
                    model: WordCategory,
                }
            ]
        });

        // Check if the word was found
        if (!word) {
            return errorResponse(res, 'Word not found', 'No word found with the given ID', 404);
        }

        // Format the response
        const response = {
            id: word.id,
            word: word.word,
            categories: word.WordCategories.map(category => ({
                category : category.category,
                translate:category.translate,
                description:category.description,
                example:category.example
            })), // assuming WordCategory is the association
        };

        // Send the success response
        successResponse(res, response, 'Word fetched successfully');
    } catch (error) {
        // Send error response in case of failure
        errorResponse(res, error, 'Error fetching word', 500);
    }
};


module.exports = { getWord,getWordById };
