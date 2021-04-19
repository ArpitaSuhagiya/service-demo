app.controller("loginCtrl",['$scope','$rootScope','$http','$state',
	function($scope,$rootScope,$http,$state) {
	
	$scope.user = {};
	
	$scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
	
	$scope.register= function(){
		$state.go("register");
	}
	
	$scope.login= function(){
		
		console.log("$scope.loginForm------->", $scope.loginForm.$valid)
		if(!$scope.loginForm.$valid) {
			$scope.loginForm.submitted = true;
			alert("Please enter valid details");
			return;
		}
		
		$http.post("/service-demo/login/login", $scope.user).then(
			function successCallback(response) {
				if(response.data.status == 200 && response.data.flag == true) {
					localStorage.setItem("token", response.data.data.token);
					$state.go("dashboard");
				} else {
					alert(response.data.message);
				}
		    },
		    function errorCallback(response) {
		    	console.log("Unable to perform post request");
		    }
		);
		
	};
	
}]);