package com.kd.tag.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.entity.Department;
import com.kd.manage.entity.Organization;



/**
 * tag 机构、科室、职位
 * @author zlm
 * @version 1.0
 */
public class InstitutionTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String style="zlmui";
	private String id;	//id
	private String type; //type
	
	protected static WebTarget osu = BaseUri.webTarget.get(BaseUri.organizationServiceUrl);
	protected static WebTarget dsu = BaseUri.webTarget.get(BaseUri.departmentServiceUrl);
	protected static WebTarget jsu = BaseUri.webTarget.get(BaseUri.jobServiceUrl);

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			String menu = (String) this.pageContext.getSession().getAttribute("institutionCache"+ type + id +style);
			if(menu!=null){
				out.print(menu);
			}else{
				menu=end();
				this.pageContext.getSession().setAttribute("institutionCache" + type + id + style,menu);
				out.print(menu);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String end() throws UnsupportedEncodingException {	
		if(id != null && type != null){
			
			if(type.equals("org")){
				Organization org=new Organization();
				org.setId(id);
				String sendData3 = new Gson().toJson(org);
				WebTarget target3 = osu.path("queryModel").queryParam("organization", URLEncoder.encode(sendData3, "utf-8"));
				Organization organization2 = target3.request().get(new GenericType<Organization>() {});
				
				return organization2.getName();
			}else if(type.equals("dep")){
				Department dep=new Department();
				dep.setId(id);;
				String sendData4 = new Gson().toJson(dep);
				WebTarget target4 = dsu.path("queryModel").queryParam("department", URLEncoder.encode(sendData4, "utf-8"));
				Department department = target4.request().get(new GenericType<Department>() {});
				
				return department.getName();
			}
		}
		return null;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	

}
