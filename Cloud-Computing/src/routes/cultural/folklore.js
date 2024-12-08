const express = require('express');
const router = express.Router();
const { getFolklore, getFolkloreDetail } = require('../../controllers/cultural/folkloreController');

router.get('/', getFolklore); // Route for getting list of folklore with optional search

router.get('/:id', getFolkloreDetail); // Route for getting detailed folklore by ID

module.exports = router;
