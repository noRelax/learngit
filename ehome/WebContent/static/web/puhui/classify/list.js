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


var classifyTable;
//(function() {
//	debugger;
classifyTable = function() {
        'use strict';
        return {
            init: function() {
              //初始化表格对象
                var oTable = $('#list-table')
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
                		{"mData" : "countNum"},
                		{"mData" : "createTime"}, 
                		{"mData" : "sort"},
                		{"mData" : null}

                		],
                        "ordering": false, // 排序操作在服务端进行，所以可以关了。
                        "bFilter": false, // 去掉搜索框
                        "bDestroy": true,
                        "processing": true,
                        "bServerSide": true,
                        "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                        "sAjaxSource": "querylist",
                        "aoColumnDefs" : [
											{
												"mRender" : function(data, type, full) {
													return new Date().format(full.createTime)
												},
												"aTargets" : [ 2 ]
											},
  										{
  											"mRender" : function(data, type,
  													full) {
  												var str = "";
  												str += '<a name="edit" class="btn btn-primary" href="javascript:;" data-id="'
  														+ full.id
  														+ '"><span class="label">编辑</span></a>';
  												
  												str += '<a name="delete" class="btn btn-danger" href="javascript:;" data-id="'
													+ full.id + '" data-num="' + full.countNum +'">删除</a>';
  												return str;
  												},
  									
  											"aTargets" : [4]
  										} ],
                        
                        
                        "fnServerData": getRoleGrid1Data
                        
                    });
                jQuery('table[id=list-table]').width('100%');
                
                
                var list = jQuery('table[id=list-table]');
    			
    			//编辑事件
    			list.on('click', 'a[name=edit]', handlerEditClick);
    			//删除事件
    			list.on('click', 'a[name=delete]', handlerdeleteClick);
               
    			function handlerEditClick(e){
    			    e.preventDefault();	
    			    var pkId = jQuery(this).attr('data-id');
    				parent.layer.open({
    				    type: 2,
    				    title: '编辑信息',
    				    area: ['700px', '700px'],
    				    content:'edit?id='+pkId,
    				    end:function(){
    				    	
    				    }
    				});
    			}
    			
    			function handlerdeleteClick(e){
    			    e.preventDefault();	
    			    var pkId = jQuery(this).attr('data-id');
    			    var countNum = jQuery(this).attr('data-num');
    			    if(countNum == 0){
    			    	layer.confirm('确认删除此分类？', {
    			    		  btn: ['是的','取消'] //按钮
    			    		}, function(){
    			    			$.post('delete',{id:pkId},function(data){
    								if(data.status == 10000){
    									parent.layer.alert('删除成功', {title : '操作提示', icon : 1, time:2000, end: function(){
    										parent.layer.closeAll();
    										window.parent.location.reload();  
    									}});
    								}else{
    									layer.alert('删除失败，请稍后重试或联系管理员',{title : '操作提示',icon: 2});
    								}
    							},'json');
    			    		}, function(){
    			    		});
    			    }else{
    			    	layer.msg('该分类下存在商户，不可删除');
    			    }
    			}
                //表格回调函数
                function getRoleGrid1Data(url, aoData, fnCallback) {
                	var start = aoData[3].value;
    				var rows = aoData[4].value;
    				var page = (start / rows) + 1;
    				var classifyName = jQuery('input[name=classifyName]').val();
    				$.ajax({
    					url : url,
    					data : {
    						"name" : classifyName,
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