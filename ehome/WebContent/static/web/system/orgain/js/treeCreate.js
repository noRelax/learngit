
var zTree,rMenu;

var async,checkbox,radio = false;

/**
 * 
 * @param nodeTree 
 * @param ulName 树所在层的id名称
 * @param treeType 树类型：put 普通 duox 多选  danx  单选
 * @param async  是否异步
 * @param rMenus 是否右键
 */
function treeCreate(nodeTree,ulName,treeType,async,rMenus){
	
	setting = {
		view:{
			
		},
		async:{
		},
		check: {
			
		},
		data: {
			key: {
				
			},
			simpleData: {
				
			}
		},
		callback: {
			beforeExpand : ibeforeExpand,
			onAsyncSuccess : iAsyncSuccess			
		}
	};
	if(treeType=="put"){
		
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
		
	}else if(treeType=="danx"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.check.enable = true;
		setting.check.chkStyle = "radio";
		setting.check.radioType = "all";
		setting.callback.onCheck=onCheck;
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
		
	}else if(treeType=="duox"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.check.enable = true;
		
		
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
	}
$.fn.zTree.init($("#"+ulName+""), setting, nodeTree);

zTree = $.fn.zTree.getZTreeObj(""+ulName+"");

}


/**
 * 加搜索的树
 * @param nodeTree 
 * @param ulName 树所在层的id名称
 * @param treeType 树类型：put 普通 duox 多选  danx  单选
 * @param async  是否异步
 * @param rMenus 是否右键
 */
function searchTreeCreate(nodeTree,ulName,treeType,async,rMenus){
	
	setting = {
		async:{
		},
		check: {
			
		},
		data: {
			key: {
				
			},
			simpleData: {
				
			}
		},
		callback: {
		} , 
        view :{  
            fontCss: getFontCss  
        }
	};
	if(treeType=="put"){
		
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
		
	}else if(treeType=="danx"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.check.enable = true;
		setting.check.chkStyle = "radio";
		setting.check.radioType = "level";
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
		
	}else if(treeType=="duox"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.check.enable = true;
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
	}
$.fn.zTree.init($("#"+ulName+""), setting, nodeTree);

zTree = $.fn.zTree.getZTreeObj(""+ulName+"");

}

//搜索样式
function getFontCss(treeId, treeNode) {
    return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};  
}

//检索]
var nodeList = new Array();
function changeColor(id, key, value) {
	treeId = id;
	updateNodes(false);
	if (value != "") {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		nodeList = treeObj.getNodesByParamFuzzy(key, value);
		if (nodeList && nodeList.length > 0) {
			updateNodes(true);
		}
	}
}  

function updateNodes(highlight) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	for ( var i = 0; i < nodeList.length; i++) {
		nodeList[i].highlight = highlight;
		treeObj.updateNode(nodeList[i]);
	}
}
//检索

/**
 * 全局单选
 * @param nodeTree 
 * @param ulName 树所在层的id名称
 * @param async  是否异步
 * @param rMenus 是否右键
 */
function treeCreateDuox(nodeTree,ulName,async,rMenus){
	
	setting = {
			
		async:{
		},
		view:{
			
		},
		check: {
			 
		},
		data: {
			key: {
				
			},
			simpleData: {
				
			}
		},
		callback: {
		}
	};
	
	setting.callback.onCheck = onCheck;
	if(async == true){
		
		setting.async.enable = true;
		setting.async.url = getUrl;
		setting.async.autoParam = ["id"];
		
	}
	if(rMenus == true){
		
		setting.callback.onRightClick = OnRightClick;
		
		rMenu = $("#rMenu");
		
	}
 
	$.fn.zTree.init($("#"+ulName+""), setting, nodeTree);

	zTree = $.fn.zTree.getZTreeObj(""+ulName+"");

}

/**
 * 增加双击方法
 * @param nodeTree
 * @param ulName
 * @param treeType
 * @param async
 * @param rMenus
 * @return
 */
function treeCreateOndb(nodeTree,ulName,treeType,async,rMenus){
	
	setting = {
		view:{
		
		},
		async:{
		},
		check: {
			
		},
		data: {
			key: {
				
			},
			simpleData: {
				
			}
		},
		callback: {
		}
	};
	if(treeType=="put"){
		
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.view.addDiyDom = addDiyDom;
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
		
	}else if(treeType=="danx"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.check.enable = true;
		setting.check.chkStyle = "radio";
		setting.check.radioType = "level";
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
		
	}else if(treeType=="duox"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.check.enable = true;
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		if(rMenus == true){
			
			setting.callback.onRightClick = OnRightClick;
			
			rMenu = $("#rMenu");
			
		}
	}
$.fn.zTree.init($("#"+ulName+""), setting, nodeTree);

zTree = $.fn.zTree.getZTreeObj(""+ulName+"");

}	

	
/*右键单击事件，只有两层树，多层需重写*/
function OnRightClick(event, treeId, treeNode) {
	
	rightClick(event, treeId, treeNode);
	 
}



