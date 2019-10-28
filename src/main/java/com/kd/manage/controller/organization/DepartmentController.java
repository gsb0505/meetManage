package com.kd.manage.controller.organization;

import java.io.IOException;
import java.io.PrintWriter;  
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.common.unit.util.EncryptUtils;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseClient;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.user.UserController;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.controller.util.StringUtil;
import com.kd.manage.controller.validator.UserValidator;
import com.kd.manage.entity.Config;
import com.kd.manage.entity.Department;
import com.kd.manage.entity.Organization;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.TreeNode;
import com.kd.manage.entity.UserInfo;
import com.kd.manage.support.ExcelSupport;


@Controller
@RequestMapping("/departmentAction")
public class DepartmentController  extends BaseController{
	private static WebTarget dsu = webTarget.get(BaseUri.departmentServiceUrl);
	
	@RequestMapping(value = "/list.do")
	public void viewList(Department Department,PageCount pageCount,HttpServletResponse res) throws  Exception{
		Department.setPageCount(pageCount);
		String sendData = new Gson().toJson(Department);
		WebTarget target = dsu.path("query").queryParam("department", URLEncoder.encode(sendData, "utf-8"));
		List<Department> userList = target.request().get(new GenericType<List<Department>>() {});
		
		pageCount = ManageUtil.packPage(userList,pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);//转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/listTree.do")
	public void viewTree(Department Department,PageCount pageCount,HttpServletResponse res) throws  Exception{
		Department.setPageCount(pageCount);
		String sendData = new Gson().toJson(Department);
		WebTarget target = dsu.path("queryList").queryParam("department", URLEncoder.encode(sendData, "utf-8"));
		List<Department> userList = target.request().get(new GenericType<List<Department>>() {});
		
		JSONArray jArr=new JSONArray();
		TreeNode tn=null;
		for (Department department2 : userList) {
			tn=new TreeNode();
			tn.setId(department2.getId());
			tn.setName(department2.getName());
			tn.setIsParent("true");
			tn.setpId(department2.getId());
			jArr.add(tn);
		}
		
		PrintWriter out = res.getWriter();
		out.print(jArr);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/listView.do")
	public String sendListView(String menuId,Model model,HttpServletRequest request)throws Exception{
		sendListCommon(menuId,model,request);
		return "organization/department/list";
	}
	
	@RequestMapping(value = "/add.do")
	public void sendAdd(Department Department,PageCount pageCount,HttpServletResponse res) throws  Exception{
		PrintWriter out = res.getWriter();
		WebTarget target = dsu.path("add");
		Response response = target.request().post(Entity.entity(Department, MediaType.APPLICATION_XML));
		Boolean result = response.readEntity(Boolean.class);
		if(result==null){
			out.print(EXCEPTION);
		}else if(result==true){
			out.print(SUCCESS);
		}else
			out.print(FAIL);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/addView.do")
	public String sendAddView(Model model,HttpServletRequest request)throws Exception{
		return "organization/department/add";
	}
	
	@RequestMapping(value="/modifyView.do")
	public String sendModifyView(String id,Model model,HttpServletRequest request)throws Exception{
		Department Department = new Department();
		Department.setId(id);
		String sendData = new Gson().toJson(Department);
		WebTarget target = dsu.path("queryModel").queryParam("department", URLEncoder.encode(sendData, "utf-8"));
		Response res = target.request().get();
		Department org = res.readEntity(Department.class);
		model.addAttribute("a", org);
		return "organization/department/modify";
	}
	
	@RequestMapping(value = "/modify.do" , method = RequestMethod.POST)
	public void update(Department Department,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try{
			WebTarget target = dsu.path("modify");
			Response res = target.request().put(Entity.entity(Department, MediaType.APPLICATION_XML));
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
	 * 编号查询
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDepByNo.do" , method = RequestMethod.POST)
	public void getDepByNo(Department Department,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try{
			if(!StringUtils.isEmpty(Department.getDepartmentNo())){
				Department.setCreator("department_no");
			}
			if(!StringUtils.isEmpty(Department.getName())){
				Department.setCreator("name");
			}
			String sendData = new Gson().toJson(Department);
			WebTarget target = dsu.path("queryModel").queryParam("department", URLEncoder.encode(sendData, "utf-8"));
			Response res = target.request().get();
			Department org = res.readEntity(Department.class);
			res.close();
			if(StringUtils.isEmpty(Department.getId())){
				if(org==null){
					out.print(true);
				}else{
					out.print(false);
				}				
			}else{
				if(org==null || org.getId().equals(Department.getId())){
					out.print(true);
				}else{
					out.print(false);
				}
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
	 * 删除
	 * @param id
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(String id, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		if(StringUtil.isBlank(id)){
			out.print(EXCEPTION);
			return;
		}
		WebTarget target = dsu.path("delete/"+id);
		try{
			Response res = target.request().delete();
			String value=res.readEntity(String.class);
			res.close();
			if("true".equals(value)){
				out.print(SUCCESS);
			}else if("false".equals(value)){
				out.print(FAIL);
			}else{
				out.print(EXCEPTION);
			}
		}catch(Exception e){
			logException(e);
			out.print(EXCEPTION);
		}finally{
			this.out(response, null);
		}
	}
	
	/**
	 * 导出 
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping(value = "/exporDepList.do")
	public void exporDepList(Department dep,HttpServletRequest request,HttpServletResponse res) throws IOException{
		WebTarget target = dsu.path("queryList");
		List<Organization> list = target.request().get(new GenericType<List<Organization>>() {});
		String[] titles={"科室id","科室编号","科室名称"};
		String[] columns={"id","departmentNo","name"};
		
		//开始生成文件
		String fos = ExcelSupport.exportExcel(request,list,columns,titles,"科室列表");
		PrintWriter out = res.getWriter();
		out.print(fos);
		out.flush();
		out.close();	
	}
}
