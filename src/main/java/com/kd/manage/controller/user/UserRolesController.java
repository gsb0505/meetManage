package com.kd.manage.controller.user;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kd.core.dto.UserRolesDto;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.Role;
import com.kd.manage.entity.UserInfo;
import com.kd.manage.entity.UserRoles;


/**
 *
 *@类名称：UserRolesAction.java
 *@类描述：用户角色关系维护

 *@创建时间：2015年1月26日-上午9:57:53
 *@修改备注:
 *@version 
 */
@Controller
@RequestMapping("/userRolesAction")
public class UserRolesController extends BaseController{
	private static WebTarget ursu;
	private static WebTarget rsu;
	static {
		
		ursu = webTarget.get(BaseUri.userRolesServerUri);
		rsu = webTarget.get(BaseUri.rolesServerUri);
	}

	/**
	 * 用户角色关系维护主页！跳转到主页以后自动查询并显示所有用户信息，可根据用户ID userId和角色编码roleCode进行查询
	 * @param userId
	 * @param roleCode
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.do")
	public void viewList(UserRolesDto dto,PageCount pageCount,HttpServletResponse res) throws Exception{
		dto.setPageCount(pageCount);
		String sendData = new Gson().toJson(dto);
		WebTarget target = ursu.path("query").queryParam("dto", URLEncoder.encode(sendData, "utf-8"));
		List<UserRolesDto> dtoList = target.request().get(new GenericType<List<UserRolesDto>>() {});
		pageCount = ManageUtil.packPage(dtoList,pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);//转化成json对象(jQgrid只能识别json对象)
			PrintWriter out = res.getWriter();
			out.print(json);
			out.flush();
			out.close();
	}

	/**
	 *	在菜单中点击链接以后直接跳转到用户主页！没有进行任何操作！
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value="/listView.do")
	public String sendListView(String menuId,Model model,HttpServletRequest request)throws Exception{
		sendListCommon(menuId,model,request);
		return "user/userRole/list";
	}

	/**
	 * 在角色配置页面点保存以后跳转到该方法！然后去core执行更新操作
	 * @param user
	 * @throws Exception
	 */
	
	
	
	@RequestMapping(value="/configView.do")
	public String configUserView(String userId,Model model ) throws Exception {
		model.addAttribute("userId", userId);
		return "user/userRole/config";
	}
	
	@RequestMapping(value = "/config.do" )
	public void config (@RequestParam("codeList[]") String []  codeList,String userId,HttpServletResponse response,HttpServletRequest request)throws Exception {
		UserRoles[] roleArray = new UserRoles[codeList.length];
		for(int i=0;i<codeList.length;i++){
			UserRoles role  = new UserRoles();
			UserInfo userLogin = (UserInfo) request.getSession().getAttribute("user");
			String loginUserId = userLogin.getUserId();
			role.setCreator(loginUserId);
			role.setUserId(userId);
			role.setRoleCode(codeList[i]);
			roleArray[i] = role;
		}
		
		PrintWriter out = response.getWriter();
		try{
			WebTarget target = ursu.path("modify/"+userId);
			Response res = target.request().put(Entity.entity(roleArray, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);
			res.close();
			
			if("true".equals(value)){

				out.print(SUCCESS);

			}else if("false".equals(value))
			{
				out.print(FAIL);
			}else{
				out.print(EXCEPTION);
			}
		}catch(Exception e)
		{
			logException(e);
			out.print(EXCEPTION);
		}finally{
			out.flush();
			out.close();
		}
		
	}
	
	@RequestMapping(value = "/configNull.do" )
	@ResponseBody
	public void configNull (String userId,HttpServletResponse response,HttpServletRequest request)throws Exception {
		String s[] = new String[0]; 
		config(s,userId,response,request);
		
	}
	
	
	@RequestMapping(value="/getRole.do")
	public void getRole(UserRolesDto dto,HttpServletResponse response,String[] ss) throws Exception {
		WebTarget target2 = rsu.path("queryList");
		List<Role> roleList = target2.request().get(new GenericType<List<Role>>() {});
		String sendData = new Gson().toJson(dto);
		WebTarget target1 = ursu.path("queryModel").queryParam("dto", URLEncoder.encode(sendData, "utf-8"));
		Response res = target1.request().get();
		UserRolesDto userRolesDto = res.readEntity(UserRolesDto.class);
		String s ="";
		PrintWriter out = response.getWriter();
		if(userRolesDto!=null) {
			String codeStr = userRolesDto.getRoleCode();
			String descStr = userRolesDto.getRoleDesc();
			if(codeStr!=null && descStr != null){
				String codes[] = codeStr.split(",");
				if(codes.length==codes.length){
					Iterator<Role> i = roleList.iterator();
					while (i.hasNext())
					{
						Role r = i.next();
						for(int j=0;j<codes.length;j++){
							if(r.getRoleCode().equalsIgnoreCase(codes[j])){
								s=s+r.getId()+",";
								
							}
						}

					}
				}
				out.print(s);
				
			}
			return;
			}
		out.print(EXCEPTION);
		}

	

	/**
	 * glt
	 * TODO 角色切换
	 * @param userName
	 * @return
	 * @return List
	 * @auditor
	 * @exception
	 * @since  1.0.0
	 */
	@RequestMapping(value = "/getUserRolesList.do" )
	@ResponseBody
	public void getUserRolesList(String userId,HttpServletRequest request,HttpServletResponse response) throws Exception{
	//	String userId = request.getParameter("userId");
		System.out.println("userId="+URLDecoder.decode(userId, "UTF-8"));
		WebTarget target = ursu.path("getUserRolesList/"+URLDecoder.decode(userId, "UTF-8"));
		List<UserRolesDto> dtoList = target.request().get(new GenericType<List<UserRolesDto>>() {});
		PrintWriter out = response.getWriter();
		JSONArray json = JSONArray.fromObject(dtoList);
		out.print(json);
	}
}
