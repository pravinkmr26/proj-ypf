<%-- 
    Document   : Temp
    Created on : 14 Feb, 2018, 2:11:33 PM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/w3/w3css.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="resources/Css/font-awesome.css" rel="stylesheet" type="text/css"/>
        <script src="resources/Bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="resources/Jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="resources/Js/profile.js" type="text/javascript"></script>
    </head>
    <body > 
        <h1>Hello World!......${Attrib}</h1>
        <h1>
            <a href="/profile">redirect to profile</a>
        </h1>
        <div id="LoginForm" class="form-group-lg">
            <div class="row w3-display-topright w3-right">
                <form method="POST" action="/signIn">
                    <div id="formData" class="col-md-12">
                        <p>User ID</p><input name="userId" type="text" class="form-control input-lg w3-animate-right" placeholder="User ID"><br>
                        <p>Password</p><input name="password" type="password" class="text-danger form-control input-lg w3-animate-opacity w3-animate-left" placeholder="Password"><br>
                        <input type="submit" value="Login" class="mybutton Myhvr"/>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-md-5 center-block">
            <form action="/profile" modelAttribute="info">
                <input type="text" name="name" required="required" placeholder="Enter name" class="form-control"/>
                <input type="text" name="gender" required="required" placeholder="Enter gender" class="form-control"/>
                <input type="text" name="age" required="required" placeholder="Enter age" class="form-control"/>
                <input type="text" name="mobile" required="required" placeholder="Enter mob" class="form-control"/>
                <input type="text" name="aadhar" required="required" placeholder="Enter aadhar" class="form-control"/>
                <input type="text" name="userId" required="required" placeholder="Enter userID" class="form-control"/>
                <input type="email" class="form-control" name="email" id="email" placeholder="Your Email" data-rule="email" data-msg="Please enter a valid email" required="required"/>
                <input type="submit" value="save" formnovalidate="formnovalidate" class="btn-primary"/>
            </form>
        </div>
        <div id="div">
            
        </div>
        <br><br>
        <a href="/gotoImages" class="btn">Redirect to images</a><br>
        <a href="/blog" class="btn btn-danger">Redirect to Blogs</a>
        <a href="/Mongo" class="btn btn-success">mongo</a><br>
        <a href="/TestMongo" class="btn btn-success">JsonObject</a>
        <a href="/updatePostMongo" class="btn btn-success">UpdatePost</a>
        <a id="fetchImage" class="btn btn-success">MongoImageLoad</a><br>
        <a id="redirect" class="btn btn-success">javascript redirection</a><br>
        <a href="/loadPost" class="btn btn-group">Load Posts</a>
        <div id="ImageBlock">
            <!--<img id="dynamicImage"/>-->
            <img id="imageTag"/>
        </div>
    </body>
</html>
