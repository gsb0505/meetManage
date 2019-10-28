/**
 * 
 */
package com.kd.manage.controller.system;

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

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseClient;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.BaseData;
import com.kd.manage.entity.PageCount;
import com.kd.manage.support.DataDictDefault;

/**
 *
 * @类名称：BaseDataAction.java
 * @类描述：基础数据管理

 * @创建时间：2015年1月23日-上午11:12:09
 * @修改备注:
 * @version
 */
@Controller
@RequestMapping(value = "/baseDataAction")
public class BaseDataController extends BaseController {
	private static WebTarget csu;
	static {
		csu = webTarget.get(BaseUri.commonServerUri);
	}

	// 首页
	@RequestMapping(value = "/list.do")
	public void viewList(BaseData baseData, PageCount pageCount,
			HttpServletResponse res) throws Exception {
		// 这边主要是通过name+typeId
		baseData.setTypeName("1");
		baseData.setPageCount(pageCount);
		String sendData = new Gson().toJson(baseData);
		WebTarget target = csu.path("queryData").queryParam("baseData", URLEncoder.encode(sendData, "utf-8"));
		List<BaseData> baseList = target.request().get(
				new GenericType<List<BaseData>>() {
				});
		pageCount = ManageUtil.packPage(baseList, pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);// 转化成json对象(jQgrid只能识别json对象)
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	// 首页
	@RequestMapping(value = "/listView.do")
	public String sendListView(String menuId, HttpServletRequest request,
			Model model) throws Exception {
		// 在这边需要获取下拉菜单的集合
		WebTarget target = csu.path("query/base");
		List<BaseData> list = target.request().get(
				new GenericType<List<BaseData>>() {
				});

		sendListCommon(menuId, model, request);
		model.addAttribute("list", list);
		return "system/baseData/list";

	}

	// 添加前的检查
	@RequestMapping(value = "/addView.do")
	public String addUserView(Model model, PageCount pageCount)
			throws Exception {
		BaseData baseData = new BaseData();
		baseData.setTypeName("2");
		pageCount.setShowCount(100);
		baseData.setPageCount(pageCount);
		String sendData = new Gson().toJson(baseData);
		// 这边页面跳转的时候
		WebTarget target = csu.path("queryData").queryParam("baseData", URLEncoder.encode(sendData, "utf-8"));
		/*
		 * BaseClient.getWebTarget(commonServerUri
		 * +"query?queryType=2"+BaseClient.createPageMessage(page));
		 * List<BaseData> list = target.request().get(new
		 * GenericType<List<BaseData>>() {});
		 */
		List<BaseData> list = target.request().get(
				new GenericType<List<BaseData>>() {
				});
		model.addAttribute("list", list);
		return "system/baseData/add";
	}

	// 获取业务码
	@RequestMapping(value = "/getCode.do", method = RequestMethod.POST)
	public void getCode(String typeId, HttpServletResponse response)
			throws Exception {
		// 这边要进行判断是否存在
		PrintWriter out = response.getWriter();
		try {

			WebTarget target = csu.path("getCode/" + typeId);
			Response res = target.request().get();
			String value = res.readEntity(String.class);
			res.close();
			try {
				// 设置默认业务码
				if (value == null || value.equals(""))
					value = RandomUtils.nextInt(9999 - 1000 + 1) + "";
				Integer.valueOf(value);
			} catch (Exception e) {
				out.print("");
				return;
			}
			out.print(value);

		} catch (Exception e) {
			logException(e);
			out.print(EXCEPTION);
		} finally {
			out.flush();
			out.close();
		}

	}

	// 添加
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public void add(BaseData data, HttpServletResponse response)
			throws Exception {
		// 这边要进行判断是否存在
		PrintWriter out = response.getWriter();
		try {

			WebTarget target = csu.path("add");
			Response res = target.request().post(
					Entity.entity(data, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {
				DataDictDefault.sync();
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

	// 检查数据是否已存在
	@RequestMapping(value = "/check.do", method = RequestMethod.POST)
	public void cheackExist(BaseData baseData, HttpServletResponse response,
			PageCount pageCount) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			baseData.setTypeName("1");
			baseData.setPageCount(pageCount);
			String sendData = new Gson().toJson(baseData);
			WebTarget target = csu.path("queryData").queryParam("baseData", URLEncoder.encode(sendData, "utf-8"));
			List<BaseData> list = target.request().get(
					new GenericType<List<BaseData>>() {
					});
			if (list != null && list.size() > 0) {
				out.print(FAIL);

			} else {
				out.print(SUCCESS);

			}
		} catch (Exception e) {
			out.print(EXCEPTION);

		} finally {
			out.flush();
			out.close();
		}

	}

	// 修改
	@RequestMapping(value = "/modifyView.do")
	public String updateUserView(String id, Model model, PageCount pageCount)
			throws Exception {

		WebTarget target = csu.path("toModify/" + id);
		BaseData baseData = target.request().get(new GenericType<BaseData>() {
		});
		pageCount.setShowCount(100);
		BaseData base = new BaseData();
		base.setTypeName("2");
		base.setPageCount(pageCount);
		String sendData = new Gson().toJson(base);
		WebTarget target2 = csu.path("queryData").queryParam("baseData", URLEncoder.encode(sendData, "utf-8"));
		List<BaseData> list = target2.request().get(
				new GenericType<List<BaseData>>() {
				});
		model.addAttribute("list", list);
		model.addAttribute("baseData", baseData);
		return "system/baseData/modify";
	}

	// 修改
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public void update(BaseData baseData, HttpServletResponse response)
			throws Exception {
		WebTarget target = csu.path("modify");
		PrintWriter out = response.getWriter();
		try {
			Response res = target.request().put(
					Entity.entity(baseData, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {
				DataDictDefault.sync();
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

	@RequestMapping(value = "/delete.do")
	public void delete(String id, HttpServletResponse response)
			throws Exception {
		WebTarget target = csu.path("delete/"+ id);
		PrintWriter out = response.getWriter();
		try {
			Response res = target.request().delete();
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {
				DataDictDefault.sync();
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

	// 检查数据是否已存在
		@RequestMapping(value = "/isUnique_param.do", method = RequestMethod.POST)
		public void isUnique_param(BaseData baseData, HttpServletResponse response) throws IOException {
			PrintWriter out = response.getWriter();
			try {
				String sendData = new Gson().toJson(baseData);
				WebTarget target= csu.path("isUnique_param").queryParam("baseData", URLEncoder.encode(sendData, "utf-8"));
				BaseData baseData2 = target.request().get(BaseData.class);
				if(null!=baseData2){
					out.print(FAIL);
				}else{
					out.print(SUCCESS);
				}
			} catch (Exception e) {
				out.print(EXCEPTION);

			} finally {
				out.flush();
				out.close();
			}

		}
}
