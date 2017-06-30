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

var listTable;
var count = 0;
// (function() {
// debugger;
listTable = function() {
	'use strict';
	return {
		init : function() {
			var _id = 0;
			// 初始化表格对象
			var oTable = $('#check-tableList')
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
											"mData" : "id",// 获取列数据，跟服务器返回字段一致
											"sClass" : "center",// 显示样式
											"mRender" : function(data, type,
													full) {// 返回自定义的样式
												return "<label><input type='checkbox' name='ids' value='"
														+ data
														+ "' class='ace' /><span class='lbl'></span></label>"
											}
										}, {
											"mData" : "name"
										}, {
											"mData" : "sexName"
										}, {
											"mData" : "politicalName"
										}, {
											"mData" : "phone"
										}, {
											"mData" : "company"
										}, {
											"mData" : "unionChairName"
										}, {
											"mData" : "upOrgName"
										}, {
											"mData" : "helpTypeName"
										}, {
											"mData" : "approvalStatus"
										}, {
											"mData" : "createTime"
										}, {
											"mData" : "remark"
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
								"sAjaxSource" : "getHelpList",

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
														+ full.instanceId+ '" data-org="'+ full.orginzeType+ '"><span class="label">审批</span></a>';

												str += '<a name="download" class="btn btn-success" href="checkExport/'
													+full.helpApplyId+'">导出  <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>';
												return str;
											},

											"aTargets" : [ 12 ]
										} ],

								"fnServerData" : getRoleGrid1Data

							});
			jQuery('table[id=check-tableList]').width('100%');

			var checkList = jQuery('table[id=check-tableList]');

			// 审批事件
			checkList.on('click', 'a[name=check]', handlerCheckClick);
			// 下载事件
			// checkList.on('click', 'a[name=download]', handlerDownloadClick);

			 //导出
//			 function handlerCheckClick(e) {
//				 e.preventDefault();
//				 var id = jQuery(this).attr('data-id');
//				 window.location.href = "../helpApply/checkExport?="+pkId;
//			 }
//			 
			// 审批事件
			function handlerCheckClick(e) {
				e.preventDefault();
				var id = jQuery(this).attr('data-id');
				var orgType=jQuery(this).attr('data-org');
				
				parent.layer.open({
					type : 2,
					title : '审批信息',
					area : [ '80%', '80%' ],
					content : '../helpApply/get-form/task/' + id+"?orginType="+orgType,
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
				$.ajax({
					url : url,
					data : {
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
			// tab2
		    var url = "../helpApply/getCheckedList";
			var oTable = $('#checked-tableList')
					.dataTable(
							{
								"bPaginate" : true,// 分页工具条显示
								// "sPaginationType" : "full_numbers",//分页工具条样式
								"bStateSave" : true, // 是否打开客户端状态记录功能,此功能在ajax刷新纪录的时候不会将个性化设定回复为初始化状态
								"bScrollCollapse" : true, // 当显示的数据不足以支撑表格的默认的高度
								"bLengthChange" : true, // 每页显示的记录数
								"bFilter" : false, // 搜索栏
								"bSort" : true, // 是否支持排序功能
								"bInfo" : true, // 显示表格信息
								"bAutoWidth" : true, // 自适应宽度
								"bJQueryUI" : false,// 是否开启主题
								"bDestroy" : true,
								"bProcessing" : true, // 开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
								"bServerSide" : true,// 服务器处理分页，默认是false，需要服务器处理，必须true
								"sAjaxDataProp" : "aData",// 是服务器分页的标志，必须有
								"sAjaxSource" : url,// 通过ajax实现分页的url路径。
								"aoColumns" : [
										{
											"mDataProp" : "id",// 获取列数据，跟服务器返回字段一致
											"sClass" : "center",// 显示样式
											"mRender" : function(data, type,
													full) {// 返回自定义的样式
												return "<label><input type='checkbox' name='ids' value='"
														+ data
														+ "' class='ace' /><span class='lbl'></span></label>"
											}
										}, {
											"mDataProp" : "name"
										}, {
											"mDataProp" : "sexName"
										}, {
											"mDataProp" : "politicalName"
										}, {
											"mDataProp" : "phone"
										}, {
											"mDataProp" : "company"
										}, {
											"mDataProp" : "unionChairName"
										}, {
											"mDataProp" : "upOrgName"
										}, {
											"mDataProp" : "helpTypeName"
										}, {
											"mDataProp" : "approvalStatus"
										}, {
											"mDataProp" : "createTime"
										}, {
											"mDataProp" : "remark"
										}, {
											"mDataProp" : null
										}

								],
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
												str += '<a name="look" class="btn btn-primary" href="javascript:;" data-id="'
														+ full.instanceId
														+ '"><span class="label">查阅</span></a>';

												str += '<a name="download" class="btn btn-success" href="checkExport/'
													+full.helpApplyId+'">导出  <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>';
												return str;
											},

											"aTargets" : [ 12 ]
										} ],
								
								"oLanguage" : {// 语言设置
									"sProcessing" : "处理中...",
									"sLengthMenu" : "显示 _MENU_ 项结果",
									"sZeroRecords" : "没有匹配结果",
									"sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
									"sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
									"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
									"sInfoPostFix" : "",
									"sSearch" : "搜索:",
									"sUrl" : "",
									"sEmptyTable" : "表中数据为空",
									"sLoadingRecords" : "载入中...",
									"sInfoThousands" : ",",
									"oPaginate" : {
										"sFirst" : "首页",
										"sPrevious" : "上页",
										"sNext" : "下页",
										"sLast" : "末页"
									},
									"oAria" : {
										"sSortAscending" : ": 以升序排列此列",
										"sSortDescending" : ": 以降序排列此列"
									}
								}
							});
			jQuery('table[id=checked-tableList]').width('100%');

			var checkedList = jQuery('table[id=checked-tableList]');

			// 查看事件
			checkedList.on('click', 'a[name=look]', handlerLookClick);
			// 下载事件
			// checkedList.on('click', 'a[name=download]',
			// handlerDownloadClick);
			
			var taskId = $('input[name=taskId]').val();
			
			function handlerLookClick(e) {
				e.preventDefault();
				var id = jQuery(this).attr('data-id');
				parent.layer.open({
					type : 2,
					title : '查看信息',
					area : [ '80%', '80%' ],
					content : '../helpApply/get-form/historytask/' + id,
					end : function() {
						oTable.fnDraw();
						parent.layer.closeAll();
					}
				});
				
				if(taskId=='undefined'){
					$('#subbmitTable').remove();
				}
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
