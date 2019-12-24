package com.kd.manage.base;

import com.kd.core.dto.ButtonDto;
import com.kd.manage.controller.user.LoginController;
import com.kd.manage.entity.BaseData;
import com.kd.manage.entity.Config;
import com.kd.manage.entity.UserInfo;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 *
 * @类名称：BaseAction.java @类描述：
 * 
 * @创建时间：2015年1月30日-下午12:42:53
 * @修改备注:
 * @version
 */
public abstract class BaseController {

	protected static final transient Logger log = Logger.getLogger(BaseController.class);
	private HttpServletRequest request;
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String EXSIT = "exsit";
	public static final String EXCEPTION = "exception";
	public static final String windowFrame = "wf_";
	public static final String resultTrue = "true";
	public static final String resultFasle = "false";
	public UserInfo user = null;
	protected Client client = BaseClient.getClient();// 新建客户端对象
	//protected Client client = BaseClient.getJerseyClient();// 新建客户端对象
	
	protected static WebTarget ausu;
	protected static WebTarget csu;
	
	
	@Resource(name = "validator")
	private Validator validator;

	/** "验证结果"参数名称 */
	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";



	static {
		ausu = BaseUri.webTarget.get(BaseUri.agentUserServerUri);
		csu = BaseUri.webTarget.get(BaseUri.configServerUri);
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void sendListCommon(String menuId, Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, List<ButtonDto>> btmap = (Map<String, List<ButtonDto>>) request.getSession().getAttribute("btmap");
		System.out.println(btmap.toString());
		List<ButtonDto> btns = new ArrayList<>();
		//模板窗口按钮
		if(!StringUtils.isEmpty(menuId) && windowFrame.equals(menuId)){
			ButtonDto dto = new ButtonDto();
			ButtonDto dto2 = new ButtonDto();
			dto.setBtnId("okclick");
			dto.setBtnText("确定");
			dto.setBtnCss("r_button r_button08 common_icon");
			dto2.setBtnId("closeclick");
			dto2.setBtnText("取消");
			dto2.setBtnCss("r_button r_button09 common_icon");

			btns.add(dto);
			btns.add(dto2);
		}else {
			//权限按钮
			btns = btmap.get(menuId);
		}
		model.addAttribute("btns", btns);
	}

	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder,HttpServletRequest request) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		this.request = request;
	}

	/**
	 * 数据验证
	 * 
	 * @param target
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Object target, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations,
					RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * 设置CharacterEncoding 与 ContentType 并输出对象
	 * 
	 * @param response
	 * @param json
	 *            输出的对象
	 * @throws IOException
	 */
	protected void out(HttpServletResponse response, Object json) throws IOException {
		//response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out= response.getWriter();
		try {
			if (json != null) {
				out.print(json);
			}
		} finally {
			out.flush();
			out.close();
		}

	}

	protected void logException(Exception ex) {
		try {
			ex.printStackTrace();
			log.error(ex);
			getRequest().setAttribute("msg", ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @描述 ：获取异常字符串中的信息Msg
	 * @创建时间：2015年4月2日 上午9:50:06 @修改人： @修改时间： @修改描述：抛出的异常msg注意点：1 末尾需要以'|'结尾 2
	 *                 调用时只需要传递参数，无需担心是否有异常
	 * @param exceptionName
	 * @param exceptionStr
	 * @return
	 */
	public String getExceptionMsgFromStr(String exceptionName, String exceptionStr) {
		if (exceptionStr.length() < 20) {
			return exceptionStr;
		}
		String ret = "";
		int index = exceptionStr.indexOf(exceptionName + ":");
		if (index > -1) {
			ret = exceptionStr.substring(index + exceptionName.length() + 1, exceptionStr.indexOf("|"));
		}
		ret = ret.trim();
		ret = ret.replace("\\s", "");
		ret = ret.replace("\n", "");
		return ret;
	}

	/**
	 * 根据类型获取数据列表
	 * 
	 * @param type
	 * @return
	 */
	public List<BaseData> getBaseDateList(String type) {
		WebTarget target2 = BaseUri.webTarget.get(BaseUri.commonServerUri).path("query"+ "/" + type);
		List<BaseData> list = target2.request().get(new GenericType<List<BaseData>>() {
		});
		return list;
	}

	/**
	 * 基础数据类型转换
	 * 
	 * @param list
	 * @param type
	 *            key类型
	 * @return
	 */
	public Map<?, String> convertMapBaseData(List<BaseData> list, Class<?> type) {
		Map<Integer, String> jsonObject = null;
		Map<String, String> jsonObject2 = null;

		if (type == Integer.class) {
			jsonObject = new HashMap<Integer, String>();
			for (BaseData data : list) {
				jsonObject.put(Integer.parseInt(data.getCode()), data.getName());
			}
			return jsonObject;
		} else if (type == String.class) {
			jsonObject2 = new HashMap<String, String>();
			for (BaseData data : list) {
				jsonObject2.put(data.getCode(), data.getName());
			}
			return jsonObject2;
		}
		return null;
	}

	public JSONObject convertJsonBaseData(List<BaseData> list) {
		JSONObject jsonObject = new JSONObject();
		for (BaseData data : list) {
			jsonObject.put(data.getCode(), data.getName());
		}
		return jsonObject;
	}

	/**
	 * 根据id获取系统配置
	 * 
	 * @param id
	 * @return
	 */
	public Config getSystemConfig(String id) {
		WebTarget tar_config = BaseUri.webTarget.get(BaseUri.configServerUri).path("queryById").queryParam("id", id);
		Config config = tar_config.request().get(new GenericType<Config>() {});
		return config;
	}

	/**
	 * 根据code获取系统配置
	 * 
	 * @param code
	 * @return
	 */
	public Config getSystemConfigByCode(String code) {
		WebTarget tar_config = BaseUri.webTarget.get(BaseUri.configServerUri).path("queryByCode").queryParam("code", code);
		Config config = tar_config.request().get(new GenericType<Config>() {});
		return config;
	}

	/**
	 * 获取ip地址
	 * 
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param contentType
	 * @param file
	 * @throws Exception
	 */
	public void fileDownLoad(HttpServletRequest request, HttpServletResponse response, String contentType, File file)
			throws Exception {
		String codedFileName = null;
		OutputStream fos = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;

		try {
			if (!file.exists())
				return;
			// 以流的形式下载
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			codedFileName = java.net.URLEncoder.encode(file.getName(), "UTF-8");
			// 清空response
			response.reset();
			response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
			response.addHeader("Content-Length", "" + file.length());
			fos = new BufferedOutputStream(response.getOutputStream());
			fos.write(buffer);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取登入Id
	 * 
	 * @param request
	 * @return
	 */
	public String getUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String ssi = (String) session.getAttribute(LoginController.CURRENT_USER_ID);
		return ssi;
	}

	/**
	 * 根据true、false，输出结果内容
	 * @param out
	 * @param value
	 */
	protected void pinrtWriter(PrintWriter out, String value) {
		if (resultTrue.equals(value)) {
			out.print(SUCCESS);
		} else if (resultFasle.equals(value)) {
			out.print(FAIL);
		} else {
			out.print(EXCEPTION);
		}
	}
	/**
	 * 根据true、false，输出结果内容
	 * @param value
	 */
	protected String result(String value) {
		if (resultTrue.equals(value)) {
			return SUCCESS;
		} else if (resultFasle.equals(value)) {
			return FAIL;
		} else {
			return EXCEPTION;
		}
	}

	protected BtResponse outPutResponse(Integer out, String value){
		return new BtResponse(out, value);
	}
	

}
