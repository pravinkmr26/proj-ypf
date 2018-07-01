<%-- 
    Document   : TestModules
    Created on : 16 Feb, 2018, 10:05:18 AM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tester Page</title>
        <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js"></script>
        <!--<script src="resources/Angular/angular.min.js" type="text/javascript"></script>-->
        <!--<script src="resources/Jquery/jquery-3.2.1.js" type="text/javascript"></script>-->
        <script>
            
angular.module('tester', []).controller('buttons', function ($scope, $http) {

                //--------------------------------------------------------------------------------function1
                $scope.submit = function (urllink) {
                    $http({
                        url: urllink.toString(),
                        method: 'GET'
                    }).then(function (result) {
                        alert("sucess " + result.data);
                    }, function (result) {
                        alert("fail " + result.data);
                    });
                };
                //-------------------------------------------------------------------------------function2
                $scope.getPost = function () {
                    $http({
                    url:'/getMongoPost',
                            method:'GET'
                    }).then(function(result){
                    alert("success");
                            var x = result.data;
                            var element = document.getElementById("CommentBlock");
                            var divComment = angular.element(element);
                            divComment.append("<br>Content type " + x.contentType);
                            divComment.append("<br>postId " + x.postId);
                            var commentArray = x.comment;
                            for(var i = 0; i < commentArray.length;i++){
                    divComment.append('<div class="comment">time: ' + commentArray[i].time + ' </div><br>');
                            divComment.append('<div class="comment">has Images ' + commentArray[i].hasImages + ' </div><br>');
                            divComment.append('<div class="comment"> comment ' + commentArray[i].comment + ' </div><br>');
                            divComment.append('<div class="comment"> userId ' + commentArray[i].userId + ' </div><br>');
                    }
                    }, function(result){
                    var x = result.data;
                            alert(x);
                    });
                    };
                });
        </script>

    </head>
    <body ng-app="tester">
        <div ng-controller="buttons">
            <input type="button" ng-click="submit('/updatePostMongo')" value="insert Coment on Mongo">
            <input type="button" ng-click="getPost()" value="insert Coment on Mongo">
            <div id="CommentBlock">

            </div>
        </div>
    </body>
</html>
