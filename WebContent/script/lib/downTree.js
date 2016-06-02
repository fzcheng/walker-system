(function($){
	$.fn.extend({
		initDownTree:function(pin){
		    var  $self = $(this);
		    var  id = $self.attr("id");
		    var  p = $.extend(true,{
			       idInputName:null,
			       zNodes:[],
			       downSetting : {
				    		view: {
								dblClickExpand: false
							},
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "pid",
									rootPId: "0"
								}
							},
							callback: {
								onClick:function(e, treeId, treeNode) {
									 var allowSel = true;
									 if (p.parentSelLimit!=null && p.parentSelLimit==true){
										 allowSel = !treeNode.isParent;										
									 };
								     if (treeNode && allowSel) {							    	 
								    	 $self.val(treeNode.name);
								         if (p.idInputName != null){
								        	 var idtemp ="#"+p.idInputName;
								        	 $(idtemp).val(treeNode.id);
								         }
								         var  divId = $self.attr("id")+"MenuContent";
						        		 var  downTree = $("#"+divId);
						        		 downTree.fadeOut("fast");
						        		 $("body").unbind("mousedown");
								     }
						        }
							}
					}
		        },pin||{});
		    
		    var  divId = id+"MenuContent";
		    var  ulId = id+"DownTree";	
		    var  content =" <div id='"+divId+"' class='"+divId+"' align='center' "+
			     " style='display: none; position: absolute; background-color: white; border: 1px solid #ADC7E0; overflow-y: auto; overflow-x: auto;'>"+
			     " <ul id='"+ulId+"' class='ztree' style='margin-top: 0; width: 180px; height: 200px;'></ul> </div>";
	         $("body").append(content);	 
	         var downDiv = $("#"+divId);
	         var downTree = $("#"+ulId); 
		     $.fn.zTree.init(downTree,p.downSetting,p.zNodes);
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
		     
		}
	});
})(jQuery);