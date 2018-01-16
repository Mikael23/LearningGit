angular.module('MyApp', [])
    .controller('MyController', function($scope, $http) {

        $scope.greeting = "Hello World";

        $scope.loadGreeting = function () {
            $http({
                method: 'GET',
                url: 'http://localhost:8088/university/greet'
            }).then(
                function (res) {
                    alert("success");
                    $scope.greeting = res.data;
                },
                function (res) {
                    alert(res.config.url + ' error');
                }
            );
        };

        $scope.loadGreeting()

    });