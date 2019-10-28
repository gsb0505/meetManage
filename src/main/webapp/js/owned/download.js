// 创建一个闭包    
(function($) {
	// 插件的定义  
	jQuery.fn.downloads = function(options) {
		//debug(this);
		// build main options before element iterationvar 
		opts = jQuery.extend({}, jQuery.fn.downloads.defaults, options);
		// iterate and reformat each matched elementreturn 
		this.each(function() {
			$this = jQuery(this);  
			// build element specific options  
			var o = jQuery.meta ? jQuery.extend({}, opts, $this.data()) : opts;  
			// update element styles  
			var form="<form id='downExcelForm' action='"+_path + "report\/download.do"+"' method='post'> <input type='hidden' name='fileName' value='"+o.fileName+"'/><input type='hidden' name='type' value='"+o.type+"'/> </form>";
			jQuery(form).appendTo('body');
			jQuery("#downExcelForm").submit().remove();
			
			/*jQuery.ajax({
				url : _path + 'card/download.do',
				type : "post",
				data : {fileName:o.fileName},
				async : false,
				success:function(jsonStr){
					var newwindow = window.open("about:blank","","height=300, width=100, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=yes");
					newwindow.document.write(jsonStr);
				}
			});*/
			
			// call our format function
			//markup = jQuery.fn.downloads.format(markup);  
			//jQuerythis.html(markup);
		});      
	};      
	// 私有函数：debugging      
	//function debug(jQueryobj) {if (window.console && window.console.log)  window.console.log('downloads selection count: ' + jQueryobj.size());      };
	// 定义暴露format函数      
	//jQuery.fn.downloads.format = function(txt) {return '<strong>' + txt + '</strong>';      };      
	// 插件的defaults      
	jQuery.fn.downloads.defaults = {fileName: 'cardTemplet.xls',type:'tmp' };    
			
	// 闭包结束    
})(jQuery);
