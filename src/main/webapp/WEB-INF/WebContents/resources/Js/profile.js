/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
     
    $('#fetchImage').click(function () {
       
        $.ajax({
            method: 'GET',
            url: '/loadImage',
            success: function (result) {
                var base64 = btoa(String.fromCharCode.apply(null, new Uint8Array(result.image)));
                document.getElementById('imageTag').src = 'data:' + result.contentType + ';base64,' + base64;
                //$('#ImageBlock').append("<p>gt imamge name " + result.fileName + "</p>");
            },
            error: function () {
                alert("failed to load image");
                
            }
        });
    });
    $('#redirect').click(function(){window.location.href='/blog';});
    $.ajax({method: 'GET',
        url: '/loadPost',
        success: function (result) {
            var item = result;
            for (var i = 0; i < item.length; i++) {
                $('#div').append("<h3> PostId " + item[i].postId + " userID " + item[i].userId + "</h3>");
            }
        },
        error: function (result) {
            alert('error' + "add");
        }
    });
});

