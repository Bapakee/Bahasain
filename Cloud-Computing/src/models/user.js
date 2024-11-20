'use strict';
const { Model } = require('sequelize');
const bcrypt = require('bcrypt');
const { nanoid } = require('nanoid');

module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    static associate(models) {
      // Relasi dengan tabel Streak
      User.hasOne(models.Streak, { foreignKey: 'user_id', as: 'streak' });

      // Relasi dengan tabel Tokens
      User.hasMany(models.Token, { foreignKey: 'user_id', as: 'tokens' });

      // Relasi dengan tabel UserAchievements (Many-to-Many dengan Achievements)
      User.belongsToMany(models.Achievement, {
        through: models.UserAchievement,
        foreignKey: 'user_id',
        otherKey: 'achievement_id',
        as: 'achievements',
      });

      // Relasi dengan tabel UserProgress
      User.hasMany(models.UserProgress, { foreignKey: 'user_id', as: 'progress' });
    }

    // Verifikasi refresh token
  }

  User.init(
    {
      id: {
        type: DataTypes.STRING,
        primaryKey: true,
        defaultValue: () => nanoid(10),
      },
      name: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      email: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
      },
      password: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      user_level: {
        type: DataTypes.INTEGER,
        allowNull: true
      }
    },
    {
      sequelize,
      modelName: 'User',
    }
  );

  return User;
};
