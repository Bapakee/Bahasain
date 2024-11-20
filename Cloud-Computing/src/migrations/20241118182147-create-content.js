'use strict';
/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.createTable('Contents', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      level_id: {
        type: Sequelize.INTEGER,
        allowNull :false,
        references : {
          model : 'Levels',
          key : 'id'
        },
        onDelete : 'CASCADE'
      },
      content_en: {
        type: Sequelize.TEXT
      },
      content_id: {
        type: Sequelize.TEXT
      },
      sequence: {
        type: Sequelize.INTEGER
      },
      transliteration: {
        type: Sequelize.TEXT
      },
      category: {
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
    await queryInterface.dropTable('Contents');
  }
};