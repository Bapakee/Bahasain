const express = require('express');
const router = express.Router();
const { getDailyTrivia } = require('../controllers/triviaController');

router.get('/', getDailyTrivia); // Route for getting list of folklore with optional search

module.exports=router