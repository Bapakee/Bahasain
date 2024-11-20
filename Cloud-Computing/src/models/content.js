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
      Content.hasMany(models.Image, {
        foreignKey: 'entity_id',
        constraints: false,
        scope: {
          entity_type: 'content',
        },
        as: 'images',
      });
    }
  }
  Content.init({
    levelId: {
      type: DataTypes.INTEGER,
      field: 'level_id'
    },
    contentEn: {
      type: DataTypes.TEXT,
      field: 'content_en'
    },
    contentId: {
      type: DataTypes.TEXT,
      field: 'content_id'
    },
    sequence: {
      type: DataTypes.INTEGER,
      field: 'sequence'
    },
    transliteration: {
      type: DataTypes.TEXT,
      field: 'transliteration'
    },
    category: {
      type: DataTypes.STRING,
      field: 'category'
    }
  }, {
    sequelize,
    modelName: 'Content',
  });
  
  return Content;
};