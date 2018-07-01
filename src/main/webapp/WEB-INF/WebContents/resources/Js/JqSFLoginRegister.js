/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    //$('#RegistrationForm').ajaxForm();//---------------------------------------------------------replace with ajax 
    $('#signUp').click(function () {
        $('#Register').css("display", "inline-block");
    });
    $body = $('body');
    

    $('#login').click(function () {
        $body.addClass("modaal");
        document.getElementById('login').disabled = true;
        event.preventDefault();
        var user = document.getElementById('uId').value;
        if (test(user)) {
            var userId = btoa(user);
            var password = btoa(document.getElementById('pass').value);
            $.ajax({
                url: '/signIn',
                method: 'POST',
                data: {'userId': userId, 'password': password},
                enctype: 'application/json',
                success: function (data) {
                    if (data === 'ok') {
                        window.location = "/profile";
                    } else {
                        alert('invalid user id or password');
                        document.getElementById('uId').value = "";
                        document.getElementById('pass').value = "";
                    }
                    document.getElementById('login').disabled = false;
                    $body.removeClass("modaal");
                },
                error: function () {
                    $body.removeClass("modaal");
                    alert("failed to establish secure connection (or) please check your internet");
                    document.getElementById('login').disabled = false;
                }
            });
        } else {
            $body.removeClass("modaal");
            alert("invalid email id or mobile number");
            document.getElementById('login').disabled = false;
        }
        $body.removeClass("modaal");

    });
    function test(userid) {
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        var rp = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s\.]{0,1}[0-9]{3}[-\s\.]{0,1}[0-9]{4}$/;
        if (re.test(String(userid).toLowerCase())) {
            return true;
        } else if (rp.test(String(userid).toLowerCase())) {
            return true;
        } else
            return false;
    }
});
