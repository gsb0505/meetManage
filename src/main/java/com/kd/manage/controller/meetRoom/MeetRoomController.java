package com.kd.manage.controller.meetRoom;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kd.manage.controller.util.PropertiesUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.StringUtil;
import com.kd.manage.entity.BaseData;
import com.kd.manage.entity.MeetRoom;
import com.kd.manage.entity.OrderDetail;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.UserInfo;
import com.kd.manage.support.DataDictDefault;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 会议室管理
 * @author zlm
 *
 */
@Controller
@RequestMapping("/meetRoom")
public class MeetRoomController extends BaseController{
	private static WebTarget tsu;
	private static WebTarget odsu;
	private static WebTarget usu;
	//资源路径
	private final static String prefix = PropertiesUtil.readValue("meetRoom.prefix");
	static{
				
		tsu = BaseUri.webTarget.get(BaseUri.meetRoomServiceUri);
		odsu = BaseUri.webTarget.get(BaseUri.orderDetailServerUri);
		usu = BaseUri.webTarget.get(BaseUri.userServerUri);
		 
	}
	
	/**
	 * 查看跳转
	 * @throws Exception
	 */
	@RequestMapping("/view.do")
	public String view(String id, Model model) throws Exception{
		WebTarget target = tsu.path("queryModel/" + id);
		Response res = target.request().get();
		MeetRoom meetRoom = res.readEntity(MeetRoom.class);
		
		Map<Integer,String> jsonObject = new HashMap<Integer,String>();
		Map<String,String> jsonObject2 = new HashMap<String,String>();
		Map<String,String> jsonObject3 = new HashMap<String,String>();
		List<BaseData> datas = getMeetRoomType();
		for (BaseData data : datas) {
			jsonObject.put(Integer.parseInt(data.getCode()), data.getName());
		}
		List<BaseData> base7List = DataDictDefault.getList(DataDictDefault.type7);
		for (BaseData data : base7List) {
			jsonObject2.put(data.getCode(), data.getName());
		}
		List<BaseData> base8List = DataDictDefault.getList(DataDictDefault.type8);
		for (BaseData data : base8List) {
			jsonObject3.put(data.getCode(), data.getName());
		}
		
		model.addAttribute("info", meetRoom);
		model.addAttribute("meetRoomTypes", jsonObject);
		model.addAttribute("terList", jsonObject2);
		model.addAttribute("parkList", jsonObject3);
		model.addAttribute("timeRange", DataDictDefault.getList(DataDictDefault.type6));
		return "meetRoom/view" ;
	}
	
