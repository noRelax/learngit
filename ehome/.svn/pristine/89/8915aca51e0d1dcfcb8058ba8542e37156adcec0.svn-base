
var dataTable;
// (function() {
// debugger;
dataTable = function() {
	'use strict';
	return {
		init : function() {
			// console.log(arguments)
			var fkGroupId = jQuery('input[name=fkGroupId]').val();
			var submitBtn = jQuery('button[name=submitAuth]');
			var cancelBtn = jQuery('button[name=cancelAuth]');
			var closeBtn = jQuery('button[name=close]');
			var searchBtn = jQuery('button[name=search]');
			var resetBtn = jQuery('button[name=reset]');
			var searchForm = jQuery('form[name=searchForm]');
			resetBtn.on('click',handlerResetClick);
			searchBtn.on('click',handlerSearchClick);

			// 初始化表格对象
			var oTable = $('#data-tableList')
					.dataTable(
							{
								"iDisplayLength" : 10,
								"sDom" : "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
								"sPaginationType" : "bootstrap",
								"bLengthChange" : false,
								"bPaginite" : true,
								"oLanguage" : {
									"sSearch" : "搜索",
									"sZeroRecords" : "没有检索到数据",
									"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
									"sInfo" : "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
									"sInfoEmtpy" : "没有数据",
									"sProcessing" : "正在加载数据...",
									"oPaginate" : {
										"sFirst" : "首页",
										"sPrevious" : "前一页",
										"sNext" : "后一页",
										"sLast" : "末页"
									}
								},
								"aoColumns" : [

									    {
											"mData" : "lifeName"
										}, {
											"mData" : "isIndexName"
										}, {
											"mData" : "createTime"
										}, {
											"mData" : "pv"
										}, {
											"mData" : "appPv"
										}, {
											"mData" : "pvPercent"
										}, {
											"mData" : "uv"
										}, {
											"mData" : "appUv"
										}, {
											"mData" : "uvPercent"
										}

								],
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false, // 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data", // 是服务器分页的标志，必须有
								"sAjaxSource" : "querydatalist",
								

								"fnServerData" : getRoleGrid1Data

							});
			jQuery('table[id=data-tableList]').width('100%');

			var list = jQuery('table[id=data-tableList]');

	
			function handlerSearchClick(e){
				e.preventDefault();
				oTable.fnDraw();
			}
	
			function handlerResetClick(e){
				e.preventDefault();
				searchForm.clearForm();  
			}
			// 表格回调函数
			function getRoleGrid1Data(url, aoData, fnCallback) {
				var start = aoData[3].value;
				var rows = aoData[4].value;
				var page = (start / rows) + 1;
				var startTime = jQuery('input[name=datetimeOne]').val();
				var endTime = jQuery('input[name=datetimeTwo]').val();
				var lifeName = jQuery('input[name=lifeName]').val();
				
				$.ajax({
					url : url,
					data : {
						"lifeName" : lifeName,
						"startTime" : startTime,
						"endTime" : endTime,
						"sEcho" : aoData[0].value,
						"rows" : rows,
						"page" : page
					},
					async : false,
					type : 'POST',
					dataType : 'json',
					success : function(result) {

						fnCallback(result.data);// 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的

					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("status:" + XMLHttpRequest.status
								+ ",readyState:" + XMLHttpRequest.readyState
								+ ",textStatus:" + textStatus);
					}
				});
			}

		
		}
	};
}();
// })(jQuery);
