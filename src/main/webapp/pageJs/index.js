jQuery(document).ready(function(){
		jQuery.cookie('user', encodeURI(jQuery("#userId").val(), "UTF-8"));
	});
	
	function turnPage(url){
		jQuery.ajax({
			url:url,
			type:"post",
			dataType:"text",
			success:function(data){
				var str=data;
				if(str.indexOf("5646546465465465465")>-1){
					window.location.href=_path;
					return ;
				}
				jQuery("#iframe").html(data);
				setTimeout( "if( jQuery.isFunction(jQuery.fn.hideLoading) ){  jQuery('#loading').hideLoading(); }", 800 );
			},beforeSend:function(XHR){
				if( jQuery.isFunction(jQuery.fn.showLoading) ){ jQuery('#loading').showLoading(); }
			},complete:function(XHR, TS){
				setTimeout( "if( jQuery.isFunction(jQuery.fn.hideLoading) ){  jQuery('#loading').hideLoading(); }", 800 );
			}
		});
	
	}
	function func(v){
		showWindow('查看详情',875,250,_path+'NoticeAction/queryById.do?nid='+v);
		
	}	
	
 function queryOtherLevel(menuId){
	 var name = jQuery('#'+menuId).attr("data");
	 var userId = jQuery('#userId').val();
	 var count = 0;
	 var expired = false;
	 jQuery("#titleOne").each(function(){
			jQuery(this).html("");
			});
	//name=name.substr(0,name.length/2);
	jQuery("#titleOne").append("<i class='tittle_icon01'></i>"+name);
	jQuery.ajax({
		url:_path+'userMenu/queryOtherLevel.do?roleId='+jQuery('#roleId').val()+"&menuId="+menuId+"&userId="+userId,
		type:"post",
		dataType:"json",
		async:false,
		statusCode: {
			518: function() {
				expired = true;
			}
		},
		success:function(data){
			jQuery("#leftM").each(function(){
				jQuery(this).html("");
			});
			for(i=0;i<data.length;i++){
				if(data[i].menuId.length==7){
	        		jQuery("#leftM").append("<li ><div class='header'><span class='label'>"+data[i].menuName+"</span><span class='arrow down'></span></div><ul class='menu' style='display: none' id='"+data[i].menuId+"'>");
	        		for(j=0;j<data.length;j++){
	        			if(data[j].parentMenuId==data[i].menuId){
	        				count++;
	        				if(count==1){ //第一个三级菜单
	            				jQuery('#sc_menuId').val(data[j].menuId);
	            				jQuery('#sc_menuUrl').val(_path+data[j].menuUrl);
	        				}
	        				if(data[j].flag=='0'){
	        					jQuery('#'+data[i].menuId).append("<a onclick=turnPage('"+_path+data[j].menuUrl+"?menuId="+data[j].menuId+"')><li><span>"+ data[j].menuName+"</span><span class='common_icon shuqian_icon' onclick=addfavorite('"+data[j].menuId+"')></span></li></a>");
	        				}
	        				else if(data[j].flag=='1'){
	        					jQuery('#'+data[i].menuId).append("<a onclick=turnPage('"+_path+data[j].menuUrl+"?menuId="+data[j].menuId+"')><li><span>"+ data[j].menuName+"</span><span class='shuqian_xz'></span></li></a>");
	        				}
	        				else{
	        					jQuery('#'+data[i].menuId).append("<a onclick=turnPage('"+_path+data[j].menuUrl+"?menuId="+data[j].menuId+"')><li><span>"+ data[j].menuName+"</span><span class='biaoqian_sc' onclick=delfavorite('"+data[j].cymenuId+"')></span></a></li>");
	        				}
	        			}
	        		}
				}
			}

		}
	});
	if (expired) return ;
	 if(menuId=='1010'){  //打开常用功能
     jQuery("#1010001").attr("style","overflow: hidden;");
	 }
	 else{
     turnPage(jQuery('#sc_menuUrl').val()+'?menuId='+jQuery('#sc_menuId').val());	 
	 }
	 jQuery("ul.expmenu li > div.header").click(function()
			{
				var arrow = jQuery(this).find("span.arrow");
				if(arrow.hasClass("up"))
				{
					arrow.removeClass("up");
					arrow.addClass("down");
				}
				else if(arrow.hasClass("down"))
				{
					arrow.removeClass("down");
					arrow.addClass("up");
				}

				jQuery(this).parent().find("ul.menu").slideToggle();
			}); 
}
	

  /**** 
   * 增加常用功能
   */
 function addfavorite(menuId){
	 var userId = jQuery('#userId').val();
		jQuery.ajax({
			url:_path+'userMenu/addFavorite.do?userId='+userId+"&menuId="+menuId,
			type:"post",
			success:function(data){
				if(data=='success'){
					location.reload();
				}
		}
		});
 }
  
 /**** 
  * 取消常用功能
  */  
  function delfavorite(menuId){
		 var userId = jQuery('#userId').val();
			jQuery.ajax({
				url:_path+'userMenu/delFavorite.do?userId='+userId+"&menuId="+menuId,
				type:"post",
				success:function(data){
					if(data=='success'){
					location.reload();
					}
			}
			});	
 }
 
  /**** 
   * 显示隐藏菜单
   */
 function showhide(){
	 var ys = jQuery("#showhide").attr("class");
	 if(ys=='ss_icon'){
		 jQuery("#con_left").hide();
		 jQuery("#showhide").attr("class","zk_icon");
		 jQuery("#iframe").attr("style","width:99.4%");
	 }
	 else{
		 jQuery("#con_left").show();
		 jQuery("#showhide").attr("class","ss_icon");
		 jQuery("#iframe").attr("style","width:84.4%");
	 }
 }
	
  function queryOneLevel(){
	  var userId = jQuery('#userId').val();
	  jQuery.ajax({
			url:_path+'userMenu/queryOneLevel.do?roleId='+jQuery('#roleId').val()+'&userId='+userId,
			type:"post",
			dataType:"json",
			async:false,
			cache:false,
			success:function(data){
				for(i=0;i<data.length;i++){
					jQuery("#topMenu").append("<li><a id='"+data[i].menuId+"' onclick='queryOtherLevel("+data[i].menuId+")' data='"+data[i].menuName+"'>"+data[i].menuName+"</a></li>");
				}
			}
		});	  
  }
  
  /**** 
   * 一次显示常用菜单
   */  
   function showcommon(){
	   queryOtherLevel("1010");
	   
	   jQuery("#1010001").attr("style","overflow: hidden; display: block;");
  }
  
   /**** 
    * 注销
    */  
    function logout(){
		showConfirmDivLayer('确定注销？',{'okFun':function(){
			if(jQuery.cookie)
				jQuery.cookie('user',null);
			window.location.href=_path+'loginAction/logout.do';
		 }
		});	   
   }
    
    //重置密码
    function resetPwd(){
    	showWindow('修改密码', 500,300,_path+'loginAction/modifyPwd.do');
    }
   
	//角色切换  
	function goUrl(va){
		jQuery("#topMenu").html("");
		jQuery("#leftM").each(function(){
			jQuery(this).html("");
		});
		jQuery("#titleOne").html("");
		jQuery("#iframe").each(function(){
			jQuery(this).html("");
		});
		
		//进行查询
		queryCommit(va);
		menu_mal();
	}
	
	//执行查询-生成主菜单
	function queryCommit(va){
		jQuery.ajax({
			url:_path+'userMenu/queryOneLevel.do?roleId='+va,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				//读取主菜单（三种菜单样式：1、frame默认;2、仿js生成;3、js生成）
				for(i=0;i<data.length;i++){
					//jQuery("#topMenu").append("<li><a id='"+data[i].menuId+"' onclick='queryOtherLevel("+data[i].menuId+")'><i class='head_icon_06'></i>"+data[i].menuName+"</a></li>");
					//jQuery("#topMenu").append("<li><a id='"+data[i].menuId+"' onclick='queryOtherLevel("+data[i].menuId+")'><span class='out' style='top:0px;'>"+data[i].menuName+"</span><span class='bg' style='top: -45px;'></span><span class='over' style='top:-45px;'>"+data[i].menuName+"</span></a></li>");
					jQuery("#topMenu").append("<li><a id='"+data[i].menuId+"' onclick='queryOtherLevel("+data[i].menuId+")'>"+data[i].menuName+"</a></li>");
				}
			}
		});
	}
	
	
	
	
	
	//用户角色列表
	jQuery('#contexttt').append('<select id = "selectElement" onchange="goUrl(this.value)"></select>');
	
	jQuery.ajax({
		url:_path+'userRolesAction/getUserRolesList.do?userId='+encodeURI(userId, "UTF-8") ,
		type:"post",
		dataType:"json",
		async:false,
		success:function(data){
			document.getElementById("roleId").value = data[0].id;
			for(i=0;i<data.length;i++){		
				jQuery('#selectElement').append('<option value = '+data[i].id+'>'+data[i].roleDesc+'</option>');
			}
		}
	});
	queryOneLevel();

	jQuery(document).ready(function(){
		showcommon();
		//动态菜单函数调用
		menu_mal();
	});
	
	jQuery("body").click(function(event){  
		var $this = $(event.target);  
		if(jQuery("#userId").val() && jQuery("#userId").val() != decodeURI(jQuery.cookie('user'), "UTF-8")){
			location.reload(true);
		}
	});
	
	/*window.onbeforeunload = function()
	{
		jQuery.ajax({
			url:_path+'loginAction/logout.do',
			type:"post",
			dataType:"json",
			async:false
		});
	}*/