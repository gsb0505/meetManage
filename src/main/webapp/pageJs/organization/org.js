(function($) {
	jQuery.fn.extend({
		orgSelect: function(options) {
			var cacheSelect = new Array();
			var cache_split = "#";
			var settings = {
					choose: "-请选择-",
					emptyValue: "",
					organ:jQuery(this),
					department:jQuery("#department")
			};
			jQuery.extend(settings, options);
			
			return this.each(function(index,element) {
				var $input = settings.organ;
				var dep = $input.attr("dep");
					
				if(dep != null && dep != '')
					select_on_organ($input.val(),dep);
				
				function select_on_organ($this,depId){
					if(cacheSelect[$this+cache_split]==null){
						jQuery.ajax({
							url:_path +"userAction/findDepartsByOrg.do",
							type : "post",
							data : {'orgId':$this},
							async : false,
							dataType:"json",
							success : function(data) {
								
								if(data != null){
									cacheSelect[$this+cache_split] = data;
								}
							}
						});
					}
					
					var data = cacheSelect[$this+cache_split];
					settings.department.empty();
					settings.department.append(addSelect_contxt(data,depId));
				}

				function addSelect_contxt(data,currId){
					var content="";
					content += '<option value="' + settings.emptyValue + '">' + settings.choose + '</option>';
					if(data==null || data =="")
						return content;
					for (var int = 0; int < data.length; int++) {
						if(currId != null && data[int].id == currId)
							content+="<option value="+data[int].id+" selected='selected'>"+data[int].name+"</option>";
						else
							content+="<option value="+data[int].id+" >"+data[int].name+"</option>";
					}
					return content;
				}
				
				
				settings.organ.bind("change",function(){
					var $this = jQuery(this);
					select_on_organ($this.val(),null);
				});
			
			});
			
			
		}
	});
})(jQuery);