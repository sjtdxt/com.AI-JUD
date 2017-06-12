var app = angular.module('myApp', [ 'ui.grid', 'ui.grid.edit',
		'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.bootstrap', 'mm.foundation',
		'ngRoute', 'chieffancypants.loadingBar', 'ngAnimate',
		'angularModalService', 'ng-fusioncharts' ]);

// app
// .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
// cfpLoadingBarProvider.includeSpinner = true;
// }])
//
// app
// .config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
// cfpLoadingBarProvider.includeBar = true;
// }])

// app.config(function($routeProvider) {
// $routeProvider.when('/login', {
// templateUrl : 'Login.html',
// controller : 'LoginCtrl'
// });
// $routeProvider.when('/', {
// templateUrl : 'Home.html',
// controller : 'HomeCtrl'
// });
// $routeProvider.otherwise({
// redirectTo : '/'
// });
// });
app.run(function(authentication, $rootScope, $location) {
	$rootScope.$on('$routeChangeStart', function(evt) {
		if (!authentication.isAuthenticated) {
			$location.url("/login");
		}
		event.preventDefault();
	});
})

app.controller('AccordionDemoCtrl', function($scope) {
	$scope.oneAtATime = true;

	$scope.groups = [ {
		title : "Dynamic Group Header - 1",
		content : "Dynamic Group Body - 1"
	}, {
		title : "Dynamic Group Header - 2",
		content : "Dynamic Group Body - 2"
	} ];

	$scope.items = [ 'Item 1', 'Item 2', 'Item 3' ];

	$scope.addItem = function() {
		var newItemNo = $scope.items.length + 1;
		$scope.items.push('Item ' + newItemNo);
	};
});

app.controller('LoginCtrl', function($scope, $http, $location, authentication,
		cfpLoadingBar) {
	$scope.login = function() {
		if ($scope.username === 'admin' && $scope.password === 'pass') {
			console.log('successful')
			authentication.isAuthenticated = true;
			authentication.user = {
				name : $scope.username
			};
			$location.url("/");
		} else {
			$scope.loginError = "Invalid username/password combination";
			console.log('Login failed..');
		}
		;
	};
});

app.controller("uigridCtrl", function($scope) {
	$scope.gridOptions = {
		enableFiltering : true,
		columnDefs : [ {
			field : 'name'
		}, {
			field : 'age'
		}, {
			field : 'location',
			enableFiltering : false
		} ],
		onRegisterApi : function(gridApi) {
			$scope.grid1Api = gridApi;
		}
	};
	$scope.users = [ {
		name : "Madhav Sai",
		age : 10,
		location : 'Nagpur'
	}, {
		name : "Suresh Dasari",
		age : 30,
		location : 'Chennai'
	}, {
		name : "Rohini Alavala",
		age : 29,
		location : 'Chennai'
	}, {
		name : "Praveen Kumar",
		age : 25,
		location : 'Bangalore'
	}, {
		name : "Sateesh Chandra",
		age : 27,
		location : 'Vizag'
	} ];
	$scope.gridOptions.data = $scope.users;
});

app.controller('ModalController', function($scope, close) {

	$scope.close = function(result) {
		console.log('this is closed');
		close(result, 5000); // close, but give 500ms for bootstrap to
		// animate
	};

});

