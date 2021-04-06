module.exports = (sequelize, DataTypes) => {
    var UserAccount = sequelize.define('UserAccount', {
        id: {
            allowNull: false,
            autoIncrement: true,
            primaryKey: true,
            type: DataTypes.INTEGER
          },
        loginUserAccount: DataTypes.STRING,
        passwordUserAccount: DataTypes.STRING,
        nameUserAccount: DataTypes.STRING,
        emailUserAccount: DataTypes.STRING,
    })
    return UserAccount;
}