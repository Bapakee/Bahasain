const express = require('express');
const swaggerUi = require('swagger-ui-express');
const swaggerDocs = require('./swagger');
const path = require('path');
require('dotenv').config();
const bodyParser = require('body-parser');
const authRoutes = require('./routes/auth');
const learnRoutes = require('./routes/learn');
const auth = require('./middleware/auth')

const app = express();
app.use(bodyParser.json());
app.use(express.static('public'));
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));
app.use('/api/auth', authRoutes);
app.get('/reset-password/:token', (req, res) => {
    res.sendFile(path.join(__dirname, '../src/public/reset-password.html'));
  });
  app.use('/api/module', learnRoutes);



const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
