<%-- 
    Document   : errorPage
    Created on : 23 Mar, 2018, 8:16:48 AM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error!..</title>
        <style>
            .backgroundImage{
                background: linear-gradient(rgb(100,80,150,0.9),rgb(100,100,100,0.9))fixed no-repeat;
                text-align: center;
                vertical-align: middle;
            }
        </style>
    </head>
    <body class="backgroundImage">
        <h1>Oops!.........An ERROR.</h1>
        <h4>${Error}</h4>
        <h2>Please try again !</h2>
        
        <h3><a href="/home">Login</a></h3>
    </body>
</html>
