<%-- 
    Document   : Forum
    Created on : 9 Mar, 2018, 8:45:15 AM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Forum</title>
        <link href="resources/w3/w3css.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/style.css" rel="stylesheet" type="text/css"/>

        
        <link href="resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/fontawesome-4.5.0.min.css" rel="stylesheet" type="text/css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/bulma-0.6.2/css/bulma.css" rel="stylesheet" type="text/css"/>
        <link href="resources/semantic/semantic.min.css" rel="stylesheet" type="text/css"/>
        <script src="resources/Jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="resources/Angular/angular.min.js" type="text/javascript"></script>
        <script src="resources/Js/ASFForum.js" type="text/javascript"></script>
        <script src="resources/Js/plugins/easyResponsiveTabs.js" type="text/javascript"></script>
        <style>
            

            #createNewPost{
                display: none;
            }
            #TrendingPosts{
                font-size: 14px;
                font-family: sans-serif;
                color: #ff7714;
            }
            .MyBlogPost{
                position: absolute;
                display: flex;

            }
            .MyBlurBackground{
                z-index: 9999;  
                filter:blur(6px);
                position: fixed;
                display: flex;
                top:0px;
                left:0px;
                width: 100%;
                height:100%;
                background-color: #009c95;
            }
            .fixImage{
                border: 1px solid #00ffff;  
                transition: transform 2s;
            }
            .fixModel{
                border: 1px solid #00ffff;  
                transition: transform 2s;
            }
            .fixModel:hover{
                cursor: pointer;
                transform: scale(1.5);
            }
            .fixModelImage{
                border: 1px solid #00ffff;  
                transition: transform 2s;
            }
            .fixModelImage:hover{
                cursor: pointer;
                transform: scaleX(1.3);
                transform: scaleY(1.1);
            }
            .fixImage:hover{
                cursor: pointer;
                transform: scale(1.5);
                transition-duration: 2s;
            }
            .imageContainer{
                opacity: 0.5;
                width: available;
                height:100px;
                overflow: hidden;
                border: 2px solid #fff;
                transition-duration: 2s;
                border: 2px solid #0d71bb;
            }
            .imageContainer:hover{
                cursor: pointer;
                opacity: 1;
                transition-duration:1.5s;
            }
            .commentButton {padding: 30px 0 0 75px;}
            .content{padding: 0px 50px 0px 50px;margin-top:0px;}

            .article-content{padding: 15px 50px 0px 0px;}
            .media-content,comments{overflow: visible;}            
            .modal{
                margin-top: 0;
            }
            .myContent{
                margin-top: 40px;
            }
            .commentImageBox{
                width:200px;
                height: 150px;
                overflow: hidden;
            }
            .commentImageBox:hover{
                cursor: pointer;
            }
            .replyImageBox{
                width:250px;
                height: 170px;
                overflow: hidden;
            }
            .reply{
                padding: 2px;
                margin: 0px;
                padding-left: 8px;
            }
            .col-md-4, .col-md-4,.w3-container{
                padding: 10px 10px;

            }
            .card-header-img{
                padding: 1px 0px;
                min-height: 200px;
                height: 200px;
                overflow: hidden;
            }
            .card-container{
                min-height: 100px;
            }
            .div-post-container{
                padding: 2px 2px 2px 2px;
                min-height: 250px;
                height: 350px;
            }
            .menu-zindex{
                z-index: 0;
            }
            .menu-left-content{
                right: 10%;
            }
            .glyphicon-remove{
                cursor: pointer;
            }
            .user-name{
                margin-bottom: 0px;
            }
        </style>
    </head>
    <body ng-app="forum">
        <div id="model"></div>
        <jsp:include page="homeMenu.jsp" ></jsp:include>
        <!--<button onclick="javascript:window.location = '/profile';" class="mybutton Myhvr"><i class="glyphicon glyphicon-home">_Back_to_Home</i></button>-->                
        <div class="myContent">
            <div class="form-group-lg row" id="createNewPost">
                <div id="blurBackground" class="MyBlurBackground">
                    <img src="/resources/Images/blackbg.jpg" alt="banner" />
                </div>  
                <div id="PostFormOnBlurBackground" class="contentOnBackground MyText">
                    <span class="w3-display-topright myExit" onclick="this.parentElement.parentElement.style.display = 'none'"><i class="glyphicon glyphicon-remove"></i></span>
                    <form id="CreatePost" enctype="multipart/form-data" ng-controller="uploadPost">
                        <p> Post title:   <input type="text" name="caption" required="required" placeholder="title here!" class="form-control MyText"/></p>
                        <p> upload images: <input type="file" accept="image/*" name="images" class="form-control MyTransperent MyText" value="select an image"/> </p>
                        <input type="submit" value="save" class="btn-primary  button is-primary" ng-click="submit()" />
                        <p id="result" ng-model="result">{{result}}</p>
                    </form>
                </div>
            </div>

            <div ng-controller="PostsAndComments">
                <div class="col-md-9 content">
                    <div class="row" id="homeContent">

                    </div>
                    <div class="row">
                        <div id="PostHeader">

                        </div>                        
                        <div id="ImageBlock" class="image img-responsive">

                        </div>
                        <div id="CommentBlock" class="comments  ">

                        </div>
                        <div  id="CommentButton" >

                        </div>
                    </div>



                </div>
                <div id="RightPane" class="col-md-3 rightBlock rightSidePaneBorder">

                </div>

            </div>
        </div>
    </body>
</html>
