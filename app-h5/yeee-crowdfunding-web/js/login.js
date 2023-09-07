
var httpRequest = require('./http-request');

$(document).ready(function(){
    $(".login_btn").on('click', function () {
        $("#resMsgID").html('')
        debugger
        postForm(API_BASE_URL + '/api/cf/user/login', {
            'username': $("#Account").val(),
            'password': $("#Password").val(),
            'code': $("#ImgCode").val()
        }).then(function(data) {
            // 将token存储到本地
            localStorage.setItem('crowdfunding-token', JSON.stringify(data))
            // 请求成功后跳转到首页
            location.href = '/'
        }).catch(function(error) {
            $("#resMsgID").html(error);
        });


    })
})