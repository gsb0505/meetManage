package com.kd.manage.controller.report;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.DateJsonValueProcessor;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.http.ContentType;
import com.kd.manage.entity.MeetRoom;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.SystemTReport;
import com.kd.manage.support.ExcelSupport;

@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
	private static WebTarget rsu;
	private static SimpleDateFormat  SDF ; 
	private static SimpleDateFormat  SDF_2 ;
	private static SimpleDateFormat  SDF_3 ;
	private static WebTarget usu ;
	
	static{
		SDF = new SimpleDateFormat("yyyy-MM-dd");
		SDF_2 = new SimpleDateFormat("yyyy-MM");
		SDF_3 = new SimpleDateFormat("yyyy");
		rsu = BaseUri.webTarget.get(BaseUri.reportServiceUri);
		usu = BaseUri.webTarget.get(BaseUri.meetRoomServiceUri);
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(SDF, true));
	}
	
	private Date getYesterday(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	private String getYester(){
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR));
	}
	private String getMonth(){
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	private String getDay(){
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.DATE));
	}

	private String getYesterdayDay(){
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.DATE)-1);
	}
	
	
	/**
	 * 导出--会议预约统计（汇总）
	 * @throws Exception
	 */
	@RequestMapping("/systemView.do")
	public String systemView(Model model){
		WebTarget target = usu.path("queryList");

		List<MeetRoom> meetRoomList = target.request().get(
				new GenericType<List<MeetRoom>>() {
				});
		model.addAttribute("meetRoomList", meetRoomList);
		
		setDateTimeToModel(model);
		model.addAttribute("tradeDate",SDF.format(getYesterday()));
		return "report/system/list";
	}
	@RequestMapping("/systemList.do")
	public void systemList(SystemTReport systemTReport, PageCount pageCount,HttpServletResponse res) throws Exception{
		if(systemTReport.getCreateTime() ==null){
			systemTReport.setCreateTime(new Date());
		}
		if(systemTReport.getUpdateTime() ==null){
			systemTReport.setUpdateTime(new Date());
		}
		systemTReport.setPageCount(pageCount);
		String sendData = new Gson().toJson(systemTReport);
		WebTarget target = rsu.path("querySystemTReport").queryParam("systemTReport",URLEncoder.encode(sendData, "utf-8"));
		List<SystemTReport> list = target.request().get(new GenericType<List<SystemTReport>>() {});
		
		if(list != null && list.size() == 1)
			list.clear();
		
		/*for (int i = 0,len = list.size(); i < len; i++) {
			if(list.get(i).getTerminalNo() == null){
				continue;
			}
			Terminal ter = new Terminal();
			ter.setTerminalID(list.get(i).getTerminalNo());
			String sendData2 = new Gson().toJson(ter);
			WebTarget targetReg = tsu.path("queryByParam").queryParam("terminal", URLEncoder.encode(sendData2, "utf-8"));
			Response responseReg = targetReg.request().get();
			
			Terminal terminal = responseReg.readEntity(Terminal.class);
			if(terminal != null)
				list.get(i).setTerminalNo(terminal.getTerminalName());
		}*/
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		pageCount = ManageUtil.packPage(list, pageCount);
		JSONObject json = JSONObject.fromObject(pageCount,jsonConfig);// 转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();		
	}
	@RequestMapping(value="/exportSystemList.do",method=RequestMethod.POST)
	public void exportSystemList(SystemTReport systemTReport,HttpServletRequest request,HttpServletResponse res,
								 String createTime,String updateTime ,String serchType) throws Exception{
		String sendData = new Gson().toJson(systemTReport);
		WebTarget target = rsu.path("querySystemTReportList").queryParam("systemTReport", URLEncoder.encode(sendData, "utf-8"));
		List<SystemTReport> list = target.request().get(new GenericType<List<SystemTReport>>() {});
//如果查询没有数据则不显示总合计
		if(list.size()==1){
			list.remove(0);
		}
		if (serchType!=null && serchType !=""){
			if(serchType.equals("2")){
				if(updateTime!=null && !"".equals(updateTime) && createTime!=null && !"".equals(createTime)){
					createTime = SDF_2.format(SDF_2.parse(createTime));
					updateTime = SDF_2.format(SDF_2.parse(updateTime));
				}
			}
			if(serchType.equals("3")){
				if(updateTime!=null && !"".equals(updateTime) && createTime!=null && !"".equals(createTime)){
					createTime = SDF_3.format(SDF_3.parse(createTime));
					updateTime = SDF_3.format(SDF_3.parse(updateTime));
				}
			}
		}
		String[] titles={"会议时间","会议室","部门","预约次数"};
		String[] columns={"meetDate","meetRoomName","orgname","meetCount"};
		int[] hb = {0,1,2};
		//开始生成文件
		String tradeDate=null;
		if(updateTime!=null && !"".equals(updateTime) && createTime!=null && !"".equals(createTime)){
			tradeDate = createTime + "至" + updateTime;
		}else{
			tradeDate= SDF.format(new Date());
		}
		String fos = ExcelSupport.exportExcelHB(request,list,columns,titles,"会议统计报表（"+createTime+"至"+updateTime+"）",hb,tradeDate,"会议统计报表");
		PrintWriter out = res.getWriter();
		out.print(fos);
		out.flush();
		out.close();		
	}
	
	
	public static boolean isChineseChar(String str){
	       boolean temp = false;
	       Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
	       Matcher m=p.matcher(str); 
	       if(m.find()){ 
	           temp =  true;
	       }
	       return temp;
   }
	

	//赋值年、月、日
	private void setDateTimeToModel(Model model){
		model.addAttribute("currentYear", getYester());
		model.addAttribute("currentMonth", getMonth());
		model.addAttribute("currentDay", getDay());
	}


	/**
	 * 下载
	 * @throws Exception
	 */
	@RequestMapping("/download.do")
	public void download(HttpServletRequest request,HttpServletResponse response,String type,String fileName){
		String contentType = ContentType.EXCEL;
		response.setContentType(contentType);
		String savePath = null;
		try {
			if(type.equals("ex"))
				savePath = request.getSession().getServletContext().getRealPath("/resources/excels");
			else
				savePath = request.getSession().getServletContext().getRealPath("/Data");
			
			//判断存放excel的文件夹是否存在，不存在创建新的文件夹
			File dir = new File(savePath);
			if(!dir.exists())
				dir.mkdirs();
			File fExcel = new File(savePath+"/"+fileName);
			
			//文件下载
			super.fileDownLoad(request, response, contentType, fExcel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 打印页面跳转
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/printViews.do")
	public String printViews(String maps,Model model) throws UnsupportedEncodingException{
		JSONObject objs = JSONObject.fromObject(maps);
		@SuppressWarnings("rawtypes")
		Iterator ite = objs.keys();
		StringBuffer para = new StringBuffer();
		String key = null;
		String printPage = null;
		String value = null;
		while (ite.hasNext()) {
			key = (String) ite.next();
			
			if(key!=null && key.equals("printPage")){
				printPage = (String) objs.get("printPage");
			}else{
				value = objs.getString(key);
				if(value != null && !value.equals("") && !value.equals("null"))
					if(isChineseChar(value))
						para.append(key+"="+URLEncoder.encode(value,"UTF-8")+"&");
					else
						para.append(key+"="+value+"&");
			}
				
		}
		model.addAttribute("sarechStr",para.toString());

		if(objs.get("tradeDate") != null){
			model.addAttribute("tradeDate",objs.get("tradeDate"));
		}else if(objs.get("tradeTime") != null){
			model.addAttribute("tradeTime",objs.get("tradeTime"));
		}else{
			if(objs.get("createTime") != null && objs.get("updateTime") != null){
				String serchType = null;
				try {
					serchType = objs.get("serchType").toString();


					if (serchType!=null && serchType !=""){
						if(serchType.equals("2")){
							Date  temp1 = SDF_2.parse(objs.get("createTime").toString());
							Date  temp2 = SDF_2.parse(objs.get("updateTime").toString());
							model.addAttribute("createTime",SDF_2.format(temp1));
							model.addAttribute("updateTime",SDF_2.format(temp2));
						}
						if(serchType.equals("3")){
							Date  temp1 = SDF_3.parse(objs.get("createTime").toString());
							Date  temp2 = SDF_3.parse(objs.get("updateTime").toString());
							model.addAttribute("createTime",SDF_3.format(temp1));
							model.addAttribute("updateTime",SDF_3.format(temp2));
						}
					}

				} catch (Exception e) {
				}
				if (serchType==null || serchType ==""){
					model.addAttribute("createTime",objs.get("createTime"));
					model.addAttribute("updateTime",objs.get("updateTime"));
				}
				if ("1".equals(serchType)){
					model.addAttribute("createTime",objs.get("createTime"));
					model.addAttribute("updateTime",objs.get("updateTime"));
				}
			}

		}


		model.addAttribute("createTableDate", SDF.format(new Date()));
		return "report/"+printPage+"/printList";
	}
	
}
