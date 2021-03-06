var groupTable;
(function() {
   groupTable = function() {
        'use strict';
        return {
            init: function() {
                //console.log(arguments)
             
              
                // 初始化表格对象
                var oTable = $('#houtai-list')
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
                        "aoColumns": [{
                                "mData": "id"
                            }, // 此列不绑定数据源，用来显示序号
                            {
                                "mData": "groupName"
                            },{
                                "mData": "memberNum"
                            },{
                                "mData": "groupTypeName"
                            },{
                                "mData": "createTime"
                            },{
                                "mData": null
                            }
                        ],
                        "ordering": false, // 排序操作在服务端进行，所以可以关了。
                        "bFilter": false, // 去掉搜索框
                        "bDestroy": true,
                        "processing": true,
                        "bServerSide": true,
                        "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                        "sAjaxSource": "queryHTlist",
                        "aoColumnDefs": [{
                            "mRender": function(data, type,full) {
                                var str = "";
                                str += '<a name="queryHT"  class="btn btn-info" href="javascript:;" data-id="' +
                                    full.id +'"><span class="label">查看后台分组用户</span></a>';
                              
                                str += '<a name="updateHT" class="btn btn-primary" href="javascript:;" data-id="' +
                                    full.id +'"><span class="label">修改用户分组</span></a>';
                                
                                str += '<a name="deleteHT" class="btn btn-danger" href="javascript:;" data-id="' +
                                    full.id +'"><span class="label">删除用户分组</span></a>';

                                return str;
                            },
                            "aTargets": [5]
                        }],
                        "fnServerData": getRoleGrid1Data
                    });
                jQuery('table[id=houtai-list]').width('100%');
                var oTable2 = $('#qiantai-list').dataTable({
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
                    "aoColumns": [{
                        "mData": "id"
                    }, // 此列不绑定数据源，用来显示序号
                    {
                        "mData": "groupName"
                    },{
                        "mData": "memberNum"
                    },{
                        "mData": "groupTypeName"
                    },{
                        "mData": "createTime"
                    },{
                        "mData": null
                    }
                    ],
                    "ordering": false, // 排序操作在服务端进行，所以可以关了。
                    "bFilter": false, // 去掉搜索框
                    "bDestroy": true,
                    "processing": true,
                    "bServerSide": true,
                    "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                    "sAjaxSource": "queryQTlist",
                    "aoColumnDefs": [{
                        "mRender": function(data, type,full) {
                        	var str = "";
                            str += '<a name="queryQT" type="width:5px" class="btn btn-info" href="javascript:;" data-id="' +
                                full.id +'"><span class="label">查看前台分组用户</span></a>';
                          
                            str += '<a name="updateQT" class="btn btn-primary" href="javascript:;" data-id="' +
                                full.id +'"><span class="label">修改用户分组</span></a>';
                            
                            str += '<a name="deleteQT" class="btn btn-danger" href="javascript:;" data-id="' +
                                full.id +'"><span class="label">删除用户分组</span></a>';

                            return str;
                        },
                        "aTargets": [5]
                    }],
                    "fnServerData": getRoleGrid2Data
                });

                jQuery('table[id=qiantai-list]').width('100%');
                var list = jQuery('table[id=houtai-list]');
               
                var remove_list = jQuery('table[id=qiantai-list]');
                //---------------------------------------------------
                //跳转用户分组成员事件
                list.on('click', 'a[name=queryHT]', handlerQueryGroupClick);
                //修改用户分组
                list.on('click', 'a[name=updateHT]', handlerUpdateGroupClick);
                //删除用户分组
                list.on('click', 'a[name=deleteHT]', handlerdeleteGroupClick);
                
                //跳转用户分组成员事件
                remove_list.on('click', 'a[name=queryQT]', handlerQueryGroupClick);
                //修改用户分组
                remove_list.on('click', 'a[name=updateQT]', handlerUpdateGroupClick);
                //删除用户分组
                remove_list.on('click', 'a[name=deleteQT]', handlerdeleteGroupClick);
                //移出事件
               // remove_list.on('click', 'a[name=updateHT]', handlerRemovedClick);
                
                function handlerQueryGroupClick(e) {
    				
    				e.preventDefault();
    				var groupId = jQuery(this).attr('data-id');
    				window.location.href = "../UserGroup/onehoutai?groupId="+groupId;
    			}
                //修改
                function handlerUpdateGroupClick(e) {
                	e.preventDefault();
    				var  groupId = jQuery(this).attr('data-id');
    				parent.layer.open({
    					type : 2,
    					title : '编辑信息',
    					area : [ '400px', '250px' ],
    					content : '../UserGroup/goEditGroupPage?id=' + groupId,
    					end : function() {

    					}
    				});
    				
    			}
                //删除
                function handlerdeleteGroupClick(e) {
    				
    				e.preventDefault();
    				var pkId = jQuery(this).attr('data-id');
     				parent.layer.open({
    					content : '删除将会丢失内页，是否继续？',
    					btn : [ '确定', '取消' ],
    					yes : function(index, layero) {
    						jQuery.post('../UserGroup/delete', {
    							"id" : pkId
    						}, function(data) {
    							if (data.status == 10000) {
    								parent.layer.alert(data.data, {
    									title : '操作提示',
    									icon : 1,
    									time : 2000,
    									end : function() {
    										window.parent.location.reload();
											parent.layer.closeAll();
    									}
    								});
    							} else {
    								layer.alert(data.data, {
    									title : '操作提示',
    									icon : 2
    								});
    								window.parent.location.reload();
									parent.layer.closeAll();
    							}
    						}, 'json');
    					},
    					btn2 : function(index, layero) {
    					},
    					cancel : function() {
    					}
    				});
    			}
                
              
                //---------------------------------------------------------------
                //表格回调函数
                function getRoleGrid1Data(url, aoData, fnCallback) {
                    var start = aoData[3].value;
                    var rows = aoData[4].value;
                    var page = (start / rows) + 1;
                    var roleName = jQuery('input[name=roleName]').val();
                    var roleType = jQuery('select[name=roleType]').val();
                    $.ajax({
                        url: url,
                        data: {
                            "groupType": 1,
                            "sEcho": aoData[0].value,
                            "page": page,
                            "rows": rows
                        },
                        //traditional: true,
                        async: false,
                        type: 'POST',
                        dataType: 'json',
                        success: function(result) {
                            fnCallback(result.data); // 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert("status:" + XMLHttpRequest.status +
                                ",readyState:" + XMLHttpRequest.readyState +
                                ",textStatus:" + textStatus);
                        }
                    });
                }
                //表格回调函数
                function getRoleGrid2Data(url, aoData, fnCallback) {
                    var start = aoData[3].value;
                    var rows = aoData[4].value;
                    var page = (start / rows) + 1;
                    $.ajax({
                        url: url,
                        data: {
                            "groupType": 2,
                            "sEcho": aoData[0].value,
                            "page": page,
                            "rows": rows
                        },
                        //traditional: true,
                        async: false,
                        type: 'POST',
                        dataType: 'json',
                        async: false,
                        success: function(result) {
                            fnCallback(result.data); // 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
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