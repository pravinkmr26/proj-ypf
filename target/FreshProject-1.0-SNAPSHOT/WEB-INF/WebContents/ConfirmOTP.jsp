<%-- 
    Document   : ConfirmOTP
    Created on : 21 Mar, 2018, 4:23:46 PM
    Author     : Pravinkumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome,</title>
        <link rel="icon" href="http://example.com/favicon.png">
        <link href="/resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/w3/w3css.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/semantic/semantic.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/Css/styles.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/bulma-0.6.2/css/bulma.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/Css/MyCSS.css" rel="stylesheet" type="text/css"/>
        <link href="/resources/Css/style.css" rel="stylesheet" type="text/css"/>
        <style>
            .BlurBackground{
                /*                 z-index: 9999;  
                                background: linear-gradient(rgb(23,49,68),rgb(38,0,22));*/
                background:url('/resources/Images/blackbg.jpg') fixed;
                background-size: 100%;
                width: 100%;
                height:100%;
/*                filter:blur(6px);*/
                position: fixed;
                display: flex;
                top:0px;
                left:0px;
            }
            .Fore-content{
                background:url('/resources/Images/blackbg.jpg') fixed;
                background-size: 100%;
                margin: 50px 0px 0px 50px;
                z-index: 9999;
                width: 100%;
                color:white;
                font-size: 1em;
                font-family: sans-serif;
            }
            .title{
                color: #3affff;
            }
            .button{
                padding: 2px 20px 2px 20px;
                font-size: 1.2em;
            }
            .button:hover{
                background-color: transparent;
                color: white;
            }
        </style>
        <script>
           
            function function2(){
                event.preventDefault();
                document.formotp.action = '/verify/'+(document.getElementById('userId').value).toString().trim()+'/'+(document.getElementById('otp').value).toString().trim();
                console.log(document.formotp.action);
            }
        </script>
            
        
    </head>
    <body class="BlurBackground">
        <div class="Fore-content col-md-offset-3 col-md-6 ">
        <h1>Welcome to Youth Political Forum</h1>
        <p>Dear ${userName},<br>
        <form method="POST" name ="formotp">
            <input type="hidden" value="${userId}" text="${userId}" id="userId" name="userId"/>
            <p>OTP :</p><input id="otp" name="otp" onkeyup="function2()" onmouseout="function2()" onchange="function2()" required type="text" class="text-danger form-control input-lg w3-animate-opacity w3-animate-left" placeholder="One time password"><br>
        
       <input type="submit" class="input-lg btn has-text-danger w3-text-light-gray button" value="submit">
        </form>
        
        </div>
        
        </div>
    </body>
</html>
