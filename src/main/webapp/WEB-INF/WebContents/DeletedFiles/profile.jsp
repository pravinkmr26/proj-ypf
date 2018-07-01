<%--
   Document   : profile
   Created on : 12 Dec, 2017, 9:15:31 AM
   Author     : Pravinkumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="cc" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home page Profile</title>

        <link href="resources/Css/fontawesome-4.5.0.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="resources/Jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
        <script src="resources/Bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    </head>
    <script >
        $(document).ready(function () {
            var c = 0;
            $('#addnew').click(function () {
                if (c < 4)
                {
//                var x = document.createElement("INPUT");
//                x.setAttribute("type","file");
//                x.setAttribute("name","image"+ c );
//                $('#uploadBlock').before(x);
                    var x = '<input type="file" class=".btn btn-primary" name=Img' + c + '/>';
                    x += '<input type="text" class="form-control" name=txt' + c + '/>';
                    $('#uploadBlock').before(x);
                    c++;
                } else {
                    alert("nope");
                }
            });

        });
    </script>
    <body class="container-fluid" >
        <!--        --------------------------------------------side bar content ----------------------->
        <jsp:include page="menu.jsp"></jsp:include> 
        <div class="Mybanner top" id ="testBannerDiv">

        </div>
        <div class="MyForeground">
            <img src="resources/Images/male.png" class="Myprofile">
            <p> this is tile </p>
        </div>
        <div class="row">
            <div class="profilePost">
                <div class=" col-sm-6 btn-group-lg">
                    <button id="id1" class="mybutton Myhvr">Button 1 >></button>
                    <button id="id1" class="mybuttonLeft Myhvr">Button 2 >></button>
                    <button id="id1" class="mybutton Myhvr">Button 3 >></button>
                    <button id="id1" class="mybuttonLeft Myhvr">Button 4 >></button>
                </div>
                <div class="col-sm-6 floatRight">
                    <button id="id2" class="mybutton Myhvr " data-toggle="modal" data-target="#idt" >Post a new story</button>
                </div>

            </div>
        </div>
        <!--    ------------------------------------------------------main content -------------------------->
        <div class="row">
            <div class="col-md-3">
                <h2 class="text-danger"> title tags</h2><p id="stack" style="display:none;">0</p>

            </div>
            <!-------------------------------------------------------------------------Content blcok===========--->
            <div class="col-md-9 form-group content">

                <!--                ---------------------------------------  dynamic body comment ------>
                <h2>YOUR POSTS:</h2>
                <div class="comment">
                    <header>
                        <figure class="avatar"><img class="avImg" src="resources/Images/male.png" alt=""></figure>
                        <address>
                            By <a href="#"></a> contactHIm 
                        </address>
                        <time datetime="2045-04-06T08:15+00:00">Friday, 6<sup>th</sup> April 2045 @08:15:00</time>
                    </header>
                    <div class="comcont">
                        <p>This is an example of a comment made on a post. You can either edit the comment, delete the comment or reply to the comment. Use this as a place to respond to the post or to share what you are thinking.</p>
                    </div>
                    <button class="mybutton Myhvr" >Test button</button>
                    <button class="mybuttonLeft Myhvr" >Test button left</button>
                </div>


                <!--   --------------------------------------- /// dynamic body comment ------>

                <footer class="text-primary text-center">
                    <button class="btn btn-default">
                        <i class="fa fa-amazon"></i>Like
                    </button>
                    <button class="btn btn-default" >
                        <i class="fa fa-bluetooth"></i>comment
                    </button>
                    <button class="btn btn-default" data-toggle="modal" data-target="#idt" >
                        <i class="fa fa-facebook"></i>Share
                    </button>
                    <input type="file"  class="btn-default" name ="filename[]" multiple="multiple" />
                    <p><a href="/save" role="button" class="btn">goto Testing page</a></p>
                </footer>
            </div>
            <!-----------------------------------------------------Model data -------------------->
            <div id="idt" class="modal fade" tabindex="-1" aria-hidden="true" aria-labelledby="testIDmodel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header" id="testIDmodel">
                            Share your story
                            <button class="class pull-right" data-dismiss="modal">&times;</button>
                        </div>

                            <div class="modal-body">
<!--                                <input type="file" class="progress" accept="image/*" multiple="4" required="required" />-->
                                <button id="addnew" >Click here to add more buttons</button>

<!--                                <textarea class="in input-sm" style="width:100%;height:25%;" required="required" id="textbox"></textarea>-->
                                <div id="uploadBlock">

                                </div>
                            </div>
                            <div class="modal-footer">
                                <button id="submit" class="mybuttonLeft Myhvr">Submit</button>
                                <button id ="cancel" class="mybutton Myhvr" data-dismiss="modal">cancel</button>
                            </div>
<!--                        </form>-->
                    </div>
                </div>
            </div>                         
            <!-- ---------------------------------------------------right side block --------------->
        </div>
    </body>
</html>
