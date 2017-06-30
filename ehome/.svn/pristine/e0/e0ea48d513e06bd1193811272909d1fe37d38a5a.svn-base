function save() {

	var nodes = zTree.getCheckedNodes();
	var tmpNode;
	var ids = "";
	
	for (var i = 0; i < nodes.length; i++) {
		tmpNode = nodes[i];
		if (i != nodes.length - 1) {
			ids += tmpNode.id + ",";
		} else {
			ids += tmpNode.id;
		}
	}

//	var roleId = "1";
	var roleId = $("#roleId").val();
	var url = "addAuth";
	var postData;

	postData = {
		"roleId" : roleId,
		"menuIds" : ids
	};

	$.post(url, postData, function(data) {
		if(data==1){
			alert("操作成功");
			window.location.href="list";
		}else{
			alert("操作失败");
		}
	});

}
