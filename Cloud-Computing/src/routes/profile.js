const express = require('express');
const router = express.Router();
const {getHead}=require('../controllers/profileController')

router.get('/head',getHead)

module.exports = router