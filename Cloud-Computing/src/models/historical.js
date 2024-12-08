'use strict';
const { Model } = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Historical extends Model {
    static associate(models) {
      // Define associations here if needed
    }
  }
  Historical.init(
    {
      title: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      map_location: {
        type: DataTypes.STRING,
        allowNull: true,
      },
      image_url: {
        type: DataTypes.STRING,
        allowNull: true,
      },
      content: {
        type: DataTypes.TEXT,
        allowNull: true,
      },
      created_at: {
        type: DataTypes.DATE,
        allowNull: false,
      },
      updated_at: {
        type: DataTypes.DATE,
        allowNull: false,
      },
    },
    {
      sequelize,
      modelName: 'Historical',
      tableName: 'Historicals',
      underscored: true,
      timestamps: false, // Since created_at and updated_at are handled manually
    }
  );
  return Historical;
};
