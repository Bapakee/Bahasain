const express = require('express');
const router = express.Router();
const { validateBody } = require('../middleware/validateBody');
const { setting } = require('../controllers/settingController')

/**
 * @swagger
 * tags:
 *   name: Settings
 *   description: Settings management
 */

/**
 * @swagger
 * /settings:
 *   post:
 *     summary: Update user settings
 *     tags: [Settings]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               notificationPreference:
 *                 type: string
 *                 description: User's notification preference
 *               avatar:
 *                 type: string
 *                 description: URL of the user's avatar
 *     responses:
 *       200:
 *         description: Settings updated successfully
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 success:
 *                   type: boolean
 *                   example: true
 *                 message:
 *                   type: string
 *                   example: Settings updated successfully
 *       400:
 *         description: Bad request
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 success:
 *                   type: boolean
 *                   example: false
 *                 message:
 *                   type: string
 *                   example: Invalid request body
 */


router.post('/', validateBody([], ['notificationPreference', 'avatar']), setting)

module.exports = router