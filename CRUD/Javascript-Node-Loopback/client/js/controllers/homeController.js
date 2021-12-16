'use strict';

/* Controllers */

var cdcApp = angular.module('cdcApp', []);

cdcApp.controller('HomeController', function($scope,$http) {
    $http.get('/produtos').success(function(data) {
        $scope.livrosDestaque = data.slice(0,3);
        $scope.outrosLivros = data;
    });
});
