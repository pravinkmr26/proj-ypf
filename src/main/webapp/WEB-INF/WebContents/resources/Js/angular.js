/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//----------------------------------------------------------------------------Blog upload form

var app = angular.module('Blog', []);
app.controller('commentController',function($scope){
    $('#CommentBlock').hide();
    $scope.PostComment = function(){
        $scope.comment = "testing connetivity";
        $('#CommentBlock').toggle();
    };
});
app.directive('ngFiles', ['$parse', function ($parse) {

            function fn_link(scope, element, attrs) {
                var onChange = $parse(attrs.ngFiles);
                element.on('change', function (event) {
                    onChange(scope, { $files: event.target.files });
                });
            };

            return {
                link: fn_link
            };
        } ]);
app.controller('fupController', function ($scope, $http) {

            var formdata = new FormData();
            $scope.getTheFiles = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append(key, value);
                });
            };

            // NOW UPLOAD THE FILES.
            $scope.uploadFiles = function () {

                var request = {
                    method: 'POST',
                    url: '/api/fileupload/',
                    data: formdata,
                    headers: {
                        'Content-Type': undefined
                    }
                };

                // SEND THE FILES.
                $http(request)
                    .success(function (d) {
                        alert(d);
                    })
                    .error(function () {
                    });
            };
        });