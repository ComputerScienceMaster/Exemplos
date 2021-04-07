'use strict';

module.exports = function (server) {
  var loopback = require('loopback');
  var session = require('express-session');

  // set the view engine to ejs
  server.set('view engine', 'ejs');

  server.use(session({
    secret: "sosecret",
    saveUninitialized: false,
    resave: false
  }));

  server.use(function (req, res, next) {
    res.locals.session = req.session;
    next();
  });

  server.use(function (req, res, next) {
    var sess = req.session;
    if (req.url == "/signin" || 
    req.url.includes('js') || 
    req.url.includes('css')) {
      next();
    } else if(sess.loginUserAccount != null &&
    sess.passwordUserAccount != null) {
      next();
    }else{
      res.redirect('/signin');
    }
  });



}
