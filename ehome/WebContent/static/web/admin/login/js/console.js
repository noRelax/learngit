//(function (){
//	
//	// ajax加载时刷新提示
//	jQuery(window).ajaxSend(function(evt, request, settings) {
//		window.parent.layer.load();
//	}).ajaxStop(function() {
//		window.parent.layer.closeAll('loading');
//	}).ajaxError(function(event, request, settings) {
//		window.parent.layer.closeAll('loading');
//		window.parent.layer.alert("服务器未响应", {
//			title : '错误提示',
//			icon : 2
//		});
//	});
//	// 当窗口失败时提示
//	jQuery(window).error(function(sMessage, sUrl, sLine) {
//		window.parent.layer.closeAll('loading');
//		window.parent.layer.alert("内部发生异常，请刷新页面" + sMessage, {
//			title : '错误提示',
//			icon : 2
//		});
//	});
//})(jQuery);
(function(){

})(jQuery);