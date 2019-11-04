package com.kd.manage.controller.user;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.controller.util.StringUtil;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.Role;
import com.kd.manage.entity.UserInfo;




/**
 *
 *@类名称：RolesAction.java
 *@类描述：用户角色维护

 *@创建时间：2015年1月28日-下午1:09:55
 *@修改备注:
 *@version 
 */
@Controller
@RequestMapping(value = "/rolesAction")
public class RolesController extends BaseController{
	

	private static WebTarget rsu;
	
	static {
		rsu = BaseUri.webTarget.get(BaseUri.rolesServerUri);
	}
	
	/**
	 * 用户角色维护主页！跳转到主页以后自动查询并显示所有角色信息，可根据角色名roleDesc进行查询
	 * @param roleDesc
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.do")
	public void viewList(Role role,PageCount pageCount,HttpServletResponse res) throws Exception{
		role.setPageCount(pageCount);
		String sendData = new Gson().toJson(role);
		WebTarget target = rsu.path("query").queryParam("role", URLEncoder.encode(sendData, "utf-8"));
		List<Role> roleList =target.request().get(new GenericType<List<Role>>() {});
		pageCount = ManageUtil.packPage(roleList,pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);//转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	
	
	/**
	 *	在菜单中点击链接以后直接跳转到角色主页！没有进行任何操作！
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value="/listView.do")
	public String sendListView(String menuId,Model model,HttpServletRequest request)throws Exception{
		sendListCommon(menuId,model,request);
		return "user/role/list";
	}
	
	/**
	 *	在主页点击添加以后跳转到添加页面！没有进行任何操作
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value="/addView.do")
	public String addUserView()throws Exception{
		return "user/role/add";
	}
	
	/**
	 * 在添加页面点击保存以后跳转到该方法！然后去core中执行添加操作
	 * @param role
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public void add(Role role,HttpServletResponse response,HttpServletRequest request)throws Exception {
			PrintWriter out = response.getWriter();
			try{
				String sendData = new Gson().toJson(role);
				WebTarget target_query = rsu.path("queryModel").queryParam("role", URLEncoder.encode(sendData, "utf-8"));
				Response res1 = target_query.request().get();
				Role roleInfo = res1.readEntity(Role.class);
				res1.close();
				if(roleInfo==null){
					WebTarget target = rsu.path("add");
					UserInfo userLogin = (UserInfo) request.getSession().getAttribute("user");
					String loginUserId = userLogin.getUserId();
					role.setCreator(loginUserId);
					Response res2 = target.request().post(Entity.entity(role, MediaType.APPLICATION_XML));
					String value=res2.readEntity(String.class);
					res2.close();
					if("true".equals(value)){

						out.print(SUCCESS);

					}else if("false".equals(value))
					{
						out.print(FAIL);
					}else{
						out.print(EXCEPTION);
					}
				}else {
					out.print(EXSIT);
				}
			}catch(Exception e){
				out.print(EXCEPTION);
				logException(e);
			}finally{
				out.flush();
				out.close();
			}
	}	
	
	
	/**
	 * 跳转到编辑页面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modifyView.do")
	public String updateUserView(@QueryParam("id") String roleCode,Model model) throws Exception{
		Role role=new Role();
		role.setRoleCode(roleCode);;
		String sendData = new Gson().toJson(role);
		WebTarget target = rsu.path("queryModel").queryParam("role", URLEncoder.encode(sendData, "utf-8"));
		Response res = target.request().get();
		Role userRole = res.readEntity(Role.class);
		res.close();
		model.addAttribute("role", userRole);
		return "user/role/modify";
	}
	
	/**
	 * 在编辑页面点保存以后跳转到该方法！然后去core执行更新操作
	 * @param user
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do" , method = RequestMethod.POST)
	public void update(@Valid Role role,BindingResult result,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
	             
		try{
			WebTarget target = rsu.path("modify");
			Response res = target.request().put(Entity.entity(role, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);
			res.close();
			if("true".equals(value)){
				out.print(SUCCESS);
			}else if("false".equals(value)){
				out.print(FAIL);
			}else{
				out.print(EXCEPTION);
			}
		}catch(Exception e){
			out.print(EXCEPTION);
			logException(e);
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 角色删除
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteByRoleID.do" , method = RequestMethod.POST)
	public void deleteByRoleID(String rid,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
	    if(StringUtil.isBlank(rid)){
	    	out.print(FAIL);
	    	return;
	    }
		try{
			WebTarget target = rsu.path("deleteByRoleID/"+rid);
			Response res = target.request().get();
			String value = res.readEntity(String.class);
			res.close();
			if("exsit".equals(value)){
				out.print(EXSIT);
			}else if("true".equals(value)){
				out.print(SUCCESS);
			}else if("false".equals(value)){
				out.print(FAIL);
			}else{
				out.print(EXCEPTION);
			}
		}catch(Exception e){
			out.print(EXCEPTION);
			logException(e);
		}finally{
			out.flush();
			out.close();
		}
	}
	
}
