Vue.filter('time', function(value) {
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
});

Vue.filter('sex', function(value) {
	if (value == 0)
		return "男";
	else
		return "女";
});

$.ajaxSetup({
	async : false
});
var EditableTable = function() {
	'use strict';
	return {
		load : function() {
			var pageSize = jQuery('select[name=editable-sample_length]').val();
			var keyword = jQuery(
					'#editable-sample_wrapper .dataTables_filter input').val();
			$.ajax({
				type : "post",
				url : "queryUser",
				data : {
					'keyword' : keyword,
					'start' : 1,
					'pageSize' : pageSize
				},
				async : false,
				success : function(data) {
					data = eval("(" + data + ")");
					// console.log(data)
					if (data.status == '10000') {
						new Vue({
							el : '.editalbeWrap',
							data : {
								tablelist : data.data.list
							}
						});
						jQuery('ul[class=pagination]').replaceWith(
								data.data.pageHtml);
					} else {
						//console.log('没有数据!');
					}
				}
			});
		},
		init : function() {
			
			var searchBtn = jQuery('button[name=search]');
			var resetBtn = jQuery('button[name=reset]');
			var searchForm = jQuery('form[name=searchForm]');
			searchBtn.on('click',handlerSearchClick);
			resetBtn.on('click',handlerResetClick);
			// 刷新行记录
			function restoreRow(oTable, nRow) {
				var aData = oTable.fnGetData(nRow);
				var jqTds = $('>td', nRow);
				for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
					oTable.fnUpdate(aData[i], nRow, i, false);
				}
				oTable.fnDraw();
			}

			// 编辑行
			function editRow(oTable, nRow) {
				// console.log(nRow)
				var aData = oTable.fnGetData(nRow);
				// console.log(aData);
				var jqTds = $('>td', nRow);
				jqTds[2].innerHTML = '<input type="text" name="userAccount" class="form-control small" value="'
						+ aData[2] + '">';
				jqTds[3].innerHTML = '<input type="text" name="userPassword" class="form-control small" value="'
						+ aData[3] + '">';
				jqTds[4].innerHTML = '<input type="text" name="userName" class="form-control small" value="'
						+ aData[4] + '">';
				if (aData[5] != null && aData[5] != '') {
					if (aData[5] == '男')
						jqTds[5].innerHTML = '<select name="sex"><option value="0" selected = "selected">男</option><option value="1">女</option></select>';
					else
						jqTds[5].innerHTML = '<select name="sex"><option value="0">男</option><option value="1" selected = "selected">女</option></select>';
				} else {
					jqTds[5].innerHTML = '<select name="sex"><option value="0" selected = "selected">男</option><option value="1">女</option></select>';
				}
				jqTds[6].innerHTML = '<input type="text" name="userEmail" class="form-control small" value="'
						+ aData[6] + '">';
				jqTds[7].innerHTML = '<input type="text" name="userTel" class="form-control small" value="'
						+ aData[7] + '">';
				jqTds[8].innerHTML = '<input type="text" name="userMobile" class="form-control small" value="'
						+ aData[8] + '">';
				// jqTds[9].innerHTML = '';
				jqTds[10].innerHTML = '<a class="edit" href=""><span class="label label-primary">保存</span></a><a class="cancel" href=""><span class="label label-info">取消</span></a>';
			}

			// 保存表格行
			function saveRow(oTable, nRow) {
				var jqInputs = $('input', nRow);
				oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
				oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
				oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
				oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
				oTable.fnUpdate(jqInputs[6].value, nRow, 6, false);
				oTable.fnUpdate(jqInputs[7].value, nRow, 7, false);
				oTable.fnUpdate(jqInputs[8].value, nRow, 8, false);
				// oTable.fnUpdate('',
				// nRow, 9, false);
				oTable
						.fnUpdate(
								'<a class="edit" href=""><span class="label label-success">编辑</span></a><a class="delete" href=""><span class="label label-danger">删除</span></a>',
								nRow, 10, false);
				oTable.fnDraw();
			}

			// 编辑表格行
			function cancelEditRow(oTable, nRow) {
				var jqInputs = $('input', nRow);
				oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
				oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
				oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
				oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
				oTable.fnUpdate(jqInputs[6].value, nRow, 6, false);
				oTable.fnUpdate(jqInputs[7].value, nRow, 7, false);
				oTable.fnUpdate(jqInputs[8].value, nRow, 8, false);
				oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, 9,
						false);
				oTable.fnDraw();
			}
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
									"mData" : "userAccount"
								}, {
									"mData" : "userName"
								}, {
									"mData" : "roleName"
								}, {
									"mData" : "orgainName"
								}, {
									"mData" : "deptName"
								}, {
									"mData" : "createTime"
								}, {
									"mData" : "userDesc"
								}, {
									"mData" : null
								}
								],
								"bSort" :false,
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false,// 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data",// 是服务器分页的标志，必须有
								"sAjaxSource" : "queryUser",
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
												str += '<a name="update" class="btn btn-warning btn-xs" href="javascript:;" style="margin-left:5px" data-id="'
														+ full.id
														+ '"><span class="label">修改</span></a>';
												
												str += '<a name="reset" class="btn btn-danger btn-xs" href="javascript:;" style="margin-left:5px" data-id="'
														+ full.id
														+ '">重置密码</a>';
												
												if(full.status == 1){
													str += '<a class="btn btn-success btn-xs"  href="javascript:;" style="margin-left:5px" name="unfreeze" data-id="'
														+ full.id
														+ '"><span class="label">解除冻结</span></a>';
												}else {
													str += '<a class="btn btn-warning btn-xs"  href="javascript:;" style="margin-left:5px" name="freeze" data-id="'
														+ full.id
														+ '"><span class="label">未冻结</span></a>';
												}
												
												str += '<a class="btn btn-danger btn-xs" name="auth" href="javascript:;" style="margin-left:5px" data-id="'
														+ full.id
														+ '"><span class="label">配置角色</span></a>';
												return str;
											},
											"aTargets" : [ 9 ]
										} ],
								// "aoColumnDefs" : [ {
								// 'bSortable' : false,
								// 'aTargets' : [ 0 ]
								// } ],
								"fnServerData" : retrieveData
							});