/**
 * 加搜索的树
 * @param nodeTree 
 * @param ulName 树所在层的id名称
 * @param treeType 树类型：put 普通 duox 多选  danx  单选
 * @param async  是否异步
 * @param rMenus 是否右键
 */
function radioCreate(nodeTree,ulName,treeType,async){
	
	setting = {
		async:{
		},
		check: {
			
		},
		data: {
			key: {
				
			},
			simpleData: {
				
			}
		},
		callback: {
		} , 
        view :{  
            fontCss: getFontCss  
        }
	};
	if(treeType=="put"){
		
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		 
		
	}else if(treeType=="danx"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.callback.onCheck = onCheck;
		setting.check.enable = true;
		setting.check.chkStyle = "radio";
		setting.check.radioType = "all";
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		 
		
	}else if(treeType=="duox"){
		
		setting.data.key.title = "t";
		setting.data.simpleData.enable = true;
		setting.callback.onClick = onClick;
		setting.check.enable = true;
		
		if(async == true){
			
			setting.async.enable = true;
			setting.async.url = getUrl;
			setting.async.autoParam = ["id"];
			
		}
		 
	}
$.fn.zTree.init($("#"+ulName+""), setting, nodeTree);

zTree = $.fn.zTree.getZTreeObj(""+ulName+"");

}

/**
 * 创建人：王杰
 * 传递tree创建的名称,可以重新加载当前选中的节点
 * @param {Object} treeName
 */
function reAsyncChiIdNode(treeName){
	var treeObj = $.fn.zTree.getZTreeObj(treeName);
	var nodes = treeObj.getSelectedNodes();
	if(nodes.length>0){
		treeObj.reAsyncChildNodes(nodes[0],"refresh");
	}
}


//隐藏右键菜单
function onBodyMouseDown(event){
	
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		rMenu.css({"visibility" : "hidden"});
	}
}	
/*隐藏右键菜单*/
function hideRMenu(rMenu) {
	if (rMenu) rMenu.css({"visibility": "hidden"});
}

function ibeforeExpand(treeId, treeNode) {
	if(typeof(TreeExpand)=='function'){
		TreeExpand(treeId,treeNode);
	}
}

function iAsyncSuccess(event, treeId, treeNode, msg){
	if(typeof(AsyncSuccess)=='function'){
		AsyncSuccess(event, treeId, treeNode, msg);
	}
}

/**以下事件均是按需要直接重写**/

/*重写鼠标单击事件*/
function onClick(event, treeId, treeNode, clickFlag) {
	click(event, treeId, treeNode, clickFlag);
}

/*选中事件*/
function onCheck(event, treeId, treeNode, clickFlag) {
	check(event, treeId, treeNode, clickFlag);
}

/*重写鼠标双击事件*/
function onDbClick(event, treeId, treeNode, clickFlag)
{
	dbClick(event, treeId, treeNode, clickFlag);
}
/*添加节点*/
function addTreeNode(){
	hideRMenu(rMenu);
	/**页面重写function addNode(zTree,rMenu){}**/
	addNode(zTree,rMenu);
}
/*删除节点*/
function delTreeNode(){
	hideRMenu(rMenu);
	/**页面重写function delNode(zTree,rMenu){}**/
	delNode(zTree,rMenu);
}
/*修改节点*/
function editTreeNode(){
	hideRMenu(rMenu);
	/**页面重写function editNode(zTree,rMenu){}**/
	editNode(zTree,rMenu);
}	
/*异步加载路径*/
function getUrl(treeId, treeNode) 
{
	/**页面重写function url(zTree,treeNode){}**/
	return urls(treeId, treeNode);
}

/*自定义增加input控件*/
function addDiyDom(treeId, treeNode) 
{
	/**页面重写function url(zTree,treeNode){}**/ 
	return addDom(treeId, treeNode);
}
/*移除自定义增加控件*/
function removeHoverDom(treeId, treeNode)
{
	/**页面重写function url(zTree,treeNode){}**/
	return removeDom(treeId, treeNode);
	
}
/*属标移入时添加自定义增加控件*/
function addHoverDom(treeId, treeNode)
{
	/**页面重写function url(zTree,treeNode){}**/
	return hoverDom(treeId, treeNode);
}
	
	
	

 




