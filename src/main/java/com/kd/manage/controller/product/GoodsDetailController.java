package com.kd.manage.controller.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.entity.BaseData;
import com.kd.manage.entity.GoodsDetail;
import com.kd.manage.entity.GoodsInfo;
import com.kd.manage.entity.PageCount;
import com.kd.manage.support.DataDictDefault;

import jersey.repackaged.com.google.common.collect.Lists;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/goodsDetail")
public class GoodsDetailController extends BaseController{
	
	private static WebTarget goodsDetailServiceUri;
	
	static{
		goodsDetailServiceUri = BaseUri.webTarget.get(BaseUri.goodsDetailServiceUri);
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
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();

		List<BaseData> datas = getGoodsInfoType();
		List<BaseData> datas2 = getStoreCode();
		for (BaseData data : datas) {
			jsonObject.put(Integer.parseInt(data.getCode()), data.getName());
		}
		for (BaseData data : datas2) {
			jsonObject2.put(Integer.parseInt(data.getCode()), data.getName());
		}
		model.addAttribute("typeCodes", jsonObject);
		model.addAttribute("typeCodess", datas);

		model.addAttribute("storeCodes", jsonObject2);
		model.addAttribute("storeCodess", datas2);
		return "product/goodsDetail/list" ;
	}
	
	/**
	 * 商品类型
	 * @return
	 */
	private List<BaseData> getGoodsInfoType(){
		List<BaseData> list =  DataDictDefault.getList(DataDictDefault.type3);
		if(list ==  null) {
			return Lists.newArrayList();
		}else{
			return list;
		}
	}

	/**
	 * 获取商家
	 * @return
	 */
	private List<BaseData> getStoreCode(){
		List<BaseData> list =  DataDictDefault.getList(DataDictDefault.type2);
		if(list ==  null) {
			return Lists.newArrayList();
		}else{
			return list;
		}
	}
	
	/**
	 * 列表查询
	 * @param pageCount
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public PageCount list(final GoodsDetail goodsDetail, PageCount pageCount, HttpServletResponse res, HttpServletRequest request) throws Exception{
		goodsDetail.setPageCount(pageCount);
		String sendData = new Gson().toJson(goodsDetail,GoodsDetail.class);
		//WebTarget target = productServiceUri.path("query").queryParam("goodsInfo", URLEncoder.encode(sendData, "utf-8"));
		Response responses = goodsDetailServiceUri.path("query").request().post(Entity.entity(goodsDetail,MediaType.APPLICATION_XML));
		List<GoodsDetail> GoodsDetailList = responses.readEntity(new GenericType<List<GoodsDetail>>(){});
		pageCount = ManageUtil.packPage(GoodsDetailList, pageCount);
		//JSONObject json = JSONObject.fromObject(pageCount);// 转化成json对象(jQgrid只能识别json对象)
		//PrintWriter out = res.getWriter();
		//out.print(json);
		//out.flush();
		//out.close();
		return pageCount;
	}
	
}
