/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var userHome = angular.module('userHome', []);
userHome.controller('foregroundController', function ($scope, $timeout,$http, $compile, $rootScope) {
    $scope.viewImage = function (element) {
        var reader = new FileReader();
        reader.readAsDataURL(element.files[0]);
        $timeout(function () {
            loader(reader);
        }, 2000);
    };
    function loader(reader) {
        var img = document.createElement('img');
        img.src = reader.result;
        console.log("filename" + img.name + " file src " + img.src + "result" + reader.result);
        $scope.popupDP(img);
    }

    $rootScope.closeModel = function () {
        angular.element(document.getElementById('model')).html('');
    };
    $rootScope.popupModelWithDp = function(){
        var url = document.getElementById('dpContainer').style.backgroundImage;
        var x = url.split("/");
        var userId_url = x[2].split(")")[0];
        userId_url = userId_url.slice(0,-1);
               
        var first = x[1];
          var string = '<div class="modal is-active" style="margin-top:0px;"><div class="modal-background"></div><div class="modal-content fixImage-model">';
        string += '<p class="image"><img src="/getDP/' +userId_url + '" class="fixImage-model" alt="model popup"></p></div><button class="modal-close is-large" onclick=angular.element(this).scope().closeModel()  aria-label="close"></button></div>';
        var ref = angular.element(document.getElementById('model')).html(string);
        $compile(ref)($scope);
    };
    $rootScope.popupDP = function (image) {
        var string = '<div class="modal is-active" style="margin-top:0px;"><div class="modal-background"></div><div onclick="angular.element(this).scope().uploadDP()" class="modal-content fixImage-model"><input class="btn button btn-lg btn-primary fix-upload-button" type="button" value="upload"/><input onclick="angular.element(this).scope().closeModel()" class="btn button btn-lg btn-primary fix-upload-button" style="right:0;" type="button" value="cancel"/>';
        string += '<p class="image"><img src="' + image.src + '" class="fixImage-model" alt="model popup"></p></div><button class="modal-close is-large" onclick=angular.element(this).scope().closeModel()  aria-label="close"></button></div>';
        var ref = angular.element(document.getElementById('model')).html(string);
        $compile(ref)($scope);
    };
    $rootScope.popupModel = function (image) {
        var string = '<div class="modal is-active" style="margin-top:0px;"><div class="modal-background"></div><div class="modal-content fixImage-model">';
        string += '<p class="image"><img src="' + image.src + '" class="fixImage-model" alt="model popup"></p></div><button class="modal-close is-large" onclick=angular.element(this).scope().closeModel()  aria-label="close"></button></div>';
        var ref = angular.element(document.getElementById('model')).html(string);
        $compile(ref)($scope);
    };
    $rootScope.uploadDP = function(){
        $http({
            method: 'POST',
            url: '/setDP',
            data:new FormData(document.getElementById('dp-form')),
            enctype:'multipart/form-data',
            headers:{
                'process-Data': false,
                'content-Type': undefined,
                'cache': false
            }
        }).then(function (result) {
            console.log('success uploading dp'+result);
        }).catch(function (result) {
             console.log('error uploading dp'+result);
        }).finally(function () {
            console.log('exit from uploading dp');
            $rootScope.closeModel();
            $rootScope.refreshDP();
            console.log('invoked refresh dp()');
        });
    };
    $rootScope.refreshDP = function(){
      var url = 'url(\'/getDP/'+document.getElementById('userId').value+'?ver='+ Math.random() +'\')';
      $('#dpContainer').css('background-image',url);  
    };
});
userHome.controller('userHomeParent', function ($rootScope, $scope, $http, $compile) {
    $rootScope.gotoYourContributions = function () {
        $http({
            method: 'GET',
            url: '/contributions'
        }).then(function (result) {


        }).catch(function (result) {

        }).finally(function () {

        });
    };
    $rootScope.gotoAbout = function () {

    };
    $rootScope.loadTimeline = function () {

    };
    $rootScope.loadFolloers = function () {

    };
});


