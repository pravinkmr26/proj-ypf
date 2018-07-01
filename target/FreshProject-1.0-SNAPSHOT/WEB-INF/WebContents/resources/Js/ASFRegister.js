/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$body = $("body");
$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
     ajaxStop: function() { $body.removeClass("loading"); }    
});


var app = angular.module('loginReg', []);
app.controller('registration', function ($scope, $http) {
    $scope.submit = function () {
        $body = $("body");
        $body.addClass("loading");
        var form = document.getElementById('RegistrationForm');
        //event.preventDefault();
        var urlLink = '/registerUserDetails';
        $http({method: 'POST',
            url: urlLink,
            data: new FormData(form),
            enctype: 'multipart/form-data',
            headers: {
                'process-Data': false,
                'content-Type': undefined
            }
        }).then(function (response) {
            console.log(response);
            
            if (response.data[0] == "YPFCERR_ALREADY_USER") {
                alert("already user");
            }
            else if (response.data[0] != "") {
                var result = response.data[0];
                $('#RegistrationContent').toggleClass("col-md-offset-7");
                $('#RegistrationContent').toggleClass("col-md-offset-4");
                $('#RegistrationContent').html("");
                $('#RegistrationContent').html("<span class=\"w3-display-topright myExit\" onclick=\"this.parentElement.parentElement.style.display = 'none'\"><i class=\"glyphicon glyphicon-remove\"></i></span><h2>Please Check your mail for the otp and verification link.</h2><h3>You may use the verification link or <a href=/verify/" + result + "> click here</a> to proceed with the OTP.</h3>");
            }else{
                $('#RegistrationContent').html("");
                $('#RegistrationContent').html("<span class=\"w3-display-topright myExit\" onclick=\"this.parentElement.parentElement.style.display = 'none'\"><i class=\"glyphicon glyphicon-remove\"></i></span><h2>Error Please try again..</h2>");
            }
        }).catch(function (response) {
           window.location = '/errorPage';
        }).finally(function () {
            $body.removeClass("loading");
            clearRegistrationForm();
            console.log("from do like comment");
        });
    };
    function clearRegistrationForm() {
        $('#RegistrationForm').val("");
    }

});


  