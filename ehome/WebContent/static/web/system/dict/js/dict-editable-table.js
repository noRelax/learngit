var DictEditableTable = function() {
	'use strict';
	return {
		init : function() {
			var searchBtn = jQuery('button[name=search]');
			var resetBtn = jQuery('button[name=reset]');
			var searchForm = jQuery('form[name=searchForm]');
			searchBtn.on('click',handlerSearchClick);
			resetBtn.on('click',handlerResetClick);
			// 初始化表格对象
			var oTable = $('#editable-sample')
					.dataTable(
							{
//								"aLengthMenu" : [ [ 5, 10, 15, 20, -1 ],
//										[ 5, 10, 15, 20, "All" ] ],
								"iDisplayLength" : 10,
								"sDom" : "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
								"sPaginationType" : "bootstrap",
								"bPaginite" : true,
								"bLengthChange": false,
								"oLanguage" : {
									"sSearch" : "搜索",
									"sZeroRecords" : "没有检索到数据",
									"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
									"sInfo" : "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
									"sInfoEmtpy" : "没有数据",
									"sProcessing" : "正在加载数据...",
									//"sLengthMenu" : "每页显示 _MENU_条记录",
									"oPaginate" : {
										"sFirst" : "首页",
										"sPrevious" : "前一页",
										"sNext" : "后一页",
										"sLast" : "末页"
									}
								},
								"aoColumns" : [ {
									"mData" : null
								}, {
									"mData" : "id"
								}, // 此列不绑定数据源，用来显示序号
								{
									"mData" : "typeCode"
								}, {
									"mData" : "typeName"
								}, {
									"mData" : "remark"
								},{
									"mData" : null
								}],
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false,// 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data",// 是服务器分页的标志，必须有
								"sAjaxSource" : "queryDictType",
								"aoColumnDefs" : [
										{
											"mRender" : function(data, type,
													full) {
												return '<input type="checkbox" class="checkchild" value="'+full.id+'"/>';
											},
											"aTargets" : [ 0 ]
										},
										{
											"mRender" : function(data, type,
													full) {
												var str = "";
												str += '<a name="update" class="btn btn-danger btn-xs" href="javascript:;" data-id="'
														+ full.id
														+ '"><span class="label">修改</span></a>';
												
												str += '<a name="delete" style="margin-left:5px" class="btn btn-warning btn-xs" href="javascript:;" data-id="'
													+ full.id
													+ '"><span class="label">删除</span></a>';												
												return str;
											},
											"aTargets" : [ 5 ]
										} ],
								"fnServerData" : retrieveData
							});
			
			var list = jQuery('table[id=editable-sample]');
			
			//修改事件
			list.on('click', 'a[name=update]', handlerUpdateClick);
			
			list.on('click', 'a[name=delete]', handlerDeleteClick);
			
			function handlerUpdateClick(e){
			    e.preventDefault();	
			    var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
				    type: 2,
				    title: '修改信息',
				    area: ['700px', '600px'],
				    content:'../dict/update-dictType.html?pkId='+pkId,
				    end:function(){
				    	
				    }
				});
			}

			function handlerDeleteClick(e){
                e.preventDefault();
                var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
				    content: '确定要删除吗?'
					  ,btn: ['确定', '取消']
					  ,yes: function(index, layero){
							jQuery.post('../dict/deleteDictType',{'pkId':pkId},function(data){
								if(data.status == 10000){
									parent.layer.alert(data.data, {title : '操作提示', icon : 1, time:2000, end: function(){
										oTable.fnDraw();
										parent.layer.closeAll();
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
			
			function handlerSearchClick(e){
				e.preventDefault();
				oTable.fnDraw();
			}
			
			function handlerResetClick(e){
				e.preventDefault();
				searchForm.clearForm();  
			}
			
			function retrieveData(url, aoData, fnCallback) {
				var start = aoData[3].value;
				var rows = aoData[4].value;
				var page = (start / rows) + 1;
				var typeName = jQuery('input[name=type_name]').val();
				$.ajax({
					url : url,
					data : {
						"typeName":typeName,
						"sEcho" : aoData[0].value,
						"rows" : rows,
						"page" : page
					},
					async : false,
					type : 'POST',
					dataType : 'json',
					success : function(result) {
						fnCallback(result.data);// 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
					}
				});
			}
			$(".checkall").click(function () {
			      var check = $(this).prop("checked");
			      $(".checkchild").prop("checked", check);
			});
		}
	};
}();