userHome.controller('postUploader', function ($rootScope, $scope, $http) {
    var element = document.getElementById('CreatePost');
    $rootScope.submit = function () {
        $http({
            url: '/savePost',
            method: 'POST',
            data: new FormData(element),
            enctype: 'multipart/form-data',
            headers: {
                'process-Data': false,
                'content-Type': undefined,
                'cache': false
            }
        }).then(function (result) {
            console.log("at the then block code ajax upload post");
            $scope.result = "upload success" + result.data + "" + result.status;
        }).catch(function (error) {
            console.log("at the catch block code ajax upload post");
            $scope.result = "upload Error" + error.status + "" + error.data;
        }).finally(function () {
            console.log("at the fianlly block code ajax upload post");
            $('input[type=file]').val('');
            $('textarea').val('');
            $('#submitP').prop('disabled', false);
            $('#UploadSucess').css('display', 'inline');
            //  $('#modal').end();
            $('#modal').fadeOut(5000, function () {
                $('#modal').modal('hide');
            });
            var ref = $scope.loadPosts();
            $compile(ref)($scope);

        });
    };
});
userHome.controller('leftPaneController', function ($scope, $http) {

});
userHome.controller('postContainer', function ($rootScope, $scope, $http, $compile) {
//    var userId = document.getElementById('userId').value;
//    var url = '/loadPost/' + userId;
//    $http({url: url, method: 'GET'}).then(function (result) {
//        var container = angular.element(document.getElementById('ContentPane'));
//        var postArray = result.data;
//        var html = "";
//        container.html(html);
//        for (var i = 0; i < postArray.length; i++) {
//            html += '<div class="postContainer">';
//            html += '<p>' + postArray[i].title + '</p>';
//            html += '<sub>' + postArray[i].time + '</sub>';
//            if (postArray[i].hasImage > 0) {
//                html += '<div class="imageContainer">';
//                for (var j = 0; j < postArray[i].imageId.length; j++)
//                {
//                    html += '<img src="/showImage/' + postArray[i].imageId[j] + '" class="fixImage width30Cent height15Cent"/>';
//                }
//                html += '</div></div>';
//            }
//        }
//        container.html(html);
//    });
    $rootScope.loadPosts = function () {
        var userId = document.getElementById('userId').value;
        var url = '/loadPost/' + userId;
        $http({url: url, method: 'GET'}).then(function (result) {
            var container = angular.element(document.getElementById('ContentPane'));
            var postArray = result.data;
            var html = "";
            container.html(html);
            html = "<div class=\"row\">";
            for (var i = 0; i < postArray.length; i++) {
                html += '<div class="col-md-4 postContainer">';
                html += '<p>' + postArray[i].title + '</p>';
                html += '<sub>' + postArray[i].time + '</sub>';
                if (postArray[i].hasImage > 0) {
                    html += '<div class="imageContainer" onclick=angular.element(this).scope().popupModel(this.childNodes[0])>';
                    for (var j = 0; j < postArray[i].imageId.length; j++)
                    {
                        html += '<img src="/showImage/' + postArray[i].imageId[j] + '" class="fixImage height15Cent"/>';
                    }
                    html += '</div></div>';
                }
            }
            html += "</div>";
            var ref = container.html(html);
            return ref;
//            $compile(ref)($scope);
        });
    };
    $scope.loadPosts();
    $scope.toBase64 = function (val) {
        var binary = "";
        var bytes = new Uint8Array(val);
        var length = bytes.byteLength;
        for (var k = 0; k < length; k++) {
            binary += String.fromCharCode(bytes[k]);
        }
        return btoa(binary);
    };
});



/*   $http({
 method: 'GET',
 url: '/loadImage'
 }).then(function (result) {
 document.getElementById('testBannerDiv').setAttribute("style", "background-image:url('" + "data:" + result.data.contentType + ";base64," + btoa(String.fromCharCode.apply(null, new Uint8Array(result.data.image))) + "')");
 }, function (result) {
 console.log("error at ajax getting banner" + result.data);
 });
 $scope.trigger = function () {
 document.getElementById('testBannerDiv').setAttribute("style", "background-image:url('/resources/Images/Img4.jpg')");
 };
 });
 */