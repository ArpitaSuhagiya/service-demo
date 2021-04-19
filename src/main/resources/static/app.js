var app = angular.module("app", [ 'ui.router', 'ui.bootstrap', 'ngTable' ]);
app.config([ "$stateProvider", "$urlRouterProvider",
		function($stateProvider, $urlRouterProvider) {
			$stateProvider.state("login", {
				url : '/login',
				templateUrl : 'apps/login/login.html',
				controller : 'loginCtrl',
				controllerAs: 'vm'
			})
			.state("register", {
				url : '/register',
			templateUrl : 'apps/register/register.html',
			controller : 'registerCtrl'
			})
			.state("dashboard", {
				url : '/dashboard',
				templateUrl : 'apps/dashboard/dashboard.html',
				controller : 'dashboardCtrl'
			});
			$urlRouterProvider.otherwise("login");
		} ]);