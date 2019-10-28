/**
*
 * @package:com.kd.manage.controller.permisson
 * @projectName:cloud-manage
 * @CreateTime:2015年1月15日-上午10:33:28
 *  2015杭州宽达信息技术有限公司-版权所有
 *
 */
package com.kd.manage.controller.permisson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyWebTarget;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;










import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseClient;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.NumberUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.Menu;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.Role;
//import com.kd.common.unit.util.TemporaryUtils;

/**
 *角色权限
 * @Class:RoleController
 * @Autor:glt
 * @CreateTime:2015年1月15日 上午10:33:28
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/pages/role")
public class RoleController extends BaseController{
	private static WebTarget rsu;
	static {
		rsu = webTarget.get(BaseUri.rolesServerUri);
	}
	/**
	 * 
	 * 获取角色列表
	 * @param request
	 * @param response
	 * @return
	 *String
	 * @exception
	 * @since  1.0.0
	 */
	@RequestMapping(value = "/tolist.do")
	public String toRoleList(HttpServletRequest request,HttpServletResponse response){
		return "role/permission_list";
	}
	
	@RequestMapping(value = "/list.do")
	public void roleList(HttpServletRequest request,HttpServletResponse response){
		WebTarget target = rsu.path("query").queryParam("queryType", 1);
		List<Role> roleList = target.request().get(new GenericType<List<Role>>() {});
		PageCount pform = new PageCount(); 
		pform.setCurrentPage(1);
//		pform.setRecords(roleList.size());
//		pform.setPageCount(roleList.size()/10+1);
		pform.setRows(roleList);
		JSONObject j = new JSONObject(pform);
		try {
			PrintWriter out = response.getWriter();
			out.print(j);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/permission.do")
	public String addPermission(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("id", request.getParameter("id"));
		System.out.println(request.getParameter("id"));
		return "role/permission";
	}
	/**
	 * 
	 * allLibraryById(角色所拥有的权限)
	 * @param request
	 * @param response
	 * @return
	 *List<LibraryVo>
	 * @exception
	 * @since  1.0.0
	 */
	public List<Menu> allLibraryById(HttpServletRequest request,HttpServletResponse response){
		String role_id = request.getParameter("role_id");
		String typeId =  request.getParameter("typeId");
		/*if(TemporaryUtils.isEmpty(role_id)){
			role_id = "null";
		}*/
		if(null==role_id||"".equals(role_id)){
			role_id = "null";
		}
		WebTarget target=rsu.path("listByRoleId/"+role_id+"/"+typeId);
		//WebTarget target = client.target();
		List<Menu> roleList = target.request().get(new GenericType<List<Menu>>() {});
		return roleList;
	}
	
	/**
	 *@描述：
	 *@创建时间：2015年3月17日 下午5:16:25
	 *@修改人：
	 *@修改时间：
	 *@修改描述：
	 */
	@RequestMapping(value = "/allLibrary.do")
	public void allLibrary(HttpServletRequest request,HttpServletResponse response){
		String typeId =  request.getParameter("typeId");
		String roleId =  request.getParameter("roleId");
		WebTarget target = rsu.path("list/"+typeId+"/"+roleId);
//		List<Menu> roleListChecked = allLibraryById(request,response);
		//List<String> checkIdList = new ArrayList<String>();
		List<Menu> roleList = target.request().get(new GenericType<List<Menu>>() {});
		 //List<Object> listZTree = new ArrayList<Object>();
//		for(Menu libraryVo:roleList){
//			String id = libraryVo.getId();
//			String btnId = libraryVo.getBtnId();
//			for(Menu libraryVoChecked:roleListChecked){
//				if(libraryVoChecked.getId().equals(id)){
//					checkIdList.add(id);
//				}
//				String strBtnId = libraryVoChecked.getBtnId();
//				if(null!=strBtnId){
//					for(String bId:strBtnId.split("\\|")){
//						if(bId.equals(btnId)){
//							checkIdList.add(btnId+"a");
//						}
//					}
//				}
//			}
//			if(checkIdList.contains(id)){
//				listZTree.add("{id:'"+libraryVo.getId() + "',pId:'" +libraryVo.getpId() +"', name:\""+libraryVo.getName()+"\",checked:true, open:true }");
//			}else{
//				listZTree.add("{id:'"+libraryVo.getId() + "',pId:'" +libraryVo.getpId() +"', name:\""+libraryVo.getName()+"\", open:true }");
//			}
//		}
		
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(roleList);
		 String  jsonString = json.toString();
		 System.out.println(jsonString);
		try {
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateLibrary.do", method = RequestMethod.POST)
	public void updateLibrary(HttpServletRequest request,HttpServletResponse rep){
		WebTarget target = null;
		boolean confirm=false;
		String strCheckedNodes = request.getParameter("checkedNodes");
		System.out.println(strCheckedNodes);
		String[] checkedNodes = null;
		if(strCheckedNodes.length()>1){
			checkedNodes =  (strCheckedNodes.substring(0, strCheckedNodes.length()-1)).split(",");
			Map<String,String> menuMap = new HashMap<String,String>();
			
			for(String s:checkedNodes){
				String menuId = s.split("#")[0];
				String btnValue = s.split("#")[1];
				String isbtn = s.split("#")[2];
//				if (!"null".equals(btnValue)) {
				if(isbtn.equals("1")){
//					menuMap.put(menuId, menuMap.get(menuId)+"|"+btnValue.substring(0, btnValue.length()));
					menuMap.put(btnValue, menuMap.get(btnValue)+"|"+menuId.substring(0, menuId.length()));
				}else{
					menuMap.put(menuId, btnValue.substring(0, btnValue.length()));
				}
//				}
			}
			Iterator iter = menuMap.keySet().iterator();
			StringBuffer lastStr = new StringBuffer();
			while (iter.hasNext()) {
				Object key = iter.next();
				Object val = menuMap.get(key);
				lastStr.append(key+"#"+val+",");
			}
			checkedNodes = lastStr.subSequence(0, lastStr.length()-1).toString().split(",");
		}else{
			checkedNodes = new String[0];
		}
		
		String role_id = request.getParameter("role_id");
		String typeId =  request.getParameter("typeId");
		request.setAttribute("id", role_id);
		try{
			target=rsu.path("deleteLibrary/"+role_id+"/"+typeId);
			Response response1 = target.request().delete();
			if(response1.getStatus()==200){
				for(int i = 0;i<checkedNodes.length;i++){
					if(checkedNodes.length >0&&null!=checkedNodes){
						Menu libraryVo = new Menu();
						libraryVo.setRoleId(role_id);
						libraryVo.setpId(String.valueOf(checkedNodes[i].split("#")[1]));
						String menuId = String.valueOf(checkedNodes[i].split("#")[0]);
						System.out.println("pid "+String.valueOf(checkedNodes[i].split("#")[1])+"  "+String.valueOf(checkedNodes[i].split("#")[0]));
						if(!NumberUtil.isNumeric(checkedNodes[i].split("#")[1])){
							libraryVo.setBtnId(checkedNodes[i].split("#")[1]);
						}
						if(null!=menuId&&!"null".equals(menuId)&&menuId.length()>0){
							libraryVo.setId(menuId);
						}
						libraryVo.setTypeId(typeId);
						target = rsu.path("addAndUpdate");
						Response response = target.request().buildPost(Entity.entity(libraryVo, MediaType.APPLICATION_XML)).invoke();
						response.close();
					}
				}
			}
			response1.close();
			confirm=true;
		}catch(Exception e){
			e.printStackTrace();
			confirm=false;
		}
		try {
			PrintWriter out = rep.getWriter();
			out.print(confirm);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return "role/permission";
	}
}
