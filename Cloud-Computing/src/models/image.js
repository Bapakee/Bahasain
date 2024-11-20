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
    entity_type: DataTypes.ENUM,
    entity_id: DataTypes.INTEGER,
    file_name: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'Image',
    hooks: {
      // Hook untuk validasi entity_type dan entity_id
      beforeSave: async (image) => {
        const model = Image.getEntityType(image.entity_type);
        const isEntityExists = await sequelize.models[model].findByPk(image.entity_id);
        if (!isEntityExists) {
          throw new Error(`${model} with ID ${image.entity_id} does not exist`);
        }
      },
    },
  });
  return Image;
};