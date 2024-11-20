'use strict';
const { faker } = require('@faker-js/faker');

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  up: async (queryInterface, Sequelize) => {
    const modules = [];

    for (let i = 0; i < 5; i++) {
      modules.push({
        name: faker.lorem.word(),
        level: faker.number.int({ min: 1, max: 5 }),
        created_at: new Date(),
        updated_at: new Date(),
      });
    }

    await queryInterface.bulkInsert('Modules', modules, {});
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.bulkDelete('Modules', null, {});
  }
};