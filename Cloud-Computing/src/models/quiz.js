'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Quiz extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // Relasi dengan Level
      Quiz.belongsTo(models.Level, { foreignKey: 'level_id', as: 'level' });

      // Relasi dengan QuizOptions
      Quiz.hasMany(models.Image, {
        foreignKey: 'entity_id',
        constraints: false,
        scope: {
          entity_type: 'quiz',
        },
        as: 'images',
      });
    
    }
  }
  Quiz.init({
    level_id: DataTypes.INTEGER,
    type: DataTypes.ENUM('essay','option'),
    question: DataTypes.STRING,
    answer: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'Quiz',
  });
  return Quiz;
};