var codeTable;
(function() {
    codeTable = function() {
        'use strict';
        return {
            init: function() {
                //console.log(arguments)
                var dictTypeId = jQuery('input[name=id]').val();

                // 初始化表格对象
                var oTable = $('#code-list')
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
                        "aoColumns": [ {
                                "mData": "id"
                            }, // 此列不绑定数据源，用来显示序号
                            {
                                "mData": "dictKey"
                            }, {
                                "mData": "dictValue"
                            }, {
                                "mData": "sort"
                            },{
                                "mData": "remark"
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
                        "sAjaxSource": "queryDict",
                        "aoColumnDefs": [{
                            "mRender": function(data, type,
                                full) {
                                var str = "";
                                str += '<a name="update" class="btn btn-warning btn-xs" href="javascript:;" data-id="' +
                                    full.id +
                                    '"><span class="label" style="padding:0 5px 0 0">修改</span></a>';
                                str += '<a name="delete" style="margin-left:5px" class="btn btn-warning btn-xs" href="javascript:;" data-id="' +
                                full.id +
                                '"><span class="label">删除</span></a>';
                                return str;
                            },
                            "aTargets": [5]
                        }],
                        "fnServerData": getCodeGridData
                    });
                jQuery('table[id=code-list]').width('100%');

                var list = jQuery('table[id=code-list]');
                //选择事件
                list.on('click', 'a[name=update]', handlerUpdateClick);
                
                list.on('click', 'a[name=delete]', handlerDeleteClick);
                function handlerUpdateClick(e) {
                    e.preventDefault();
    			    var pkId = jQuery(this).attr('data-id');
    				parent.layer.open({
    				    type: 2,
    				    title: '修改常数代码项',
    				    area: ['700px', '600px'],
    				    content:'../dict/update-dict.html?pkId='+pkId,
    				    end:function(){
    				    	oTable.fnDraw();
    				    }
    				});
                    
                   // 
                }
                
                function handlerDeleteClick(e) {
                    e.preventDefault();
                    var pkId = jQuery(this).attr('data-id');
    				parent.layer.open({
    				    content: '确定要删除吗?'
    					  ,btn: ['确定', '取消']
    					  ,yes: function(index, layero){
    							jQuery.post('../dict/deleteDict',{'pkId':pkId},function(data){
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
                //表格回调函数
                function getCodeGridData(url, aoData, fnCallback) {
                    var start = aoData[3].value;
                    var rows = aoData[4].value;
                    var page = (start / rows) + 1;
                    $.ajax({
                        url: url,
                        data: {
                            "dictTypeId": dictTypeId,
                            "sEcho": aoData[0].value,
                            "page": page,
                            "rows": rows
                        },
                        //traditional: true,
                        async: false,
                        type: 'post',
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
            }
        };
    }();
})(jQuery);