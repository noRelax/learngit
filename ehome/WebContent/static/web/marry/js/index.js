
var marryPhotoTable;
 (function() {
marryPhotoTable = function() {
	'use strict';
	return {
		init : function() {
			
			var fkGroupId = jQuery('input[name=fkGroupId]').val();
			var submitBtn = jQuery('button[name=submitAuth]');
			var cancelBtn = jQuery('button[name=cancelAuth]');
			var closeBtn = jQuery('button[name=close]');
			var searchBtn = jQuery('button[name=search]');
			var resetBtn = jQuery('button[name=reset]');
			var searchForm = jQuery('form[name=searchForm]');
			searchBtn.on('click',handlerSearchClick);
			resetBtn.on('click',handlerResetClick);
			var uid = jQuery('input[name=uid]').val();
			
			var thumbUp = jQuery('a[name=thumb_up]');
			var commentUp = jQuery('a[name=comment_up]');
			var timeUp = jQuery('a[name=time_up]');
			thumbUp.on('click',function(e){
				e.preventDefault();
				jQuery('input[name=fieldName]').val(jQuery(this).attr('data-field'));
				jQuery('input[name=fieldSort]').val(jQuery(this).attr('data-sort'));
				oTable.fnDraw();
			});
			commentUp.on('click',function(e){
				e.preventDefault();
				jQuery('input[name=fieldName]').val(jQuery(this).attr('data-field'));
				jQuery('input[name=fieldSort]').val(jQuery(this).attr('data-sort'));
				oTable.fnDraw();
			});
			timeUp.on('click',function(e){
				e.preventDefault();
				jQuery('input[name=fieldName]').val(jQuery(this).attr('data-field'));
				jQuery('input[name=fieldSort]').val(jQuery(this).attr('data-sort'));
				oTable.fnDraw();
			});
			var thumbDown = jQuery('a[name=thumb_down]');
			var commentDown = jQuery('a[name=comment_down]');
			var timeDown = jQuery('a[name=time_down]');
			thumbDown.on('click',function(e){
				e.preventDefault();
				jQuery('input[name=fieldName]').val(jQuery(this).attr('data-field'));
				jQuery('input[name=fieldSort]').val(jQuery(this).attr('data-sort'));
				oTable.fnDraw();
			});
			commentDown.on('click',function(e){
				e.preventDefault();
				jQuery('input[name=fieldName]').val(jQuery(this).attr('data-field'));
				jQuery('input[name=fieldSort]').val(jQuery(this).attr('data-sort'));
				oTable.fnDraw();
			});
			timeDown.on('click',function(e){
				e.preventDefault();
				jQuery('input[name=fieldName]').val(jQuery(this).attr('data-field'));
				jQuery('input[name=fieldSort]').val(jQuery(this).attr('data-sort'));
				oTable.fnDraw();
			});
			// 初始化表格对象
			var oTable = $('#life-tableList')
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
											"mData" : null,// 获取列数据，跟服务器返回字段一致
											"sClass" : "center",
											"mRender" : function(data, type,
													full) {// 返回自定义的样式
												return "<label><input type='checkbox' name='key' value='"
														+ full.id
														+ "' class='ace' /><span class='lbl'></span></label>"
											}
										}, 
										{
											"mData" : "id"
										}, // 此列不绑定数据源，用来显示序号
										{
											"mData" : "imgUrl"
										}, 
										{
											"mData" : "idea",
											"mRender" : function(data,
													type, full) {// 返回自定义的样式

												var str = "";
												var len = data.length;
												if (len > 20) {
													str = data.substring(0,
															20)
															+ "......";
												} else {
													str = data;
												}
												return "<span style='width:100px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;' onmousemove='mouseOver(this,\""
												+ data
												+ "\");'>"
												+ str + "</span>";
											}
										},
										{
											"mData" : "location"
										}, 
										{
											"mData" : "appUserId"
										}, 
										{
											"mData" : "thumbUpNum"
										}, 
										{
											"mData" : "commentNum"
										}, 
										{
											"mData" : "publicTime"
										}, 
										{
											"mData" : "isRecommendedName"
										}, 
										{
											"mData" : "isShieldingName"
										}, 
										{
											"mData":null
										}
								],
								"bSort" :false,
								"ordering" : false, // 排序操作在服务端进行，所以可以关了。
								"bFilter" : false, // 去掉搜索框
								"bDestroy" : true,
								"processing" : true,
								"bServerSide" : true,
								"sAjaxDataProp" : "data", // 是服务器分页的标志，必须有
								"sAjaxSource" : "list",
								"aoColumnDefs" : [
										{
											"mRender" : function(data, type,
													full) {
												return '<input type="checkbox" class="checkchild" id="zcheckbox" value="'
														+ full.id + '"/>';
											},
											"aTargets" : [ 0 ]
										},
										{
											"mRender" : function(data, type,
													full) {
												var defaultImg = "../../static/ui/xq-mirror/image/u4952.png";
												if(full.pictureUrl!=null && full.pictureUrl!=''){
													return '<img style="width: 42px; height: 38px;" src="../../'+full.pictureUrl + '" rel="../../'+full.pictureUrl + '" onerror="errorImg(this)"  alt="" onmousemove="jqzoom(this)"/>';
												}else{
													return '<img style="width: 42px; height: 38px;" src="../../static/ui/xq-mirror/image/u4952.png" rel="../../static/ui/xq-mirror/image/u4952.png" alt="" onmousemove="jqzoom(this)">';
												}
												//return '<div class="jqzoom"><img style="width: 42px; height: 38px;" jqimg="images/shoe1_big.jpg"  src="../..//files/20170522/7fe1ff12e4b.jpg"/></div>';
											   // <img src="images/shoe1_small.jpg" style="width:300px; height:300px;" alt=""  jqimg="images/shoe1_big.jpg" id="bigImg"/>
											},
											"aTargets" : [ 2 ]
										},
										{
											"mRender" : function(data, type, full){
												if(full.thumbUpNum!=null && full.thumbUpNum!='')
												    return full.thumbUpNum;
												else
													return 0;
											},
											"aTargets" : [ 6 ]
										},
										{
											"mRender" : function(data, type, full){
												//return '<a name="" href="">'+full.commentNum+'</a>';
												if(full.commentNum!=null && full.commentNum!='')
												    return '<a style="color:#F00" name="commentPage" data-id="'+full.id+'" href="javascript:;">'+full.commentNum+'</a>';
												else
													return '<a style="color:#F00" name="commentPage" data-id="'+full.id+'" href="javascript:;">0</a>';
											},
											"aTargets" : [ 7 ]
										},
										{
											"mRender" : function(data, type,
													full) {
												var str = "";
												str += '<a name="edit" class="btn btn-primary" href="javascript:;" data-id="'
														+ full.id
														+ '"><span class="label">编辑</span></a>';
												return str;
											},
											"aTargets" : [11]
										} ],
								"fnServerData" : getRoleGrid1Data
							});
			jQuery('table[id=life-tableList]').width('100%');

			var list = jQuery('table[id=life-tableList]');
			
			list.on('click', 'a[name=commentPage]', handlerCommentPageClick);

			// 编辑事件
			list.on('click', 'a[name=edit]', handlerEditClick);

			function handlerEditClick(e) {
				e.preventDefault();
				var pkId = jQuery(this).attr('data-id');
				parent.layer.open({
					type : 2,
					title : '编辑信息',
					area : [ '1000px', '800px' ],
					content : '../../marry/photo/edit.html?id=' + pkId,
					end : function() {

					}
				});
			}

			// 表格回调函数
			function getRoleGrid1Data(url, aoData, fnCallback) {
				var start = aoData[3].value;
				var rows = aoData[4].value;
				var page = (start / rows) + 1;
				var searchType = jQuery('select[name=searchType]').val();
				var keyword = jQuery('input[name=keyword]').val();
				var isRecommended = jQuery('select[name=isRecommended]').val();
				var isShielding = jQuery('select[name=isShielding]').val();
				var selectRequire = jQuery('select[name=selectRequire]').val();
				var startPublictTime = jQuery('#startPublictTime').val();
				var endPublicTime = jQuery('#endPublicTime').val();
				if (keyword.length > 10) {
					keyword = "";
				}
				if(uid!=null && uid!='' && uid!=0){
					searchType=1;
					keyword=uid;
				}
				var fieldName = jQuery('input[name=fieldName]').val();
				var fieldSort = jQuery('input[name=fieldSort]').val();
				if(fieldName=='')
					fieldName="public_time";
				if(fieldSort == "")
					fieldSort ="DESC";
				
				$.ajax({
					url : url,
					data : {
						"searchType" : searchType,
						"keyword" : keyword,
						"isRecommended" : isRecommended,
						"isShielding" : isShielding,
						"selectRequire" : selectRequire,
						"startPublictTime" : startPublictTime,
						"endPublicTime" : endPublicTime,
						"fieldName" : fieldName,
						"fieldSort" : fieldSort,
						"sEcho" : aoData[0].value,
						"rows" : rows,
						"page" : page
					},
					async : false,
					type : 'POST',
					dataType : 'json',
					success : function(result) {
                    	if(result.status == '20003'){
                    		layer.alert(result.data,{title : '操作提示',icon: 2});
                    	}else{
                    		fnCallback(result.data); // 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
                    	}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("status:" + XMLHttpRequest.status
								+ ",readyState:" + XMLHttpRequest.readyState
								+ ",textStatus:" + textStatus);
					}
				});
			}
			
           

			function handlerSearchClick(e){
				e.preventDefault();
				var keyword = $('input[name=keyword]').val();
				if (keyword.length > 10) {
					alert("关键字长度不能超过10个字符");
					return false;
				} else {
					e.preventDefault();
					oTable.fnDraw();
				}
			}
			
			function handlerResetClick(e){
				e.preventDefault();
				searchForm.clearForm();  
			}
			
			function handlerCommentPageClick(e){
				e.preventDefault();
				var pkId = jQuery(this).attr('data-id');
				window.location.href = "../../marry/comment/index?photoId="+pkId;
			}
			
			/**
			 * 取消事件
			 */
			cancelBtn.on('click', function(e) {
				var value = window.location.search;
				var groupId = value.replace(/[^0-9]/ig, "");
				window.location.href = "houtai.html?groupId=" + groupId;
			})
			closeBtn.on('click', function(e) {
				var value = window.location.search;
				var groupId = value.replace(/[^0-9]/ig, "");
				window.location.href = "houtai.html?groupId=" + groupId;
			})
			
		}
	};
}();
 })(jQuery);
