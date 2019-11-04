package com.kd.manage.controller.system;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.kd.core.dto.UserLogDto;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.UserLog;

@Controller
@RequestMapping("/queryLogAction")
public class QueryLogController extends BaseController{
	private static WebTarget ulsu;
	static {
		ulsu = BaseUri.webTarget.get(BaseUri.userLogServerUri);
	}



	
	@RequestMapping(value="/listView.do")
	public String sendListView( )throws Exception{
		return "system/log/list";
		
	}
	
	
	
	@RequestMapping("list.do")
	public void list(HttpServletResponse response,UserLogDto userLogDto,PageCount pageCount) throws Exception{
		UserLogDto userLog = userLogDto;
		if(userLog.getType() == null || "".equals(userLog.getType())){
			userLog.setType("0");
		}
		userLog.setPageCount(pageCount);
		String sendData = new Gson().toJson(userLog);
		WebTarget target= ulsu.path("queryLogByCondition").queryParam("userLogDto", URLEncoder.encode(sendData, "UTF-8"));
		List <UserLog> logList = target.request().get(new GenericType<List<UserLog>>() {});
		PrintWriter out = response.getWriter();
		pageCount = ManageUtil.packPage(logList,pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);//将json字符串转化成json对象(jQgrid只能识别json对象)
		out.print(json);
		out.flush();
		out.close();
	}	
	
	
	
	

}



