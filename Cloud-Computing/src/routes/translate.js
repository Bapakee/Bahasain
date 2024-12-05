const express = require('express');
const router = express.Router();
const {getTranslate} = require('../controllers/translateController')
const { validateBody } = require('../middleware/validateBody');

router.post('/',validateBody(['text']),getTranslate);

module.exports=router