	/**
	 * 列表跳转
	 * @param menuId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listView.do")
	public String listView(String menuId, Model model,
			HttpServletRequest request) throws Exception{
		sendListCommon(menuId, model, request);
		JSONObject jsonObject = new JSONObject();

		List<BaseData> datas = getMeetRoomType();
		for (BaseData data : datas) {
			jsonObject.put(data.getCode(), data.getName());
		}
		model.addAttribute("meetRoomTypes", jsonObject);
		model.addAttribute("meetRoomTypess", datas);
		return "meetRoom/list" ;
	}
	
	/**
	 * 列表查询
	 * @param card
	 * @param pageCount
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public void list(MeetRoom meetRoom, PageCount pageCount,HttpServletResponse res,HttpServletRequest request) throws Exception{
		meetRoom.setPageCount(pageCount);

		String sendData = new Gson().toJson(meetRoom);
		WebTarget target = tsu.path("query").queryParam("meetRoom", URLEncoder.encode(sendData, "utf-8"));
		List<MeetRoom> meetRoomList = target.request().get(new GenericType<List<MeetRoom>>() {});
		pageCount = ManageUtil.packPage(meetRoomList, pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);// 转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();		
	}

	/**
	 * 根据终端号查询终端
	 */
	@RequestMapping("/queryTerID.do")
	public void queryTerID(String no,HttpServletResponse res,HttpServletRequest request) throws Exception{
		PrintWriter out = res.getWriter();
		if(StringUtil.isBlank(no)){
			out.print(false);
			out.flush();
			out.close();
			return;
		}
		
		MeetRoom ter = new MeetRoom();
//		ter.setTerminalID(no);
//		ter.setTerminalType(webType);
		String sendData = new Gson().toJson(ter);
		WebTarget target = tsu.path("existByParam").queryParam("terminal", URLEncoder.encode(sendData, "utf-8"));
		Boolean l = target.request().get(new GenericType<Boolean>() {});
		if(l != null && l){
			out.print(true);
		}else{
			out.print(false);
		}
		out.flush();
		out.close();	
	}
	/**
	 * 查询终端 判断psam
	 */
	@RequestMapping("/queryTerNOPSAM.do")
	public void queryTerNOPSAM(String no,String psam,HttpServletResponse res,HttpServletRequest request) throws Exception{
		PrintWriter out = res.getWriter();
		if(StringUtil.isBlank(no) || StringUtil.isBlank(psam)){
			out.print("false");
			out.flush();
			out.close();
			return;
		}
		
		MeetRoom ter = new MeetRoom();
//		ter.setTerminalID(no);
//		ter.setTerminalType(webType);
		String sendData = new Gson().toJson(ter);
		WebTarget targetReg = tsu.path("queryByParam").queryParam("terminal", URLEncoder.encode(sendData, "utf-8"));
		Response responseReg = targetReg.request().get();
		
		MeetRoom terminal = responseReg.readEntity(MeetRoom.class);
		if(terminal != null){
//			if(terminal.getPcmNo().equals(psam)){
//				out.print("true");
//			}else{
//				out.print("fail");
//			}
			out.print("true");
		}else{
			out.print("false");
		}
		out.flush();
		out.close();	
	}
	
	/**
	 * 添加视图跳转
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addView.do")
	public String addView(Model model) throws Exception {
		WebTarget target = usu.path("queryAll");
		List<UserInfo> userList = target.request().get(
				new GenericType<List<UserInfo>>() {
				});

		List<BaseData> base1List = DataDictDefault.getList(DataDictDefault.type1);
		model.addAttribute("typeList", base1List);
		model.addAttribute("userList",userList);
		
		return "meetRoom/add";
	}
	
	/**
	 * 添加
	 * @throws IOException 
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public void add(MeetRoom meetRoom, @RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
					HttpServletResponse response, HttpServletRequest request) throws IOException{
		PrintWriter out = response.getWriter();
		try {
			//String photo = savePhoto(photoFile, request, prefix);
			//meetRoom.setPhotoUrl(photo);

			WebTarget target = tsu.path("add");
			String user = this.getUserId(request);
			meetRoom.setCreator(user);
			Response responses = target.request().buildPost(Entity.entity(meetRoom,MediaType.APPLICATION_XML)).invoke();
			String value = responses.readEntity(String.class);
			responses.close();
			if ("true".equals(value)) {
				out.print(SUCCESS);
			} else if ("false".equals(value)) {
				out.print(FAIL);
			} else {
				out.print(EXCEPTION);
			}
		} catch (Exception e) {
			logException(e);
			out.print(EXCEPTION);
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 修改视图跳转
	 * @throws Exception
	 */
	@RequestMapping(value = "/modifyView.do")
	public String modifyView(String id, Model model) throws Exception {
		
		WebTarget target1 = tsu.path("queryModel/" + id);
		Response res = target1.request().get();
		MeetRoom meetRoom = res.readEntity(MeetRoom.class);
		res.close();
		
		WebTarget target = usu.path("queryAll");
		List<UserInfo> userList = target.request().get(
				new GenericType<List<UserInfo>>() {
				});

		List<BaseData> base1List = DataDictDefault.getList(DataDictDefault.type1);
		model.addAttribute("t", meetRoom);
		model.addAttribute("typeList", base1List);
		model.addAttribute("userList",userList);
		return "meetRoom/modify";
	}
	
