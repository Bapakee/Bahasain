'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Image extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // Relasi dengan Content
      Image.belongsTo(models.Content, { foreignKey: 'content_id', as: 'content' });

      // Relasi dengan Quiz
      Image.belongsTo(models.Quiz, { foreignKey: 'quiz_id', as: 'quiz' });
    }
  }
  Image.init({
    content_id: DataTypes.INTEGER,
    quiz_id: DataTypes.INTEGER,
    file_name: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'Image',
  });
  return Image;
};