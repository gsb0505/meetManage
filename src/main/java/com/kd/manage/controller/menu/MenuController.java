package com.kd.manage.controller.menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kd.common.unit.util.TemporaryUtils;
import com.kd.core.dto.ButtonDto;
import com.kd.core.dto.MenuDto;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.PropertiesUtil;

/**
 *
 *@类名称：MenuAction.java
 *@类描述：后台菜单

 *@创建时间：2015年2月6日-下午3:27:02
 *@修改备注:
 *@version 
 */
@Controller
@RequestMapping("userMenu")
public class MenuController extends BaseController{
	private static WebTarget msu;
	private static WebTarget bsu;
	
	static {
		/*menuServerUri = PropertiesUtil.readValue("menuServerUri");
		buttonServerUri = PropertiesUtil.readValue("buttonServerUri");*/
		
		msu = webTarget.get(BaseUri.menuServerUri);
		bsu = webTarget.get(BaseUri.buttonServerUri);
	}
	
	/**
	 *@描述:返回一级菜单以及把该角色的所有菜单都放到session里面
	 *@创建时间：2015年2月6日 下午3:25:59
	 *@修改人：
	 *@修改时间：
	 *@修改描述：
	 *
	 * @param roleId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("queryOneLevel.do")
	public void queryOneLevel( String roleId,String userId,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		WebTarget target = msu.path("queryAllLevel").queryParam("roleId", roleId).queryParam("userId", userId);
		List<MenuDto> list = target.request().get(new GenericType<List<MenuDto>>(){});
		request.getSession().setAttribute("list", list);
		List <MenuDto> oneList = new ArrayList<MenuDto>();
		for(MenuDto m:list){
			String pId = m.getParentMenuId();
			if("1".equals(pId)){
				oneList.add(m);
			}
		}
		PrintWriter out = response.getWriter();
		JSONArray json = JSONArray.fromObject(oneList) ;
		out.print(json);
		out.flush();
		out.close();
	}
	
	
	/**
	 *@描述：显示二级和三级菜单
	 *@创建时间：2015年2月6日 下午3:27:28
	 *@修改人：
	 *@修改时间：
	 *@修改描述：
	 *
	 * @param roleId
	 * @param menuId
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="queryOtherLevel.do")
	public void queryOtherLevel( String roleId, String menuId,HttpServletResponse response,HttpServletRequest request) throws Exception{
		WebTarget targetButton = bsu.path("queryAllButton");
		//WebTarget targetButton = client.target(buttonServerUri+"queryButton?menuId="+menuId+"&roleId="+roleId);
		List<ButtonDto> listButton = targetButton.request().get(new GenericType<List<ButtonDto>>(){});
		@SuppressWarnings("unchecked")
		List<MenuDto> list = (List<MenuDto>) request.getSession().getAttribute("list");
		List <MenuDto> twoList = new ArrayList<MenuDto>();
		for(MenuDto m:list){
			String pId = m.getParentMenuId();
			if(menuId.equals(pId)){
				twoList.add(m);
			}
			
		}
		/*得到三级菜单 */
		List <MenuDto> threeList = new ArrayList<MenuDto>();
		for(MenuDto m1:list){
			for(MenuDto m2:twoList){
				if(m2.getMenuId().equals(m1.getParentMenuId())){
					threeList.add(m1);
				}
			}
		}
		
		/*得到该角色三级菜单下的所有按钮 */
		Map <String,List<ButtonDto>> btmap = new HashMap<String,List<ButtonDto>>();
		for(MenuDto dto:threeList){
			String btnId = dto.getBtnId();
			if(TemporaryUtils.isEmpty(btnId)){
				btnId ="";
			}
			String [] buttons = btnId.split("\\|");
			List<ButtonDto> btns = new ArrayList<ButtonDto>();
			for(int i=0;i<buttons.length;i++){
				for(ButtonDto bDto:listButton){
					
					if(buttons[i].equals(bDto.getBtnId())){
						btns.add( bDto);
						break;
					}
				}
		}
			btmap.put(dto.getMenuId(),btns);
		}
		request.getSession().setAttribute("btmap", btmap);
		twoList.addAll(threeList);
		PrintWriter out = response.getWriter();
		
		JSONArray json = JSONArray.fromObject(twoList);
		out.print(json);
	}
	
	/**
	 *@描述：用户增加常用功能
	 *@创建时间：2015年3月16日 下午5:18:32
	 *@修改人：
	 *@修改时间：
	 *@修改描述：
	 */
	@RequestMapping(value="addFavorite.do", method = RequestMethod.POST)
	public String addFavorite(MenuDto menuDto,HttpServletResponse response,HttpServletRequest request) throws IOException{
//		String sendData = new Gson().toJson(menuDto);
		PrintWriter out = response.getWriter();
		try {
			WebTarget target = msu.path("addFavorite");
			target.request().post(Entity.entity(menuDto, MediaType.APPLICATION_XML));
			out.print(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		finally{
			out.flush();
			out.close();
		}		
		return SUCCESS;
	}
	
	/**
	 *@描述：用户删除常用功能
	 *@创建时间：2015年3月16日 下午5:18:32
	 *@修改人：
	 *@修改时间：
	 *@修改描述：
	 */
	@RequestMapping(value="delFavorite.do", method = RequestMethod.POST)
	public void delFavorite(MenuDto menuDto,HttpServletResponse response,HttpServletRequest request) throws IOException{
//		String sendData = new Gson().toJson(menuDto);
		PrintWriter out = response.getWriter();
		try {
			WebTarget target = msu.path("delFavorite").queryParam("menuId", menuDto.getMenuId()).queryParam("userId", menuDto.getUserId());
			target.request().delete();
			out.print(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			out.flush();
			out.close();
		}		
	}
	

}
