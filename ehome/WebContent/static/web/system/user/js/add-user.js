(function() {
	// ajax加载时刷新提示
	// jQuery(window).ajaxSend(function(evt, request, settings) {
	// }).ajaxStop(function() {
	// }).ajaxError(function(event, request, settings) {
	// });
	//
	// // 当窗口失败时提示
	// jQuery(window).error(function(sMessage, sUrl, sLine) {
	// });
	var form = jQuery('form[name=basicForm]');
	var submitBtn = jQuery('input[name=submitBtn]');
	var cancelBtn = jQuery('input[name=cancelBtn]');

	var id=jQuery('input[name=id]');
	var userType = jQuery('select[name=userType]');
	var userAccount = jQuery('input[name=userAccount]');
	var userPassword = jQuery('input[name=userPassword]');
	var confirmUserPassword = jQuery('input[name=confirmUserPassword]');
	var userName = jQuery('input[name=userName]');
	var userMobile = jQuery('input[name=userMobile]');
	var userEmail = jQuery('input[name=userEmail]');
	var userDesc = jQuery('input[name=userDesc]');
	var orgainId = jQuery('input[name=orgainId]');

	// submitBtn.on('click',function(e){
	// e.preventDefault();
	// jQuery.post('addUser',{
	// 'userType':userType.val(),
	// 'userAccount':userAccount.val(),
	// 'userPassword':userPassword.val(),
	// 'confirmUserPassword':confirmUserPassword.val(),
	// 'userName':userName.val(),
	// 'userMobile':userMobile.val(),
	// 'userEmail':userEmail.val(),
	// 'userDesc':userDesc.val()
	// },function(data){
	// if(data.code == '10000'){
	// alert('新增用户成功!');
	// window.location.reload();
	// }else{
	// alert('系统出错了,请检查数据并重试!');
	// }
	// },'json');
	// });

	cancelBtn.on('click', function(e) {
		e.preventDefault();
        var closeBtn = jQuery('button[name=close]');
        closeBtn.click();
	});

	form.validate({
		errorClass: "label.error", //默认为错误的样式类为：error
		focusInvalid : false, // 当为false时，验证无效时，没有焦点响应
		onkeyup : false,
		submitHandler : function() { // 表单提交句柄,为一回调函数，带一个参数：form
			//form.submit(); //提交表单
			form.ajaxSubmit({
				dataType : "json",
				success : function(data) {
					if (data.status == 10000) {
						if(id!=null && id!='')
							alert('修改用户成功!');
						else
						    alert('新增用户成功!');
						window.location.href="index.html";
					} else {
						// alert('系统出错了,请检查数据并重试!');
					}
				}
			});
		},
		rules : {
			userType : {
				required : true
			},
			userAccount : {
				required : true
			},
			userPassword : {
				required : true,
				rangelength : [ 3, 10 ]
			},
			confirmUserPassword : {
				required : true,
				equalTo : "#userPassword"
			},
			userName : {
				required : true
			},
			userMobile : {
				required : true
			},
			userEmail : {
				required : true,
				email : true
			}
		},
		messages : {
			userType : {
				required : "<font color='red'>帐号类型不能为空</font>",
			},
			userAccount : {
				required : "<font color='red'>登录帐号不能为空</font>",
			},
			userName : {
				required : "<font color='red'>姓名不能为空</font>",
			},
			userMobile : {
				required : "<font color='red'>手机号码不能为空</font>",
			},
			userEmail : {
				required : "<font color='red'>邮箱不能为空</font>",
				email : "<font color='red'>邮箱格式不正确</font>"
			},
			userPassword : {
				required : "<font color='red'>密码不能为空</font>",
				rangelength : $.format("<font color='red'>密码最小长度:{0}, 最大长度:{1}。</font>")
			},
			confirmUserPassword : {
				required : "<font color='red'>密码不能为空</font>",
				equalTo : "<font color='red'>两次密码输入不一致</font>"
			}
		}
	});

})(jQuery);