//			jQuery('#editable-sample_wrapper .dataTables_filter input')
//					.addClass("form-control medium").attr("name", 'keyword');
//			jQuery('#editable-sample_wrapper .dataTables_length select')
//					.addClass("form-control xsmall");
			var nEditing = null;
			
			var list = jQuery('table[id=editable-sample]');
			
			//修改事件
			list.on('click', 'a[name=update]', handlerUpdateClick);
			//冻结事件
			list.on('click', 'a[name=freeze]', handlerFreezeClick);
			//解除冻结事件
			list.on('click', 'a[name=unfreeze]', handlerUnFreezeClick);
			//重置密码
			list.on('click', 'a[name=reset]', handlerResetPwClick);
			//配置角色
			list.on('click', 'a[name=auth]', handlerAuthClick);
			function handlerFreezeClick(e){
				e.preventDefault();
				var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
				    content: '确定要冻结该帐号吗?'
					  ,btn: ['确定', '取消']
					  ,yes: function(index, layero){
							jQuery.post('../user/updateStatus',{"status":1,'id':pkId},function(data){
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
					    //按钮【按钮二】的回调
					  }
					  ,cancel: function(){ 
					  }
					});
			}
			
			function handlerUnFreezeClick(e) {
				e.preventDefault();
				var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
			    content: '确定要解除冻结该帐号吗?'
				  ,btn: ['确定', '取消']
				  ,yes: function(index, layero){
						jQuery.post('../user/updateStatus',{"status":0,'id':pkId},function(data){
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
				    //按钮【按钮二】的回调
				  }
				  ,cancel: function(){ 
				  }
				});
			}
			
			function handlerUpdateClick(e){
			    e.preventDefault();	
			    var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
				    type: 2,
				    title: '修改信息',
				    area: ['700px', '700px'],
				    content:'../user/update-user-page.html?userId='+pkId,
				    end:function(){
				    	
				    }
				});
