function click(event, treeId, treeNode, clickFlag) {
	var id = treeNode.id;
	var type = treeNode.type;

	$('#menuId').val(id);
	// 增加节点类型的赋值 --zsh
	$('#type').val(type);
	$
			.ajax({
				url : 'getChilds?nodeId=' + id,
				type : 'POST',
				success : function(data) {
					var obj = eval('(' + data + ')');
					var tabDataHtml = "<table id='table_report' class='table table-striped table-bordered table-hover'>"
							+ "<th width='10%' align='center'>菜单id</th>"
							+ "<th width='10%' align='center'>菜单code</th>"
							+ "<th width='10%' align='center'>菜单url</th>"
							+ "<th width='100%' align='center'>菜单名称</th>"
							+ "<th width='7%' align='center'>排序</th>"
							+ "<th width='30%' align='center'>备注</th>"
							+ "<th width='16%' align='center'>操作</th>";
					for (var k = 0; k < obj.length; k++) {
						tabDataHtml = tabDataHtml
								+ "<tr class='trHead' height='33px' >";
						tabDataHtml = tabDataHtml + "<td>" + obj[k].id
								+ "</td>" + "<td>" + obj[k].code + "</td>"
								+ "<td>" + obj[k].menu_url + "</td>" + "<td>"
								+ obj[k].name + "</td>" + "<td>"
								+ obj[k].orderby + "</td>";
						if (obj[k].remark == null) {
							obj[k].remark = "";
						}
						tabDataHtml = tabDataHtml
								+ "<td>"
								+ obj[k].remark
								+ "</td>"
								+ "<td><div class='btn-group btnGroup'><a class='btn btn-mini btn-info' title='编辑' onclick='modify("
								+ obj[k].id
								+ ")'><i class='icon-edit'></i>编辑</a><a class='btn btn-mini btn-danger' title='删除' onclick='delNode("
								+ obj[k].id
								+ ")'><i class='icon-trash'></i>删除</a></div></td>"
								+

								"</tr>";
					}

					tabDataHtml = tabDataHtml + "</table>";

					$('#tab_tmp').hide();
					$('#data_tab').html(tabDataHtml);
				}

			});
}

// 新增
function addNode() {

	var pid = $('#menuId').val();
	if (pid == null || pid == "") {
		alert('请选择菜单节点');
		return;
	}
	var type = $('#type').val();// 父节点类型
	// window.location.href="add.html?id="+id;
	// id+"&pid="+pid+"&type="+type+"&time="+new Date().getTime();
	window.location.href = "add?parent_id=" + pid + "&type=" + type + "&time"
			+ new Date().getTime();
}

// 编辑
function modify(id) {
	// var pid = $('#menuId').val();
	// var type = $('#type').val();

	window.location.href = "add?id=" + id + "&time" + new Date().getTime();

}

// 删除
function delNode(id) {
	var pid = $('#menuId').val();
	// 判断是否有子节点 有 return false
	if (confirm("你确认删除吗?")) {
		$.ajax({
			url : 'deleteNode?id=' + id,
			type : 'get',
			success : function(data) {
				// var obj = eval('(' + data + ')');
				if (data == 1) {
					alert("删除成功");
					window.location.href = "list";
				} else {
					alert("删除失败");
					window.location.href = "list";
				}
			}
		});
	} else {

	}

}

// 多选
function selectAll() {
	alert(111);
	var checklist = document.getElementsByName("ids");
	alert(checklist.length);
	if (document.getElementById("zcheckbox").checked) {
		for (var i = 0; i < checklist.length; i++) {
			checklist[i].checked = true;
		}
	} else {
		for (var j = 0; j < checklist.length; j++) {
			checklist[j].checked = false;
		}
	}
}
