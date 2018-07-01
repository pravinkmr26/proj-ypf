/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
  $(document).ready(function () {
        
            var c = 0;
            $('#addnew').click(function () {
                if(c < 4)
                {
//                var x = document.createElement("INPUT");
//                x.setAttribute("type","file");
//                x.setAttribute("name","image"+ c );
//                $('#uploadBlock').before(x);
                    var x = '<input type="file" class=".btn btn-primary" name=Img' + c + '/>';
                    x += '<input type="text" class="form-control" name=txt' + c + '/>';
                    $('#uploadBlock').before(x);
                    c++;
                }else {
                    alert("nope");
                }
            });
            $('#image').change(function(event){
                 $('#hasImage').html  = event.target.files.length;
            });
            $('#submitP').click(function(){

                var Caption = $('#caption').val();
                var ob = new FormData();
                ob.append("image",$('#image').value);
                ob.append("hasImage",files.length);
                ob.append("caption",Caption);
                $.ajax({
                   url:'/savePost',
                   type:'POST',
                   enctype:'multipart/form-data',
                   data:ob,
                   processData: false,
                   contentType:false,
                   cache:false,
                   success:function(result){
                    $('#uploadBlock').append(result);
                    $('#postProgress').html("loading");
                   },error:function(data){
                       alert(data.data);
                   }
                   
                });
//                var xhr = $.ajaxSettings.xhr();
//                xhr.upload.onprogress = function(e)
            });
        });
 $(document).ready(function () {
                $('.MyProgress').hide();
                $('#formData').ajaxForm(function () {
                    alert('success');
                });
                
                
                
            });