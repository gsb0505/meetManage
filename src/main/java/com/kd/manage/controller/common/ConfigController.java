package com.kd.manage.controller.common;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseClient;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.BaseData;
import com.kd.manage.entity.BaseEntity.Save;
import com.kd.manage.entity.BaseEntity.Update;
import com.kd.manage.entity.Config;
import com.kd.manage.entity.PageCount;

@Controller
@RequestMapping("/ConfigAction")
public class ConfigController extends BaseController {
	private static WebTarget csu;
	static {
		/*configuri = PropertiesUtil.readValue("configuri");
		commonServerUri = PropertiesUtil.readValue("commonServerUri");
		noticeTableid = PropertiesUtil.readValue("commonServerUri.basetype.query9");*/
		
		csu = webTarget.get(BaseUri.configServerUri);
	}

	@RequestMapping("listView.do")
	public String listView(String menuId, HttpServletRequest request,
			Model model) throws Exception {
		/*WebTarget target = BaseClient.getWebTarget(commonServerUri + "query/"
				+ noticeTableid);
		List<BaseData> list = target.request().get(
				new GenericType<List<BaseData>>() {
				});
		model.addAttribute("list", list);*/
		sendListCommon(menuId, model, request);
		return "system/config/list";
	}

	@RequestMapping("/addView.do")
	public String addView(HttpServletRequest request, Model model)
			throws Exception {

		return "system/config/add";
	}

	@RequestMapping("/query.do")
	public void query(Config config, PageCount pageCount,
			HttpServletResponse response) throws Exception {
		if (pageCount == null) {
			pageCount = new PageCount();
		}
		config.setPageCount(pageCount);
		String str = new Gson().toJson(config);
		WebTarget target = csu.path("query").queryParam("str", URLEncoder.encode(str, "utf-8"));
		List<Config> lists = target.request().get(
				new GenericType<List<Config>>() {
				});
		pageCount = ManageUtil.packPage(lists, pageCount);
		JSONObject json = JSONObject.fromObject(pageCount);

		this.out(response, json);
	}

	@RequestMapping("/add.do")
	public void add(@Validated(Save.class)Config config, BindingResult result, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		if (result.hasErrors()) {  
			out.print(EXCEPTION);
			return;
	    } 
		/*
		 * WebTarget target=BaseClient.getWebTarget(configuri+"queryMaxcode");
		 * Response response2=target.request().get(); String
		 * code=response2.readEntity(String.class);
		 * config.setCode((Integer.parseInt(code)+1)+"");
		 */
		WebTarget target2 = csu.path("add");
		Response res = target2.request().post(
				Entity.entity(config, MediaType.APPLICATION_XML));
		String value = res.readEntity(String.class);
		res.close();
		if ("true".equals(value)) {

			out.print(SUCCESS);

		} else {
			out.print(FAIL);
		}
	}

	@RequestMapping("/modifyView.do")
	public String updateView(HttpServletRequest request, Model model, String id) {
		WebTarget tar = csu.path("queryById").queryParam("id", id);
		Config config = tar.request().get(new GenericType<Config>() {
		});
		model.addAttribute("config", config);
		return "system/config/modify";
	}

	@RequestMapping("/modify.do")
	public void update(@Validated(Update.class)Config config, BindingResult result, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		if (result.hasErrors()) {  
			out.print(EXCEPTION);
			return;
	    } 
		WebTarget target = csu.path("modify");
		Response res = target.request().put(
				Entity.entity(config, MediaType.APPLICATION_XML));
		String value = res.readEntity(String.class);
		res.close();
		if ("true".equals(value)) {
			out.print(SUCCESS);
		} else {
			out.print(FAIL);
		}
	}

	/**
	 * 唯一编号查询
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/findByCode.do")
	public void findByCode(String code, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		WebTarget target = csu.path("queryByCode").queryParam("code", code);
		Response res = target.request().get();
		Config config = res.readEntity(Config.class);
		res.close();
		if (config == null)
			out.print(true);
		else
			out.print(false);
	}
	@RequestMapping("/findByCodeConfig.do")
	public void findByCodeConfig(String code, HttpServletResponse response)
			throws Exception {
		WebTarget target = csu.path("queryByCode").queryParam("code", code);
		Response res = target.request().get();
		Config config = res.readEntity(Config.class);
		JSONObject json = JSONObject.fromObject(config);
		res.close();
		out(response, json);
	}

	@RequestMapping(value = "/delete.do")
	public void delete(String id, HttpServletResponse response)
			throws Exception {
		WebTarget target = csu.path("delete").queryParam("id", id);
		PrintWriter out = response.getWriter();
		try {
			Response res = target.request().delete();
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
			logException(e);
			out.print(EXCEPTION);
		} finally {
			this.out(response, null);
		}
	}
}
