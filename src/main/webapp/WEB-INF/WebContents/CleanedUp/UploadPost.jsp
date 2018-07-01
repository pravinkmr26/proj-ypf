<%-- 
    Document   : UploadPost
    Created on : 22 Feb, 2018, 2:39:09 PM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload your Post</title>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/bulma-0.6.2/css/bulma.css" rel="stylesheet" type="text/css"/>
        <script src="resources/Angular/angular.min.js" type="text/javascript"></script>
        <script src="resources/Js/angularSericeTwo.js" type="text/javascript"></script>
    </head>
    <body ng-app="PostUploader">
        <form id="CreatePost" enctype="multipart/form-data" ng-controller="uploadPost">
            <p> Post title:   <input type="text" name="caption" required="required" placeholder="title here!" class="form-control"/></p>
            <p> Post Description  <textarea type="text" rows="5" name="description" required="required" placeholder="describe your post " class="form-control"></textarea></p>
            <p> upload images: <input type="file" accept="image/*" name="image" class="form-control upload button" value="select an image"/> </p>
            <input type="submit" value="save" class="btn-primary  button is-primary" ng-click="submit()" />
            <p id="result" ng-model="result">{{result}}</p>
        </form>
        
    </body>
</html>
