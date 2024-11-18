'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Streak extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Streak.belongsTo(models.User, { foreignKey: 'user_id', as: 'user' });
    }
  }
  Streak.init({
    user_id: DataTypes.INTEGER,
    streak: DataTypes.INTEGER,
    last_activity: DataTypes.DATE
  }, {
    sequelize,
    modelName: 'Streak',
  });
  return Streak;
};