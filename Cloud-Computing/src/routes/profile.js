const express = require('express');
const router = express.Router();
const {getHead,getProfile}=require('../controllers/profileController')

router.get('/head',getHead)
router.get('/',getProfile)

module.exports = router