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

var unsignTable;
var count = 0;
// (function() {
// debugger;
unsignTable = function() {
	'use strict';
	return {
		init : function(id) {
			// 初始化表格对象
			var oTable = $('#member-list')
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

								"aoColumns" :[ {
									"mData" : "id"
								}, {
									"mData" : "name"
								}, {
									"mData" : "sex"
								}, {
									"mData" : "age"
								}, {
									"mData" : "idCard"
								}, {
									"mData" : "cardNum"
								}, {
									"mData" : "companyOrAddress"
								}, {
									"mData" : "amountNum"
								}, {
									"mData" : "amountSource"
								}, {
									"mData" : "cellphone"
								}, {
									"mData" : "helpProject"
								}, {
									"mData" : "employerNum"
								}, {
									"mData" : "documentNum"
								}

								],
								"bSort" : false,// 去掉点击标题刷新排序
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false, // 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data", // 是服务器分页的标志，必须有
								"sAjaxSource" : "queryTempList",

								"aoColumnDefs" : [
										{
											"mRender" : function(data, type,
													full) {
												return '<input type="checkbox" class="checkchild" data-value="'
														+ full.id + '"/>';
											},
											"aTargets" : [ 0 ]
										}],

								"fnServerData" : getRoleGrid1Data

							});

			// 表格回调函数
			function getRoleGrid1Data(url, aoData, fnCallback) {

				var start = aoData[3].value;
				var rows = aoData[4].value;
				var page = (start / rows) + 1;
				$.ajax({
					url : url,
					data : {
						"id" : id,
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
