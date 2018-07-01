/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.moduel('UserHome',[]);
app.controller('leftPaneController',function($http,$compile,$scope){
    $scope.getTrendingPost = function(){};
    $http({
        url:'/getTrendingPosts',
        method:'GET'
    }).then(function(){
        
    }).catch(function(){
        
    }).finally(function(){
        
    });
});
