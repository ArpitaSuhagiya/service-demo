app.controller("registerCtrl",['$scope','$rootScope','$http','$state',
	function($scope,$rootScope,$http,$state){
	
	$scope.user={};
	
	 $scope.text = 'me@example.com';
	$scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
	
	$scope.login= function(){
		$state.go("login");
	}
	$scope.register= function(){
		if(!$scope.registerForm.$valid) {
			$scope.registerForm.$submitted = true;
			alert("Please enter valid details");
			return;
		}
		console.log("$scope.user---------------->", $scope.user);
		$http.post("/service-demo/user/save", $scope.user).then(
				function successCallback(response) {
					if(response.data.status == 200 && response.data.flag == true) {
						alert(response.data.message);
						$state.go("login");
					} else {
						alert(response.data.message);
					}
			    },
			    function errorCallback(response) {
			    	console.log("Unable to perform get request");
			    }
		);
				
		
		
	};

}]);
