

function ajaxRequestArea(){
	 $.ajax({
			type : "POST",
			url : '../life/getArea',
			dataType : 'json',
			data:{},
			async : false,
			cache : false,
			success : function(result) {
				for(i=0;i<result.data.length;i++){
				if(result.data[i].level==1){
					result.data[i].type="province";
					result.data[i].value=result.data[i].label;
					result.data[i].parent="";
				}else{
					result.data[i].type="city";
					result.data[i].value=result.data[i].label;
					result.data[i].parent=result.data[i].pid;
				}
				}
				project = result.data;
			},

		});
}
ajaxRequestArea();
/*
var project = [
               {
                 id: null,
                 value: '北京',
                 level: 1,
                 pid: 0,
                 uid:110000
               },
                {
                 id: null,
                 value: '北京市',
                 level: 2,
                 pid: 110000,
                 uid:110100
               },
               {
                 id: null,
                 value: '湖南省',
                 level: 1,
                 pid: 1,
                 uid:430000
               },
               {
                 id: null,
                 value: '长沙市',
                 level: 2,
                 pid: 430000,
                 uid: 430100
               },
               {
                 id: null,
                 value: '衡阳市',
                 level: 2,
                 pid: 430000,
                 uid: 430422
               },
               {
                 id: null,
                 value: '湖北省',
                 level: 1,
                 pid: 1,
                 uid:450000
               },
               {
                 id: null,
                 value: '武汉市',
                 level: 2,
                 pid: 450000,
                 uid: 450100
               },
               {
                 id: null,
                 value: '襄阳',
                 level: 2,
                 pid: 450000,
                 uid: 450200
               },
               {
                 id: null,
                 value: '离岛',
                 level: 2,
                 pid: 820000,
                 uid: 820200
               },
               {
                 id: null,
                 value: '澳门特别行政区',
                 level: 1,
                 pid: 1,
                 uid: 820000
               },
               {
                 id: null,
                 value: '广东省',
                 level: 1,
                 pid: 1,
                 uid:440000
               },
               {
                 id: null,
                 value: '广西省',
                 level: 1,
                 pid: 1,
                 uid:420000
               },

               {
                 id: null,
                 value: '桂林市',
                 level: 2,
                 pid: 420000,
                 uid:420100
               },
               {
                  id: null,
                 value: '玉林市',
                 level: 2,
                 pid: 420000,
                 uid:420200
               },
               {
                  id: null,
                 value: '贵港市',
                 level: 2,
                 pid: 420000,
                 uid:420300
               },
               {
                 id: null,
                 value: '广州市',
                 level: 2,
                 pid: 440000,
                 uid:440100
               },
               {
                 id: null,
                 value: '湛江市',
                 level: 2,
                 pid: 440000,
                 uid:440200
               },
               {
                 id: null,
                 value: '汕头市',
                 level: 2,
                 pid: 440000,
                 uid:440300
               },
               {
                 id: null,
                 value: '广元市',
                 level: 2,
                 pid: 400000,
                 uid: 400100
               },
               {
                 id: null,
                 value: '广安市',
                 level: 2,
                 pid: 400000,
                 uid: 400200
               },
             ];
		*/	
			// 假设后台返回的数据
             var selectedData = [
               {
                 id: null,
                 value: '广东省',
                 level: 1,
                 pid: 1,
                 uid:440000
               },
               {
                 id: null,
                 value: '广西省',
                 level: 1,
                 pid: 1,
                 uid:420000
               },
               {
                 id: null,
                 value: '广州市',
                 level: 2,
                 pid: 440000,
                 uid:440100
               },
               {
                 id: null,
                 value: '广元市',
                 level: 2,
                 pid: 400000,
                 uid: 400100
               },
               {
                 id: null,
                 value: '广安市',
                 level: 2,
                 pid: 400000,
                 uid: 400200
               }
             ];





             var vm = new Vue({
               el: '#app',
               data: {
                 inputLock:false,
                 cityData: [],		// 所有城市数据
                 searchData: [],	// 搜索返回的城市数据
                 showData: [],		// 已选择的城市数据
                 isCity: false,
                 inputVal: ''
               },
               mounted:function(){
                /* this.showData = [
                   {
                     pid: 440000,
                     pname: '广东省',
                     children: [
                       {
                         cid: 440100,
                         cname: '广州市'
                       },
                       {
                         cid: 440200,
                         cname: '湛江市'
                       },
                       {
                         cid: 440300,
                         cname: '汕头市'
                       }
                     ]
                   },
                   {
                     pid: 420000,
                     pname: '广西省',
                     children: [
                       {
                         cid: 420200,
                         cname: '玉林市'
                       },
                       {
                         cid: 440100,
                         cname: '桂林市'
                       },
                       {
                         cid: 420300,
                         cname: '贵港市'
                       }
                     ]
                   }
                 ];*/
               },
               methods: {
                 // 输入中文时
                 comPositionStart:function() {
                   console.log('start');
                   this.inputLock = true;
                 },
                 // 中文输入完成时
                 comPositionEnd:function() {
                   var self = this;
                   this.inputLock = false;
                  
                   if(!this.inputLock) {
                       // 输入的值不为空的时候才可以请求
                	   setTimeout(function(){
                		   if(self.inputVal != '') {
                    		   console.log(self.inputVal);
                    		   self.isCity = true;
                               
                    		   var areaName = self.inputVal;
                    		   $.ajax({
                    				type : "POST",
                    				url : '../life/getAreaName',
                    				dataType : 'json',
                    				data:{
                    					areaName:areaName
                    				},
                    				async : false,
                    				cache : false,
                    				success : function(result) {
                    					if (result.status == 10000) {
                    						self.searchData = result.data;
                    					}
                    				},

                    			});
                           
                    	   }
                	   },100);
                      
                   }
                 },
                 // 城市点击时
                 selected:function(uid,level,pname) {
                   var self = this;
                   if (level == 1){
                     var c = [];
                     var p = {
                       children: []
                     };
                     var flagP = false;
                     var flagC = false;
                     var index = '';
                     for(var i in project) {
                       if(project[i].pid == uid) {

                           // 保存城市id和名称
                           c.push({
                             cid: project[i].uid,
                             cname: project[i].value
                           });
                           // 遍历是否有已经添加了省份
                           for(var j in this.showData) {
                             if (this.showData[j].pid == uid) {
                               flagP = true;
                               flagC = true;
                               index = j;
                             }
                           }
                           if(!flagP) {
                             p = {
                                   pid: project[i].pid,
                                   pname: pname,
                                   children: c
                             }
                             this.showData.push(p);
                           }else if(flagC) {
                             // 删除当前省份下的所有数据
                             this.showData.splice(index,1);
                              p = {
                                   pid: project[i].pid,
                                   pname: pname,
                                   children: c
                             }
                             // 重新添加所有的城市数据
                             this.showData.push(p);
                           }
                           this.isCity = false;
                       }
                     }
                     return false;
                   } else if(level == 2){
                     var p = {
                       children: []
                     };
                     var c = {};
                     var flagP = false;
                     var flagC = false;
                     for(var i in project) {
                       if (project[i].uid == uid) {
                           // 获取城市的省id
                           p.pid = project[i].pid;
                           // 获取城市的id
                           c.cid = uid;
                           // 获取城市的名称
                           c.cname = project[i].value;
                           for(var j in project) {
                               if (project[j].uid == p.pid) {
                                 // 获取城市的省名称
                                 p.pname = project[j].value;
                               }
                            }

                           // 去查找当前选择的城市的省份是否已经存在
                           for(var k in this.showData) {
                             if(this.showData[k].pid == p.pid) {
                               flagP = true;
                               // 再去查找当前选择的城市是否存在
                               for(var o in this.showData[k].children) {
                                   if(this.showData[k].children[o].cid == c.cid) {
                                    flagC = true;
                                   }
                               }
                             }
                           }
                           if (!flagP) {
                             if(!flagC) {
                               p.children.push(c);
                             }
                             // 如果省份不存在就直接添加
                             this.showData.push(p);
                           }else if(!flagC) {
                            for (var b in this.showData) {
                             if(this.showData[b].pid == p.pid) {
                               // 追加新的城市
                               this.showData[b].children.push(c);
                             }
                            }
                           }
                            this.isCity = false;
                       }
                     }
                   }
                 },
                 // 城市删除时
                 cityDel: function(pid,cid){
                   this.showData[pid].children.splice(cid,1);
                   if(!this.showData[pid].children.length){
                      this.showData.splice(pid,1);
                   }
                 },
                 // 清空城市
                 cityEmpty: function(pid) {
                   this.showData.splice(pid,1);
                 }
               }
             });


             document.documentElement.addEventListener('click', function() {
            	vm.$data.isCity = false;
             }, false);


