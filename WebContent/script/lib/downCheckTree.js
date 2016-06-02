(function($){
	$.fn.extend({
		initDownTree:function(pin){
		    var  $self = $(this);
		    var  id = $self.attr("id");
		    var  p = $.extend(true,{
			       idInputName:null,
			       parentSelLimit:false,
			       async:false,
			       zNodes:[],
			       url:"obtainChild.action",
			       idKey:"id",
			       pIdKey: "pid",
			       rootPId: "0",
			       name: "name",
			       autoParam:["id","name"]			       
		        },pin||{});
		     p = $.extend(true,{
	    		 downSetting : {
	    			check : {
		    				enable : true,
		    				chkStyle: "checkbox",
		    				chkboxType: {"Y":"ps","N":"ps"}
		    	    },
	    			data: {
						key: {
							name: p.name
						},
						simpleData: {
							enable: true,
							idKey: p.idKey,
							pIdKey: p.pIdKey,
							rootPId: p.rootPId
						}
					}
	    	     }
	         },p);
		     if (p.async==true){
		    	  p = $.extend(true,{
				    		downSetting : {			    		   
								async: {
									enable: true,
									url:p.url,
									autoParam:p.autoParam
							    }
				    	    }
		    	        },p);	    	
		      };
		      p = $.extend(true,{
		    		downSetting : {			    		   
		    			callback: {
					        onCheck: function (event, treeId, treeNode) {
					             var  treeObj = $.fn.zTree.getZTreeObj($self.attr("id")+"DownTree");
					             var  nodes = treeObj.getCheckedNodes(true);
					             var  ids="",names="";
					             var  nameKey = p.downSetting.data.key.name;
					             var  idKey = p.downSetting.data.simpleData.idKey;
					             var  idInputName,nodeId,nodeName,halfCheck; 
					             var  allowSel = true;															    							        					             
					             for(var i=0,j=nodes.length;i<j;i++){
					            	 if (p.parentSelLimit!=null && p.parentSelLimit==true){
										 allowSel = !nodes[i].isParent;										
									 };
									 if (!allowSel) continue;
									 halfCheck = nodes[i].getCheckStatus().half;
					            	 if (!halfCheck) {
						            	 nodeId = nodes[i][idKey]; 
						            	 if (ids===""){
						            		 ids=nodeId;
						            	 }else{
						            	     ids=ids+","+nodeId;
						            	 };						            
						            	 nodeName = nodes[i][nameKey];
						            	 if (names===""){
						            		 names=nodeName;
						            	 }else{
						            	     names=names+","+nodeName;	
						            	 };							            	
					            	 }
					             };	
					             $self.val(names);	
					             if (p.idInputName != null){
						        	 idInputName = "#"+p.idInputName;								        								        	
						        	 $(idInputName).val(ids);
						         };				             
					        }
						}
		    	    }
		        },p); 		
		    
		    var  divId = id+"MenuContent";
		    var  ulId = id+"DownTree";	
		    var  content =" <div id='"+divId+"' class='"+divId+"' align='center' "+
			     " style='display: none; position: absolute; background-color: white; border: 1px solid #ADC7E0; overflow-y: auto; overflow-x: auto;'>"+
			     " <ul id='"+ulId+"' class='ztree' style='margin-top: 0; width: "+$self.width()+"px; height: 200px;'></ul> </div>";
	         $("body").append(content);	 
	         var downDiv = $("#"+divId);
	         var downTree = $("#"+ulId); 
		     $.fn.zTree.init(downTree,p.downSetting);
		     $self.bind("click",
			            function(){ 		    		 
			                if(downDiv.css("display") == "none"){
			                	var cityObj = $self;			
			        			var cityOffset = $self.offset();
			        			var cityPosition = $self.position();
			        			var  divId = $self.attr("id")+"MenuContent";			        		 
			        			downDiv.css({left:cityPosition.left + "px", top:cityPosition.top + cityObj.height()+6+ "px"}).slideDown("fast");
			        			$("body").bind("mousedown", function(event) {			        				
			        				if (!(event.target.id == divId || $(event.target).parents("#"+divId).length>0)) {		        				
			        					downDiv.fadeOut("fast");
					        			$("body").unbind("mousedown");
			        				}	
			        		    });
			                }
			                else{		                
			                	downDiv.fadeOut("fast");
			        			$("body").unbind("mousedown");
			                }
			            }
		     );
		     return  this;
		     
		}
	});
})(jQuery);