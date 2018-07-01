<%-- 
    Document   : ImageLoad
    Created on : 13 Dec, 2017, 10:00:22 PM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Image upload</title>
        <script src="resources/Angular/angular.min.js" type="text/javascript"></script>
    </head>
    <body>
        <form method="POST" action="/saveImage" enctype="multipart/form-data">
            <input type="file" name="image" required="required"/>
            <input type="text" name="caption"/><br>
            <input type="submit" value="submit">
        </form>
    </body>
</html>
