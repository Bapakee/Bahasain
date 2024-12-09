'use strict';

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    await queryInterface.addColumn('quizzes', 'voice_url', {
      type: Sequelize.STRING,
      allowNull: true, // Sesuaikan dengan kebutuhan Anda
      defaultValue: null, // Optional, level default jika diperlukan
    });
    await queryInterface.changeColumn('quizzes', 'explanation', {
      type: Sequelize.TEXT, // Kembalikan tipe data ke VARCHAR (atau tipe data sebelumnya)
      allowNull: true, // Sesuaikan dengan kebutuhan Anda
      defaultValue: null, // Optional, defaultValue jika diperlukan
    });
    await queryInterface.changeColumn('quizzes', 'type', {
      type : Sequelize.ENUM('essay','option','rearrange'),
      allowNull : false,
    });
  },

  async down (queryInterface, Sequelize) {
    await queryInterface.removeColumn('quizzes', 'voice_url');
    await queryInterface.changeColumn('quizzes', 'explanation', {
      type: Sequelize.JSON, // Kembalikan tipe data ke VARCHAR (atau tipe data sebelumnya)
      allowNull: true, // Sesuaikan dengan kebutuhan Anda
      defaultValue: null, // Optional, defaultValue jika diperlukan
    });
    await queryInterface.changeColumn('quizzes', 'type', {
      type : Sequelize.ENUM('essay','option'),
      allowNull : false,
    });
  }
};
