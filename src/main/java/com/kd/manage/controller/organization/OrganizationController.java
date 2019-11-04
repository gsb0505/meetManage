package com.kd.manage.controller.organization;

import java.io.IOException;
import java.io.PrintWriter;  
import java.net.URLEncoder;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.core.dto.ODJDto;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.StringUtil;
import com.kd.manage.entity.BaseEntity.Save;
import com.kd.manage.entity.OrgDepartmentJob;
import com.kd.manage.entity.Organization;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.BaseEntity.Update;
import com.kd.manage.support.ExcelSupport;

/**
 * 机构管理--Controller
 * @author kd003
 *
 */
@Controller
@RequestMapping("/organizationAction")
public class OrganizationController  extends BaseController{
	private static WebTarget osu;
	static {
		osu = BaseUri.webTarget.get(BaseUri.organizationServiceUrl);
	}
	
	
	@RequestMapping(value = "/list.do")
	public void viewList(Organization organization,PageCount pageCount,HttpServletResponse res) throws  Exception{
		organization.setPageCount(pageCount);
		String sendData = new Gson().toJson(organization);
		WebTarget target = osu.path("query").queryParam("organization", URLEncoder.encode(sendData, "utf-8"));
		List<Organization> userList = target.request().get(new GenericType<List<Organization>>() {});
		
		pageCount = ManageUtil.packPage(userList,pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);//转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/listView.do")
	public String sendListView(String menuId,Model model,HttpServletRequest request)throws Exception{
		sendListCommon(menuId,model,request);
		return "organization/organization/list";
	}
	
	@RequestMapping(value = "/add.do")
	public void sendAdd(@Validated(Save.class)Organization organization, BindingResult bresult,PageCount pageCount,HttpServletResponse res) throws  Exception{
		PrintWriter out = res.getWriter();
		if (bresult.hasErrors()) {  
			out.print(EXCEPTION);
			return;
	    } 
		
		WebTarget target = osu.path("add");
		Response response = target.request().post(Entity.entity(organization, MediaType.APPLICATION_XML));
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
		return "organization/organization/add";
	}
	
	@RequestMapping(value="/cinfigView.do")
	public String sendCinfigView(String orgId,Model model,HttpServletRequest request)throws Exception{
		model.addAttribute("orgId", orgId);
		return "organization/organization/cinfigView";
	}
	
	@RequestMapping(value="/modifyView.do")
	public String sendModifyView(String id,Model model,HttpServletRequest request)throws Exception{
		Organization organization = new Organization();
		organization.setId(id);
		String sendData = new Gson().toJson(organization);
		WebTarget target = osu.path("queryModel").queryParam("organization", URLEncoder.encode(sendData, "utf-8"));
		Response res = target.request().get();
		Organization org = res.readEntity(Organization.class);
		model.addAttribute("a", org);
		return "organization/organization/modify";
	}
	
	@RequestMapping(value = "/modify.do" , method = RequestMethod.POST)
	public void update(@Validated(Update.class)Organization organization, BindingResult bresult,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		if (bresult.hasErrors()) {  
			out.print(EXCEPTION);
			return;
	    } 
		try{
			WebTarget target = osu.path("modify");
			Response res = target.request().put(Entity.entity(organization, MediaType.APPLICATION_XML));
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
	 * 查询编号
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrgByNo.do" , method = RequestMethod.POST)
	public void getOrgByNo(Organization organization,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try{
			if(!StringUtils.isEmpty(organization.getAgencyNo())){
				organization.setCreator("agency_no");				
			}
			if(!StringUtils.isEmpty(organization.getName())){
				organization.setCreator("name");
			}
			String sendData = new Gson().toJson(organization);
			WebTarget target = osu.path("queryModel").queryParam("organization", URLEncoder.encode(sendData, "utf-8"));
			Response res = target.request().get();
			Organization org = res.readEntity(Organization.class);
			res.close();
			if(StringUtils.isEmpty(organization.getId())){
				if(org == null){
					out.print(true);
				}else{
					out.print(false);
				}				
			}else{
				if(org == null || org.getId().equals(organization.getId())){
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
	 * 条件查询
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDataByOrgId.do" , method = RequestMethod.POST)
	public void queryDepartsJobsByOrgId(OrgDepartmentJob info,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		if(info==null){
			out(response, FAIL);
			return;
		}
		String sendData = new Gson().toJson(info);
		try{
			WebTarget target = osu.path("queryDepartsJobsByCondition").queryParam("orgDepartmentJob", URLEncoder.encode(sendData, "utf-8"));
			List<OrgDepartmentJob> result =  target.request().get(new GenericType<List<OrgDepartmentJob>>() {});
			if(result != null && result.size()>0){
				out.print(JSONArray.fromObject(result));
			}
		}catch(Exception e){
			logException(e);
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 配置
	 * @throws Exception
	 */
	@RequestMapping(value = "/config.do" , method = RequestMethod.POST)
	public void config(OrgDepartmentJob info,String type,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		if(info==null || type == null || type.equals("")){
			out(response, FAIL);
			return;
		}
		try{
			WebTarget target = osu.path("configOrgDepartmentJob").queryParam("type", type);
			Response res =  target.request().post(Entity.entity(info, MediaType.APPLICATION_XML));
			Boolean result = res.readEntity(Boolean.class);
			if(result != null){
				out.print(result);
			}
		}catch(Exception e){
			logException(e);
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 删除
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(String id, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		
		if(StringUtil.isBlank(id)){
			out.print(EXCEPTION);
			return;
		}
		WebTarget target = osu.path("delete/"+id);
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
	@RequestMapping(value = "/exportOrgList.do")
	public void exportOrgList(Organization org,HttpServletRequest request,HttpServletResponse res) throws IOException{
		WebTarget target = osu.path("queryODJList");
		List<ODJDto> list = target.request().get(new GenericType<List<ODJDto>>() {});
		String[] titles={"机构id","机构编号","机构名称","科室id","科室编号","科室名称","职位id","职位编号","职位名称"};
		String[] columns={"orgId","orgNo","orgName","depId","depNo","depName","jobId","jobNo","jobName"};
		
		//开始生成文件
		String fos = ExcelSupport.exportExcel(request,list,columns,titles,"机构、科室、职位列表");
		PrintWriter out = res.getWriter();
		out.print(fos);
		out.flush();
		out.close();	
	}
}
