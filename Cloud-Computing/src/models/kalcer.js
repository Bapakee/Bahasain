'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Kalcer extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      Kalcer.hasMany(models.Image, {
        foreignKey: 'entity_id',
        constraints: false,
        scope: {
          entity_type: 'kalcer',
        },
        as: 'images',
      });
    }
  }
  Kalcer.init({
    title: {
      type: DataTypes.STRING,
      field: 'title'
    },
    content: {
      type: DataTypes.TEXT,
      field: 'content'
    },
    type: {
      type: DataTypes.STRING,
      field: 'type'
    },
    link: {
      type: DataTypes.STRING,
      field: 'link'
    },
    createdAt: {
      type: DataTypes.DATE,
      field: 'created_at'
    },
    updatedAt: {
      type: DataTypes.DATE,
      field: 'updated_at'
    }
  }, {
    sequelize,
    modelName: 'Kalcer',
  });
  ;
  return Kalcer;
};