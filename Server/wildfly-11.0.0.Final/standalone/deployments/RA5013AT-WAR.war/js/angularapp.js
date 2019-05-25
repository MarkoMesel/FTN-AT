var app = angular.module("angApp", []);

    app.controller("angCtrl", function($scope) {
        $scope.book = "The Alchemist";
        $scope.author = "Paulo Coelho";
    });