const express = require('express');
const swaggerUi = require('swagger-ui-express');
const swaggerDocs = require('./config/swagger');
const bodyParser = require('body-parser');
const path = require('path');
const routes = require('./routes');

const app = express();

// Middleware
app.use(bodyParser.json());
app.use(express.static('src/public'));
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

// Logging middleware
app.use((req, res, next) => {
  console.log(`${req.method} ${req.originalUrl} - Body:`, req.body);
  next();
});

// Routes
app.use('/api', routes);

// Handle reset-password
app.get('/reset-password/:token', (req, res) => {
  res.sendFile(path.join(__dirname, 'public/reset-password.html'));
});

// 404 Handler
app.use((req, res) => {
  res.status(404).json({ error: 'Endpoint not found' });
});

// 500 Error Handler
app.use((err, req, res, next) => {
  console.error('Internal Server Error:', err.stack); // Log the error for debugging
  res.status(500).json({
    error: 'Internal Server Error',
    message: err.message || 'Something went wrong!',
  });
});

module.exports = app;
