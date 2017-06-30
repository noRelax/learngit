Date.prototype.format = function(value) {
	var format = 'yyyy-MM-dd HH:mm:ss';
	var t = new Date(value);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
		switch (a) {
		case 'yyyy':
			return tf(t.getFullYear());
			break;
		case 'MM':
			return tf(t.getMonth() + 1);
			break;
		case 'mm':
			return tf(t.getMinutes());
			break;
		case 'dd':
			return tf(t.getDate());
			break;
		case 'HH':
			return tf(t.getHours());
			break;
		case 'ss':
			return tf(t.getSeconds());
			break;
		}
	});
}

var applyFileTableList;
var count = 0;
// (function() {
// debugger;
applyFileTableList = function() {
	'use strict';
	return {
		init : function() {
			var _id = 0;
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
											"mData" : "name"
										}, {
											"mData" : "idCard"
										}, {
											"mData" : "phone"
										}, {
											"mData" : "applyFileTime"
										}, {
											"mData" : "upperOorgainName"
										}, {
											"mData" : null
										}

								],
								"bSort" : false,// 去掉点击标题刷新排序
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false, // 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data", // 是服务器分页的标志，必须有
								"sAjaxSource" : "getApplyFileList",

								"aoColumnDefs" : [
										{},

										{
										/*
										 * "mRender" : function(data, type,
										 * full) { if(!full.starttime) return
										 * ''; return new
										 * Date().format(full.starttime) },
										 * "aTargets" : [3]
										 */
										},
										{
											"mRender" : function(data, type,
													full) {
												var str = "";
												str += '<a name="check" class="btn btn-primary" href="javascript:;" data-id="'
														+ full.processInstanceId+ '" data-org="'+ full.orginzeType+ '"><span class="label">查阅归档详情</span></a>';

												return str;
											},

											"aTargets" : [ 5 ]
										},
										{
											"mRender" : function(data, type,
													full) {
												var str = "";
												str += '<a name="download" class="btn btn-success" data-id="'
													+full.processInstanceId+'">导出<span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>';
												return str;
											},

											"aTargets" : [ 6 ]
										} ],

								"fnServerData" : getRoleGrid1Data

							});
			jQuery('table[id=data-tableList]').width('100%');

			var checkList = jQuery('table[id=data-tableList]');

			// 审批事件
			checkList.on('click', 'a[name=check]', handlerCheckClick);
			// 下载事件
			 checkList.on('click', 'a[name=download]', handlerDownloadClick);

			 //导出
			 function handlerDownloadClick(e) {
				 e.preventDefault();
				 var id = jQuery(this).attr('data-id');
				 window.location.href = '../applyFile/checkExport/'+id
			 }
			 
			// 审批事件
			function handlerCheckClick(e) {
				e.preventDefault();
				var id = jQuery(this).attr('data-id');
				var orgType=jQuery(this).attr('data-org');
				
				parent.layer.open({
					type : 2,
					title : '归档信息',
					area : [ '80%', '80%' ],
					content : '../applyFile/get-form/historytask/' + id+"?orginType="+orgType,
					end : function() {
						oTable.fnDraw();
						parent.layer.closeAll();
					}
				});
			}
			// 表格回调函数
			function getRoleGrid1Data(url, aoData, fnCallback) {
				var start = aoData[3].value;
				var rows = aoData[4].value;
				var page = (start / rows) + 1;
				var name = jQuery('input[name=name]').val();
				var startTime = jQuery('input[name=startTime]').val();
				var endTime = jQuery('input[name=endTime]').val();
				$.ajax({
					url : url,
					data : {
						"name" : name,
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
						count = result.data.iTotalRecords;
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("status:" + XMLHttpRequest.status
								+ ",readyState:" + XMLHttpRequest.readyState
								+ ",textStatus:" + textStatus);
					}
				});
			}
			
			// 搜索按钮绑定事件
			$('button[name=search]').click(function(e) {
				e.preventDefault();
				oTable.fnDraw();
			});

		}
	};
}();
// })(jQuery);
