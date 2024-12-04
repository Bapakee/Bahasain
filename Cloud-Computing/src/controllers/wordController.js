const { Word,WordCategory,Sequelize  } = require('../models');
const { Op, Model, where } = require('sequelize'); // Sequelize operators
const { successResponse, errorResponse,paginatedResponse } = require('../utils/responseConsistency'); // Import utility functions


const getWord = async (req, res) => {
    const { page = 1, limit = 20, search = '', categories = '' } = req.query;

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

        // Build query conditions for counting total
        const totalConditions = {
            where: {
                word: {
                    [Op.like]: `%${search}%`, // Search by word
                },
            },
            include: [
                {
                    model: WordCategory,
                    where: {
                        ...categoryFilter
                    },
                    reqruired:false,
                    
                }
            ],
            disticnt:true,
            col: 'id',
        };

        // Count the total number of records
        const total = await Word.count(totalConditions);

        // Build query conditions for fetching data with pagination
        const fetchConditions = {
            ...totalConditions,
            order: [['word', 'ASC']], // Order by word alphabetically
            offset: (page - 1) * limit, // For pagination
            limit: parseInt(limit, 10), // Limit number of records per page
        };

        // Fetch the words from the database
        const words = await Word.findAll(fetchConditions);

        // Format the response
        const response = words.map(word => ({
            id: word.id,
            word: word.word,
        }));

        // Send the success response with pagination info
        paginatedResponse(res, response, 'Words fetched successfully', page, limit, total);
    } catch (error) {
        // Send error response in case of failure
        errorResponse(res, error.message, 'Error fetching data', 500);
    }
};



const getWordById = async (req, res) => {
    const { id } = req.params;

    try {
        let word;

        // Jika ID adalah 0, ambil data secara acak
        if (id == '0') {
            word = await Word.findOne({
                order: Sequelize.literal('RAND()'),
                include: [
                    {
                        model: WordCategory,
                    }
                ]
            });
        } else {
            // Ambil data berdasarkan ID
            word = await Word.findByPk(id, {
                include: [
                    {
                        model: WordCategory,
                    }
                ]
            });
        }

        // Periksa apakah data ditemukan
        if (!word) {
            return errorResponse(res, 'Word not found', 'No word found with the given ID', 404);
        }

        // Format respons
        const response = {
            id: word.id,
            word: word.word,
            categories: word.WordCategories.map(category => ({
                category: category.category,
                translate: category.translate,
                description: category.description,
                example: category.example,
            })), // assuming WordCategory is the association
        };

        // Kirim respons sukses
        successResponse(res, response, 'Word fetched successfully');
    } catch (error) {
        // Kirim respons kesalahan jika ada masalah
        errorResponse(res, error, 'Error fetching word', 500);
    }
};



module.exports = { getWord,getWordById };
