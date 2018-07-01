/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
















var BlogApp = angular.module('Blog', []);

BlogApp.directive('compile', ['$compile', function ($compile) {
    return function(scope, element, attrs) {
      scope.$watch(
        function(scope) {
          // watch the 'compile' expression for changes
          return scope.$eval(attrs.compile);
        },
        function(value) {
          // when the 'compile' expression changes
          // assign it into the current DOM
          element.html(value);

          // compile the new DOM and link it to the current
          // scope.
          // NOTE: we only compile .childNodes so that
          // we don't get into infinite loop compiling ourselves
          $compile(element.contents())(scope);
        }
    );
  };
}]);

//-------- 0----------------------------------------------------------------------------pOSTAndCommentConotroller
BlogApp.controller('PostAndComments', function ($rootScope, $scope, $http) {
    $http({url: '/getTrendingPosts', method: 'GET'}).then(function (result) {
        function Check(){
            console.log('button click');
          alert('hi');  
        };


        var postList = result.data;
        postIdSelected = result.data[1].postId;
        var block = angular.element(document.getElementById('TrendingPosts'));
        var html = '<ul>';//href="/getPost/' + postList[i].postId + '"
        for (var i = 0; i < postList.length; i++) {
            html += '<li><a onclick= angular.element(this).scope().Check() >' + postList[i].title + '</a></li>';
        }
        html += ('</ul>');
        block.html(html);





        var urlPost = '/getPost/' + postIdSelected;

        $http({url: urlPost, method: 'GET'}).then(function (result1) {
            $scope.displayPost(result1.data);
            if (result1.data.hasImage > 0) {
                debugger;
                var images = result1.data.imageId;
                for (var j = 0; j < images.length; j++) {
                    var ImageUrl = '/getImage/' + images[j];
                    $http({url: ImageUrl, method: 'GET'}).then(function (result2) {
                        debugger;
                        var base64 = $scope.toBase64(result2.data.image);
                        document.getElementById('postImage').src = 'data:' + result2.data.contentType + ';base64,' + base64;
                    });
                }
            }

            if (result.data.comment) {
                var commentList = result.data.comment;
                var block = angular.element(document.getElementById('container'));
                for (var i = 0; i < commentList.length; i++) {
                    var comment = commentList[i];
                    var string = '<div class="comment"><header><figure class="avatar"><img class="avImg" src="/showThumb/' + comment.userId + '" alt="DP"/></figure><address>By <a href="/gotoUser/' + comment.userId + '" ng-bind="getUserName(' + comment.userId + ')">UserName here</a><span class="w3-hide""> Hidden field </span></address>';
                    string += '<time class="w3-lime">' + comment.time + '</time> </header><div class="comcont"><p>' + comment.comment + '</p> </div>';
                    if (result.data.comment.hasResponse > 0) {
                        var replyList = result.data.comment.reply;
                        for (var j = 0; j < replyList.length; j++) {
                            reply = replyList[j];
                            string += '<div class="comment" id="replyBlock"> <article> <header> <figure class="avatar"><img class="avImg" src="/showThumb/' + reply.userId + '" alt="userThumbnail"/></figure>';
                            string += 'By <a href="/gotoUser/' + reply.userId + '" ng-bind="getUserName(' + reply.userId + ')"></a></header> <time>' + reply.time + '</time> </article> <div class="comment"> <p>"' + reply.reply + '"</p> </div></div></div>';
                        }
                    }
                    block.append(string);
                }
            }
            $scope.post = result.data.title;
        });
        $scope.toBase64=function(val){
            var binary = "";
            var bytes = new Uint8Array(val);
            var length = bytes.byteLength;
            for(var k = 0; k < length ; k ++){
                binary += String.fromCharCode(bytes[k]);
            }
            return btoa(binary);
        };
        $scope.displayPost = function(val){
            var html = '<p>'+val.title;
            html += '<sub> Posted On' + val.time +' By '+ val.userId +'</sub></p>';
            html += '<input id="HPI" type="hidden" value="'+val.postId+'"/>';
            angular.element(document.getElementById('postHeader')).html(html);
        };
    });

});
BlogApp.controller('RightSidePane', function ($rootScope, $scope) {
    $rootScope.$on('event', function (event, data) {
        console.log('event' + event + '..........data is: ' + data);
    });
    $scope.links = $rootScope.linksGot;
    alert('angular is on action');
});
//-----------------------------------------------------------------------------------------Upload Post controller
BlogApp.controller('uploadPost', function ($scope, $http) {
    $scope.submit = function () {
        //$scope .test set the button type loading
        $http({
            url: '/saveBlogPost',
            method: 'POST',
            data: new FormData(document.getElementById('CreatePost')),
            enctype: 'multipart/form-data',
            headers: {
                'process-Data': false,
                'content-Type': undefined,
                'cache': false
            }
        }).then(function (result) {
            if (result) {
                $scope.result = "Succesfully uploaded" + result.data;
            }
        }, function (result) {
            if (result) {
                $scope.result = "Succesfully uploaded" + result.data;
            }
        });
    };
});