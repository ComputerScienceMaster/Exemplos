var models = require('../../models');

module.exports = function (app) {

    app.get("/manageusers", function (req, res) {
        models.UserAccount.findAll().then(function (useraccounts) {
            res.render("usermodule/manageusers", {
                user: useraccounts
            })
        });
    });

    app.get("/signup", function (req, res) {
        res.render('usermodule/signup');
    });

    app.post("/signup", function (req, res) {
        var uc = req.body;
        models.UserAccount.create({
            loginUserAccount: uc['loginUser'],
            passwordUserAccount: uc['passwordUser'],
            nameUserAccount: uc['nameUser'],
            emailUserAccount: uc['emailUser']
        }).then(function () {
            res.redirect('/manageusers');
        })

    });

    app.post("/delete", function (req, res) {
        var idToDelete = req.body['usertodelete'];
        console.log(idToDelete);
        models.UserAccount.destroy({
            where: {
                id: idToDelete
            }
        }).then(function () {
            res.redirect('/manageusers');
        })

    });

    app.post("/edit", function (req, res) {
        var idToDelete = req.body['usertoedit'];
        console.log(idToDelete);
        models.UserAccount.findById(idToDelete).then(function (useraccounts) {
            res.render("usermodule/edit", {
                user: useraccounts
            })
        });
    });

    app.post("/update", function (req, res) {
        console.log("update");
        var userToEdit = req.body['usertoedit'];
        var uc = req.body;
        var oldUser;

        models.UserAccount.findById(userToEdit).then(function (old) {

            oldUser = old;

        }).then(function () {
            models.UserAccount.update({
                    loginUserAccount : uc['loginUser'],
                    passwordUserAccount : uc['passwordUser'],
                    nameUserAccount : uc['nameUser'],
                    emailUserAccount : uc['emailUser']
                }, {
                    where: {
                        id: userToEdit
                    }
                }

            ).then(function () {
                res.redirect('/manageusers');
            })
        });



    });

}