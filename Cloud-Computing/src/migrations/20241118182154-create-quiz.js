'use strict';
/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.createTable('Quizzes', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      level_id: {
        type: Sequelize.INTEGER,
        allowNull : false,
        references : {
          model : 'Levels',
          key : 'id'
        },
        onDelete : 'CASCADE'
      },
      type :{
        type : Sequelize.ENUM('essay','option'),
        allowNull : false,
      },
      question: {
        type: Sequelize.STRING
      },
      answer: {
        type: Sequelize.STRING
      },
      created_at: {
        allowNull: false,
        type: Sequelize.DATE
      },
      updated_at: {
        allowNull: false,
        type: Sequelize.DATE
      }
    });
  },
  async down(queryInterface, Sequelize) {
    await queryInterface.dropTable('Quizzes');
  }
};