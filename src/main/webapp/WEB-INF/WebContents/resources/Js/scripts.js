/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module('Login',[]);
app.controller('LoginController',function($scope,$http){
    $scope.Login = function(){
        var request = {
            method:'POST',
            data :{'userId':$scope.userId,'password':$scope.password},
            url : '/signIn',
            headers:{
                'content-Type':undefined
            }
        };
      $http(request).then(function(result){
          window.location('/login');
      },function(result){
           alert('Error in authentication! Please try again with correct details.'+result);
      });  
    };
});
