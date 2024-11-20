'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class QuizOption extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // Relasi dengan Quiz
      QuizOption.belongsTo(models.Quiz, { foreignKey: 'quiz_id', as: 'quiz' });
    }
  }
  QuizOption.init({
    quiz_id: DataTypes.INTEGER,
    option: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'QuizOption',
  });
  return QuizOption;
};