//			    jQuery.post('queryById',{'id':pkId},function(data){
//			    	if(data.code == 10000){
//			    		jQuery('input[name=id]').val(pkId);
//			    		jQuery('input[name=userAccount]').val(data.data.userAccount);
////			    		jQuery('input[name=userPassword]').hide();
////			    		jQuery('input[name=userPassword]').hide();
//			    		jQuery('div[name=pw]').hide();
//			    		jQuery('div[name=confirm_pw]').hide();
//			    		jQuery('input[name=userName]').val(data.data.userName);
//			    		jQuery('input[name=userMobile]').val(data.data.userMobile);
//			    		jQuery('input[name=userEmail]').val(data.data.userEmail);
//			    		jQuery('input[name=userDesc]').val(data.data.userDesc);
//			    		jQuery('input[name=orgainId]').val(data.data.orgainId);
//			    		
//			    	}else{
//			    		alert('服务端出错了!');
//			    	}
//			    },'json');
			}
			
			function handlerResetPwClick(e){
				e.preventDefault();
				var pkId= jQuery(this).attr('data-id');
				parent.layer.open({
				    type: 2,
				    title: '重置密码',
				    area: ['580px', '400px'],
				    content:'../user/resetPassword.html?pkId='+pkId,
				    end:function(){
				    }
				});
				
//				jQuery('#myModal_reset').find('button[name=submit]').on('click',function(e){
//					var userPassword = jQuery('#myModal_reset').find('input[name=userPassword]').val();
//					var confirmUserPassword = jQuery('#myModal_reset').find('input[name=confirmUserPassword]').val();
//					jQuery.post('resetPassword',{"userPassword":userPassword,'id':pkId,
//						'comfirmUserPassword':confirmUserPassword},function(data){
//						if(data.code == 10000){
//							jQuery('#myModal_reset').find('button[name=close]').click();
//							jQuery('#myModal_alert').modal();
//							oTable.fnDraw();
//						}else {
//							alert(data.data);
//						}
//					},'json');
//				});
			}
			
			function handlerAuthClick(e){
				e.preventDefault();
				var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
				    type: 2,
				    title: '配置角色',
				    area: ['780px', '700px'],
				    content:'../user/toAuthRole.html?pkId='+pkId,
				    end:function(){
				    }
				});
				//jQuery('#myModal_auth_role').find('input[name=fkUserId]').val(pkId);
				//roleTable.init();//初始化角色配置表格
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
				var userName = jQuery('input[name=user_name]').val();
				var roleName = jQuery('input[name=role_name]').val();
				var orgName = jQuery('input[name=org_name]').val();
				var deptId = jQuery('select[name=dept_id]').val();
				$.ajax({
					url : url,
					data : {
						"deptId" : deptId,
						"roleName":roleName,
						"orgName":orgName,
						"userName":userName,
						"sEcho" : aoData[0].value,
						"rows" : rows,
						"page" : page
					},
					async : false,
					type : 'POST',
					dataType : 'json',
					success : function(result) {
						// var obj=JSON.parse(result);
//						console.log(result);
						fnCallback(result.data);// 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
						// new Vue({
						// el : '.editalbeWrap',
						// data : {
						// tablelist : result.data.data
						// }
						// });
					},
//					error : function(XMLHttpRequest, textStatus, errorThrown) {
//						alert("status:" + XMLHttpRequest.status
//								+ ",readyState:" + XMLHttpRequest.readyState
//								+ ",textStatus:" + textStatus);
//
//					}
				});
			}
			// 触发新增按钮事件
			// $('#editable-sample_new').live('click',function(e) {
			// e.preventDefault();
			// //添加一行空的数据行
			// var aiNew = oTable
			// .fnAddData([
			// '',
			// '',
			// '',
			// '',
			// '',
			// '',
			// '',
			// '',
			// '',
			// '',
			// '<a class="edit" href="">编辑</a><a class="cancel" data-mode="new"
			// href="">取消</a>' ]);
			// var nRow = oTable.fnGetNodes(aiNew[0]);
			// editRow(oTable, nRow);
			// nEditing = nRow;
			// });
			// 行数据的删除事件
			$('#editable-sample a.delete').live('click', function(e) {
				e.preventDefault();
				if (confirm('确定要删除这条记录吗?')) {
					var id = jQuery(this).attr('id');
					jQuery.post('deleteUser', {
						'userId' : id
					}, function(data) {
						if (data.status == '10000') {
							window.location.reload();
						} else {
							alert('删除出错，请检查数据');
						}
					}, 'json');
				}
			});

			$('#editable-sample a.update').live('click', function(e) {
				e.preventDefault();
				var nRow = jQuery(this).parents('tr')[0];
				// if (nEditing !== null && nEditing != nRow) {
				// restoreRow(oTable, nEditing);
				editRow(oTable, nRow);
				nEditing = nRow;
				// }
			});
			// 取消编辑事件
			$('#editable-sample a.cancel').live('click', function(e) {
				e.preventDefault();
				if ($(this).attr("data-mode") == "new") {
					var nRow = $(this).parents('tr')[0];
					oTable.fnDeleteRow(nRow);
				} else {
					restoreRow(oTable, nEditing);
					nEditing = null;
				}
			});
			// 触发编辑事件
			$('#editable-sample a.edit').live(
					'click',
					function(e) {
						e.preventDefault();
						var nRow = $(this).parents('tr')[0];
						if (nEditing !== null && nEditing != nRow) {
							restoreRow(oTable, nEditing);
							editRow(oTable, nRow);
							nEditing = nRow;
						} else if (nEditing == nRow && this.innerText == "保存") {
							var userId = jQuery(this).parents('tr').find(
									'input[name=ids]').attr('id');
							if (userId == null || userId == ''
									|| userId == 'undefined')
								userId = '';
							var userAccount = jQuery(this).parents('tr').find(
									'input[name=userAccount]').val();
							var userName = jQuery(this).parents('tr').find(
									'input[name=userName]').val();
							var sex = jQuery(this).parents('tr').find(
									'select[name=sex]').val();
							var userEmail = jQuery(this).parents('tr').find(
									'input[name=userEmail]').val();
							var userTel = jQuery(this).parents('tr').find(
									'input[name=userTel]').val();
							var userMobile = jQuery(this).parents('tr').find(
									'input[name=userMobile]').val();
							var userPassword = jQuery(this).parents('tr').find(
									'input[name=userPassword]').val();
							var url = 'addUser';
							if (userId != '')
								url = 'updateUser';
							jQuery.post(url, {
								'id' : userId,
								'userAccount' : userAccount,
								'userPassword' : userPassword,
								'userName' : userName,
								'sex' : sex,
								'userEmail' : userEmail,
								'userTel' : userTel,
								'userMobile' : userMobile
							}, function(data) {
								if (data.status == '10000') {
									if (userId != '')
										alert('修改用户成功!');
									else
										alert('新增用户成功!');
									window.location.reload();
								} else {
									alert('系统出错了,请检查数据并重试!');
								}
							}, 'json');
							// saveRow(oTable, nEditing);
							// nEditing = null;
						} else {
							editRow(oTable, nRow);
							nEditing = nRow;
						}
					});
			$(".checkall").click(function () {
			      var check = $(this).prop("checked");
			      $(".checkchild").prop("checked", check);
			});
		}
	};
}();