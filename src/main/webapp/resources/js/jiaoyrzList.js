angular.module('home', ['ngRoute']).controller('userCtrl', function($scope) {
	 $scope.users = [
					{id:1, fName:'Hege', lName:"Pege" },
					 {id:2, fName:'Kim',  lName:"Pim" },
					{id:3, fName:'Sal',  lName:"Smith" },
					 {id:4, fName:'Jack', lName:"Jones" },
					{id:5, fName:'John', lName:"Doe" },
					{id:6, fName:'Peter',lName:"Pan" }
	];
		
});