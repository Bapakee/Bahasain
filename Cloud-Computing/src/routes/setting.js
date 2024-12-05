const express = require('express');
const router = express.Router();
const { validateBody } = require('../middleware/validateBody');
const { setting } = require('../controllers/settingController')

router.post('/', validateBody([], ['notificationPreference', 'avatar']), setting)

module.exports = router