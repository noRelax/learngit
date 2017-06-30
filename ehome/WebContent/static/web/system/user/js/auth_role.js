var roleTable;
(function() {
    roleTable = function() {
        'use strict';
        return {
            init: function() {
                //console.log(arguments)
                var fkUserId = jQuery('input[name=fkUserId]').val();
                var submitBtn = jQuery('button[name=submitAuth]');
                var cancelBtn = jQuery('button[name=cancelAuth]');
                var closeBtn = jQuery('button[name=close]');
                //客户端缓存
                var authRoleId = sessionStorage.getItem("auth_role_id_" + fkUserId);
                authRoleId = authRoleId == null ? '[]' : authRoleId;
                authRoleId = jQuery.parseJSON(authRoleId);
                if (jQuery.isArray(authRoleId)) {} else {
                    authRoleId = [];
                }
                var unAuthRoleId = sessionStorage.getItem("un_auth_role_id_" + fkUserId);
                unAuthRoleId = unAuthRoleId == null ? '[]' : unAuthRoleId;
                unAuthRoleId = jQuery.parseJSON(unAuthRoleId);
                if (jQuery.isArray(unAuthRoleId)) {} else {
                    unAuthRoleId = [];
                }

                // 初始化表格对象
                var oTable = $('#role-list')
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
                                "mData": "roleName"
                            }, {
                                "mData": null
                            }
                        ],
                        "ordering": false, // 排序操作在服务端进行，所以可以关了。
                        "bFilter": false, // 去掉搜索框
                        "bDestroy": true,
                        "processing": true,
                        "bServerSide": true,
                        "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                        "sAjaxSource": "queryRoleList",
                        "aoColumnDefs": [{
                            "mRender": function(data, type,
                                full) {
                                var str = "";
                                str += '<a name="selected" class="btn btn-warning btn-xs" href="javascript:;" data-id="' +
                                    full.id +
                                    '"><span class="label">选择</span></a>';
//								str += '<a class="btn btn-danger btn-xs" name="query" href="javascript:;" style="margin-left:5px" data-id="'
//									+ full.id
//									+ '"><span class="label">权限预览</span></a>';
                                return str;
                            },
                            "aTargets": [2]
                        }],
                        "fnServerData": getRoleGrid1Data
                    });
                jQuery('table[id=role-list]').width('100%');
                var oTable2 = $('#selected-role-list').dataTable({
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
                        "mData": "roleName"
                    }, {
                        "mData": null
                    }],
                    "ordering": false, // 排序操作在服务端进行，所以可以关了。
                    "bFilter": false, // 去掉搜索框
                    "bDestroy": true,
                    "processing": true,
                    "bServerSide": true,
                    "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                    "sAjaxSource": "querySelectRoleList",
                    "aoColumnDefs": [{
                        "mRender": function(data, type,
                            full) {
                            var str = "";
                            str += '<a name="removed" class="btn btn-warning btn-xs" href="javascript:;" data-id="' +
                                full.id +
                                '" ><span class="label">移出</span></a>';
//                            
//							str += '<a class="btn btn-danger btn-xs" name="query" href="javascript:;" style="margin-left:5px" data-id="'
//								+ full.id
//								+ '"><span class="label">权限预览</span></a>';
                            return str;
                        },
                        "aTargets": [1]
                    }],
                    "fnServerData": getRoleGrid2Data
                });

                jQuery('table[id=selected-role-list]').width('100%');
                var list = jQuery('table[id=role-list]');
                var remove_list = jQuery('table[id=selected-role-list]');
                //选择事件
                list.on('click', 'a[name=selected]', handlerSelectedClick);
                //移出事件
                remove_list.on('click', 'a[name=removed]', handlerRemovedClick);
                //预览事件
                list.on('click','a[name=query]',handlerQueryClick);
                remove_list.on('click','a[name=query]',handlerQueryClick);

                function handlerSelectedClick(e) {
                    e.preventDefault();
                    var pkId = jQuery(this).attr('data-id');
                    if (jQuery.inArray(pkId, unAuthRoleId) >= 0) {
                        for (var x in unAuthRoleId) {
                            if (unAuthRoleId[x] == pkId) {
                                delete unAuthRoleId[x];
                            }
                        }
                    } else {
                        authRoleId.push(pkId);
                    }
                    oTable.fnDraw();
                    oTable2.fnDraw();
                }

                function handlerRemovedClick(e) {
                    e.preventDefault();
                    var pkId = jQuery(this).attr('data-id');
                    if (jQuery.inArray(pkId, authRoleId) >= 0) {
                        for (var x in authRoleId) {
                            if (authRoleId[x] == pkId) {
                                delete authRoleId[x];
                            }
                        }
                    } else {
                        unAuthRoleId.push(pkId);
                    }
                    oTable.fnDraw();
                    oTable2.fnDraw();
                }
                
                function handlerQueryClick(e){
                	e.preventDefault();
                	var pkId = jQuery(this).attr('data-id');
    				parent.layer.open({
    				    type: 2,
    				    title: '权限预览',
    				    area: ['700px', '700px'],
    				    content:'../user/resourceQuery.html?userId='+pkId,
    				    end:function(){
    				    	
    				    }
    				});
                }
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
                            "userId": fkUserId,
                            "roleType": roleType,
                            "roleName": roleName,
                            "authRoleId": authRoleId,
                            "unAuthRoleId": unAuthRoleId,
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
                            "userId": fkUserId,
                            "authRoleId": authRoleId,
                            "unAuthRoleId": unAuthRoleId,
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

                /**
                 * 授权角色事件
                 */
                submitBtn.on('click', function(e) {
                    $.ajax({
                        url: 'authRole',
                        data: {
                        	"userId": fkUserId,
                            "authRoleId": authRoleId,
                            "unAuthRoleId": unAuthRoleId
                        },
                        //traditional: true,
                        async: false,
                        type: 'POST',
                        dataType: 'json',
                        success: function(result) {
                            if(result.status == 10000){
                            	parent.layer.alert(result.data, {title : '操作提示', icon : 1, time:2000, end: function(){
									parent.layer.closeAll();
									window.parent.location.reload();  
								}});
                            	//window.location.href="index.html";
                            }else{
                            	parent.layer.alert(result.data,{title : '操作提示',icon: 2});
                            }
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                        }
                    });
                });

                /**
                 * 取消事件
                 */
                cancelBtn.on('click', function(e) {
                	window.location.href="index.html";
                })
                closeBtn.on('click', function(e) {
                	window.location.href="index.html";
                })
            }
        };
    }();
})(jQuery);