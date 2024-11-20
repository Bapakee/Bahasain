'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class UserProgress extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // Relasi dengan User
      UserProgress.belongsTo(models.User, { foreignKey: 'user_id' });

      // Relasi dengan Module
      UserProgress.belongsTo(models.Module, { foreignKey: 'module_id' });

      // Relasi dengan Level
      UserProgress.belongsTo(models.Level, { foreignKey: 'level_id' });
    }
  }
  UserProgress.init({
    user_id: DataTypes.STRING,
    module_id: DataTypes.INTEGER,
    level_id: DataTypes.INTEGER,
    completed: DataTypes.BOOLEAN,
    last_accessed: DataTypes.DATE,
    score: DataTypes.INTEGER
  }, {
    sequelize,
    modelName: 'UserProgress',
  });
  return UserProgress;
};