function click(event, treeId, treeNode, clickFlag)
	{
	   var id=treeNode.id;
	   var type=treeNode.type;
	 
	   $('#orgainId').val(id);
	   //增加节点类型的赋值 --zsh
	   $('#type').val(type);
	   $.ajax({
		   url:'getChilds?nodeId='+id,
		   dataType:'json',
		   type:'POST',
		   success:function(data){
			   var obj = data;
			      var tabDataHtml=  
			      "<table id='table_report' class='table table-striped table-bordered table-hover'>"+
						"<th width='10%' align='center'>机构编号</th>"+
						"<th width='15%' align='center'>机构名称</th>"+
						"<th width='15%' align='center'>机构类型</th>"+
						"<th width='20%' align='center'>单位性质</th>"+
						"<th width='7%' align='center'>排序</th>"+
						"<th width='10%' align='center'>备注</th>"+
						"<th width='26%' align='center'>操作</th>";
				       for(var k=0;k<obj.length;k++)
					   {
				    	   var typeName='';
				    	   var company_nature ='';
				    	   if(obj[k].type==0)
				    		   typeName='上级工会';
				    	   else if(obj[k].type==1)
				    		   typeName='部门';
				    	   else
				    		   typeName='基层工会';
				    	   
				    	   if(obj[k].company_nature==1)
				    		   company_nature='国家机关/事业单位';
				    	   else if(obj[k].company_nature==2)
				    		   company_nature='国有企业';
				    	   else if(obj[k].company_nature==3)
				    		   company_nature='集体企业';
				    	   else if(obj[k].company_nature==4)
				    		   company_nature='民营/私营/个体企业';
				    	   else if(obj[k].company_nature==5)
				    		   company_nature='其它';
				    	   else if(obj[k].company_nature==6)
				    		   company_nature='与港澳台合资/合作';
				    	   else if(obj[k].company_nature==7)
				    		   company_nature='中外合资/合作';
				    	   else
				    		   company_nature='';
				    	   
				    	   tabDataHtml=tabDataHtml + "<tr class='trHead' height='33px' >";
				    	   tabDataHtml=tabDataHtml +"<td>"+obj[k].id+"</td>"+
				    	   "<td>"+obj[k].name+"</td>"+
				    	   "<td>"+typeName+"</td>"+
				    	   "<td>"+company_nature+"</td>"+
				    	   "<td>"+obj[k].orderby+"</td>";
				    	   if(obj[k].remark==null)
				    	   {
				    		   obj[k].remark="";
				    	    } 
				    	   tabDataHtml=tabDataHtml + 	
				    	   "<td>"+obj[k].remark+"</td>"+
				    	   "<td><div class='btn-group btnGroup'><a class='btn btn-mini btn-info' title='编辑' onclick='modify("+obj[k].id+")'><i class='icon-edit'></i>编辑</a><a class='btn btn-mini btn-danger' title='删除' onclick='delNode("+obj[k].id+")'><i class='icon-trash'></i>删除</a></div></td>"+
				    	  
				           "</tr>";
					   }
				       
				       tabDataHtml=tabDataHtml+"</table>";
				       
				       $('#tab_tmp').hide();
				       $('#data_tab').html(tabDataHtml);
		   }
		   
	   });
	}
				
				
//新增
function addNode()
{
 
	var pid=$('#orgainId').val();
	if(pid==null || pid=="")
		{
		   alert('请选择机构节点');
		   return ;
		}
    var type= $('#type').val();//父节点类型
   // window.location.href="add.html?id="+id;
    //id+"&pid="+pid+"&type="+type+"&time="+new Date().getTime();
	window.location.href="add?parent_id="+pid+"&type="+type+"&time"+new Date().getTime();
}
				 
//编辑
function modify(id)
{
//	var pid = $('#orgainId').val();
//	var type = $('#type').val();
	
	window.location.href="add?id="+id+"&time"+new Date().getTime();

	
}

function AsyncSuccess(event, treeId, treeNode, msg) {
	if (msg=='[]') {
		treeNode.isParent = false;
		zTree.updateNode(treeNode);
	}
}
	
function TreeExpand(treeId,treeNode){
	if (!treeNode.isAjaxing) {
		zTree.reAsyncChildNodes(treeNode, "refresh", true);
		return true;
	} else {
		alert("正在下载数据中，请稍后展开节点。。。");
		return false;
	}
}

function urls(treeId, treeNode){
	return 'getChildNode?nodeId='+treeNode.id;
}

//删除
function delNode(id)
{
	var pid=$('#orgainId').val();
	//判断是否有子节点 有 return false
	if(confirm("你确认删除吗?")){
		$.ajax({
			url:'deleteNode?id='+id,
			type:'get',
			success:function(data){
//         var obj = eval('(' + data + ')');
				if(data==1){
					alert("删除成功");
					window.location.href="list";
				}else{
					alert("删除失败");
					window.location.href="list";
				}
			}
		});
	}else{
		
	}

}	
				
//多选				
function selectAll()
{
	 var checklist = document.getElementsByName ("ids");
	 alert(checklist.length);
	   if(document.getElementById("zcheckbox").checked){
	   for(var i=0;i<checklist.length;i++){
	      checklist[i].checked = true;
	   } 
	 }else{
	  for(var j=0;j<checklist.length;j++){
	     checklist[j].checked = false;
	  }
	 }
}
	


