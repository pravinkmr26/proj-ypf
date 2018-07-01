/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var app = angular.module("UserHome",[]);
app.controller("uploadController",function($scope){ 
    var form = document.forms[0];
    var varformData = new FormData(form);
    $scope.formData={};
    $scope.formData.caption="algular is on mark";
    $scope.submit = function($scope,$http){
        alert("request Submitted");
        $http.post({
            enctype:"multipart/form-data"
            ,url:'/savePost'
            ,data:varformData
            ,headers:{'Content-type':undefined,'cache':false}
        }).then(function(response){
            $scope.message = "upload success "+response;
            $('#postProgress').html("loading");
        },function(response){
            $scope.message = "update failed "+response;
            $('#postProgress').html("loading");
        });
    };
});


var app = angular.module('plunkr', [])
  app.controller('UploadController', function($scope, fileReader) {
    $scope.imageSrc = "";
    
    $scope.$on("fileProgress", function(e, progress) {
      $scope.progress = progress.loaded / progress.total;
    });
  });




  app.directive("ngFileSelect", function(fileReader, $timeout) {
    return {
      scope: {
        ngModel: '='
      },
      link: function($scope, el) {
        function getFile(file) {
          fileReader.readAsDataUrl(file, $scope)
            .then(function(result) {
              $timeout(function() {
                $scope.ngModel = result;
              });
            });
        }

        el.bind("change", function(e) {
          var file = (e.srcElement || e.target).files[0];
          getFile(file);
        });
      }
    };
  });

app.factory("fileReader", function($q, $log) {
  var onLoad = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.resolve(reader.result);
      });
    };
  };

  var onError = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.reject(reader.result);
      });
    };
  };

  var onProgress = function(reader, scope) {
    return function(event) {
      scope.$broadcast("fileProgress", {
        total: event.total,
        loaded: event.loaded
      });
    };
  };

  var getReader = function(deferred, scope) {
    var reader = new FileReader();
    reader.onload = onLoad(reader, deferred, scope);
    reader.onerror = onError(reader, deferred, scope);
    reader.onprogress = onProgress(reader, scope);
    return reader;
  };

  var readAsDataURL = function(file, scope) {
    var deferred = $q.defer();

    var reader = getReader(deferred, scope);
    reader.readAsDataURL(file);

    return deferred.promise;
  };

  return {
    readAsDataUrl: readAsDataURL
  };
});

//---------------------------------------------
var app = angular.module("Blog",[]);
app.controller("UploadBox",function($scope,$http){
    $scope.title="Tesing from angular";
    $scope.submitPost = function(){
        alert('got request');
        event.preventDefault();
        var form = new FormData(document.getElementById('createPost'));
        $http.post('/submitBlogPost',form,{
            enctype:"multipart/form-data"
            ,headers:{'Content-type':undefined,'cache':false}
        }).then(function(response){
            $scope.message = "upload success "+response;
            $('#postProgress').html("loading");
        }),(function(response){
            $scope.message = "update failed "+response;
            $('#postProgress').html("loading");
        });
    };
});

