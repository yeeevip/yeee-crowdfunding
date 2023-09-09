$(document).ready(function(){
    $(".login_btn").on('click', function () {
        $("#resMsgID").html('')
        var password = $("#Password").val()
        var confirmPassword = $("#ConfirmPassword").val()
        if (password != confirmPassword) {
            $("#resMsgID").html("前后密码不一致")
            return
        }
        HttpRequest.postForm(API_BASE_URL + '/api/cf/user/register', {
            'username': $("#Account").val(),
            'password': password,
            'code': $("#ImgCode").val()
        }).then(function (res) {
            layer.confirm('注册成功，去登录？', {
                btn: ['确定'], //按钮
                closeBtn: 0
            }, function(){
                location.href = '/pages/front/public/login.html'
            });
        }).catch(function (res) {
            $("#resMsgID").html(res.message)
        })
    })
})