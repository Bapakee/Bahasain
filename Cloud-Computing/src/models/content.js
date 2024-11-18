'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Content extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // Relasi dengan Level
      Content.belongsTo(models.Level, { foreignKey: 'level_id', as: 'level' });

      // Relasi dengan Images
      Content.hasMany(models.Image, { foreignKey: 'content_id', as: 'images' });
    }
  }
  Content.init({
    level_id: DataTypes.INTEGER,
    content_en: DataTypes.TEXT,
    content_id: DataTypes.TEXT,
    sequence: DataTypes.INTEGER,
    transliteration: DataTypes.TEXT,
    category: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'Content',
  });
  return Content;
};