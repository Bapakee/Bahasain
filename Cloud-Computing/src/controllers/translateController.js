const axios = require('axios');
const { successResponse, errorResponse } = require('../utils/responseConsistency'); // Import utility functions

const getTranslate = async (req, res) => {
    const { text } = req.body;
    console.log(text);
    
    try {
        // Await the axios POST request to get the translation response
        const translate = await axios.post(`${process.env.TRANSLATE_URL}/translate`, { text });
        const translated=translate.data.translation
        const pos = await axios.post(`${process.env.POS_URL}/predict`, { text:translated });
        const response = {
            translate : translated,
            pos :pos.data
        }

        // Send the success response with translation data
        successResponse(res, response, 'Word fetched successfully');
    } catch (error) {
        // Handle error and send failure response
        errorResponse(res, error, 'Error fetching word', 500);
    }
};

module.exports = { getTranslate };
