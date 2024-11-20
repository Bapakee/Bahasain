const express = require('express');
const router = express.Router();
const auth = require('../middleware/validateBody')
const {getModules} = require('../controllers/learnController')

router.get('/learn', auth, getModules);

module.exports = router;