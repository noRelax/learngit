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

var helpRightExplain;
var count = 0;
// (function() {
// debugger;
helpRightExplain = function() {
	'use strict';
	return {
		init : function() {
			var _id = 0;
			var search = jQuery('a[name=search]');
			var add = jQuery('a[name=add]');
			search.on('click',handlerSearch);
			add.on('click',handlerAdd);
			// 初始化表格对象
			var oTable = $('#explain-list')
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
											"sClass" : "center",// 显示样式
											},{
												"mData" : "title"
											}, {
												"mData" : "explainTypeName"
											}, {
												"mData" : "createPerson"
											}, {
												"mData" : "rightOrHelpType"
											}, {
												"mData" : "serviceButtunType"
											}, {
												"mData" : "phone"
											}, {
												"mData" : "createDate"
											}

									],
								"bSort" : false,// 去掉点击标题刷新排序
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false, // 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data", // 是服务器分页的标志，必须有
								"sAjaxSource" : "queryList",

								"aoColumnDefs" : [
										
										{
				                        	"mRender": function(data, type, full){
				                        		return '<input type="checkbox" class="checkchild" data-value="'+full.id+'"/>';
				                        	},
				                        	"aTargets": [0]
				                        },
									
										{
											"mRender" : function(data, type,
													full) {
												var str = "";
												str += '<a name="editExplain" class="btn btn-primary" href="javascript:;" data-value="'
														+ full.id
														+ '"><span class="label">编辑</span></a>';
												str += '<a name="deleteExplain" class="btn btn-primary" href="javascript:;" data-value="'
													+ full.id
													+ '"><span class="label">删除</span></a>';
												return str;
											},

											"aTargets" : [ 8 ]
										} ],

								"fnServerData" : getRoleGrid1Data

							});
			jQuery('table[id=explain-list]').width('100%');

			var list = jQuery('table[id=explain-list]');

			// 编辑查看事件
			list.on('click', 'a[name=editExplain]', handlerCheckClick);
			// 添加签领个案时间
			list.on('click', 'a[name=deleteExplain]', handlerDeleteClick);
	
			
			function handlerCheckClick(e) {
				
				e.preventDefault();
				var id = jQuery(this).attr('data-value');
				parent.layer.open({
					type : 2,
					title : '说明信息',
					area : [ '80%', '80%' ],
					content : '../helpRightExplain/explainInfo?id='+ id,
					end : function() {
							
					}
				});
				
				oTable.fnDraw();
			}
			
	
			 function handlerDeleteClick(e) {
	                e.preventDefault();
	  
	                var id = jQuery(this).attr('data-value');
					parent.layer.open({
					    content: '确定要删除么吗?'
						  ,btn: ['确定', '取消']
						  ,yes: function(index, layero){
							jQuery.post('../helpRightExplain/deleteExplain',{'id':id},function(data){
									if(data.status == 10000){
										parent.layer.alert('删除成功', {title : '操作提示', icon : 1, time:2000, end: function(){
											parent.layer.closeAll();
											oTable.fnDraw();
										}});
									}else{
										layer.alert(data.data,{title : '操作提示',icon: 2});
									}
							},'json');
							 
						  },btn2: function(index, layero){
						  }
						  ,cancel: function(){
						  }
						});
	            } 
			
			
			 function handlerAdd(e){
				 parent.layer.open({
						type : 2,
						title : '添加说明',
						area : [ '80%', '80%' ],
						content : '../helpRightExplain/addExplainInfo',
						end : function() {

						}
					});
			 }
			 
			
			 function handlerSearch(e){
             	e.preventDefault();
             	oTable.fnDraw();
             }
			
			// 表格回调函数
			 function getRoleGrid1Data(url, aoData, fnCallback) {
					var explainType = $("#explainType").find("option:selected").val()
	                var title = $("#keyword").val()
					var start = aoData[3].value;
					var rows = aoData[4].value;
					var page = (start / rows) + 1;
					$.ajax({
						url : url,
						data : {
							"title" : title,
							"explainType" : explainType,
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