	/**
	 * 修改
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public void modify(MeetRoom meetRoom, @RequestParam(value = "photoFile", required = false) MultipartFile photoFile
			, HttpServletResponse response,HttpServletRequest request) throws Exception {
		PrintWriter out = response.getWriter();

		//String photo = savePhoto(photoFile, request, prefix);
		//meetRoom.setPhotoUrl(photo);

		WebTarget target = tsu.path("modify");
		try {
			Response res = target.request().put(
					Entity.entity(meetRoom, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {
				out.print(SUCCESS);
			} else if ("false".equals(value)) {
				out.print(FAIL);
			} else {
				out.print(EXCEPTION);
			}

		} catch (Exception e) {
			out.print(EXCEPTION);

		} finally {
			out.flush();
			out.close();
		}

	}
	
	/**
	 * 激活
	 * @throws Exception
	 */
	@RequestMapping(value = "/backOut.do")
	public void backOut(String id, HttpServletResponse response)
			throws Exception {
		WebTarget target = tsu.path("modify");
		PrintWriter out = response.getWriter();
		try {
			MeetRoom terminal = new MeetRoom();
			terminal.setId(id);
			terminal.setStatus(0);
			Response res = target.request().put(
					Entity.entity(terminal, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {

				out.print(SUCCESS);

			} else if ("false".equals(value)) {
				out.print(FAIL);
			} else {
				out.print(EXCEPTION);
			}

		} catch (Exception e) {
			out.print(EXCEPTION);

		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 注销
	 * @throws Exception
	 */
	@RequestMapping(value = "/logOut.do")
	public void logOut(String id, HttpServletResponse response)
			throws Exception {
		WebTarget target = tsu.path("modify");
		PrintWriter out = response.getWriter();
		try {
			MeetRoom terminal = new MeetRoom();
			terminal.setId(id);
			terminal.setStatus(1);
			Response res = target.request().put(
					Entity.entity(terminal, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {
				out.print(SUCCESS);
			} else if ("false".equals(value)) {
				out.print(FAIL);
			} else {
				out.print(EXCEPTION);
			}

		} catch (Exception e) {
			out.print(EXCEPTION);

		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 终端名称唯一验证
	 */
	@RequestMapping(value = "/isUnique.do", method = RequestMethod.POST)
	public void isUnique(String name, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MeetRoom ter = new MeetRoom();
			ter.setMeetRoomName(name);
			String sendData = new Gson().toJson(ter);
			WebTarget targetReg = tsu.path("queryByParam").queryParam("meetRoom", URLEncoder.encode(sendData, "utf-8"));
			Response responseReg = targetReg.request().get();
			
			MeetRoom meetRoom = responseReg.readEntity(MeetRoom.class);
			if (meetRoom != null ) {
				out.print(false);
			} else {
				out.print(true);
			}
		} catch (Exception e) {
			logException(e);
			out.print(EXCEPTION);
		} finally {
			out.flush();
			out.close();
		}

	}
	
	/**
	 * 删除
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(String id, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		
		WebTarget targetw = tsu.path("queryModel/" + id);
		Response resw = targetw.request().get();
		MeetRoom meetRoom = resw.readEntity(MeetRoom.class);
		
		OrderDetail od=new OrderDetail();
		od.setMeetRoomID(meetRoom.getMeetRoomID());
		String sendData = new Gson().toJson(od);
		//查询交易记录
		WebTarget targetDet = odsu.path("exitByParam").queryParam("orderDetail", URLEncoder.encode(sendData, "utf-8"));
		Response responseReg = targetDet.request().get();
		
		Boolean rest = responseReg.readEntity(Boolean.class);
		
		if(rest){
			super.out(response, EXSIT);
			return;
		}
		
		WebTarget target = tsu.path("delete/" + id);
		
		try {
			Response res = target.request().delete();
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {

				out.print(SUCCESS);

			} else if ("false".equals(value)) {
				out.print(FAIL);
			} else {
				out.print("false");
			}
		} catch (Exception e) {
			logException(e);
			out.print(EXCEPTION);
		} finally {
			out.flush();
			out.close();
		}
	}
	
		
	/**
	 * 金额方式
	 * @return
	 */
	private List<BaseData> getMeetRoomType(){
		List<BaseData> list =  DataDictDefault.getList(DataDictDefault.type1);
		return list;
	}


	
}
