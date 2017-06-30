var memberTable;
(function() {
    memberTable = function() {
        'use strict';
        return {
            init: function() {
            	var searchForm = jQuery('form[name=searchForm]');
            	var search = jQuery('button[name=search]');
            	var reset = jQuery('button[name=reset]');
            	var keyword = jQuery('input[name=keyword]');
            	search.on('click',handlerSearch);
            	reset.on('click',handlerReset);

                // 初始化表格对象
                var oTable = $('#member-list')
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
                        "aoColumns": [{"mData":null},{
                                "mData": "id"
                            }, // 此列不绑定数据源，用来显示序号
                            {
                                "mData": "memberName"
                            }, {
                                "mData": "idCard"
                            }, {
                                "mData": "tel"
                            },{
                                "mData": "orgName"
                            }, {
                                "mData": "auditStatusName"
                            }, {
                            	"mData" : "createTime"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
                            },{
                            	"mData" : "userResourceName"
                            }
                        ],
                        "bSort" :false,
                        "searching": false,
                        "ordering": false, // 排序操作在服务端进行，所以可以关了。
                        "bFilter": false, // 去掉搜索框
                        "bDestroy": true,
                        "processing": true,
                        "bServerSide": true,
                        "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                        "sAjaxSource": "queryMember",
                        "aoColumnDefs": [{
                        	"mRender": function(data, type, full){
                        		return '<input type="checkbox" class="checkchild" value="'+full.id+'"/>';
                        	},
                        	"aTargets": [0]
                        },{
                            "mRender": function(data, type,
                                full) {
                                var str = "";
                                str += '<a name="info" style="margin-left:5px" class="btn btn-success btn-xs" href="javascript:;" data-id="' +
                                full.id +
                                '"><span class="label" style="padding:0 5px 0 0">查看详情</span></a>';

                                str += '<a name="update" style="margin-left:5px" class="btn btn-danger btn-xs" href="javascript:;" data-id="' +
                                    full.id +
                                    '"><span class="label" style="padding:0 5px 0 0">修改</span></a>';

                                str += '<a name="approval" style="margin-left:5px" class="btn btn-success btn-xs" href="javascript:;" data-id="' +
                                full.id +
                                '"><span class="label" style="padding:0 5px 0 0">审核</span></a>';

                                str += '<a name="transfer" style="margin-left:5px;margin-top:5px" class="btn btn-danger btn-xs" href="javascript:;" data-id="' +
                                full.id +
                                '"><span class="label" style="padding:0 5px 0 0">转出</span></a>';

                                str += '<a name="delete" style="margin-left:5px;margin-top:5px" class="btn btn-warning btn-xs" href="javascript:;" data-id="' +
                                full.id +
                                '"><span class="label">删除</span></a>';
                                return str;
                            },
                            "aTargets": [9]
                        }],
                        "fnServerData": getCodeGridData
                    });
               // jQuery('table[id=member-list]').width('100%');

                var list = jQuery('table[id=member-list]');
                
                list.on('click', 'a[name=info]', handlerInfoClick);
                //选择事件
                list.on('click', 'a[name=update]', handlerUpdateClick);

                list.on('click', 'a[name=delete]', handlerDeleteClick);

                list.on('click', 'a[name=approval]', handlerApprovalClick);

                list.on('click', 'a[name=transfer]', handlerTransferClick);
                
      
                
                function handlerInfoClick(e){
                	e.preventDefault();
                	var pkId = jQuery(this).attr('data-id');
                	parent.layer.open({
                		type:2,
                		title:'详细资料',
                		area: ['80%', '80%'],
                		content:'../member/find-member.html?pkId='+pkId,
                		end:function(){

                		}
                	});
                }
                function handlerUpdateClick(e) {
                    e.preventDefault();
    			    var pkId = jQuery(this).attr('data-id');
    				parent.layer.open({
    				    type: 2,
    				    title: '修改',
    				    area: ['80%', '80%'],
    				    content:'../member/update-member.html?pkId='+pkId,
    				    end:function(){
    				    	oTable.fnDraw();
    				    }
    				});
                }

                function handlerDeleteClick(e) {
                    e.preventDefault();
                    var pkId = jQuery(this).attr('data-id');
    				parent.layer.open({
    				    content: '确定要删除吗?'
    					  ,btn: ['确定', '取消']
    					  ,yes: function(index, layero){
    							jQuery.post('../member/deleteMember',{'pkId':pkId},function(data){
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

                function handlerApprovalClick(e){
                	e.preventDefault();
                	var pkId = jQuery(this).attr('data-id');
					  var memberIds = [];
					  memberIds.push(pkId);
    				parent.layer.open({
    				    content: '请选择审核结果?'
    					  ,btn: ['通过', '不通过']
    					  ,yes: function(index, layero){
    						  jQuery.post('../member/updateStatus',{'memberIds':memberIds,"auditStatus":1},function(data){
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
    						  parent.layer.open({
    		    				    type: 2,
    		    				    title: '审核不通过',
    		    				    area: ['300px', '300px'],
    		    				    content:'../member/unApproval.html?memberIds='+memberIds,
    		    				    end:function(){
    		    				    	//oTable.fnDraw();
    		    				    }
    		    				});
    					  }
    					  ,cancel: function(){
    					  }
    					});
                }

                function handlerTransferClick(e){
                	  e.preventDefault();
                	  var pkId = jQuery(this).attr('data-id');
					  var memberIds = [];
					  memberIds.push(pkId);
					  parent.layer.open({
	    				    type: 2,
	    				    title: '转出',
	    				    area: ['300px', '300px'],
	    				    content:'../member/transfer.html?memberIds='+memberIds,
	    				    end:function(){
	    				    	//oTable.fnDraw();
	    				    }
	    				});
                }

                function handlerSearch(e){
                	e.preventDefault();
                	oTable.fnDraw();
                }

                function handlerReset(e){
                	e.preventDefault();
                	searchForm.clearForm();
                }

                //表格回调函数
                function getCodeGridData(url, aoData, fnCallback) {
                	var keyword =jQuery('input[name=keyword]').val();
                	var sex=jQuery('select[name=sex]').val();
                	var status=jQuery('select[name=status]').val();
                	var education=jQuery('select[name=degree]').val();
                	var auditStatus=jQuery('select[name=auditStatus]').val();
                	var userResource=jQuery('select[name=userResource]').val();
                	var political=jQuery('select[name=political]').val();
                    var start = aoData[3].value;
                    var rows = aoData[4].value;
                    var page = (start / rows) + 1;
                    $.ajax({
                        url: url,
                        data: {
                        	"keyword":keyword,
                        	"sex":sex,
                        	"status":status,
                        	"education":education,
                        	"auditStatus":auditStatus,
                        	"userResource":userResource,
                        	"political":political,
                            "sEcho": aoData[0].value,
                            "page": page,
                            "rows": rows
                        },
                        //traditional: true,
                        async: true,
                        type: 'post',
                        dataType: 'json',
                        success: function(result) {
                        	if(result.status == '20003'){
                        		layer.alert(result.data,{title : '操作提示',icon: 2});
                        	}else{
                        		fnCallback(result.data); // 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
                        	}
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert("status:" + XMLHttpRequest.status +
                                ",readyState:" + XMLHttpRequest.readyState +
                                ",textStatus:" + textStatus);
                        }
                    });
                }
            }
        };
    }();
})(jQuery);