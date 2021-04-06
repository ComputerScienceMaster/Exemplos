var express = require('express');
var load = require('express-load');
var bodyParser = require('body-parser');
var expressValidator = require('express-validator');

module.exports = function() {

    var app = express();
    app.use(express.static('./public'));
    app.set('view engine', 'ejs');
    app.set('views','./app/views');

    app.use(bodyParser.urlencoded({extended: true}));
    app.use(bodyParser.json());
    app.use(expressValidator());

    load('routes',{cwd:'app',verbose:true})
        .then('infra')
        .into(app);

    app.use(function(req, res, next){
        res.status(404).render("erros/404");
    });

    app.use(function(error,req, res, next){
        res.status(500).render("erros/500");
    });
    //tem que colocar na ordem, caso contrário ele passa pelo middleware e ainda não vai ter acontecido nenhum erro.

    return app;
};
