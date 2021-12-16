'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('UserAccount', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      loginUserAccount: {
        type: Sequelize.STRING
      },
      passwordUserAccount: {
        type: Sequelize.STRING
      },
      nameUserAccount: {
        type: Sequelize.STRING
      },
      emailUserAccount: {
        type: Sequelize.STRING
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE
      },
      updatedAt:{
        allowNull: false,
        type: Sequelize.DATE
      }
    });

  },

  down: (queryInterface, Sequelize) => {
    return queryInterface.dropTable('UserAccount');
  }
};