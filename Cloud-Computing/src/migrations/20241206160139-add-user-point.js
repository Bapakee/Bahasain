'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    await queryInterface.addColumn('users', 'point', {
      type: Sequelize.STRING,
      allowNull: true, // Sesuaikan dengan kebutuhan Anda
      defaultValue: null, // Optional, level default jika diperlukan
    });
  },

  async down (queryInterface, Sequelize) {
    await queryInterface.removeColumn('users', 'point');
  }
};
