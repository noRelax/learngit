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
//(function() {
//	debugger;
listTable = function() {
        'use strict';
        return {
            init: function() {
                var _id = 0;
              //初始化表格对象
                var oTable = $('#check-tableList')
                    .dataTable({
                        "iDisplayLength": 10,
                        "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                        "sPaginationType": "bootstrap",
                        "bLengthChange": false,
                        "bPaginite": true,
                        "oLanguage": {
                            "sSearch": "搜索",
                            "sZeroRecords": "没有检索到数据",
                            "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
                            "sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
                            "sInfoEmtpy": "没有数据",
                            "sProcessing": "正在加载数据...",
                            "oPaginate": {
                                "sFirst": "首页",
                                "sPrevious": "前一页",
                                "sNext": "后一页",
                                "sLast": "末页"
                            }
                        },

                        "aoColumns" : [
                		{"mData" : "name"},
                		{"mData" : "sex"},
                		{"mData" : "workPhone"},
                		{"mData" : "IDcard"},
                		{"mData" : "company"},
                		{"mData" : "legalProceedings"},
                		{"mData" : "applicationTime"},
                		{"mData" : null}

                		],
                		"bSort": false,//去掉点击标题刷新排序
                        "ordering": false, // 排序操作在服务端进行，所以可以关了。
                        "bFilter": false, // 去掉搜索框
                        "bDestroy": true,
                        "processing": true,
                        "bServerSide": true,
                        "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                        "sAjaxSource": "getCheckList",

                        "aoColumnDefs" : [
							{
							},

							{
								/*"mRender" : function(data, type, full) {
									if(!full.starttime) return '';
									return new Date().format(full.starttime)
								},
								"aTargets" : [3]*/
							},
								{
									"mRender" : function(data, type,
											full) {
										var str = "";
										str += '<a name="check" class="btn btn-primary" href="javascript:;" data-id="'
												+ full.fp_instanceId
												+ '"><span class="label">审批</span></a>';

										str += '<a name="download" class="btn btn-success" href="checkExport/'
											+full.fp_instanceId+'">导出  <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>';
										return str;
										},

									"aTargets" : [7]
								}
                        ],

                        "fnServerData": getRoleGrid1Data


                    });
                jQuery('table[id=check-tableList]').width('100%');


                var checkList = jQuery('table[id=check-tableList]');

    			//审批事件
                checkList.on('click', 'a[name=check]', handlerCheckClick);
                
                //审批事件
                function handlerCheckClick(e){
                	e.preventDefault();
                	var id = jQuery(this).attr('data-id');
                	parent.layer.open({
                		type: 2,
                		title: '审批信息',
                		area: ['45%', '80%'],
                		content:'get-form/task/'+id,
                		end:function(){
                			
                		}
                	});
                	
                }
    			
                //表格回调函数
                function getRoleGrid1Data(url, aoData, fnCallback) {
//                	var start = aoData[3].value;
//                	var rows = aoData[4].value;
//                	var page = (start / rows) + 1;
                	$.ajax({
                		url : url,
                		data : {
                			"sEcho" : aoData[0].value,
                			"iDisplayStart" : aoData[3].value,
                			"iDisplayLength" : aoData[4].value
                		},
                		async : false,
                		type : 'POST',
                		dataType : 'json',
                		success : function(result) {
                			fnCallback(result.data);// 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
                			//console.log(result);
                			count = result.data.iTotalRecords;
                		},
                		error: function(XMLHttpRequest, textStatus, errorThrown) {
                			alert("status:" + XMLHttpRequest.status +
                					",readyState:" + XMLHttpRequest.readyState +
                					",textStatus:" + textStatus);
                		}
                	});
                }
    			//tab2
    			var oTable = $('#checked-tableList')
    			.dataTable({
    				"iDisplayLength": 10,
    				"sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
    				"sPaginationType": "bootstrap",
    				"bLengthChange": false,
    				"bPaginite": true,
    				"oLanguage": {
    					"sSearch": "搜索",
    					"sZeroRecords": "没有检索到数据",
    					"sInfoFiltered": "数据表中共为 _MAX_ 条记录",
    					"sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
    					"sInfoEmtpy": "没有数据",
    					"sProcessing": "正在加载数据...",
    					"oPaginate": {
    						"sFirst": "首页",
    						"sPrevious": "前一页",
    						"sNext": "后一页",
    						"sLast": "末页"
    					}
    				},
    				
    				"aoColumns" : [
    				               {"mData" : "name"},
    				               {"mData" : "sex"},
    				               {"mData" : "workPhone"},
    				               {"mData" : "IDcard"},
    				               {"mData" : "company"},
    				               {"mData" : "legalProceedings"},
    				               {"mData" : "applicationTime"},
    				               {"mData" : null}
    				               
    				               ],
    				               "bSort": false,//去掉点击标题刷新排序
    				               "ordering": false, // 排序操作在服务端进行，所以可以关了。
    				               "bFilter": false, // 去掉搜索框
    				               "bDestroy": true,
    				               "processing": true,
    				               "bServerSide": true,
    				               "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
    				               "sAjaxSource": "getCheckedList",
    				               
    				               "aoColumnDefs" : [
    				                                 {
    				                                 },
    				                                 
    				                                 {
    				                                	 /*"mRender" : function(data, type, full) {
									if(!full.starttime) return '';
									return new Date().format(full.starttime)
								},
								"aTargets" : [3]*/
    				                                 },
    				                                 {
    				                                	 "mRender" : function(data, type,
    				                                			 full) {
    				                                		 var str = "";
    				                                		 str += '<a name="look" class="btn btn-primary" href="javascript:;" data-id="'
    				                                			 + full.fp_instanceId
    				                                			 + '"><span class="label">查看</span></a>';
    				                                		 
    				                                		 str += '<a name="download" class="btn btn-success" href="checkedExport/'
    				 											+full.fp_instanceId+'">导出  <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>';
    				                                		 return str;
    				                                	 },
    				                                	 
    				                                	 "aTargets" : [7]
    				                                 }
    				                                 ],
    				                                 
    				                                 "fnServerData": getRoleGrid2Data
    				                                 
    				                                 
    			});
    			jQuery('table[id=checked-tableList]').width('100%');
    			
    			
    			var checkedList = jQuery('table[id=checked-tableList]');
    			
    			//查看事件
    			checkedList.on('click', 'a[name=look]', handlerLookClick);
    			
    			function handlerLookClick(e){
    				e.preventDefault();
    				var id = jQuery(this).attr('data-id');
    				parent.layer.open({
    					type: 2,
    					title: '查看信息',
    					area: ['45%', '80%'],
    					content:'look-form/task/'+id,
    					end:function(){
    						
    					}
    				});
    			}
    			
                //表格回调函数
                function getRoleGrid2Data(url, aoData, fnCallback) {
//                	var start = aoData[3].value;
//    				var rows = aoData[4].value;
//    				var page = (start / rows) + 1;
    				$.ajax({
    					url : url,
    					data : {
    						"sEcho" : aoData[0].value,
    						"iDisplayStart" : aoData[3].value,
                			"iDisplayLength" : aoData[4].value
    					},
    					async : false,
    					type : 'POST',
    					dataType : 'json',
    					success : function(result) {
    						fnCallback(result.data);// 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
                            //console.log(result);
                            count = result.data.iTotalRecords;
    					},
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert("status:" + XMLHttpRequest.status +
                                ",readyState:" + XMLHttpRequest.readyState +
                                ",textStatus:" + textStatus);
                        }
                    });
                }


                //搜索按钮绑定事件
                $('button[name=search]').click(
                	function(e) {
    					e.preventDefault();
    					oTable.fnDraw();
    				}
                );


        }
        };
    }();
//})(jQuery);