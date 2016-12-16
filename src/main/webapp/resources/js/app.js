/**
 * 
 */
var BcsApp = angular.module('BcsApp',['ngRoute']);

BcsApp.config(['$routeProvider' , function($routeProvider) {
	
	$routeProvider.when('/', { 
		controller: 'LoginController', 
		templateUrl: 'login.html', 
	})
	.when('/home',{
		controller: 'HomeController', 
		templateUrl: 'pages/home.html',
	})
	.when('/quotation',{
		controller: 'QuotationController', 
		templateUrl: 'pages/quotation.html',
	})
	.when('/transactionRep',{
		controller: 'TransactionRepController', 
		templateUrl:'pages/transaction_rep.html',
	})
    .when('/transactionLog',{
    	controller: 'TransactionLogController', 
    	templateUrl:'pages/transaction_log.html'
    })
    .otherwise({redirectTo:'/'});
}]);
