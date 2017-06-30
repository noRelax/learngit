
var houtaiMemberTable;
//(function() {
//	debugger;
houtaiMemberTable = function() {
    	
        'use strict';
        return {
            init: function() {
                //console.log(arguments)
                var fkGroupId = jQuery('input[name=fkGroupId]').val();
                var submitBtn = jQuery('button[name=submitAuth]');
                var cancelBtn = jQuery('button[name=cancelAuth]');
                var closeBtn = jQuery('button[name=close]');
                var searchBtn = jQuery('button[name=search]');
    			var resetBtn = jQuery('button[name=reset]');
    			var searchForm = jQuery('form[name=searchForm]');
    			searchBtn.on('click',handlerSearchClick);


              //初始化表格对象
                var oTable = $('#houtai-member-list')
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
                       "aoColumns" : [ {
							"mData" : "id",//获取列数据，跟服务器返回字段一致
							"sClass" : "center",//显示样式
							"mRender" : function(data, type, full) {//返回自定义的样式
								return "<label><input type='checkbox' name='key' value='"+data+"' id='zcheckbox' class='ace' /><span class='lbl'></span></label>"
						      }
                        }, 
						{
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
                        "ordering": false, // 排序操作在服务端进行，所以可以关了。
                        "bFilter": false, // 去掉搜索框
                        "bDestroy": true,
                        "processing": true,
                        "bServerSide": true,
                        "sAjaxDataProp": "data", // 是服务器分页的标志，必须有
                        "sAjaxSource": "queryUserMember",
                        "aoColumnDefs": [{
                            "mRender": function(data, type,full) {
                            	var str = "";
                                str += '<a name="delete" class="btn btn-danger" href="javascript:;" data-id="' +
                                    full.id +'"><span class="label">删除分组成员</span></a>';
                                return str;
                            },
                            "aTargets": [9]
                        }],
                        "fnServerData": getRoleGrid1Data
                        
                    });
                jQuery('table[id=houtai-member-list]').width('100%');
                
                var list = jQuery('table[id=houtai-member-list]');
                
                list.on('click', 'a[name=delete]', getDeleteData);
                
                function getDeleteData(e){
                	e.preventDefault();
    				var pkId = jQuery(this).attr('data-id');
    				var groupId = jQuery('input[name=groupId]').val();
     				parent.layer.open({
    					content : '删除将会丢失内页，是否继续？',
    					btn : [ '确定', '取消' ],
    					yes : function(index, layero) {
    						jQuery.post('../UserGroup/deleteGroup', {
    							"userId" : pkId,
    							"groupId":groupId
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
                
                //表格回调函数
                function getRoleGrid1Data(url, aoData, fnCallback) {
                	var start = aoData[3].value;
    				var rows = aoData[4].value;
    				var page = (start / rows) + 1;
    				var userName = jQuery('input[name=userName]').val();
    				var groupId = jQuery('input[name=groupId]').val();
    				$.ajax({
    					url : url,
    					data : {
    					
    						"userName":userName,
    						"groupId":groupId,
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
                
                //加入用户分组
                submitBtn.on('click', function(e) {
                	     //var groupsId = jQuery('a[id=groupsId]').val();
                	
                	var checklist = document.getElementsByName("key");
            		if (document.getElementById("zcheckbox").checked) {
            			for ( var i = 0; i < checklist.length; i++) {
            				checklist[i].checked = 1;
            			}
            		} else {
            			for ( var j = 0; j < checklist.length; j++) {
            				checklist[j].checked = 0;
            			}
            		}
                	
                	     var value =window.location.search;
                	     var groupId = value.replace(/[^0-9]/ig,""); 
                         var checkboxes = document.getElementsByName('key'); 
                         var array=[];
                         var select = "";
                            for (var i = 0; i < checkboxes.length; i++) { 
                               if (checkboxes[i].checked) {
                                 // select += "" + Number(checkboxes[i].value) + ",";
                                  array.push(Number(checkboxes[i].value));
                               } 
                           } 
                           
                                 var newSelect = array;
                                  if (newSelect.length > 0) {
                                     //document.getElementById('ids').value = newSelect; 
                                     //var ids = $("#ids").val();
                                	 
                                     //console.log(ids)
                                     $.ajax({
         								
         								type: "POST",
         								
         								url: 'addMember',
         			                    data: {
         			                       	"USER_IDS": array,
         			                       	"groupId": groupId
         			                        },
         								//url: '${ctxPath}/UserGroup/addMember.do?USER_IDS='+ide,
         								dataType: 'json',
         		                        async: false,
         								cache: false,
         								success: function(result) {
         		                            if(result.status == 10000){
         		                            	//console.log(result)
         		                            	alert('添加成功!');
         		                            	window.location.href="houtai.html?groupId="+result.data;
         		                            	
         		                            }
         		                        },
         		                        error: function(XMLHttpRequest, textStatus, errorThrown) {
         		                        }
         							});
         					
                                         
                                      } else {
                                       alert('没选中项'); 
                                       return false;
                                  }
                         
                   
                });

                /**
                 * 取消事件
                 */
                cancelBtn.on('click', function(e) {
                	var value =window.location.search;
           	        var groupId = value.replace(/[^0-9]/ig,""); 
                	window.location.href="houtai.html?groupId="+groupId;
                })
                closeBtn.on('click', function(e) {
                	var value =window.location.search;
           	        var groupId = value.replace(/[^0-9]/ig,""); 
                	window.location.href="houtai.html?groupId="+groupId;
                })
            }
        };
    }();
//})(jQuery);