<%--
   Document   : profile
   Created on : 12 Dec, 2017, 9:15:31 AM
   Author     : Pravinkumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="cc" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Home page Profile</title>
        <link href="resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/fontawesome-4.5.0.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/w3/w3css.css" rel="stylesheet" type="text/css"/>


        <script src="resources/Jquery/jquery-3.2.1.js" type="text/javascript"></script>
        <script src="resources/Bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="resources/Angular/angular.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
        <script src="resources/Js/myScripts.js" type="text/javascript"></script>
        <!--<script src="resources/Js/secondAngular.js" type="text/javascript"></script>-->
        <script src="resources/Js/userHomeAngular.js" type="text/javascript"></script>
        <style>
            .MyProgress{
                float:right;
            }
            .ImgProgress{
                width: 45px;
                height: 45px;
                display: inline-block;
                background-color: transparent;
            }
        </style>
        <script>
            
        </script>

    </head>
    <body class="container-fluid" ng-app="userHome">
        <!--        --------------------------------------------side bar content ----------------------->
        <jsp:include page="menu.jsp"></jsp:include> 
            <div class="Mybanner top" id ="testBannerDiv">
                        
            </div>
            <div class="MyForeground">

                <img src="resources/Images/male.png" class="Myprofile">
                <p> this is tile </p>
            </div>
            <div ng-controller="tst">
                <button id="tiggerBackgroundImage" ng-click="trigger()">click here to change bg</button>
            </div>
            
            <button class="w3-btn w3-hover-shadow" onclick="document.getElementById('testAlert').style.display = 'block';">showAlert</button>
            <div id ="testAlert" style="display:none;position: absolute;" class="w3-container w3-yellow w3-panel w3-grayscale w3-cell-middle">
                <span class ="w3-button" style="float:right;" onclick="this.parentElement.style.display = 'none';" role="button">&times;</span>
                <h4> hello </h4>
            </div>
            <div class="w3-container w3-red">
                <span class ="w3-button" style="float:right;" onclick="this.parentElement.style.display = 'none';" role="button">&times;</span>
                <h3>Danger!</h3>
                <p>Red often indicates a dangerous or negative situation.</p>
            </div> 
            <div class="row">
                <div class="profilePost">
                    <div class="col-xs-3 col-sm-6 btn-group-lg">
                        <button id="id1" class="mybutton Myhvr">Button 1 >></button>
                        <button id="id2" class="mybuttonLeft Myhvr">Button 2 >></button>
                        <button id="id3" class="mybutton Myhvr">Button 3 >></button>
                        <button id="id4" class="mybuttonLeft Myhvr">Button 4 >></button>
                    </div>
                    <div class="col-sm-6 floatRight">
                        <div id="imgBlock" class="MyProgress">
                            <img class="ImgProgress" src="resources/Images/Loading.gif" alt=""/>
                        </div>
                        <button id="id2" class="mybutton Myhvr " data-toggle="modal" data-target="#modal" >Post a new story</button>
                    </div>
                </div>
            </div>
            <!--    ------------------------------------------------------main content -------------------------->
            <div class="row">
                <div class="col-md-3">
                    <p id="postProgress"></p>
                    <h2 class="text-danger"> title tags ${user}</h2><p id="stack" style="display:none;">0</p>
            </div>
            <!-------------------------------------------------------------------------Content blcok===========--->
            <div class="col-md-9 form-group content">
                <div class="progress">
                    <div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
                         aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
                </div>
                <div id="alertMsg" style="color: red;font-size: 18px;"></div>

                <!--                ---------------------------------------  dynamic body comment ------>
                <h2>YOUR POSTS:</h2>
                <div id="posts">
                </div>

                <!--            <div class="comment">
                                <header>
                                    <figure class="avatar"><img class="avImg" src="resources/Images/male.png" alt=""></figure>
                                    <address>
                                        By <a href="#"></a> contactHIm 
                                    </address>
                                    <time datetime="2045-04-06T08:15+00:00">Friday, 6<sup>th</sup> April 2045 @08:15:00</time>
                                </header>
                                <div class="comcont">
                                    <p>  </p>
                                </div>
                                <button class="mybutton Myhvr" >Test button</button>
                                <button class="mybuttonLeft Myhvr" >Test button left</button>
                            </div>-->
                <!--   --------------------------------------- /// dynamic body comment ------>

                <footer class="text-primary text-center">
                    <button class="btn btn-default">
                        <i class="fa fa-amazon"></i>Like
                    </button>
                    <div id="AlertToShow" class=" divToggle w3-container w3-amber">
                        Content here!
                    </div>
                    <button class="btn btn-default" id="CloseAlert">
                        <i class="fa fa-bluetooth"></i>comment

                    </button>
                    
                    <button class="btn btn-default" data-toggle="modal" data-target="#modal">
                        <i class="fa fa-facebook"></i>Share
                    </button>
                    <input type="file"  class="btn-default" name ="filename[]" multiple="multiple" />
                    <p><a href="/save" role="button" class="btn">goto Testing page</a></p>
                </footer>
            </div>
            <!-----------------------------------------------------Model data -------------------->


            <div  id="modal" class="modal fade" tabindex="-1" aria-hidden="true" aria-labelledby="testIDmodel">
                <div class="modal-dialog">
                    <form id="CreatePost" enctype="multipart/form-data">
                        <div class="modal-content">
                            <div class="modal-header" id="testIDmodel">
                                Share your story
                                <button class="class pull-right" data-dismiss="modal">&times;</button>
                            </div>

                            <div class="modal-body">

                                <input name="image" id="img" type="file" multiple accept="image/*" required="required"/>
                                <input type="text" required="required" name="caption"/>
                                <textarea id="title" class="in input-sm" style="width:100%;height:25%;" required="required" name="description"></textarea>

                                <div id="uploadBlock"></div>
                            </div>
                            <div class="modal-footer">
                                <button id="submitP" class="mybutton Myhvr" data-dismiss="modal">Submit</button>
                                <button id ="cancel"  data-dismiss="modal">cancel</button><br>
                                <div id="UploadSuccess">
                                    <h3>Bye Bye</h3>
                                    <h2>Your file is being uploaded</h2>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>                         

            <!-- ---------------------------------------------------right side block --------------->
        </div>
    </body>
</html>
