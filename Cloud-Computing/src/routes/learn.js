const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth')
const {getModules} = require('../controllers/learnController')

router.get('/', auth, getModules);

module.exports = router;