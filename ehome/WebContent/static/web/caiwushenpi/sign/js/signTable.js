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

var signTable;
var count = 0;
// (function() {
// debugger;
signTable = function() {
	'use strict';
	return {
		init : function() {
			var _id = 0;
			var bantchDowload = jQuery('a[name=batch-approval]');
			bantchDowload.on('click',handlerBantchDownloadClick);
			// 初始化表格对象
			var oTable = $('#member-list')
					.dataTable(
							{
								"iDisplayLength" : 3,
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
											"sClass" : "center",// 显示样式
											"mRender" : function(data, type,
													full) {// 返回自定义的样式
												return "<label><input type='checkbox' class='checkchild' name='ids' value='"
														+ full.id
														+ "' class='ace' /><span class='lbl'></span></label>"
											}
										}, {
											"mData" : "id"
										}, {
											"mData" : "signTableName"
										}, {
											"mData" : "createDate"
										}, {
											"mData" : "totalAmount"
										}, {
											"mData" : "totalEmployer"
										}, {
											"mData" : "amountSourceName"
										}, {
											"mData" : "helpProjectName"
										}
								],
								"bSort" : false,// 去掉点击标题刷新排序
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false, // 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data", // 是服务器分页的标志，必须有
								"sAjaxSource" : "showSignTalbeList",

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
														+ full.id
														+ '"><span class="label">查看</span></a>';

												str += '<a name="import" class="btn btn-primary" href="javascript:;" data-id="'
													+ full.id
													+ '"><span class="label">导出</span></a>';
												return str;
											},

											"aTargets" : [ 8 ]
										} ],

								"fnServerData" : getRoleGrid1Data

							});
			jQuery('table[id=tableList]').width('100%');

			var list = jQuery('table[id=member-list]');

			// 审批事件
			list.on('click', 'a[name=check]', handlerCheckClick);
			// 下载事件
			list.on('click', 'a[name=import]', handlerDownloadClick);

			function handlerCheckClick(e) {
				e.preventDefault();
				
				var id = jQuery(this).attr('data-id');
				parent.layer.open({
					type : 2,
					title : '',
					area : [ '80%', '80%' ],
					content : '../signTable/showSignTalbeInfo?id='+ id,
					end : function() {
						
					}
				});
			}
			// 导出
			function handlerDownloadClick(e) {
				e.preventDefault();
				var pkId = jQuery(this).attr('data-id');
				window.location.href='../signTable/exportSignTable?signId='+pkId;
			}
			
			function handlerBantchDownloadClick(){
				var idList=new Array();
				jQuery('input[name=ids]').each(function(index, element) {
					if (jQuery(this).is(':checked')) {
						var pkId = Number(jQuery(this).val());
						idList.push(pkId);
					}
				});
				if(idList.length<=0){
					alert("请先选择记录");
				}else{
					window.location.href='../signTable/batchExportSign?idList='+idList;
				}
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
						//console.log(result);
						count = result.data.iTotalRecords;
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
