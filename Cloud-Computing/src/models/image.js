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
      Image.belongsTo(models.Content, {
        foreignKey: 'entity_id',
        constraints: false,
        as: 'content',
      });
      Image.belongsTo(models.Quiz, {
        foreignKey: 'entity_id',
        constraints: false,
        as: 'quiz',
      });
      Image.belongsTo(models.Kalcer, {
        foreignKey: 'entity_id',
        constraints: false,
        as: 'kalcer',
      });
      
    }
    static getEntityType(type) {
      const types = {
        content: 'Content',
        quiz: 'Quiz',
        kalcer: 'Kalcer',
      };
      return types[type];
    }
  }
  
  Image.init({
    entityType: {
      type: DataTypes.ENUM('quiz', 'content', 'kalcer'),
      field: 'entity_type'
    },
    entityId: {
      type: DataTypes.INTEGER,
      field: 'entity_id'
    },
    fileName: {
      type: DataTypes.STRING,
      field: 'file_name'
    }
  }, {
    sequelize,
    modelName: 'Image',
    hooks: {
      beforeSave: async (image) => {
        const model = Image.getEntityType(image.entityType);  // Menggunakan camelCase di kode
        const isEntityExists = await sequelize.models[model].findByPk(image.entityId); // Menggunakan camelCase di kode
        if (!isEntityExists) {
          throw new Error(`${model} with ID ${image.entityId} does not exist`);
        }
      },
    },
  });
  
  return Image;
};