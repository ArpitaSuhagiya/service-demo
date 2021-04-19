app.controller("dashboardCtrl",['$scope','$rootScope','$http','$state','NgTableParams',
	function($scope,$rootScope,$http,$state,NgTableParams){
	
	$scope.product={};
	$scope.isShowForm = false;
	
	$scope.create= function(){
		$scope.product = {};
		$scope.isShowForm = true;
		
	};
	
	$scope.token = localStorage.getItem("token");
	if($scope.token == undefined || $scope.token == '' || $scope.token == null) {
		$state.go("login");
	}
	
	$scope.edit= function(data){
		$scope.product = angular.copy(data);
		$scope.isShowForm = true;
	};
	
	$scope.saveOrUpdate = function(){
		if(!$scope.dashForm.$valid) {
			$scope.dashForm.$submitted = true;
			alert("Please enter valid details");
			return;
		}
		$http({method: 'POST', url : "/service-demo/product/saveOrUpdate", data : $scope.product, headers : {"token" : $scope.token}}).then(
				function successCallback(response) {
					if(response.data.status == 200 && response.data.flag == true) {
						alert(response.data.message);
						$scope.product = {};
						$scope.isShowForm = false;				
						$scope.getAllProduct();
					} else if(response.data.status == 401) { 
						alert("Token Is Expired !!");
						$state.go("login");
				    } else {
						alert(response.data.message);
					}
			    },
			    function errorCallback(response) {
			    	console.log("Unable to perform get request");
			    }
		);
	}
	
	$scope.deleteProduct = function(id){
		$http({method: 'GET', url : "/service-demo/product/deleteById/" + id, headers : {"token" : $scope.token}}).then(
				function successCallback(response) {
					
					if(response.data.status == 200 && response.data.flag == true) {
						alert(response.data.message);
						// get list method
						$scope.getAllProduct();
					}else if(response.data.status == 401) { 
						alert("Token Is Expired !!");
						$state.go("login");
				    } else {
						alert(response.data.message);
					}
			    },
			    function errorCallback(response) {
			    	console.log("Unable to perform get request");
			    }
		);
	}
	
	$scope.getAllProduct = function(){
		$http({method: 'GET', url : "/service-demo/product/getAll", headers : {"token" : $scope.token}}).then(
				function successCallback(response) {
					if(response.data.status == 200 && response.data.flag == true) {
						$scope.productList = response.data.data;
						console.log("$scope.productList-------->", $scope.productList)
						$scope.tableParams = 
							new NgTableParams({count : 10},{dataset: response.data.data});
					}else if(response.data.status == 401) { 
						alert("Token Is Expired !!");
						$state.go("login");
				    } else {
						alert(response.data.message);
					}
			    },
			    function errorCallback(response) {
			    	console.log("Unable to perform get request");
			    }
		);
	}
	$scope.getAllProduct();
	
	$scope.logout = function(){
		$http({method: 'GET', url : "/service-demo/login/logout", headers : {"token" : $scope.token}}).then(
				 
				function successCallback(response) {
					localStorage.removeItem("token");
					$state.go("login");
					if(response.data.status == 401) { 
						alert("Token Is Expired !!");
						$state.go("login");
				    }
			    },
			    function errorCallback(response) {
			    	console.log("Unable to perform get request");
			    }
		);
	}
	
}]);