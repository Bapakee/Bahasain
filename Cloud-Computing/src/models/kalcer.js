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
    title: DataTypes.STRING,
    content: DataTypes.TEXT,
    type: DataTypes.STRING,
    link: DataTypes.STRING,
    created_at: DataTypes.DATE,
    updated_at: DataTypes.DATE
  }, {
    sequelize,
    modelName: 'Kalcer',
  });
  return Kalcer;
};