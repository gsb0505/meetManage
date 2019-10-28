package com.kd.manage.controller.meetDesk;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.entity.MeetRoom;
import com.kd.manage.entity.OrderDetail;
import com.kd.manage.entity.PageCount;

/**
 * 订单明细-Action
 * 
 * @author zlm
 *
 */
@Controller
@RequestMapping("meetDeskAction")
public class meetDeskController extends BaseController {
	private static WebTarget odsu;
	private static WebTarget usu;

	static {
		odsu = webTarget.get(BaseUri.orderDetailServerUri);
		usu = webTarget.get(BaseUri.meetRoomServiceUri);
	}

	/**
	 * @描述:预定列表
	 */
	@RequestMapping(value = "/list.do")
	public void List(OrderDetail dto, PageCount pageCount,
			HttpServletResponse res) throws Exception {
		dto.setPageCount(pageCount);
		String sendData = new Gson().toJson(dto);
		WebTarget target = odsu.path("queryCheck").queryParam("orderDetail", URLEncoder.encode(sendData, "utf-8"));
		List<OrderDetail> odList = target.request().get(
				new GenericType<List<OrderDetail>>() {
				});
		pageCount = ManageUtil.packPage(odList, pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);// 转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	/**
	 * 审核列表跳转
	 * @param menuId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listView.do")
	public String ListView(String menuId, Model model,
			HttpServletRequest request) throws Exception {
		WebTarget target = usu.path("queryList");

		List<MeetRoom> meetRoomList = target.request().get(
				new GenericType<List<MeetRoom>>() {
				});
		SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
		String dateStr = dateformat.format(System.currentTimeMillis());
		System.out.println(dateStr);
		model.addAttribute("curDate", dateStr);
		model.addAttribute("meetRoomList", meetRoomList);
		return "detail/meetDesk/list";
	}

}