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
		init : function() {
			var _id = 0;
			var search = jQuery('button[name=search]');
			search.on('click',handlerSearch);
			
			var batchAdd = jQuery('a[name=batchAdd]');
			batchAdd.on('click',handlerBatchJoinTemp);
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

								"aoColumns" : [
										{
											"sClass" : "center",// 显示样式
										}, {
											"mData" : "id"
										}, {
											"mData" : "name"
										}, {
											"mData" : "sexName"
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
											"mData" : "amountSourceName"
										}, {
											"mData" : "cellphone"
										}, {
											"mData" : "helpProjectName"
										}, {
											"mData" : "employerNum"
										}, {
											"mData" : "documentNum"
										}, {
											"mData" : "remark"
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
												str += '<a name="check" class="btn btn-primary" href="javascript:;" data-value="'
														+ full.id
														+ '"><span class="label">查看</span></a>';
												str += '<a name="SigleJoinTemp" class="btn btn-primary" href="javascript:;" data-value="'
													+ full.id
													+ '"><span class="label">添加签领个案</span></a>';
												return str;
											},

											"aTargets" : [ 15 ]
										} ],

								"fnServerData" : getRoleGrid1Data

							});
			jQuery('table[id=member-list]').width('100%');

			var list = jQuery('table[id=member-list]');

			// 查看事件
			list.on('click', 'a[name=check]', handlerCheckClick);
			// 添加签领个案时间
			list.on('click', 'a[name=SigleJoinTemp]', handlerJoinTempClick);
			// 打回时间
			//list.on('click', 'a[name=goBack]', handlerGoBackClick);
			
			function handlerCheckClick(e) {
				e.preventDefault();
				var id = jQuery(this).attr('data-value');
				parent.layer.open({
					type : 2,
					title : '审批信息',
					area : [ '80%', '80%' ],
					content : '../signTable/helpApply.html?id='+ id,
					end : function() {
							
					}
				});
			}
			
			var selected = sessionStorage.getItem("cache-choose-record");
			if(selected==null){
				  selected = selected == null ? '[]' : selected;
			  }else{
				selected = selected == "" ? '[]' : selected;
			  }
			selected = jQuery.parseJSON(selected);
			
			 function handlerJoinTempClick(e) {
	                e.preventDefault();
	                var amount_source = $("#amount_source").find("option:selected").text()
	                var help_project = $("#help_project").find("option:selected").text()
	                var id = jQuery(this).attr('data-value');
					parent.layer.open({
					    content: '确定要加入吗?'
						  ,btn: ['确定', '取消']
						  ,yes: function(index, layero){
								/*jQuery.post('../signTable/joinTemp',{'id':id},function(data){
									if(data.status == 10000){
										parent.layer.alert(data.data, {title : '1条"'+ amount_source +'","' + help_project + '"项目的申请个案加入待生成签领表列表'
											, icon : 1, time:2000, end: function(){
											parent.layer.closeAll();
										}});
									}else{
										layer.alert(data.data,{title : '操作提示',icon: 2});
									}
							},'json');*/
							/*  for(var x=0; x<selected.length; x++){
									if(selected[x]!=null && selected[x]!='' && selected[x]!='null')
										_c++;
							  }*/
							 //alert(sessionStorage.getItem("cache-choose-record"))
							  selected = sessionStorage.getItem("cache-choose-record");
							  if(selected==null){
								  selected = selected == null ? '[]' : selected;
							  }else{
								selected = selected == "" ? '[]' : selected;
							  }
								selected = jQuery.parseJSON(selected);  
								for(var i=0; i<selected.length; i++){
									if(selected[i]==id){
										layer.alert("请勿重复添加");
										return;
									}
								}
							 var number = selected.length;
							 selected.push(id);
							 jQuery('span[name=numberSpan]').html(number + 1);
							 saveCache(1)
							 parent.layer.closeAll();
						  },btn2: function(index, layero){
						  }
						  ,cancel: function(){
						  }
						});
	            } 
			 
			 
			 function handlerBatchJoinTemp(e) {
				 	
	                e.preventDefault();
	                var amount_source = $("#amount_source").find("option:selected").text()
	                var help_project = $("#help_project").find("option:selected").text()
	                var idList = new Array();
	                
	                jQuery('input[class=checkchild]').each(function(index, element) {
	                	if (jQuery(this).is(':checked')) {
	                		var pkId = Number(jQuery(this).attr('data-value'));
	                		idList.push(pkId);
						}
	                });
					
	                if(idList.length<=0){
						layer.alert("请选择签领个案");
						return;
					}
	            
	                parent.layer.open({
					    content: '确定要加入吗?'
						  ,btn: ['确定', '取消']
						  ,yes: function(index, layero){
							  
							  selected = sessionStorage.getItem("cache-choose-record");
							  if(selected==null){
								  selected = selected == null ? '[]' : selected;
							  }else{
								selected = selected == "" ? '[]' : selected;
							  }
								selected = jQuery.parseJSON(selected); 
								for(var j=0; j<idList.length; j++){
									for(var i=0; i<selected.length; i++){
										if(selected[i]==idList[j]){
											idList.remove[j];
											break;
										}
										
									}
								}
							 var number = selected.length;
							 
							 for(var j=0; j<idList.length; j++){
								 selected.push(idList[j]);
							 }
							 
							 jQuery('span[name=numberSpan]').html(number + idList.length);
							 saveCache(idList.length)
							 parent.layer.closeAll();
						  },btn2: function(index, layero){
						  }
						  ,cancel: function(){
						  }
						});
	            }
			
			 function saveCache(total){
					var json = {};
					json.helpProject = $("#help_project").find("option:selected").text();
					json.amountSource = $("#amount_source").find("option:selected").text();
					sessionStorage.setItem('cache-choose-rel-record', JSON
							.stringify(json));
					sessionStorage.setItem('cache-choose-record', JSON
							.stringify(selected));
					if(total == 0){
						alert('保存成功');
					}else{
						var str = total + '条'+ $("#amount_source").find("option:selected").text() + '资金来源' + ','
						+ $("#help_project").find("option:selected").text()+ '项目的申请个案成功加入待生成签领列表';
				        //alert(str);
						layer.msg(str, {
							  time: 8000, //2s后自动关闭
							  btn: ['确定']
						});
					}
					$('select[name=help_project]').attr("disabled",true); 
					$('select[name="amount_source"]').attr("disabled",true); 
					//search();
				}
			 
			 
			
			 function handlerSearch(e){
             	e.preventDefault();
             	oTable.fnDraw();
             }
			
			// 表格回调函数
			function getRoleGrid1Data(url, aoData, fnCallback) {
				var amount_source = $("#amount_source").find("option:selected").val()
                var help_project = $("#help_project").find("option:selected").val()
                var amount_source_str = $("#amount_source").find("option:selected").text()
                var help_project_str = $("#help_project").find("option:selected").text()
                //var helpType = '${helpProject}', distAmountSource = '${amountSource}';
                var condition = jQuery.parseJSON(sessionStorage.getItem('cache-choose-rel-record'))
               
				if(condition==null){
					document.getElementById("projectSpan").innerHTML = help_project_str;
					document.getElementById("sourceSpan").innerHTML = amount_source_str;
				}
				
				
				/*if (jQuery.isArray(selected)) {
					var _c=0;
					for(var x=0; x<selected.length; x++){
						if(selected[x]!=null && selected[x]!='' && selected[x]!='null')
							_c++;
					}
					jQuery('span[name=numberSpan]').html(_c);
					if(_c>0){
						project.attr("disabled",true); 
						moneyFrom.attr("disabled",true); 
					}else{
						project.attr("disabled",false); 
						moneyFrom.attr("disabled",false);
					}
				} else {
					selected = [];
					jQuery('span[name=numberSpan]').html(0);
				}*/
				
				
				
				var start = aoData[3].value;
				var rows = aoData[4].value;
				var page = (start / rows) + 1;
				$.ajax({
					url : url,
					data : {
						"helpProject" : help_project,
						"amountSource" : amount_source,
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
