module.exports = function (app) {

  app.get("/signup", function (req, res) {
    res.render('signup');
  });

  app.post("/signup", function (req, res) {
    var uc = req.body;
    console.log(uc);
    var useraccount = app.models.UserAccount;
    useraccount.create({
      loginUserAccount: uc['loginUser'],
      passwordUserAccount: uc['passwordUser'],
      nameUserAccount: uc['nameUser'],
      emailUserAccount: uc['emailUser']
    }).then(function () {
      res.redirect('index');
    })
  });

  app.get("/manageusers", function (req, res) {
    var useraccount = app.models.UserAccount;
    useraccount.find().then(function (useraccounts) {
      res.render("usermodule/manageusers", {
        user: useraccounts
      })
    });
  });

  app.post("/delete", function (req, res) {
    var idtodelete = req.body['usertodelete'];
    var useraccount = app.models.UserAccount;
    console.log(idtodelete);
    useraccount.destroyAll({
      id: idtodelete
    }).then(function () {
      res.redirect('/manageusers');
    })

  });

  app.get("/edit", function (req, res) {
    var sess = req.session;
    var idUserAccount = sess.idUserAccount;
    var useraccount = app.models.UserAccount;
    useraccount.find({
      where: {
        id: idUserAccount
      }
    }).then(function (useraccounts) {
      res.render("usermodule/edit",{
        user: useraccounts
      })
    });
  });

  app.post("/edit", function (req, res) {
    var useraccount = app.models.UserAccount;
    var userToEdit = req.body['usertoedit'];
    var uc = req.body;

    useraccount.updateAll({
      id: userToEdit
    }, {
      id: userToEdit,
      loginUserAccount: uc['loginUser'],
      passwordUserAccount: uc['passwordUser'],
      nameUserAccount: uc['nameUser'],
      emailUserAccount: uc['emailUser']
    }).then(function () {
      res.redirect('/dashboard');
    })
  });

}
