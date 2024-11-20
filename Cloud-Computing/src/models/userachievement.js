'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class UserAchievement extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // Relasi ke User
      UserAchievement.belongsTo(models.User, { foreignKey: 'user_id', as: 'user' });

      // Relasi ke Achievement
      UserAchievement.belongsTo(models.Achievement, { foreignKey: 'achievement_id', as: 'achievement' });
    }
  }
  UserAchievement.init({
    user_id: DataTypes.STRING,
    achievement_id: DataTypes.INTEGER,
    achieved_at: DataTypes.DATE
  }, {
    sequelize,
    modelName: 'UserAchievement',
  });
  return UserAchievement;
};