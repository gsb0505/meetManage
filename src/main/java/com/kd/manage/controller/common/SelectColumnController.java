package com.kd.manage.controller.common;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;

@Controller
@RequestMapping("selectColumn")
public class SelectColumnController extends BaseController {

	@RequestMapping(value = "/selectColView.do")
	public String selectColView(String columnArrayJson, Model model)
			throws Exception {
		columnArrayJson = URLDecoder.decode(columnArrayJson, "utf-8");
		System.out.println(columnArrayJson);
		@SuppressWarnings("rawtypes")
		List columnArray = new Gson().fromJson(columnArrayJson, List.class);
		model.addAttribute("columnArray", columnArray);
		return "common/selectCol";
	}
	
	@RequestMapping(value = "/saveCol.do")
	public void saveCol(String columnName, HttpServletResponse response)
			throws Exception {
		this.out(response, columnName);
	}


}
