/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('register', []).controller('registerFormController', function ($scope, $http) {
    $scope.submit = function () {
        $scope.enableDisable = false;
        var formdata = {'name':$scope.name,'gender':$scope.gener,'dob':$scope.age,'mobile':$scope.mobile,'aadhaar':$scope.aadhaar,'emailId':$scope.email};
        $http({
            url:'/registerUserDetails',
            method:'POST',
            data: formdata,
            headers:{
                'content-Type':'application/json'
            }
                    
        }).then(function(result){
            if(result.data.equals("ok")){
                alert("sucess");
            }else{
                alert("error");
            }
        });
    };
});
angular.module('verfication', []).controller('verificationController', function ($scope, $http) {
    $scope.secQstns = ['QstnOne', 'Qstn2', 'Qstn3', 'Qstn4', 'Qstn5'];
    $scope.submit = function () {
        document.getElementById("password").value = btoa(document.getElementById("password").value);
        $scope.enableDisable = false;
        var req = {
            url: '/verifyUserDetails',
            data: new FormData(document.getElementById('verificationForm')),
            enctype: 'multipart/form-data',
            headers: {
                'content-Type': undefined,
                'process-Data': false
            }
        };
        $http(req).then(function () {
            alert("registration success");
            $scope.showHide = false;
        }, function () {
            alert('failed');
        });
    };
});

/*
 * enctype: 'multipart/form-data',
            headers: {
                'process-Data': false,
                'content-Type': undefined,
                'cache': false
            }
        })
 */