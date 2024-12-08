const express = require('express');
const router = express.Router();

const historicalRoutes = require('./historical')

router.use('/historical',historicalRoutes);

module.exports=router

