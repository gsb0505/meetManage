package com.kd.manage.controller.meetNotice;

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
import com.kd.manage.entity.MeetNotice;
import com.kd.manage.entity.MeetRoom;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.UserInfo;
import com.kd.manage.support.DataDictDefault;

/**
 * 会议室通知管理
 * @author thq
 *
 */
@Controller
@RequestMapping("/meetNotice")
public class MeetNoticeController extends BaseController{
	
	
	private static WebTarget tsu;
	private static WebTarget usu;
	static{
		tsu = BaseUri.webTarget.get(BaseUri.meetNoticeServiceUri);
		usu = BaseUri.webTarget.get(BaseUri.meetRoomServiceUri);
		 
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
WebTarget target = usu.path("queryList");
		
		List<MeetRoom> meetRoomList = target.request().get(
				new GenericType<List<MeetRoom>>() {
				});

		model.addAttribute("meetRoomList",meetRoomList);
		return "meetNotice/list" ;
	}
	
	/**
	 * 列表查询
	 * @param card
	 * @param pageCount
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public void list(MeetNotice meetNotice, PageCount pageCount,HttpServletResponse res,HttpServletRequest request) throws Exception{
		meetNotice.setPageCount(pageCount);

		String sendData = new Gson().toJson(meetNotice);
		WebTarget target = tsu.path("query").queryParam("meetNotice", URLEncoder.encode(sendData, "utf-8"));
		List<MeetNotice> meetNoticeList = target.request().get(new GenericType<List<MeetNotice>>() {});
		pageCount = ManageUtil.packPage(meetNoticeList, pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);// 转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
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
		WebTarget target = usu.path("queryList");
		
		List<MeetRoom> meetRoomList = target.request().get(
				new GenericType<List<MeetRoom>>() {
				});

		model.addAttribute("meetRoomList",meetRoomList);
		
		return "meetNotice/add";
	}
	
	/**
	 * 添加
	 * @throws IOException 
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public void add(MeetNotice meetNotice, HttpServletResponse response,HttpServletRequest request) throws IOException{
		PrintWriter out = response.getWriter();
		try {
			WebTarget target = tsu.path("add");
			String user = this.getUserId(request);
			meetNotice.setCreator(user);
			Response responses = target.request().buildPost(Entity.entity(meetNotice,MediaType.APPLICATION_XML)).invoke();
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
		MeetNotice meetNotice = res.readEntity(MeetNotice.class);
		res.close();
		
		WebTarget target = usu.path("queryList");
		
		List<MeetRoom> meetRoomList = target.request().get(
				new GenericType<List<MeetRoom>>() {
				});

		model.addAttribute("meetRoomList",meetRoomList);
		model.addAttribute("t", meetNotice);
		return "meetNotice/modify";
	}
	
	/**
	 * 修改
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public void modify(MeetNotice meetNotice, HttpServletResponse response,HttpServletRequest request) throws Exception {
		PrintWriter out = response.getWriter();

		WebTarget target = tsu.path("modify");
		try {
			Response res = target.request().put(
					Entity.entity(meetNotice, MediaType.APPLICATION_XML));
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
			MeetNotice meetNotice = new MeetNotice();
			meetNotice.setId(id);
			meetNotice.setStatus(0);
			Response res = target.request().put(
					Entity.entity(meetNotice, MediaType.APPLICATION_XML));
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
	 * 撤消发布
	 * @throws Exception
	 */
	@RequestMapping(value = "/logOut.do")
	public void logOut(String id, HttpServletResponse response)
			throws Exception {
		WebTarget target = tsu.path("modify");
		PrintWriter out = response.getWriter();
		try {
			MeetNotice meetNotice = new MeetNotice();
			meetNotice.setId(id);
			meetNotice.setStatus(1);
			Response res = target.request().put(
					Entity.entity(meetNotice, MediaType.APPLICATION_XML));
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
	 * 删除
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(String id, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();		
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
	
	
}
