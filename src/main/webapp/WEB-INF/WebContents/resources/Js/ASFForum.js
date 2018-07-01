/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */

var BlogApp = angular.module('forum', []);
BlogApp.controller('PostsAndComments', function ($rootScope, $scope, $http, $compile) {
    
    $http({url: '/getTrendingPosts', method: 'GET'}).then(function (result) {
        $scope.displayRightPane(result.data);
    });
    $http({url: '/getHomeContent', method : 'GET'}).then(function (result) {
        $scope.displayHomeContent(result.data);
    });

    $scope.closeModel = function () {
        angular.element(document.getElementById('model')).html('');
    };
    $scope.popubUpload = function () {
        document.getElementById('createNewPost').style.display = 'inline-block';
    };
    $scope.popupModel = function (image) {
        var string = '<div class="modal is-active"><div class="modal-background"></div><div class="modal-content fixImage">';
        string += '<p class="image"><img src="' + image.src + '" class="fixImage" alt="model popup"></p></div><button class="modal-close is-large" onclick=angular.element(this).scope().closeModel()  aria-label="close"></button></div>';
        var ref = angular.element(document.getElementById('model')).html(string);
        $compile(ref)($scope);
    };
    $rootScope.getPost = function (postIdSelected) {
        angular.element(document.getElementById('CommentBlock')).html("");
        angular.element(document.getElementById('CommentButton')).html("");
        var urlPost = '/getPost/' + postIdSelected;
        $http({url: urlPost, method: 'GET'}).then(function (result) {
            $scope.displayPost(result.data);
            var postId = result.data.postId;

            if (result.data.hasImage > 0) {
                debugger;
                var images = result.data.imageId;
                var element = angular.element(document.getElementById('ImageBlock'));
                var buffer = '';
                element.html(buffer);
                for (var j = 0; j < images.length; j++) {
                    $http({url: '/getImage/' + images[j], method: 'GET'}).then(function (result2) {
                        var base64 = $scope.toBase64(result2.data.image);
                        buffer += '<div class="imageContainer ui animated fade" id="postImageContainer"><img src="data:' + result2.data.contentType + ';base64,' + base64 + '" onclick="angular.element(this).scope().popupModel(this)" /></div>';
                        var ref = element.append(buffer);
                        $compile(ref)($scope);
                    });
                }
            }
            if (result.data.comments) {
                var commentList = result.data.comments;
                var block = angular.element(document.getElementById('CommentBlock'));
                block.html('');
                for (var i = 0; i < commentList.length; i++) {
                    var comment = commentList[i];
                    var string = $scope.getCommentCode(comment, postId);
                    if (comment.hasResponse > 0) {
                        var replyList = comment.replys;
                        string += '<div id="RB' + comment.commentId + '">';
                        string += $scope.getReplyCode(replyList);
                        string += '</div>';
                    } else {
                        string += '<div id="RB' + comment.commentId + '"></div>';
                    }
                    string += '<div id="RP' + comment.commentId + '"></div></div></article>';
                    var ref = block.append(string);
                    $compile(ref)($scope);
                }
            }
            $scope.post = result.data.title;
        });
    };
    $scope.loadComments = function (comments, postId) {
        if (comments.length > 0) {
            var commentList = comments;
            var block = angular.element(document.getElementById('CommentBlock'));
            block.html('');
            for (var i = 0; i < commentList.length; i++) {
                var comment = commentList[i];
                var string = $scope.getCommentCode(comment, postId);
                if (comment.hasResponse > 0) {
                    var replyList = comment.replys;
                    string += '<div id="RB' + comment.commentId + '">';
                    string += $scope.getReplyCode(replyList);
                    string += '</div>';
                } else {
                    string += '<div id="RB' + comment.commentId + '"></div>';
                }
                string += '<div id="RP' + comment.commentId + '"></div></div></article>';
                var ref = block.append(string);
                $compile(ref)($scope);
            }
        }
    };
    $scope.getUserName = function (url) {
        var el = angular.element(document.getElementById('UN'+url));
        url += '/getUserName'+url;
        $http({method: 'get',
            url: url
        }).then(function (result) {
            el.html(result.data.userId);
        }).catch(function (result) {
           el.html(result.data.userId);
        }).finally(function () {
            console.log("from getUserName");
        });
    };                                          
    $scope.getCommentCode = function (comment, postId) {
        var string = '<article class="media"><figure class="media-left"><p class="image is-64x64">';
        string += '<img src="/getDP/'+comment.userId+'"  class="w3-left w3-circle"></p></figure><div class="media-content">';
        string += '<div class="article-content"><p class="user-name"><strong><a href="/gotoUser/' + comment.userId + '">userName here</a></strong>';
        string += '<br>' + comment.comment + '<br><small><a class="link" ng-click="doLikeComment(this.parentElement,\'' + postId + '\',\'' + comment.commentId + '\')"><i class="fa fa-thumbs-up"></i>Like</a> · <a class="link" ng-click="displayReplyBox(\'' + comment.commentId + '\',\'' + postId + '\')"><i class="fa fa-reply"></i>Reply</a> - ' + comment.time + '</small></p></div>';
        if (comment.hasImage > 0) {
            var imageId = comment.imageId;
            string += '<div class="commentImageBox">';
            for (var j = 0; j < imageId.length; j++) {
                string += '<img src="/getSource/' + imageId[j] + '" onclick="angular.element(this).scope().popupModel(this)" class="replyImageBox"/>';
            }
            string += '</div>';
        }
        return string;
    };//----after user name here link ---<p class="user-name" ng-bind="angular.element(this).scope().getUserName(\'' + comment.userId + '\' id="UN'+comment.userId+'")"></p>
    $scope.getReplyCode = function (replyList) {
        var string = '';
        for (var j = 0; j < replyList.length; j++) {
            var reply = replyList[j];
            string += '<article class="media reply"><p>';
            string += reply.reply + '-<strong><a href="/gotoUser/' + reply.userId + '"><p ng-bind="angular.element(this).scope().getUserName(\'' + reply.userId + '\');" id="UN'+reply.userId+'">userNamehere</p></a></strong><small>-On ' + reply.time + '</small>';
            string += '</p></article>';
        }
        return string;
    };


    $scope.doLikeComment = function (element, postId, commentId) {
        var urlLink = '/doLikeComment/' + postId + '/' + commentId;
        $http({method: 'POST',
            url: urlLink
        }).then(function (result) {
            element.childNodes[1].innerHtml = result.data.comments[0].gotLikes;
        }).catch(function (result) {
            element.childNodes[1].innerHtml = result.data.comments[0].gotLikes;
        }).finally(function () {
            console.log("from do like comment");
        });
    };
    $scope.refreshComments = function (postId) {
        var resultMain;
        $http({url: '/getComments/' + postId,
            method: 'POST',
            data: {'postId': postId},
            enctype: 'application/json'}).then(function (result) {
            resultMain = result.data[0].comments;
        }).catch(function (result) {
            resultMain = result.data.comments;
        }).finally(function () {
            console.log("from refreshComments finally block ");
            $scope.loadComments(resultMain, postId);
            $scope.displayCommentBox("CommentButton", postId);
        });
    };
    $scope.refreshReply = function (postId, commentId) {
        var replyBox = 'RP' + commentId;
        angular.element(document.getElementById(replyBox)).html("");
        //console.log(postId + ""+commentId);
        var resultMain;
        var url = '/getReplies/' + postId + '/' + commentId;
        $http.get(url).then(function (result) {
            resultMain = result.data[0].comments[0].replys;
        }).catch(function (result) {
            resultMain = result.data[0].comments[0].replys;
            alert("error @refreshReply()");
        }).finally(function () {
            $scope.loadReply(resultMain, commentId);
        });
    };
    $scope.loadReply = function (replyList, commentId) {
        if (replyList.length > 0) {
            var replyBlock = 'RB' + commentId;
            var block = angular.element(document.getElementById(replyBlock));
            block.html('');
            var string = $scope.getReplyCode(replyList);
            var ref = block.html(string);
            $compile(ref)($scope);
        }
    };
    $scope.setComment = function (element) {
        var formData = new FormData(element);
        var input = element.childNodes[1].childNodes[0];
        var image = element.childNodes[2].childNodes[0];
        var button = element.childNodes[3].childNodes[0];
        image.disabled = true;
        input.disabled = true;
        button.disabled = true;
        $scope.uploadComment(formData, input, image, button);
    };
    $scope.doReply = function (element) {
        var formData = new FormData(element);
        var input = element.childNodes[3].childNodes[0];
        input.disabled = true;
        var button = element.childNodes[4].childNodes[0];
        button.disabled = true;
        $scope.uploadReply(formData, input, button);
    };
    $scope.uploadReply = function (formData, x, y) {
        $http({url: '/saveReply', data: formData, method: 'POST', enctype: 'application/json',
            headers: {
                'process-Data': false,
                'content-Type': undefined,
                'cache': false
            }}).then(function (result) {
            console.log("From uploadREply success" + result.status + "" + result.data);
            x.disabled = false;
            y.disabled = false;
        }).catch(function () {
            console.log("upload Rpely catch block");
            x.disabled = false;
            y.disabled = false;
        }).finally(function () {
            console.log("upload Rpely finally block");
            x.disabled = false;
            y.disabled = false;
            $scope.refreshReply(formData.get("postId"), formData.get("commentId"));
        });
    };
    $scope.uploadComment = function (formData, input, image, button) {
        $http({url: '/saveComment', data: formData, method: 'POST', enctype: 'multipart/form-data',
            headers: {
                'process-Data': false,
                'content-Type': undefined,
                'cache': false
            }}).then(function (result) {
            console.log("From uploadComment Error" + result.status + "" + result.data);
            input.disabled = false;
            image.disabled = false;
            button.disabled = false;
        }).catch(function (error) {
            console.log("From uploadComment Error" + error.status + "" + error.data);
            input.disabled = false;
            image.disabled = false;
            button.disabled = false;
        }).finally(function () {
            console.log("From uploadComment finally bllock ");
            $scope.refreshComments(formData.get("postId"));
        });
    };
    $scope.displayHomeContent = function (postList) {
        var block = angular.element(document.getElementById('homeContent'));
        var html = '<div class="row" >';
        for (var i = 0; i < postList.length; i++) {
            html += '<div class="col-md-3 div-post-container">';
            html += '<div class="w3-container"><div class="w3-card-4 card-container">';
            if (postList[i].hasImage > 0) {
                var images = postList[i].imageId;
                for (var j = 0; j < images.length; j++) {
                        html += '<header class="w3-container w3-light-grey card-header-img">';
                        html += '<img src ="/getSource/'+ images[j]+'" style="width:100%;height:inherit;">';
                        html += '</header>';
                }
            }
            html += '<div class="w3-container">';
            html += '<img src="/getDP/' + postList[i].userId + '" class="w3-left w3-circle w3-margin-right" style="width:30px">';
            html += '<p>'+postList[i].title+'</p><br>';
            html += '<p>- <small>' + postList[i].time + '</small></p></div><button ng-click=getPost(\'' + postList[i].postId + '\') class="w3-button w3-block w3-dark-grey"> View Post </button></div></div>';
            html += '</div>';
        }
        html += '</div>';
        var el = block.append(html);
        $compile(el)($scope);
    };
    $scope.displayRightPane = function (postList) {
        var block = angular.element(document.getElementById('RightPane'));
        var html = '<a id="whatsOnYourMind" ng-click="popubUpload()" class="button is-primary ">Write your own</a><h2 class="text-danger">Trending tags</h2><ul>';
        for (var i = 0; i < postList.length; i++) {
            html += "<li><a ng-click=getPost('" + postList[i].postId + "') >" + postList[i].title + '</a></li>';
        }
        html += ('</ul>');
        var el = block.append(html);
        $compile(el)($scope);
    };
    $scope.toBase64 = function (val) {
        var binary = "";
        var bytes = new Uint8Array(val);
        var length = bytes.byteLength;
        for (var k = 0; k < length; k++) {
            binary += String.fromCharCode(bytes[k]);
        }
        return btoa(binary);
    };
    $scope.displayReplyBox = function (commentId, postId) {
        var html = '<article class="commentButton"> <div ><form method="POST" class="file has-name" enctype="multipart/form-data"><input type="text" class="hidden" name="postId" value="' + postId + '"/> <input type="text" class="hidden" name="commentId" value="' + commentId + '"/><span class="control"><textarea class="input is-16by9 text-area" name="comment" rows=1 cols=25 type="text" placeholder="your reply here!" required></textarea>';
        html += '</span><div class="control"><button class="button is-16by9" onclick=angular.element(this).scope().doReply(this.parentElement.parentElement) value="Comment"><i class="glyphicon glyphicon-send"></i> _Reply</button></div></form></div></article>';
        commentId = 'RP' + commentId;
        var ref = angular.element(document.getElementById(commentId)).html(html);
        $compile(ref)($scope);
    };
    $scope.displayCommentBox = function (valueOne, valueTwo) {
        var html = '<article class="commentButton"> <div ><form class="file has-name" method="POST" enctype="multipart/form-data"><input type="hidden" name="postId" value="' + valueTwo + '"/><span class="control"><textarea class="input is-16by9 text-area" name="comment" rows=1 cols=25 required placeholder="Write your comment here!"></textarea>';
        html += ' </span><label class="file-label"><input class="file-input" type="file" name="images"><span class="file-cta"><span class="file-icon">';
        html += '<i class="glyphicon glyphicon-picture"></i> </span></span></label><div class="control"><button class="button is-16by9" onclick=angular.element(this).scope().setComment(this.parentElement.parentElement) value="Comment"><i class="glyphicon glyphicon-send"></i> _Comment</button></div></div></form></article>';
        var ref = angular.element(document.getElementById(valueOne)).html(html);
        $compile(ref)($scope);
    };
    $scope.displayPost = function (val) {
        angular.element(document.getElementById('homeContent')).html("");
        var html = '<div class="text-primary"><div id="postHeader"><h1 style="margin-top:0px;">' + val.title + '</h1></div>';
        html += '<p><sub> Posted On' + val.time + ' By ' + val.userId + '</sub></p>';
        html += '<input id="HPI" type="hidden" value="' + val.postId + '"/></div>';
        var ref = angular.element(document.getElementById('PostHeader')).html(html);
        $compile(ref)($scope);
        $scope.displayCommentBox("CommentButton", val.postId);
    };
});



