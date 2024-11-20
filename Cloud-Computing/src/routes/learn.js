const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth')
const {getModules,getLevel} = require('../controllers/learnController')

router.get('/', auth, getModules);
router.get('/:moduleId/level/:levelId',auth,getLevel)

module.exports = router;