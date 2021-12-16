var UserAccount = require('../../models/UserAccount');

module.exports = function (app) {

    app.get("/manageusers", function (req, res) {
        UserAccount.find({}, function (err, useraccounts) {
            res.render('usermodule/manageusers', {
                user: useraccounts
            });
        })
    });

    app.get("/signup", function (req, res) {
        res.render('usermodule/signup');
    });

    app.post("/signup", function (req, res) {
        var uc = req.body;

        // create a new user called chris
        var useraccount = UserAccount({
            name: uc['nameUser'],
            username: uc['loginUser'],
            password: uc['passwordUser'],
            email: uc['emailUser']
        });

        useraccount.save(function (err) {
            if (err) {
                throw err
            } else {
                res.redirect('/manageusers');
            }
        });

    });

    app.post("/delete", function (req, res) {
        var usertodelete = req.body['usertodelete'];

        UserAccount.findOneAndRemove({
            username: usertodelete
        }, function (err) {
            if (err) throw err;
            res.redirect('/manageusers');
        });

    });

    app.get("/edit", function (req, res) {
        var idtoedit = req.query.usertoedit;
        UserAccount.findById(idtoedit, function (err, useraccounts) {
            res.render('usermodule/edit', {
                user: useraccounts
            });
        })

    });

    app.post("/edit", function (req, res) {
        var userToEdit = req.body['usertoedit'];
        var uc = req.body;

        UserAccount.findByIdAndUpdate(userToEdit, {
            username: uc['loginUser'],
            name: uc['nameUser'],
            email: uc['emailUser'],
            password: uc['passwordUser']
        }, function (err, user) {
            if (err) throw err;
            res.redirect('/manageusers');
        });

    });

}