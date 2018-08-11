var app = angular.module("myApp",['ui.router']);


app.config(function ($stateProvider,$urlRouterProvider) {
    $stateProvider.state("entreprises",{
        url:"/entreprises",
        templateUrl:"views/entreprises.html",
        controller: "myController"
    });
    $stateProvider.state("taxes",{
        url:"/taxes",
        templateUrl:"views/taxes.html",
        controller: "taxeController"
    });
    $stateProvider.state("ajoutTaxe",{
        url:"/ajoutTaxe",
        templateUrl:"views/ajoutTaxe.html",
        controller: "taxeController"
    });
    $stateProvider.state("ajoutEntreprise",{
        url:"/ajoutEntreprise",
        templateUrl:"views/ajoutEntreprise.html",
        controller: "myController"
    });
    $urlRouterProvider.otherwise("/entreprises");
})

//Taxes controller
app.controller("taxeController",function ($scope,$http) {
    $scope.pageTaxes = [];
    $scope.listeEntreprises = [];
    $scope.pages = [];
    $scope.pageCourante = 0;
    $scope.size = 5;
    $scope.motcle = "";
    $scope.listerallTaxes = function () {
        $http.get("http://localhost:8087/listeTaxes?page="+$scope.pageCourante+"&size="+$scope.size+"&motcle="+$scope.motcle+"")
            .then(function success(response) {
                    $scope.pageTaxes = response.data;
                    $scope.pages = new Array(response.data.totalPages);
                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        $scope.errorMessage = 'Taxe not found!';
                    }
                    else {
                        $scope.errorMessage = "Error getting Taxe!";
                    }
                });
    }

    $scope.listerallEntreprises = function () {
        $http.get("http://localhost:8087/listeEntreprises")
            .then(function success(response) {
                    $scope.listeEntreprises = response.data;
                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        $scope.errorMessage = 'Taxe not found!';
                    }
                    else {
                        $scope.errorMessage = "Error getting Taxe!";
                    }
                });
    }

    //lister toutes les taxes au chargement
    $scope.listerallTaxes();

    //lister toutes les taxes au chargement
    $scope.listerallEntreprises();

    $scope.gotopage = function (index) {
        $scope.pageCourante = index;
        $scope.listerallTaxes();
    }
    $scope.resize = function (index) {
        $scope.size = index;
        $scope.pageCourante = 0;
        $scope.listerallTaxes();
    }

});

//Entreprise controller
app.controller("myController",function ($scope,$http) {
    $scope.pageEntreprises = [];
    $scope.pages = [];
    $scope.pageCourante = 0;
    $scope.size = 5;
    $scope.motcle = "";
/*
    $http.get("http://localhost:8087/listeEntreprises?page="+$scope.pageCourante+"&size="+$scope.size)
        .then(function success(response) {
                $scope.pageEntreprises = response.data;
                $scope.pages = new Array(response.data.totalPages);
            },
            function error(response) {
                $scope.message = '';
                if (response.status === 404) {
                    $scope.errorMessage = 'Entreprise not found!';
                }
                else {
                    $scope.errorMessage = "Entreprise getting user!";
                }
            });
*/

    $scope.listeEntreprises = function () {
        $http.get("http://localhost:8087/pageEntreprises?motcle=" + $scope.motcle + "&page=" + $scope.pageCourante + "&size=" + $scope.size)
            .then(function success(response) {
                    $scope.pageEntreprises = response.data;
                    $scope.pages = new Array(response.data.totalPages);
                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        $scope.errorMessage = 'User not found!';
                    }
                    else {
                        $scope.errorMessage = "Error getting user!";
                    }
                });
    }

    //cherger les entreprises au démarrage
    $scope.listeEntreprises();

    $scope.chercher = function(){
        $scope.pageCourante = 0;
        $scope.listeEntreprises();
    }
    $scope.gotopage = function (index) {
        $scope.pageCourante = index;
        $scope.listeEntreprises();
    }
    $scope.resize = function (index) {
        $scope.size = index;
        $scope.pageCourante = 0;
        $scope.listeEntreprises();
    }

});