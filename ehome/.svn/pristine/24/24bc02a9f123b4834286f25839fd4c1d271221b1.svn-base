(function(){
	var loginName = jQuery('input[name=loginName]').val();
	var password = jQuery('input[name=password]').val();
	var loginForm = jQuery('form[name=loginForm]');
	var submitBtn = jQuery('input[name=submitBtn]');
	loginForm.validate({
		errorClass: "error",
		focusInvalid : false,
		onkeyup : false,
		submitHandler : function() {
			loginForm.ajaxSubmit({
				dataType : "json",
				type:'post',
				success : function(data) {
					if (data.status == 10000) {
						window.location.href="admin/index.html";
					} else {
						 alert(data.data);
					}
				}
			});
		},
		rules : {
			loginName : {
				required : true
			},
			password : {
				required : true
			}
		},
		messages : {
			loginName : {
				required : "<font color='red'>姓名不能为空</font>",
			},
			password : {
				required : "<font color='red'>密码不能为空</font>",
				rangelength : $.format("<font color='red'>密码最小长度:{0}, 最大长度:{1}。</font>")
			}
		}
	});
})(jQuery);