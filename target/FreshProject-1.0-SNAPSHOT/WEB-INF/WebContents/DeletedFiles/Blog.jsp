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
        <link href="resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/fontawesome-4.5.0.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/w3/w3css.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="resources/Angular/angular.min.js" type="text/javascript"></script>
        <script src="resources/Js/angular.js" type="text/javascript"></script>
        <script src="resources/Bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="resources/Jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    </head>
    <body class="container-fluid" ng-app="Blog">
        <!--        --------------------------------------------side bar content ----------------------->
        <jsp:include page="menu.jsp"></jsp:include> 

        <!--    ------------------------------------------------------main content -------------------------->
        <div class="myContent">
            <div class="col-md-3 jumbotron rightBlock">
                <h2 class="text-danger"> title tags</h2>
                
                
                
                <!--right side pane here           this block for user information and contributions                      //--> 

            </div>
            <!-------------------------------------------------------------------------Content blcok===========--->
            <div class="col-md-9 form-group content">
                <div class="text-primary titleBlock"> 
                    <h2 class="h2"> question's title here</h2>
                    <span class="label label-default">tag Name</span>
                </div>
                <article>
                    <h3 class="text-info">Comments</h3>
                    <!--                ---------------------------------------  dynamic body comment ------>
                    <div class="comment">
                        <header>
                            <figure class="avatar"><img class="avImg" src="resources/Images/male.png" alt=""></figure>
                            <address>
                                By <a href="#"> </a> contact him at
                                <span aria-hidden="true"> Test for area hidden field </span>
                            </address>
                            <time datetime="2045-04-06T08:15+00:00">Friday, 6<sup>th</sup> April 2045 @08:15:00</time>
                        </header>
                        <div class="comcont">
                            <p>This is an example of a comment made on a post. You can either edit the comment, delete the comment or reply to the comment. Use this as a place to respond to the post or to share what you are thinking.</p>
                        </div>
                    </div>
                    <div id="addComment" ng-controller="commentController">

                        <div id="AddComment">
                            <div id="CommentBlock" class="row w3-padding-16">
                                <div class="col-md-3 w3-right pull-right"> <button class="mybutton My btn pull-right" ><i class="fa-comment">comment</i></button></div>
                                <div class="w3-twothird col-md-9 w3-col"><input type="text" id="commentField" class="w3-input form-control input-sm" required="required" placeholder="Your comment here!" ng-model="comment"/></div>
                                
                            </div>
                            <button  id="addComment" ng-click="PostComment()" class="mybutton Myhvr">Leave a comment here</button>
                        </div>
                    </div>
                    <!--                --------------------------------------- /// dynamic body comment ------>
                    <footer class="text-primary text-center">
                        <button class="btn btn-default">
                            <i class="fa fa-amazon"></i>Like
                        </button>
                        <button class="btn btn-default" onclick="document.getElementById('w3Modal').style.display = 'block';">
                            <i class="fa fa-bluetooth" ></i>comment
                        </button>
                        <button class="btn btn-default" data-toggle="modal" data-target="#modal">
                            <i class="fa fa-facebook"></i>Share
                        </button>
                        <a href="/save" role="button" class="btn">goto Testing page</a>
                    </footer>
                </article>
            </div>
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
