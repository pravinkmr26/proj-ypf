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
        <title>${userName} </title>

        <link href="/resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/w3/w3css.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/bulma-0.6.2/css/bulma.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/Css/fontawesome-4.5.0.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
        <link href="/resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>


        <script src="/resources/Jquery/jquery-3.2.1.js" type="text/javascript"></script>
        <script src="/resources/Bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="/resources/Angular/angular.min.js" type="text/javascript"></script>
        <script src="/resources/Js/ASFUserHome.js" type="text/javascript"></script>

        <style>
            .imageFrame{
                margin-left: 30px;
                border: 2px solid #f0f0f0;
                overflow: hidden;
                transition-duration: .5s;
            }
            .imageFrame:hover{
                overflow-style: marquee;
                transform: scale(1.1);
                transition-duration: .5s;
            }
            .height150{
                height: 160px;
                min-height: 150px;
            }

            .width100{
                width: 130px;
                min-width: 120px;
            }
            .MyProgress{
                float:right;
            }
            .ImgProgress{
                width: 45px;
                height: 45px;
                display: inline-block;
                background-color: transparent;
            }
            .fixImage{
                max-width: inherit;
                width: 100%;
                border: 2px solid #204d74;
            }
            .fixImage-model{
                max-width: inherit;
                width: fit-content;
                border: 2px solid #204d74;
            }
            .width30Cent{
                width: 30%;
            }
            height15Cent{
                height: 15%;
            }
            .imageContainer{
                background: linear-gradient(rgba(208, 205, 204, 0.53),rgba(199, 199, 196, 0.62));
                border-width: 2px;
                border-style: double;
                border-color: rgba(50, 50, 50, 0.71);
            }
            .postContainer{
                background: linear-gradient(rgba(239, 243, 243, 0.71),rgb(234, 236, 230));
                border-width: 4px;
                border-style: solid;
                border-color: rgb(255, 244,233);
                color: black;
                font-size: 15px;
                font-family: cursive;
            }
            .imageAnimation{
                cursor: pointer;
                width: 160px;
                height: 170px;
                transition-duration: 1s
            }                
            .imageAnimation:hover{
                transform: scale(1.5);
            }
            .imageContainer{
                /*opacity: 0.5;*/
                width: available;
                overflow: hidden;
                border: 2px solid #fff;
                transition-duration: 2s;
                border: 2px solid #0d71bb;
            }
            .imageContainer:hover{
                cursor: pointer;
                /*opacity: 1;*/
                transition-duration:1.5s;
            }
            .Mybanner{
                z-index: 0;
            }
            .MyForeground,.myName,.imageFrame{
                z-index: 1;
            }
            .myWaterMark {
                margin-left:70%;
                color:white;
                background-color: #1b78e7;
            }
            .myTextArea{
                font-size: 14px;
                height: 2.41em !important;
                width: 500px;
            }
            .upload-dp{
                align-items:flex-start;
                background-color: #00ffff;
                text-align:right;
                position: absolute;
                display: none;
                opacity: 0.9;
            }
            .upload-dp:hover{
                display: inline-block;
            }
            .Myprofile{
                width: inherit;
                text-align: right;
            }
            .dp-options{
                color: white;
            }
            .dp-options:hover{
                cursor: pointer;
                color: #efefef;
            }
            .dp-options-links{
                display: none;
                position: absolute;
                right: inherit;
                top:inherit;
            }
            .fix-upload-button{
                position: absolute;
                z-index: 1;
            }
            fix-cancel-right{
                right:0;
            }
        </style>
    </head>
    <body class="container-fluid" ng-app="userHome">
        <!--        --------------------------------------------side bar content ----------------------->
        <jsp:include page="homeMenu.jsp"></jsp:include> 
            <div id="model"></div>
            <div class="Mybanner top" id ="testBannerDiv">
                <h1 class="myWaterMark"> Youth political forum  </h1>
            </div>
            <div class="MyForeground" ng-controller="foregroundController">
                <div class="imageFrame width100 height150" >
                    <div class="Myprofile " id="dpContainer" style="background:url('/getDP/${userId}');background-size: 100% 100%">

                    <label class="dp-options">
                        <form id="dp-form"><input class="file-input" type="file" onchange="angular.element(this).scope().viewImage(this);" id="dp" name="dp"></form>
                        <span class="glyphicon glyphicon-picture"></span>    
                    </label>
            
            <label class="dp-options" ng-click="popupModelWithDp();">
                        <span class="glyphicon glyphicon-resize-full"></span>    
                    </label>
                </div>
            </div>
            <p class="myName"> ${userName} </p>
        </div>
        <div ng-controller="userHomeParent">
            <div class="row">
                <div class="profilePost">
                    <div class="col-xs-9 col-md-9 col-sm-9 btn-group-lg">
                        <button id="id2" class="mybutton Myhvr" ng-click="gotoAbout()"> About </button>
                        <button id="id3" class="mybutton Myhvr" ng-click="loadTimeline()"> Time line </button>
                        <button id="id4" class="mybutton Myhvr" ng-click="loadFolloers()"> Followers </button>
                    </div>
                    <div class="col-xs-3 col-md-3 col-sm-3 floatRight">
                        <div id="imgBlock" class="MyProgress">
                            <input type="hidden" id="userId" value="${userId}" ng-model="userId" />
                            <img class="ImgProgress" src="resources/Images/Loading.gif" alt=""/>
                        </div>
                        <button id="id2" class="mybutton Myhvr " data-toggle="modal" data-target="#modal" >Post a new story</button>
                    </div>
                </div>
            </div>
            <!--    ------------------------------------------------------main content -------------------------->
            <div class="row rowAdjuster ">
                <hr>

                <div id="rightSidePane" class="col-lg-4 col-xs-4 col-md-4 col-sm-4" style="margin-top: 10px;">
                    <div id="postsOfYou" ng-controller="leftPaneController">
                        <p class="text-danger">Last seen : time here!</p>
                        <p class="text-primary">Recent post of you!</p>
                        <ul>
                            <li><a href="#">List of posts hrer in list item</a></li>
                            <li><a href="#">List of posts hrer in list item</a></li>
                            <li><a href="#">List of posts hrer in list item</a></li>
                            <li><a href="#">List of posts hrer in list item</a></li>
                        </ul>
                        <p class="text-warning">Trending in blog! </p>
                        <ul>
                            <li><a href="#">List of trending posts in list item</a></li>
                            <li><a href="#">List of posts 1 in list item</a></li>
                            <li><a href="#">List of posts 2 in list item</a></li>
                            <li><a href="#">List of posts 3 in list item</a></li>
                        </ul>
                    </div>
                </div>
                <!-------------------------------------------------------------------------Content blcok===========--->
                <div class="col-lg-8 col-xs-8 col-md-8 col-sm-8 form-group content">
                    <div id="alertMsg" style="color: red;font-size: 18px;"></div>
                    <!--                ---------------------------------------  dynamic body comment ------>
                    <h2>${view} POSTS:</h2>
                    <div id="postUploadResult"></div>
                    <div id="ContentPane" ng-controller="postContainer">

                    </div>


                </div>
                <!-----------------------------------------------------Model data -------------------->


                <div  id="modal" class="modal fade" tabindex="-1" aria-hidden="true" aria-labelledby="testIDmodel">
                    <div class="modal-dialog" ng-controller="postUploader">
                        <form id="CreatePost" enctype="multipart/form-data">
                            <div class="modal-content">
                                <div class="modal-header" id="testIDmodel">
                                    Share your story
                                    <button class="class pull-right" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body commentButton file has-name">
                                    <label class="file-label is-16by9">
                                        <input class="file-input" type="file" multiple accept="image/*" name="image">
                                        <span class="file-cta">
                                            <span class="file-icon">
                                                <i class="glyphicon glyphicon-picture"></i> 
                                            </span>
                                        </span>
                                    </label>
                                    <span >
                                        <textarea id="title" class="in input-sm myTextArea" required="required" name="caption"></textarea>
                                    </span>
                                    <div id="uploadBlock"></div>
                                </div>
                                <div class="modal-footer">
                                    <button id="submitP" ng-click="submit()" class="mybutton Myhvr" data-dismiss="modal">Submit</button>
                                    <button id ="cancel"  data-dismiss="modal">cancel</button><br>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>                         

                <!-- ---------------------------------------------------right side block --------------->
            </div>
        </div>
    </body>
</html>































