<%-- 
    Document   : Blog
    Created on : 15 Dec, 2017, 9:21:16 PM
    Author     : Pravinkumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home page Profile</title>
        <link href="resources/Css/fontawesome-4.5.0.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/fontawesome-4.5.0.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/w3/w3css.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="resources/bulma-0.6.2/css/bulma.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/style.css" rel="stylesheet" type="text/css"/>
        <script src="resources/Angular/angular.min.js" type="text/javascript"></script>

        <script src="resources/Bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="resources/Jquery/jquery-3.2.1.min.js" type="text/javascript"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="resources/Js/angularServiceForBlog.js" type="text/javascript"></script>
        <script src="resources/Js/JqServiceForBlog.js" type="text/javascript"></script>
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
            .fixImage:hover{
                cursor: pointer;
                transform: scale(1.5);
                transition-duration: 2s;
            }
            .imageContainer{
                width: available;
                height:100px;
                overflow: hidden;
                border: 2px solid #fff;
                transition-duration: 2s;
                border: 2px solid #0d71bb;
            }
            .imageContainer:hover{
                transition-duration:1.5s;
            }
        </style>
    </head>
    <body class="container-fluid" ng-app="Blog">
        <!--        --------------------------------------------side bar content ----------------------->
        <jsp:include page="menu.jsp"></jsp:include> 

        <!--    ------------------------------------------------------main content -------------------------->
        <div class="myContent">
            <div class="form-group-lg row" id="createNewPost">
                <div id="blurBackground" class="MyBlurBackground">
                    <img src="/resources/Images/blackbg.jpg" alt="banner" />
                </div>  
                <div id="PostFormOnBlurBackground" class="contentOnBackground MyText">
                    <span class="w3-display-topright myExit" onclick="this.parentElement.parentElement.style.display = 'none'"><i class="glyphicon glyphicon-remove"></i></span>
                    <form id="CreatePost" enctype="multipart/form-data" ng-controller="uploadPost">
                        <p> Post title:   <input type="text" name="caption" required="required" placeholder="title here!" class="form-control MyText"/></p>
                        <!--<p> Post Description  <textarea type="text" rows="5" name="description" required="required" placeholder="describe your post " class="MyTransperent form-control MyText"></textarea></p>-->
                        <p> upload images: <input type="file" accept="image/*" name="image" class="form-control MyTransperent MyText" value="select an image"/> </p>
                        <input type="submit" value="save" class="btn-primary  button is-primary" ng-click="submit()" />
                        <p id="result" ng-model="result">{{result}}</p>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-9 form-group content" ng-controller="PostAndComments">
                    <div id="Post">
                        <div class="text-primary titleBlock"> 
                            <div id="postHeader">

                            </div>

                        </div>
                        <div class="imageContainer" id="postImageContainer">
                            <img id="postImage" class="fixImage"/>
                        </div>
                        <h3 class="text-info">Comments</h3>
                        <!--                ---------------------------------------  dynamic body comment ------>
                        <div id="container">

                        </div>
                    </div>

                    <div id="addComment" ng-controller="commentController">
                        <div id="AddComment" ng-controller="CommentUploader">
                            <div id="CommentBlock" class="row w3-padding-16">
                                <div class="w3-twothird col-md-9 w3-col">
                                    <input type="text" id="commentField" class="w3-input form-control input-sm"placeholder="Your comment here!" ng-model="comment"/>
                                </div>
                                <div class="col-md-3 w3-right pull-right"> 
                                    <button class="mybutton My btn pull-right">comment</button>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!--                --------------------------------------- /// dynamic body comment ------>


                </div>
            </div>
            <footer class="text-primary text-center w3-right w3-bottom">
                <p >copyright@Youth Political Forum. 2018</p>
                <a href="/aboutUs">About us </a>|
                <a id="followOnTwitter">Follow us on twitter </a>|
                <a id="followOnFacebook">Follow us on facebook</a>
            </footer>

            <div class="col-md-3 jumbotron rightBlock" ng-controller="RightSidePane">
                <a id="whatsOnYourMind" class="button is-primary ">what's on your mind ?</a>
                <h2 class="text-danger">Trending tags</h2>
                <div compile="htmlCode">

                </div>
                <!--                <div ng-repeat="link in links">
                                    <ul>
                                        <li><a ng-click="getPost('/getPost/'+{{link.postId}})">{{link.title}}</a><sub>posted on {{link.time}}</sub></li>
                                    </ul>
                                </div>-->
                <div id="TrendingPosts">

                </div>
                <!--right side pane here           this block for user information and contributions                      //--> 

            </div>
            <!-------------------------------------------------------------------------Content blcok===========--->

        </div>



        <!-----------------------------------------------------Model data -------------------->
        <div id="modal" class="modal fade w3-animate-zoom" tabindex="-1" aria-hidden="true" aria-labelledby="testIDmodel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header " id="testIDmodel">
                        model header
                        <button class="class pull-right" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        model body
                    </div>
                    <div class="modal-footer">
                        model footer
                    </div>
                </div>
            </div>
        </div>                         

        <!-- ||||||||||||||||||||||||||||           ||||||||||||||||||||||||||||||||||---------     -->


        <div class="w3-modal w3-animate-zoom w3-display-middle" id="w3Modal" >
            <header class="w3-container">
                <span class="w3-button pull-right" onclick="document.getElementById('w3Modal').style.display = 'none'">&times;</span>
                header 
            </header>
            <div class="w3-container modal-content" ng-controller="uploadBox" ng-init="name = 'hello'; test = 'hai';">
                <input type="file" id="file1" name="file" multiple
                       ng-files="getTheFiles($files)" />

                <input type="button" ng-click="uploadFiles()" value="Upload" />
            </div>
            content

            <footer class="w3-container modal-footer">
                footer
            </footer>
        </div>
        <!--        ---------------------------------------------------------------right side block --------------->
    </body>


    <!--    <div ng-app="plunkr">
    <div ng-controller="UploadController ">
        <form>
          <input type="file" ng-file-select="onFileSelect($files)" ng-model="imageSrc">
            <input type="file" ng-file-select="onFileSelect($files)" multiple> 
        </form>
        
        <img ng-src="{{imageSrc}}" />
          
      </div>-->
</div>
</html>
