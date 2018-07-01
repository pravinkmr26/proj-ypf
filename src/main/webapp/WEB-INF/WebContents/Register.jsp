<%-- 
    Document   : Register
    Created on : 23 Feb, 2018, 12:47:06 PM
    Author     : Pravinkumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link href="resources/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="resources/bulma-0.6.2/css/bulma.css" rel="stylesheet" type="text/css"/>
        <link href="resources/semantic/semantic.css" rel="stylesheet" type="text/css"/>
        <script src="resources/Jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="resources/Js/plugins/form.js" type="text/javascript"></script>
        <script src="resources/Js/JQueryServiceOne.js" type="text/javascript"></script>
    </head>
    
    <body ng-app="register">
        <div class="col-md-5 center-block" ng-controller="registerFormController" ng-init="showHide=true;enableDisbale=false;">
            <form action="/registerUserDetails" method="POST" enctype="application/json" id="RegistrationForm" ng-show="showHide" ng-disabled="enableDisable">
                <input type="text" ng-model="name" name="name" required="required" placeholder="Enter name" class="form-control"/>
                <input type="text" ng-model="gender" name="gender" required="required" placeholder="Enter gender" class="form-control"/>
                <input type="text" ng-model="age" name="age" required="required" placeholder="Enter age" class="form-control"/>
                <input type="text" ng-model="aadhaar" name="aadhaar" required="required" placeholder="Enter aadhar" class="form-control"/>
                <input type="text" ng-model="mobile" name="mobile" required="required" placeholder="Enter mob" class="form-control"/>
                <input type="email" ng-model="email" class="form-control" name="emailId" id="email" placeholder="Your Email" data-rule="email" data-msg="Please enter a valid email" required="required"/>
                <input type="submit" id="ButtonRegister" ng-click="submit()" value="save" formnovalidate="formnovalidate" class="btn-primary btn-lg"/>
            </form>
        </div>
    </body>
</html>



<!--kj

 <form enctype="multipart/form-data" id="RegistrationForm" ng-show="showHide" ng-disabled="enableDisable">
                <input type="text" ng-model="name" name="name" required="required" placeholder="Enter name" class="form-control"/>
                <input type="text" ng-model="gender" name="gender" required="required" placeholder="Enter gender" class="form-control"/>
                <input type="text" ng-model="age" name="age" required="required" placeholder="Enter age" class="form-control"/>
                <input type="text" ng-model="aadhaar" name="aadhaar" required="required" placeholder="Enter aadhar" class="form-control"/>
                <input type="text" ng-model="mobile" name="mobile" required="required" placeholder="Enter mob" class="form-control"/>
                <input type="email" ng-model="email" class="form-control" name="emailId" id="email" placeholder="Your Email" data-rule="email" data-msg="Please enter a valid email" required="required"/>
                <input type="button" ng-click="submit()" value="save" formnovalidate="formnovalidate" class="btn-primary btn-lg"/>

            </form>



                <input type="text"ng-model="name" name="name" class="input is-text"/>
                <input type="text" ng-model="gender" name="gender" class="input"/>
                <input type="email" ng-model="email" name="email" class="input"/>
                <button id="submit" ng-click="submit()">submit</button>
            </form>
lk-->