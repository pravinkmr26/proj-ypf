<%-- 
    Document   : HomePage
    Created on : 9 Mar, 2018, 9:18:00 PM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
         <div class="columns">
                <div class="column is-two-fifths">

                </div>
                <div class="column is-three-fifths paddingLeft fixCarousel">
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                            <li data-target="#myCarousel" data-slide-to="3"></li>
                            <li data-target="#myCarousel" data-slide-to="4"></li>
                            <li data-target="#myCarousel" data-slide-to="5"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <img src="resources/Images/carousel/img1.jpg" class="fixImageCarousesl" alt=""/>
                            </div>

                            <div class="item">
                                <img src="resources/Images/carousel/img2.jpg" class="fixImageCarousesl" alt=""/>
                            </div>

                            <div class="item">
                                <img src="resources/Images/carousel/img5.jpg" class="fixImageCarousesl" alt=""/>
                                <!--                                <div class="carousel-caption">
                                                                    <h3>New York</h3>
                                                                    <p>We love the Big Apple!</p>
                                                                </div>-->
                            </div>
                            <div class="item">
                                <img src="resources/Images/carousel/img6.jpg" class="fixImageCarousesl" alt=""/>
                            </div>
                            <div class="item">
                                <img src="resources/Images/carousel/img8.jpg" class="fixImageCarousesl" alt=""/>
                            </div>
                            <div class="item">
                                <img src="resources/Images/carousel/img9.jpg" class="fixImageCarousesl" alt=""/>
                            </div>
                        </div>

                    </div>

                </div>

            </div>
    </body>
</html>