app
		.controller(
				'AuthC',
				function($scope, $modal, $http, authentication, ModalService,
						$window) {
					console.log('AuthC')
					$scope.templates = [ {
						url : 'Login.html'
					}, {
						url : 'Home.html'
					}, {
						url : 'SimilarCase.html'
					}, {
						url : 'businessindex.html'
					}, {
						url : 'CaseDetail.html'
					}, {
						url : 'ReviewPendingList.html'
					} ];
					$scope.template = $scope.templates[0];

					$scope.login = function(username, password) {
						console.log('LoginMethod')
						console.log('Auth')
						// $http.get(
						// 'http://10.234.84.213:7373/com.alm/rest/service/auth/'
						// + username + "splitString" + password)
						// .then(function(response) {
						// console.log(response);
						// if (response.data.data == 'unAuth') {
						// console.log(response);
						// console.log('In');
						// $scope.show();
						// console.log('could not login');
						//
						// } else {
						// console.log(response);
						// $scope.greeting = response;// .data;
						// authentication.isAuthenticated = true;
						// $scope.udata = username;
						// $scope.pdata = password;
						// $scope.template = $scope.templates[1];
						// $scope.user = username;
						// }
						//
						// });

						if (username == 'admin') {
							if (password == 'admin') {
								authentication.isAuthenticated = true;
								$scope.udata = username;
								$scope.pdata = password;
								$scope.user = username;
								$scope.template = $scope.templates[1];
								$http
										.get(
												'http://10.234.84.213:7373/com.AI-JUD/rest/service/getFiles/')
										.then(function(response) {
											var files = [];
											files = response.data;
											$scope.fileList = files;
											// console.log($scope.fileList);
										});

								$http
										.get(
												'http://10.234.84.213:7373/com.AI-JUD/rest/service/getNewCase/')
										.then(function(response) {
											var newFiles = [];
											newFiles = response.data;
											$scope.newFileList = newFiles;
											// console.log($scope.fileList);
										});
								$scope.user = username;
							} else {
								$scope.show();

							}

						} else {

						}

					};

					$scope.backHome = function() {
						$scope.template = $scope.templates[1];
					}
					
					$scope.backBusiness = function() {
						$scope.template = $scope.templates[3];
					}

					$scope.findSimilar = function() {

						$scope.template = $scope.templates[2];
					}

					$scope.show = function() {
						ModalService.showModal({
							templateUrl : 'modal.html',
							controller : "ModalController"
						}).then(function(modal) {
							modal.element.modal();
							modal.close.then(function(result) {
							});
						});
					};
					
					$scope.caseDetails = function(data) {
						var response = $http
						.post(
								'http://10.234.84.213:7373/com.AI-JUD/rest/service/getCaseDetails',
								angular.toJson(data));
				response.success(function(data, status) {
					var ja = angular.toJson(data);
					var aj = angular.fromJson(data);
					console.log('hi');
					console.log(aj);
					$scope.datas=aj[0];
					console.log($scope.datas);
					$scope.template = $scope.templates[4];

				});

				response.error(function(data, status) {
					alert("AJAX failed to get data, status=" + status,
							scope);

				})


					}

					$scope.reviewPendingList = function(data) {

						var response = $http
								.post(
										'http://10.234.84.213:7373/com.AI-JUD/rest/service/getBasic',
										angular.toJson(data));
						response.success(function(data, status) {
							var ja = angular.toJson(data);
							var aj = angular.fromJson(ja);

							console.log('hi');
							console.log(aj);
							$scope.newcase = aj[0];
							$scope.totalcase = aj[1];
							$scope.indi = aj[2];
							$scope.notindi = aj[3];
							// $scope.template = $scope.templates[3];

						});

						response.error(function(data, status) {
							alert("AJAX failed to get data, status=" + status,
									scope);

						})

						// Call service

						var response = $http
								.post(
										'http://10.234.84.213:7373/com.AI-JUD/rest/service/getTop3',
										angular.toJson(data));
						response.success(function(data, status) {
							var ja = angular.toJson(data);
							var aj = angular.fromJson(ja);

							console.log('hitop3');
							console.log(aj);

							$scope.topcase1name = aj[0].name;
							var res1 = aj[0].steps.split("%_%");
							$scope.topcase1step = res1;
							console.log(res1);
							$scope.topcase2name = aj[1].name;
							var res2 = aj[1].steps.split("%_%");
							$scope.topcase2step = res2;
							
							$scope.topcase3name = aj[2].name;
							var res3 = aj[2].steps.split("%_%");
							$scope.topcase3step = res3;

							// $scope.template = $scope.templates[3];

						});

						response.error(function(data, status) {
							alert("AJAX failed to get data, status=" + status,
									scope);

						})

						// Call Service

						var response = $http
								.post(
										'http://10.234.84.213:7373/com.AI-JUD/rest/service/getPendingChecklistDetails',
										angular.toJson(data));
						response.success(function(data, status) {
							var ja = angular.toJson(data);
							var aj = angular.fromJson(ja);

							$scope.myDataSource = {
								chart : {
									caption : "Case similarity Percentage",
									subCaption : "Top similar cases",
								},
								data : [ {
									label : "30%-40%",
									value : aj[0]
								}, {
									label : "40%-50%",
									value : aj[1]
								}, {
									label : ">50%",
									value : aj[2]
								} ]
							};

							// c

							// $scope.template = $scope.templates[3];

						});

						response.error(function(data, status) {
							alert("AJAX failed to get data, status=" + status,
									scope);

						})

						// call service

						// call service

						var response = $http
								.post(
										'http://10.234.84.213:7373/com.AI-JUD/rest/service/getSimilarResult',
										angular.toJson(data));
						response
								.success(function(data, status) {
									var ja2 = angular.toJson(data);
									var aj2 = angular.fromJson(ja2);
									console.log(aj2);

									var array = [];
									for (var i = 0; i < aj2.length; i++) {
										var obj = {};

										obj.outcome = aj2[i].outcome;
										obj.summary = aj2[i].summary;
										obj.label = aj2[i].label;

										array.push(obj);
									}
									console.log(array);
									$scope.gridOptions = {
										enableCellEditOnFocus : true,
										rowEditWaitInterval : -1
									};

									$scope.gridOptions.columnDefs = [ {
										name : 'outcome',
										sort : {
											direction : 'asc',
											priority : 0
										},
										enableCellEdit : false,
										displayName : 'Outcome',
										width : '30%'
									}, {
										name : 'summary',
										enableCellEdit : false,
										displayName : 'Summary ',
										width : '100%'
									}, {
										name : 'label',
										enableCellEdit : false,
										displayName : 'Label',
										width : '100%'
									}

									];

									$scope.msg = {};

									$scope.gridOptions.onRegisterApi = function(
											gridApi) {
										// set gridApi on scope
										$scope.gridApi = gridApi;
										gridApi.edit.on
												.afterCellEdit(
														$scope,
														function(rowEntity,
																colDef,
																newValue,
																oldValue) {
															$scope.msg.lastCellEdited = 'edited row id:'
																	+ rowEntity.id
																	+ ' Column:'
																	+ colDef.name
																	+ ' newValue:'
																	+ newValue
																	+ ' oldValue:'
																	+ oldValue;
															$scope.$apply();
															console
																	.log($scope.msg.lastCellEdited);

														});
									};

									var d = angular.toJson(array);
									$scope.gridOptions.data = d;
									$scope.template = $scope.templates[3];
								});

						response.error(function(data, status) {
							alert("AJAX failed to get data, status=" + status,
									scope);

						})

						// call service

					}

					$scope.getDirtyRows = function() {
						var flag = false;
						var dirtyRows = $scope.gridApi.rowEdit
								.getDirtyRows($scope.gridApi.grid);

						var allRows = $scope.gridApi.core
								.getVisibleRows($scope.gridApi.grid);

						for (var i = 0; i < allRows.length; i++) {
							if (allRows[i].entity.IntroducedBy == "") {
								$scope.show();
								flag = true;
								break;
							}
						}

						if (!flag) {
							var rows = [];
							var oldDef = $scope.defects;

							for (var i = 0; i < dirtyRows.length; i++) {
								var row = {};

								row.id = dirtyRows[i].entity.ID;
								row.name = dirtyRows[i].entity.Name;
								row.status = dirtyRows[i].entity.status;
								row.priority = dirtyRows[i].entity.Priority;
								row.introducedBy = dirtyRows[i].entity.IntroducedBy;

								for (var j = 0; j < oldDef.length; j++) {
									if (oldDef[j].id == dirtyRows[i].entity.ID) {
										row.summary = oldDef[j].summary;
										row.developmentWorkstream = oldDef[j].developmentWorkstream;
										row.detectedInDrop = oldDef[j].detectedInDrop;
										row.phase = oldDef[j].phase;
										row.resolution = oldDef[j].resolution;
										row.assignedToName = oldDef[j].assignedToName;
										row.assignedTo = oldDef[j].assignedTo;
										row.comments = oldDef[j].comments;
										row.severity = oldDef[j].severity;
										row.detectedEnv = oldDef[j].detectedEnv;
										row.detectedInVersion = oldDef[j].detectedInVersion;
										row.closedInVersion = oldDef[j].closedInVersion;
										row.cycleID = oldDef[j].cycleID;
										row.detectedBy = oldDef[j].detectedBy;
										row.detectedOnDate = oldDef[j].detectedOnDate;
										row.actualFixTime = oldDef[j].actualFixTime;
										row.estimatedDateRetest = oldDef[j].estimatedDateRetest;
										row.closingDate = oldDef[j].closingDate;
										break;
									}
								}

								rows.push(row);
							}

							var jsonRows = angular.toJson(rows);
							console.log(rows);
							console.log(jsonRows);

							var response = $http
									.post(
											'http://10.234.84.213:7373/com.alm/rest/service/addDef',
											jsonRows);
							response.success(function(data, status) {
								console.log("Inside create operation..."
										+ angular.toJson(data, false)
										+ ", status=" + status);
							});

							response.error(function(data, status) {
								alert("AJAX failed to get data, status="
										+ status, scope);

							})

						}

					}

				});

app.controller('AppCtrl', function($scope, $http, authentication, $window) {
	console.log('AppCtrl')
	$scope.templates = [ {
		url : 'Login.html'
	}, {
		url : 'Home.html'
	} ];
	$scope.template = $scope.templates[0];
	$scope.login = function(username, password) {
		console.log('LoginMethod')
		if (username === 'admin' && password === '1234') {
			authentication.isAuthenticated = true;
			$scope.template = $scope.templates[1];
			$scope.user = username;
		} else {
			$scope.loginError = "Invalid username/password combination";
		}
		;
	};

});

app.controller('HomeCtrl', function($scope, authentication) {
	$scope.user = authentication.user.name;

});

app.factory('authentication', function() {
	return {
		isAuthenticated : false,
		user : null
	}
});