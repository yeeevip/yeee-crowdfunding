/**
 * 
 */

// const STATIC_BASE_URL = '/'

var API_BASE_URL = ''

function getProvince(pid,obj){
	$.get("/api/cf/general/region?pid="+pid,function(res){
		data = res.data
		obj.find("option").remove();
		obj.append('<option value="-1">请选择</option>');
		for(var i in data){
			obj.append('<option value="'+data[i].id+'">'+data[i].name+'</option>');
		}
	});
}

function handleNull(val) {
	return val ? val : ""
}

function getQueryVariable(variable) {

	var query = window.location.search.substring(1);

	var vars = query.split("&");

	for (var i=0;i<vars.length;i++) {

		var pair = vars[i].split("=");

		if(pair[0] == variable){return pair[1];}

	}

	return('');

}



var HttpRequest = (function () {
	function postForm(url, data, headers) {
		return new Promise(function(resolve, reject) {
			$.ajax({
				type: 'POST',
				async: false,
				url: url,
				data: data,
				headers: headers,
				dataType: 'json',
				success: function(res) {
					if (res.code == 401) {
						localStorage.removeItem('crowdfunding-token')
						window.location.href = "/pages/front/public/login.html"
					} else if (res.code != 200) {
						reject(res);
					} else {
						resolve(res);
					}
				},
				error: function(xhr, status, error) {
					reject(error);
				}
			});
		});
	}
	function postJson(url, data, headers) {
		return new Promise(function(resolve, reject) {
			$.ajax({
				type: 'POST',
				async: false,
				url: url,
				contentType: "application/json;charset=utf-8",
				data: data,
				headers: headers,
				dataType: 'json',
				success: function(res) {
					if (res.code == 401) {
						localStorage.removeItem('crowdfunding-token')
						window.location.href = "/pages/front/public/login.html"
					} else if (res.code != 200) {
						reject(res);
					} else {
						resolve(res);
					}
				},
				error: function(xhr, status, error) {
					reject(error);
				}
			});
		});
	}
	function getRequest(url, headers) {
		return new Promise(function(resolve, reject) {
			$.ajax({
				type: 'GET',
				async: false,
				url: url,
				dataType: 'json',
				headers: headers,
				success: function(res) {
					if (res.code == 401) {
						localStorage.removeItem('crowdfunding-token')
						window.location.href = "/pages/front/public/login.html"
					} else if (res.code != 200) {
						reject(res);
					} else {
						resolve(res);
					}
				},
				error: function(xhr, status, error) {
					reject(error);
				}
			});
		});
	}
	return {
		postForm: postForm,
		postJson: postJson,
		getRequest: getRequest
	}
})()