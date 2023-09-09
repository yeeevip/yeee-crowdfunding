$(document).ready(function(){
    $(".login_btn").on('click', function () {
        $("#resMsgID").html('')
        HttpRequest.postForm(API_BASE_URL + '/api/cf/user/login', {
            'username': $("#Account").val(),
            'password': $("#Password").val(),
            'code': $("#ImgCode").val()
        }).then(function(res) {
            // 将token存储到本地
            localStorage.setItem('crowdfunding-token', JSON.stringify(res.data))
            // 请求成功后跳转到首页
            location.href = '/'
        }).catch(function(res) {
            $("#resMsgID").html(res.message);
        });


    })
})