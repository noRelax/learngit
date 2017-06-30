
var localTable;
// (function() {
// debugger;
localTable = function() {
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
			searchBtn.on('click',handlerSearchClick);
			resetBtn.on('click',handlerResetClick);


			// 初始化表格对象
			var oTable = $('#local-tableList')
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
												return "<label><input type='checkbox' name='key' value='"
														+ data
														+ "' class='ace' /><span class='lbl'></span></label>"
											}
										}, {
											"mData" : "id"
										}, // 此列不绑定数据源，用来显示序号
										{
											"mData" : "icon"
										}, {
											"mData" : "lifeName"
										}, {
											"mData" : "url",
											"mRender" : function(data,
													type, full) {// 返回自定义的样式

												var str = "";
												var len = data.length;
												if (len > 15) {
													str = data.substring(0,
															40)
															+ "......";
												} else {
													str = data;
												}

												return "<span style='width:100px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;' onmousemove='mouseOver(this,\""
														+ data
														+ "\");'>"
														+ str
														+ "</span>"
											}
										}, {
											"mData" : "sort"
										}, {
											"mData" : "statusName"
										},{
											"mData" : null
										}

								],
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false, // 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data", // 是服务器分页的标志，必须有
								"sAjaxSource" : "querylocallist",
								"aoColumnDefs" : [
										{
											"mRender" : function(data, type,
													full) {
												return '<input type="checkbox" class="checkchild" value="'
														+ full.id + '"/>';
											},
											"aTargets" : [ 0 ]
										},
										{
											"mRender" : function(data, type,
													full) {
												return '<img style="width: 42px; height: 38px;" src="'
														+"../"+ full.icon + '"/>';
											},
											"aTargets" : [ 2 ]
										},
										{
											"mRender" : function(data, type,
													full) {
												return '<a  href="' + full.url + '">'+full.url+'</a>';
											},
											"aTargets" : [ 4 ]
										},
										{
											"mRender" : function(data, type,
													full) {
												var str = "";
												str += '<a name="edit" class="btn btn-primary" href="javascript:;" data-id="'
														+ full.id
														+ '"><span class="label">编辑</span></a>';

												str += '<a name="delete" class="btn btn-danger" href="javascript:;" data-id="'
														+ full.id + '">删除</a>';
												return str;
											},
											"aTargets" : [ 7 ]
										} ],

								"fnServerData" : getRoleGrid1Data

							});
			jQuery('table[id=local-tableList]').width('100%');

			var list = jQuery('table[id=local-tableList]');

			// 编辑事件
			list.on('click', 'a[name=edit]', handlerEditClick);
			// 删除事件
			list.on('click', 'a[name=delete]', handlerdeleteClick);

			function handlerEditClick(e) {
				e.preventDefault();
				var lifeId = jQuery('input[name=localId]').val();
				var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
					type : 2,
					title : '编辑信息',
					area : [ '700px', '700px' ],
					content : '../life/edit-local-page?localId='+pkId,
					end : function() {

					}
				});
			}

			function handlerdeleteClick(e) {
				e.preventDefault();
				var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
					content : '删除将会丢失内页，是否继续？',
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						jQuery.post('../life/delete', {
							"id" : pkId
						}, function(data) {
							if (data.status == 10000) {
								parent.layer.alert(data.data, {
									title : '操作提示',
									icon : 1,
									time : 2000,
									end : function() {
										oTable.fnDraw();
										parent.layer.closeAll();
									}
								});
							} else {
								layer.alert(data.data, {
									title : '操作提示',
									icon : 2
								});
							}
						}, 'json');
					},
					btn2 : function(index, layero) {
					},
					cancel : function() {
					}
				});
			}
			
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
				var lifeName = jQuery('input[name=lifeName]').val();
				var localId = jQuery('input[name=localId]').val();
				$.ajax({
					url : url,
					data : {
						"localId" : localId,
						"lifeName" : lifeName,
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
            
			/**
			 * ，长度限制显示30字符，超出部分不显示，鼠标移到上面显示全部
			 */
			setTimeout(function(){
				$('.table').find('tr').each(function(i){
					var url_val = $(this).find('td').eq(4).find('a').html();
				
					if (url_val != undefined) {
						$(this).find('td').eq(4).find('a').html(url_val.substr(0,30));
						$(this).find('td').eq(4).find('a').attr({
							'data-url': url_val,	// 保存完整的url
							'data-unurl': url_val.substr(0,30) // 保存前30位
						});
					}
					// 鼠标经过
					$(this).find('td').eq(4).find('a').hover(function(){
						$(this).html($(this).data('url'));
					},function(){
						$(this).html($(this).data('unurl'));
					});
				});
			},500);
		
		}
	};
}();
// })(jQuery);
