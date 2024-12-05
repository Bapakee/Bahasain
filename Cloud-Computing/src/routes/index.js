const express = require('express');
const authRoutes = require('./auth');
const learnRoutes = require('./learn');
const progressRoutes = require('./progress');
const wordRoutes = require('./word');
const translateRoutes = require('./translate');
const settingRoutes = require('./setting')
const auth = require('../middleware/auth');

const router = express.Router();

router.use('/auth', authRoutes);
router.use('/module', auth, learnRoutes);
router.use('/progress', auth, progressRoutes);
router.use('/word', auth, wordRoutes);
router.use('/translate', auth, translateRoutes);
router.use('/setting',auth,settingRoutes)

module.exports = router;
