        /* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $('#CloseAlert').click(function(){
        //$('#AlertToShow').css('display','block');
        $('#AlertToShow').toggle();
    });
    $('#submitP').click(function (event) {
        event.preventDefault();
        $(this).prop('disabled', true);
        var req = $.ajax({
            url: '/savePost',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: new FormData(document.getElementById('CreatePost')),
            processData: false,
            contentType: false,
            cache: false,
            xhr: function () {
                //Get XmlHttpRequest object
                var xhr = $.ajaxSettings.xhr();
                //Set onprogress event handler 
                xhr.upload.onprogress = function (event) {
                    var perc = Math.round((event.loaded / event.total) * 100);
                    $('#progressBar').text(perc + '%');
                    $('#progressBar').css('width', perc + '%');
                };
                return xhr;
            },
            beforeSend: function(xhr){
                //Reset alert message and progress bar
                $('#alertMsg').text('');
                $('#progressBar').text('');
                $('#progressBar').css('width', '0%');
            }
        });
        req.done(function (data) {
            $('input[type=file]').val('');
            $('textarea').val('');
            $('#submitP').prop('disabled', false);
            $('#uploadBlock').html(data);
            $('#UploadSucess').css('display','inline');
          //  $('#modal').end();
            $('#modal').fadeOut(1200, function () {
                $('#modal').modal('hide');
//                $('#UploadSucess').hide();
//                $('#UploadSucess').css('display','inline-block');
            });
        });
        req.fail(function (jqXHR, textS) {
            alert(jqXHR.response + "  and :" + textS);
            $('#submitP').prop('disabled', false);
            $('#alertMsg').text(jqXHR.response.data);
        });
    });
});
