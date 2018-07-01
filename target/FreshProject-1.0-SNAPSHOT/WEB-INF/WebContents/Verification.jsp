<%-- 
    Document   : Verification
    Created on : 23 Feb, 2018, 7:05:39 PM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verification page</title>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/bulma-0.6.2/css/bulma.css" rel="stylesheet" type="text/css"/>
        <link href="resources/semantic/semantic.min.css" rel="stylesheet" type="text/css"/>
    </head>
    <body ng-app="verification">
        <div class="form" id="verificationForm">
            <h1><center>Welcome to our forum</center></h1>
            <input type="text" id="password" name="password" required="required" placeholder="Enter your password" class="form-control"/>
            <input type="text" id="conformpassword" required="required" placeholder="Confirm your password here!" class="form-control"/>
            <select name="secQstn" ng-model="model.selectedIndex" ng-options="index as qstn for  (index, secQstn)in secQstns"></select>
            <input type="text" name="answerToSecQstn" class="form-control" required="required" placeholder="your answer!">
        </div>
    </body>
</html>
