'use strict';
const { faker } = require('@faker-js/faker');

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  up: async (queryInterface, Sequelize) => {
    const levels = [];

    for (let i = 0; i < 5; i++) {
      levels.push({
        module_id: i+1,
        title: faker.lorem.word(),
        created_at: new Date(),
        updated_at: new Date(),
      });
    }

    await queryInterface.bulkInsert('Levels', levels, {});
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.bulkDelete('Levels', null, {});
  }
};