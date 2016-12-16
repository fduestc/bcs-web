/**
 * 
 */
var BCSApp = angular.module('BCSApp',['ngRoute']);

BCSApp.config(['$routeProvider' , function($routeProvider) {
	
	$routeProvider.when('/', { 
		controller: 'LoginController', 
		templateUrl: 'login.html', 
	})
	.when('/home',{
		controller: 'HomeController', 
		templateUrl: 'pages/home.html',
	})
	.when('/baojhq',{
		controller: 'BaojhqController', 
		templateUrl: 'pages/baojhq.html',
	})
	.when('/jiaoyyd',{
		controller: 'JiaoyydController', 
		templateUrl:'pages/jiaoyyd.html',
	})
    .when('/jiaoyrz',{
    	controller: 'JiaoyrzController', 
    	templateUrl:'pages/jiaoyrz.html'
    })
    .otherwise({redirectTo:'/'});
}]);
