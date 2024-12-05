const axios = require('axios');
const { successResponse, errorResponse } = require('../utils/responseConsistency'); // Import utility functions

const getTranslate = async (req, res) => {
    const { text } = req.body;
    try {
        // Await the axios POST request to get the translation response
        const translate = await axios.post('http://localhost:8000/translate', { text });

        // Log the response from translation
        console.log(translate.data);

        // Send the success response with translation data
        successResponse(res, translate.data, 'Word fetched successfully');
    } catch (error) {
        // Handle error and send failure response
        errorResponse(res, error, 'Error fetching word', 500);
    }
};

module.exports = { getTranslate };
