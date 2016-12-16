/**
 * 
 */
BcsApp.controller('LoginController', ['$rootScope', '$scope', '$http','$location', function($rootScope, $scope,$http,$location) {
	$scope.login=function(){
		$location.path("quotation");
	};
}]);