BlogApp.controller('uploadPost', function ($rootScope, $scope, $http, $timeout, $compile) {
    $timeout(function () {
        console.log('timeout');
    }, 4000);
    $scope.submit = function () {
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
            console.log("at the then block code ajax upload post");
            $scope.result = "upload success" + result.data + "" + result.status;
        }).catch(function (error) {
            console.log("at the catch block code ajax upload post");
            $scope.result = "upload Error" + error.status + "" + error.data;
        }).finally(function () {
            console.log("at the fianlly block code ajax upload post");
            $scope.reloadPane();
        });
    };
    $scope.reloadPane = function () {
        $http({url: '/getTrendingPosts', method: 'GET'}).then(function (result) {
            $scope.displayRightPane(result.data);
        });
    };
    $scope.displayRightPane = function (postList) {
        var block = angular.element(document.getElementById('RightPane'));
        block.html('');
        var html = '<a id="whatsOnYourMind" ng-click="popubUpload()" class="button is-primary ">Write your own</a><h2 class="text-danger">Trending tags</h2><ul>';
        for (var i = 0; i < postList.length; i++) {
            html += "<li><a ng-click=getPost('" + postList[i].postId + "') >" + postList[i].title + '</a></li>';
        }
        html += ('</ul>');
        var el = block.append(html);
        $compile(el)($rootScope);
    };
});





/*
 * $scope.pushImageAtComment = function (images,block) {
 $http({url: '/getImage/' + images, method: 'GET'}).then(function (result) {
 var base64 = $scope.toBase64(result.data.image);
 var string = '<div class="commentImageBox ui animated fade" id="postImageContainer"><img src="data:' + result.data.contentType + ';base64,' + base64 + '" onclick="angular.element(this).scope().popupModel(this)" /></div>';
 var ref = angular.element(document.getElementById(block)).append(string);
 $compile(ref)($scope);
 });
 };
 
 
 string += '<article class="media"> <figure class="media-left"><p class="image is-48x48"><img src="resources/Images/male.png"></p>';
 string += '</figure><div class="media-content"><div class="article-content"><p><strong><a href="/gotoUser/' + reply.userId + '">UserName here</a></strong>';
 string += '<br>' + reply.reply + '<small> -On ' + reply.time + '</small>';
 string += '</p></div></div></article>';
 
 
 
 
 */