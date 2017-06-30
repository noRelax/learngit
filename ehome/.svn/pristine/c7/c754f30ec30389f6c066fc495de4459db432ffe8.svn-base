function getValue(tr){
	var id = tr.childNodes[0].innerHTML;
	window.location.href="add?id="+id;
}
function delData(tr,tb){
	var id = tr.childNodes[0].innerHTML;
	if(confirm("你确认删除吗?")){
		$.ajax({
			url:'deleteRole?id='+id,
			type:'get',
			success:function(data){
//         var obj = eval('(' + data + ')');
				if(data==1){
					alert("删除成功");
					tb.removeChild(tr);
				}else{
					alert("删除失败");
				}
			}
		});
	}else{
		
	}
}
// 待完善
function fixData(tr,tb){
	
}

//菜单权限
function editRights(tr) {
	var id = tr.childNodes[0].innerHTML;
	window.location.href="auth?roleId="+id;	
}

//查看自己角色的菜单权限
function catRights(tr) {
	var id = tr.childNodes[0].innerHTML;
	debugger;
	window.location.href="catAuth?roleId="+id;	
}