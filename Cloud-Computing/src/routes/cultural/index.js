const express = require('express');
const router = express.Router();

const historicalRoutes = require('./historical')
const folkloreRoutes = require('./folklore')

router.use('/historical',historicalRoutes);
router.use('/folklore',folkloreRoutes)

module.exports=router

