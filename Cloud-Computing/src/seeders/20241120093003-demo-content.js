'use strict';
const { faker } = require('@faker-js/faker');

/** @type {import('sequelize-cli').Migration} */
module.exports = {
  up: async (queryInterface, Sequelize) => {
    const contents = [];

    for (let i = 0; i < 5; i++) {
      contents.push({
        level_id: i+1,
        content_en: faker.lorem.words(2),
        content_id: faker.lorem.words(2),
        sequence: faker.number.int({ min: 1, max: 5 }),
        transliteration: faker.lorem.word(),
        category: faker.lorem.word(),
        created_at: new Date(),
        updated_at: new Date(),
      });
    }

    await queryInterface.bulkInsert('Contents', contents, {});
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.bulkDelete('Contents', null, {});
  }
};