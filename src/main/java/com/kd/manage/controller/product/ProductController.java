package com.kd.manage.controller.product;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.controller.util.StringUtil;
import com.kd.manage.entity.*;
import com.kd.manage.support.DataDictDefault;
import jersey.repackaged.com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 商品管理
 * @author zlm
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController{
	private static WebTarget productServiceUri;
	//商品图片地址前缀
	private final static String prefix = PropertiesUtil.readValue("product.prefix");
	/**
	 * 价格单位
	 */
	private final static int priceUnit = 100;

	static{
		productServiceUri = BaseUri.webTarget.get(BaseUri.productServiceUri);
	}
	
	/**
	 * 修改跳转
	 * @throws Exception
	 */
	@RequestMapping("/modifyView.do")
	public String view(@RequestParam("id") String id, Model model) throws Exception{
		//GoodsInfo goodsInfo = new GoodsInfo();
		//goodsInfo.setId(id);
		WebTarget target = productServiceUri.path("getGinfo/"+id);
		Response res = target.request().get();
		GoodsInfo t = res.readEntity(new GenericType<GoodsInfo>(){});

//		if(t != null){
//			//价格处理
//			t.setDoublePrice(t.getPrice() / priceUnit);
//		}

		List<BaseData> base1List = DataDictDefault.getList(DataDictDefault.type3);
		List<BaseData> storeList = getStoreCode();
		model.addAttribute("typeList", base1List);
		model.addAttribute("storeList", storeList);
		model.addAttribute("t", t);
		return "product/modify" ;
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
		return "product/list" ;
	}
	
	/**
	 * 列表查询
	 * @param pageCount
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public PageCount list(final GoodsInfo goodsInfo, PageCount pageCount, HttpServletResponse res, HttpServletRequest request) throws Exception{
		goodsInfo.setPageCount(pageCount);
		String sendData = new Gson().toJson(goodsInfo,GoodsInfo.class);
		//WebTarget target = productServiceUri.path("query").queryParam("goodsInfo", URLEncoder.encode(sendData, "utf-8"));
		Response responses = productServiceUri.path("query").request().post(Entity.entity(goodsInfo,MediaType.APPLICATION_XML));
		List<GoodsInfo> GoodsInfoList = responses.readEntity(new GenericType<List<GoodsInfo>>(){});
		pageCount = ManageUtil.packPage(GoodsInfoList, pageCount);
		//JSONObject json = JSONObject.fromObject(pageCount);// 转化成json对象(jQgrid只能识别json对象)
		//PrintWriter out = res.getWriter();
		//out.print(json);
		//out.flush();
		//out.close();
		return pageCount;
	}
	
	/**
	 * 添加视图跳转
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addView.do")
	public String addView(Model model) throws Exception {
		//WebTarget target = productServiceUri.path("queryAll");
		//List<UserInfo> userList = target.request().get(new GenericType<List<UserInfo>>() { });

		List<BaseData> base1List = DataDictDefault.getList(DataDictDefault.type3);
		List<BaseData> storeList = getStoreCode();
		model.addAttribute("typeList", base1List);
		model.addAttribute("storeList", storeList);

		return "product/add";
	}
	
	/**
	 * 添加
	 * @throws IOException 
	 */
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public String add(GoodsInfo goodsInfo, @RequestParam(value = "photoUrl", required = false) MultipartFile photoUrl,
					  HttpServletResponse response, HttpServletRequest request) throws IOException{
		Response responses = null;
		try {
			//价格处理
//			double doup =goodsInfo.getDoublePrice();
//			if(!StringUtils.isEmpty(doup)) {
//				;BigDecimal decimal=new BigDecimal(doup);
//				goodsInfo.setPrice((double)(decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() * priceUnit));
//			}
			savePhoto(photoUrl, request, goodsInfo);

			WebTarget target = productServiceUri.path("add");
			responses = target.request().buildPost(Entity.entity(goodsInfo,MediaType.APPLICATION_XML)).invoke();
			String value = responses.readEntity(String.class);
			String result = result(value);

			return result;
		} catch (Exception e) {
			logException(e);
			return EXCEPTION;
		}finally {
			if(responses != null){
				responses.close();
			}
		}
	}

	/**
	 * 修改
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	@ResponseBody
	public String modify(GoodsInfo goodsInfo,@RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
						 HttpServletResponse response,HttpServletRequest request) throws Exception {
		WebTarget target = productServiceUri.path("modify");
		Response res = null;
		try {
			savePhoto(photoFile, request, goodsInfo);

			res = target.request().put(
					Entity.entity(goodsInfo, MediaType.APPLICATION_XML));
			String value = res.readEntity(String.class);

			return result(value);
		} catch (Exception e) {
			return EXCEPTION;
		} finally {
			if(res != null){
				res.close();
			}
		}

	}
	
	/**
	 * 商品下架
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/lowerShelf.do")
	@ResponseBody
	public String lowerShelf(String id, HttpServletResponse response)
			throws Exception {
		if(StringUtils.isEmpty(id)){
			return "参数异常";
		}
		Boolean rest = executeShelf(id,"2");

		try {
			if(rest == null || !rest){
				return FAIL;
			}else if (rest) {
				return SUCCESS;
			} else {
				return EXCEPTION;
			}
		} catch (Exception e) {
			logException(e);
			return EXCEPTION;
		}
	}
	/**
	 * 商品上架
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/shelf.do")
	@ResponseBody
	public String shelf(String id, HttpServletResponse response)
			throws Exception {
		if(StringUtils.isEmpty(id)){
			return "参数异常";
		}
		Boolean rest = executeShelf(id,"1");

		try {
			if(rest == null || !rest){
				return FAIL;
			}else if (rest) {
				return SUCCESS;
			} else {
				return EXCEPTION;
			}
		} catch (Exception e) {
			logException(e);
			return EXCEPTION;
		}
	}

	/**
	 * 修改上架/下架
	 * @param id
	 * @return
	 */
	private Boolean executeShelf(String id, String status) {
		//修改下架状态
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setId(id);
		goodsInfo.setStatus(status);
		String sendData = new Gson().toJson(goodsInfo);
		WebTarget targetw = productServiceUri.path("modify");

		Response resw = targetw.request().put(Entity.entity(goodsInfo,MediaType.APPLICATION_XML));
		return resw.readEntity(Boolean.class);
	}

	/**
	 * 删除
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public String delete(String id, HttpServletResponse response,HttpServletRequest request)
			throws Exception {
		if(StringUtils.isEmpty(id)){
			return "参数异常";
		}
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setId(id);

		//删除前检查
		String sendData = new Gson().toJson(goodsInfo);
		WebTarget targetw = productServiceUri.path("getModel");

		Response resw = targetw.request().post(Entity.entity(goodsInfo,MediaType.APPLICATION_XML));
		GoodsInfo rest = resw.readEntity(GoodsInfo.class);
		if(rest != null && "2".equals(rest.getStatus())){
			return "请先下架商品！";
		}

		WebTarget target = productServiceUri.path("delete/" + id);
		try {
			Response res = target.request().delete();
			String value = res.readEntity(String.class);
			res.close();
			if ("true".equals(value)) {
				String prefix = PropertiesUtil.readValue("head.prefix");    //资源路径
				String path = request.getSession().getServletContext().getRealPath(prefix);
				//执行删除图片文件
				new Thread(new DelePhoto(path));

				return SUCCESS;
			} else if ("false".equals(value)) {
				return FAIL;
			} else {
				return "false";
			}
		} catch (Exception e) {
			logException(e);
			return EXCEPTION;
		}
	}

	/**
	 * 商品是否下单判断
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/confirmOrder.do")
	@ResponseBody
	public String confirmOrder(HttpServletResponse response,HttpServletRequest request){

		return null;
	}

	/**
	 * 删除商品图片
	 */
	class DelePhoto implements Runnable{
		private String url;
		public DelePhoto(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			File file = new File(url);
			if(file != null && file.exists()){
				file.delete();
			}
		}
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

	//保存图片
	private void savePhoto(MultipartFile photoUrl, HttpServletRequest request, GoodsInfo goodsInfo) throws IOException {
		//保存图片
		if (photoUrl != null && photoUrl.getSize() > 0 && photoUrl.getName() != null) {
			String name = photoUrl.getOriginalFilename();
			String subffix = name.substring(name.lastIndexOf("."), name.length());
			String filePath = UUID.randomUUID().toString() + subffix;
			String path = request.getSession().getServletContext().getRealPath(prefix);
			File localFile = new File(path + filePath);
			photoUrl.transferTo(localFile);
			goodsInfo.setPhotoUrl(prefix + filePath);
		}
	}
	
}
