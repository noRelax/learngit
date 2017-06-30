(function(){
	// ajax加载时刷新提示
	jQuery(window).ajaxSend(function(evt, request, settings) {
		window.parent.layer.load();
	}).ajaxStop(function() {
		window.parent.layer.closeAll('loading');
	}).ajaxError(function(event, request, settings) {
		window.parent.layer.closeAll('loading');
		window.parent.layer.alert("服务器未响应", {
			title : '错误提示',
			icon : 2
		});
	});
	// 当窗口失败时提示
	jQuery(window).error(function(sMessage, sUrl, sLine) {
		window.parent.layer.closeAll('loading');
		window.parent.layer.alert("内部发生异常，请刷新页面" + sMessage, {
			title : '错误提示',
			icon : 2
		});
	});	
	var dataForm = jQuery("form[name=form]");//excel表单对象
	var dataBtn = jQuery('input[name=dataBtn]');
 	// 表单验证和提交
	dataForm.data('isSubmit', false);
	dataForm.validate({
		onclick:false,
		onkeyup:false,
		onfocusout:false,
        showErrors: function (errorMap, errorList) {
        	if(errorList[0]){
            	errorList[0].element.focus();
            	layer.tips(errorList[0].message, errorList[0].element);
        	}
        },
		submitHandler: function() {
			if(dataForm.data('isSubmit')) return;
			dataForm.ajaxSubmit({
				beforeSubmit : function() {
					if(jQuery('input[name=file]').val()==null ||jQuery('input[name=file]').val()==''){
						parent.layer.alert('请选择文件', {
			    			title : '错误提示',
			    			icon : 2
			    		});
						return false;
					}
					dataForm.data('isSubmit', true);
					parent.layer.closeAll('loading');
				},
				success : function(data) {
					dataForm.data('isSubmit', false);
					data=jQuery.parseJSON(data);
					if(data.status == 10000){
						data=data.data;
						if(data.success){
							parent.layer.alert('导入成功!', {title : '操作提示', icon : 1,time:2000,end:function(){
								parent.layer.closeAll();
								window.parent.location.reload();
							}});
					    }else if(data.error){
							dataBtn.removeClass("layui-btn-disabled");
							parent.layer.alert(data.error, {title : '操作提示', icon : 2});
						}else{
							parent.layer.open({
							    content: '校验到有身份证号码已存在,请问是否覆盖更新?'
								  ,btn: ['确定', '取消']
								  ,yes: function(index, layero){
									  $.post('../member/saveTemp',{},function(data){
										  if(data.status == 10000){
												parent.layer.alert('操作成功', {title : '操作提示', icon : 1, time:2000, end: function(){
													parent.layer.closeAll();
													window.parent.location.reload();
												}});
										  }else{
											  parent.layer.alert(data.data,{title : '操作提示',icon: 2});
										  }
									  },'json');
								  },btn2: function(index, layero){
									  $.post('../member/clearTemp',{},function(data){
										  if(data.status == 10000){
												parent.layer.alert('操作成功', {title : '操作提示', icon : 1, time:2000, end: function(){
													parent.layer.closeAll();
													window.parent.location.reload();
												}});
										  }else{
											  parent.layer.alert(data.data,{title : '操作提示',icon: 2});
										  }
									  },'json');
								  }
								  ,cancel: function(){ 
								  }
								});
						}
					} else {
						parent.layer.alert(data.message, {
			    			title : '错误提示',
			    			icon : 2
			    		});
					}
				},
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	dataForm.data('isSubmit', false);
					parent.layer.closeAll('loading');
		        	parent.layer.alert('机器人在忙，请稍候重试');
		        }
			});
		}
	});
})(jQuery);