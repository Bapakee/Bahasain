const express = require('express');
const router = express.Router();

const historicalRoutes = require('./historical')
const folkloreRoutes = require('./folklore')
const recipeRoutes = require('./recipe')
const triviaRoutes = require('./trivia')

router.use('/historical',historicalRoutes);
router.use('/folklore',folkloreRoutes)
router.use('/recipe',recipeRoutes)
router.use('/trivia',triviaRoutes)

module.exports=router

