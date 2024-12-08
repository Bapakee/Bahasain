const express = require('express');
const router = express.Router();
const {getHistorical,getHistoricalDetail}=require('../../controllers/cultural/historicalController')

router.get('/',getHistorical);

router.get('/:id',getHistoricalDetail);

module.exports=router