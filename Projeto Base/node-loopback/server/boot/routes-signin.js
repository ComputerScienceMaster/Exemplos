module.exports = function (app) {
  app.get("/signin", function (req, res) {
    res.render('signin');
  });

  app.get("/dashboard", function (req, res) {
    res.render('usermodule/dashboard');
  });


  app.get("/signout", function (req, res) {
    var sess = req.session;
    sess.destroy;
    res.render('logoutmessage');
  });

  app.post("/signin", function (req, res) {
    var body = req.body;
    var useraccount = app.models.UserAccount;
    var sess = req.session;
    useraccount.find({
      where: {
        loginUserAccount: body['loginUser'],
      }
    }).then(function (userfound) {

      if (userfound[0] == null) {
        res.render('erros/loginfail');
      } else {
        if (userfound[0].loginUserAccount != null ||
          userfound[0].passwordUserAccount != null) {
          if (userfound[0].loginUserAccount == body['loginUser'] &&
            userfound[0].passwordUserAccount == body['passwordUser']) {
            sess.idUserAccount = userfound[0].id;
            sess.loginUserAccount = userfound[0].loginUserAccount;
            sess.passwordUserAccount = userfound[0].passwordUserAccount;
            sess.nameUserAccount = userfound[0].nameUserAccount;
            sess.emailUserAccount = userfound[0].emailUserAccount;
            res.render('usermodule/dashboard');
          } else {
            res.render('erros/loginfail');
          }
        }
      }
    });
  });
}
