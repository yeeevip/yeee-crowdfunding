

export function postForm(url, data) {
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: 'POST',
            async: false,
            url: url,
            data: data,
            dataType: 'json',
            success: function(res) {
                if (res.code == 401) {
                    window.location.href = "/pages/front/public/login.html"
                } else if (res.code != 200) {
                    reject(res.message);
                } else {
                    resolve(res.data);
                }
            },
            error: function(xhr, status, error) {
                reject(error);
            }
        });
    });
}