var MenuComponent;

(function() {
	var layer ;
	layui.use('layer', function(){
		 layer = layui.layer;
			jQuery('a[name=update_info]').on('click',function(e){
				e.preventDefault();
				var userId = jQuery(this).attr('data-id');
//				parent.layer.tab({
//					area: ['700px', '550px'],
//				    tab: [{
//				        title: '个人信息修改',
//					    content: '<iframe id="user-info" src="../user/updateInfo.html?userId='+userId+'" frameborder="0" scrolling="yes" height="550" width="700"></iframe>'
//				    }
//				    /*#if($employee.isMgr) */
//				    , {
//				        title: '密码重置',
//					    content: '<iframe id="pw-reset" src="../user/resetPassword.html?pkId='+userId+'" frameborder="0" scrolling="yes" height="550" width="700"></iframe>'
//				    }
//				    /*#end */
//				    ],
//					end:function(){
//
//				    }
//				});
				parent.layer.open({
				    type: 2,
				    title: '修改资料',
				    area: ['700px', '600px'],
				    content:'../user/updateInfo.html?userId='+userId,
				    end:function(){
				    }
				});
			});
		});
	/**
	 * 加载系统菜单 暂时先在这里做啦 后面再改。。。。
	 */
	MenuComponent = function() {
		'use strict';
		return {
			init : function(path) {
				$.ajax({
			          type : "post",
			          url : path+'/admin/sysMenu',
			          data : {},
			          async : false,
			          success : function(data){
			          	var url = window.location.pathname;
			            data = eval("(" + data + ")");
						var menuList = data.data.menuList;
						var userAccount = data.data.userAccount;
						var userName = data.data.userName;
						var userId = data.data.id;
						jQuery('span[class=username]').html(userName);
						jQuery('li[name=updateinfo]>a').attr('data-id',userId);
						var div = jQuery('div[id=sidebar]');
						var ul = jQuery('ul[id=nav-accordion]',
								div);
						var html = "<li> ";
						html+='<a href="index.html" class="active"> ';
						html+='<i class="fa fa-dashboard"> ';
						html+="</i> ";
						html+="<span>Dashboard</span> ";
						html+="</a> ";
						html+="</li> ";
						//构造json树
						var jsonTree={};
						jsonTree.items=[];
						if(menuList != null && menuList.length > 0){
							for(var i=0;i<menuList.length;i++){
								if(menuList[i].parentId==0){
									var json ={};
									json.id=menuList[i].id;
									json.menuCode=menuList[i].menuCode;
									json.menuName=menuList[i].menuName;
									json.menuUrl=menuList[i].menuUrl;
									jsonTree.items.push(json);
								}
							}
							for(var i=0;i<jsonTree.items.length;i++){
								jsonTree.items[i].items=[];
								var level=[];
								for(var j=0;j<menuList.length;j++){
									if(menuList[j].parentId==jsonTree.items[i].id){
										var json={};
										json.id=menuList[j].id;
										json.menuCode=menuList[j].menuCode;
										json.menuName=menuList[j].menuName;
										json.menuUrl=menuList[j].menuUrl;
										if(menuList[j].isButton == 1)
										    level.push(0);
										else
											level.push(menuList[j].levelNum);
										json.items=[];
										for(var k=0;k<menuList.length;k++){
											if(menuList[k].parentId == json.id){
												var json1={};
												json1.id=menuList[k].id;
												json1.menuCode=menuList[k].menuCode;
												json1.menuName=menuList[k].menuName;
												json1.menuUrl=menuList[k].menuUrl;
												if(menuList[k].isButton == 1)
													level.push(0);
												else
												    level.push(menuList[k].levelNum);
												json1.items=[];
												for(var x=0;x<menuList.length;x++){
													if(menuList[x].parentId == json1.id){
														var json2={};
														json2.id=menuList[x].id;
														json2.menuCode=menuList[x].menuCode;
														json2.menuName=menuList[x].menuName;
														json2.menuUrl=menuList[x].menuUrl;
														if(menuList[x].isButton == 1)
														    level.push(0);
														else
															level.push(menuList[x].levelNum);
														json1.items.push(json2);
													}
												}
												json.items.push(json1);
											}
										}
										jsonTree.items[i].items.push(json);
									}
								}
								jsonTree.items[i].level=Math.max.apply(null, level);
							}
						}
						//console.log(jsonTree);
						//解析树
						var iconArr = ['fa-cogs','fa-tasks','fa-th','fa-book','fa-glass','fa-laptop','fa-shopping-cart','fa-sitemap','fa-th','fa-user'];
						for(var i=0;i<jsonTree.items.length;i++){
							if(jsonTree.items[i].level == 2 ){
								html+='<li class="sub-menu"> ';
								html+='<a href="javascript:;" > ';
								html+='<i class="fa '+iconArr[i]+'">';
								html+="</i> ";
								html+="<span> "+jsonTree.items[i].menuName+" </span> ";
								//html+='<span class="label label-success span-sidebar">4</span> ';
								html+='<span class="dcjq-icon"></span> ';
								html+='</a> ';
								html+='<ul class="sub"> ';
								for(var j=0;j<jsonTree.items[i].items.length;j++){
									//if(jsonTree.items[i].items[j].items!=null && jsonTree.items[i].items[j].items.length>0){
										html+='<li> ';
										html+="<a class='menu-item' href='"+path+"/"+jsonTree.items[i].items[j].menuUrl+"'>"+jsonTree.items[i].items[j].menuName+"</a> ";
										html+="</li> ";
									//}
								}
								html+='</ul> ';
								html+='</li> ';
							}else{
								html+='<li class="sub-menu">';
								html+='<a href="javascript:;">';
								html+='<i class="fa fa-sitemap"> </i> <span> '+jsonTree.items[i].menuName+' </span>';
								html+='</a>';
								html+='<ul class="sub">';
								for(var j=0;j<jsonTree.items[i].items.length;j++){
									if(jsonTree.items[i].items[j].items!=null && jsonTree.items[i].items[j].items.length>0){
										html+='<li class="sub-menu">';
										html+='<a href="boxed_page.html">';
										html+=''+jsonTree.items[i].items[j].menuName+'';
										html+='</a>';
										html+='<ul class="sub">';
										for(var k=0;k<jsonTree.items[i].items[j].items.length;k++){
											html+='<li>';
											html+="<a class='menu-item' href='"+path+"/"+jsonTree.items[i].items[j].items[k].menuUrl+"'> "+jsonTree.items[i].items[j].items[k].menuName+"</a>";
											html+='</li>';
										}
										html+='</ul>';
										html+='</li>';
									}else{
										html+='<li>';
										html+="<a class='menu-item' href='"+path+"/"+jsonTree.items[i].items[j].menuUrl+"'> "+jsonTree.items[i].items[j].menuName+"</a>";
										html+='</a>';
										html+='</li>';
									}
								}
								html+='</ul>';
								html+='</li> ';
							}
						}
						ul.html(html);
						var flag = false;
						setTimeout(function(){
							$('.menu-item').each(function(){
								var href = this.href;
								var index = href.indexOf('/ehome');
								var str = href.substring(index);
								var wh = str.indexOf('?');
								var val = wh > 0 ? str.substring(0,wh) : str;
								if (url == val) {
									$(this).parent().parent().prev().addClass('active');
									$(this).parent().parent().show();
									var search = window.location.search || '';
									var s = this.href.substring(this.href.indexOf('/ehome'));
									if (url + search == s) {
										$(this).parent().parent().find('li').removeClass('active');
										$(this).parent().addClass('active');
										flag = true;
									} else {
										if (!flag) $(this).parent().addClass('active');
									}

									if ($(this).parent().parent().prev().parent().parent().prev().length) {
										$(this).parent().parent().prev().parent().parent().prev().addClass('active');
										$(this).parent().parent().prev().parent().parent().show();
									}
								}
							});
						},500);

						//console.log(html);
			          }
			     });
			}
		};
	}();
})(jQuery);