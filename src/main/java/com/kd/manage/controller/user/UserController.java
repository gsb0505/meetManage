package com.kd.manage.controller.user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.common.unit.util.EncryptUtils;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.controller.validator.UserValidator;
import com.kd.manage.entity.Config;
import com.kd.manage.entity.Department;
import com.kd.manage.entity.Organization;
import com.kd.manage.entity.PageCount;
import com.kd.manage.entity.UserInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @类名称：UserAction.java
 * @类描述：用户维护
 * @创建时间：2015年1月26日-上午9:56:33
 * @修改备注:
 */

@Controller
@RequestMapping("/userAction")
public class UserController extends BaseController {

    private static WebTarget usu;
    private static WebTarget orgTarget;
    private static String jobServiceUrl = "";
    private static String departmentServiceUrl = "";
    //资源路径
    private final static String prefix = PropertiesUtil.readValue("head.prefix");
    /**
     * 获取密码错误次数，判断是否锁定用户
     */
    private static String errNum = "";

    static {
        errNum = PropertiesUtil.readValue("configuri.LoginAction.queryById");

        usu = BaseUri.webTarget.get(BaseUri.userServerUri);
        orgTarget = BaseUri.webTarget.get(BaseUri.organizationServiceUrl).path("getAllOrg");
        jobServiceUrl = BaseUri.jobServiceUrl;
        departmentServiceUrl = BaseUri.departmentServiceUrl;
    }

    /**
     * 注册的验证器
     *
     * @param binder
     */
    @InitBinder("userInfo")
    public void initBinder(WebDataBinder binder) {
        // 添加一个日期类型编辑器，也就是需要日期类型的时候，怎么把字符串转化为日期类型
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
        // 添加一个spring自带的validator
        binder.setValidator(new UserValidator());
    }

    /**
     * 用户维护主页！跳转到主页以后自动查询并显示所有用户信息，可根据用户名userName和状态flag进行查询
     *
     * @param res
     * @throws Exception
     */
    @RequestMapping(value = "/list.do")
    public void viewList(UserInfo userInfo, PageCount pageCount,
                         HttpServletResponse res) throws Exception {
        userInfo.setPageCount(pageCount);
        String sendData = new Gson().toJson(userInfo);
        WebTarget target = usu.path("query").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
        List<UserInfo> userList = target.request().get(
                new GenericType<List<UserInfo>>() {
                });

        WebTarget tar_config = csu.path("queryByCode").queryParam("code", UserController.errNum); // 配置密码输入错误次数-system_config表
        Config config = tar_config.request().get(new GenericType<Config>() {
        });
        // 用户密码错误状态
        for (UserInfo a : userList) {
            if (config == null) {
                a.setErrNum("未知");
            } else {
                if (null == a.getErrNum() || a.getErrNum().equals("0")) {
                    a.setErrNum("未锁");
                } else {
                    int errNum = Integer.parseInt(a.getErrNum());
                    int cerrNum = Integer.parseInt(config.getNum());
                    if (errNum > cerrNum) {
                        a.setErrNum("已锁");
                    }
                }
            }
        }

        pageCount = ManageUtil.packPage(userList, pageCount);
        JSONObject json = JSONObject.fromObject(pageCount);// 转化成json对象(jQgrid只能识别json对象)
        PrintWriter out = res.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 在菜单中点击链接以后直接跳转到用户主页！没有进行任何操作！
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listView.do")
    public String sendListView(String menuId, Model model,
                               HttpServletRequest request) throws Exception {
        sendListCommon(menuId, model, request);
        return "user/user/list";
    }

    /**
     * 在主页点击添加以后跳转到添加页面！没有进行任何操作
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addView.do")
    public String addUserView(Model model) throws Exception {
        List<Organization> list4 = orgTarget.request().get(new GenericType<List<Organization>>() {
        });
        model.addAttribute("orgList", list4);
        return "user/user/add";
    }

    /**
     * 跳转到编辑页面！跳转前先去core取到选中用户的具体信息
     * @return
     */
    @RequestMapping(value = "/modifyView.do")
    public String updateUserView(UserInfo userFromView, Model model)
            throws Exception {
        String sendData = new Gson().toJson(userFromView);
        WebTarget target = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
        Response res = target.request().get();
        UserInfo user = res.readEntity(UserInfo.class);
        res.close();
        List<Organization> list1 = orgTarget.request().get(new GenericType<List<Organization>>() {
        });

        model.addAttribute("orgList", list1);

        if (user.getOrgId() != null) {
            WebTarget target2 = client.target(departmentServiceUrl + "queryDepartsByOrg?orgId=" + user.getOrgId());
            List<Department> list2 = target2.request().get(new GenericType<List<Department>>() {
            });
            model.addAttribute("departList", list2);

        }
        model.addAttribute("user", user);
        return "user/user/modify";
    }

    /**
     * 注销用户
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/logOut.do")
    public void logOut(String id, HttpServletResponse response,
                       HttpServletRequest request) throws Exception {
        PrintWriter out = response.getWriter();
        user = new UserInfo();
        user.setId(id);
        // 是否是当前登入用户
        HttpSession session = request.getSession(false);
        // Object obj=session.getAttribute("userId");
        UserInfo userInfo = (UserInfo) session
                .getAttribute(LoginController.CURRENT_USER);

        // if(!(obj!=null&&id.equals(obj.toString()))){
        if (userInfo.getId() != null && id.equals(userInfo.getId())) {
            out.print("current");
            out.flush();
            out.close();
            return;
        }
        String sendData = new Gson().toJson(user);
        WebTarget target = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
        Response res = target.request().get();
        UserInfo user = res.readEntity(UserInfo.class);
        user.setFlag("0");
        try {
            WebTarget tar = usu.path("modify");
            Response r = tar.request().post(
                    Entity.entity(user, MediaType.APPLICATION_XML));
            String value = r.readEntity(String.class);
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
            logException(e);
        } finally {
            out.flush();
            out.close();
        }

    }

    /**
     * 重置密码账户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/reset.do")
    public void reset(String id, HttpServletResponse response) throws Exception {
        user = new UserInfo();
        user.setId(id);
        String sendData = new Gson().toJson(user);
        WebTarget target = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
        Response res = target.request().get();
        UserInfo user = res.readEntity(UserInfo.class);
        EncryptUtils loginE = new EncryptUtils(user.getUserId(), "MD5");
        user.setLoginPSW(loginE.encode("123456"));
        PrintWriter out = response.getWriter();
        try {
            WebTarget tar = usu.path("modify");
            Response r = tar.request().post(Entity.entity(user, MediaType.APPLICATION_XML));
            String value = r.readEntity(String.class);
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
            logException(e);
        } finally {
            out.flush();
            out.close();
        }

    }

    /**
     * 启用账户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/backOut.do")
    public void backOut(String id, HttpServletResponse response)
            throws Exception {
        user = new UserInfo();
        user.setId(id);
        String sendData = new Gson().toJson(user);
        WebTarget target = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
        Response res = target.request().get();
        UserInfo user = res.readEntity(UserInfo.class);
        user.setFlag("1");
        PrintWriter out = response.getWriter();
        try {
            WebTarget tar = usu.path("modify");
            Response r = tar.request().post(
                    Entity.entity(user, MediaType.APPLICATION_XML));
            String value = r.readEntity(String.class);
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
            logException(e);
        } finally {
            out.flush();
            out.close();
        }

    }

    /**
     * 在编辑页面点保存以后跳转到该方法！然后去core执行更新操作
     *
     * @param userInfo
     * @throws Exception
     */
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public void update(UserInfo userInfo, HttpServletResponse response, HttpServletRequest request) throws Exception {
        PrintWriter out = response.getWriter();
        try {
            if (userInfo != null && !StringUtils.isEmpty(userInfo.getLoginPSW())
                    && !userInfo.getLoginPSW().equals("default")) {
                userInfo.setCreator(userInfo.getUserId());
                EncryptUtils loginE = new EncryptUtils(userInfo.getUserId(), "MD5");
                userInfo.setLoginPSW(loginE.encode(userInfo.getLoginPSW()));
            } else {
                String sendData = new Gson().toJson(userInfo);
                WebTarget target = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
                Response res = target.request().get();
                UserInfo user1 = res.readEntity(UserInfo.class);
                userInfo.setLoginPSW(user1.getLoginPSW());
            }
            //savePhoto(photoFile, request, userInfo);

            WebTarget target = usu.path("modify");
            Response res = target.request().post(
                    Entity.entity(userInfo, MediaType.APPLICATION_XML));
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
            logException(e);
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 用户解锁
     *
     * @throws Exception
     */
    @RequestMapping(value = "/jiesuo.do", method = RequestMethod.POST)
    public void jiesuo(String userId, HttpServletResponse response,
                       HttpServletRequest request) throws Exception {
        PrintWriter out = response.getWriter();
        try {
            UserInfo user = new UserInfo();
            user.setErrNum("0");
            user.setUserId(userId);
            WebTarget target = usu.path("modifyError");
            Response res = target.request().post(Entity.entity(user, MediaType.APPLICATION_XML));
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
            logException(e);
        } finally {
            out.flush();
            out.close();
        }

    }

    /**
     * 在添加页面点击保存以后跳转到该方法！然后去core中执行添加操作
     *
     * @param user
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public void add(UserInfo user, HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        PrintWriter out = response.getWriter();

        try {
            String sendData = new Gson().toJson(user);
            WebTarget target_query = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
            Response res = target_query.request().get();
            UserInfo userInfo = res.readEntity(UserInfo.class);
            if (userInfo == null) {
                UserInfo userinfo = (UserInfo) request.getSession()
                        .getAttribute("user");
                user.setCreator(userinfo.getUserId());
                EncryptUtils loginE = new EncryptUtils(user.getUserId(), "MD5");
                user.setLoginPSW(loginE.encode(user.getLoginPSW()));
                //保存头像图片
                //savePhoto(photoUrl, request, user);

                WebTarget target = usu.path("add");
                target.request().post(Entity.entity(user, MediaType.APPLICATION_XML)).close();
                out.print(SUCCESS);
            } else {
                out.print(EXSIT);
            }
        } catch (Exception e) {
            out.print(EXCEPTION);
            logException(e);
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 检查用户名是否被注册
     */
    @RequestMapping(value = "/isUnique.do", method = RequestMethod.POST)
    public void isUnique(UserInfo info, HttpServletRequest request,
                         HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String sendData = new Gson().toJson(info);
            WebTarget target_query = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
            Response res = target_query.request().get();
            UserInfo userInfo = res.readEntity(UserInfo.class);
            if (userInfo == null) {
                out.print(true);
            } else {
                out.print(false);
            }
        } catch (Exception e) {
            logException(e);
            out.print(EXCEPTION);
        } finally {
            out.flush();
            out.close();
        }
    }

    @RequestMapping(value = "/verifyOldPwd.do")
    public void verifyOldPwd(String oldPwd, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        UserInfo userInfo = (UserInfo) session.getAttribute(LoginController.CURRENT_USER);
        EncryptUtils loginE = new EncryptUtils(userInfo.getUserId(), "MD5");
        String inPws = loginE.encode(oldPwd);

        userInfo.setLoginPSW(inPws);

        UserInfo user1 = new UserInfo();
        user1.setUserId(userInfo.getUserId());
        String sendData = new Gson().toJson(user1);
        WebTarget target = usu.path("validUser").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
        Response res = target.request().get();
        UserInfo userRec = res.readEntity(UserInfo.class);
        res.close();
        PrintWriter out = response.getWriter();

        if (userRec != null) {
            if (userRec.getLoginPSW().equals(userInfo.getLoginPSW())) {
                out.print(true);
            } else {
                out.print(false);
            }
        } else {
            out.print(false);
        }
        res.close();
    }


    /**
     * 修改账户密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyPwd.do")
    public void modifyPwd(String newPwd, HttpServletResponse response, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        UserInfo userInfo = (UserInfo) session.getAttribute(LoginController.CURRENT_USER);
        UserInfo user1 = new UserInfo();
        user1.setId(userInfo.getId());
        //user1.setUserId(userInfo.getUserId());
        //String sendData = new Gson().toJson(user1);
        //WebTarget target = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
        //Response res = target.request().get();
        //UserInfo user = res.readEntity(UserInfo.class);
        EncryptUtils loginE = new EncryptUtils(userInfo.getUserId(), "MD5");
        user1.setLoginPSW(loginE.encode(newPwd));
        PrintWriter out = response.getWriter();
        try {
            WebTarget tar = usu.path("modifyByCondition");
            Response r = tar.request().post(Entity.entity(user1, MediaType.APPLICATION_XML));
            String value = r.readEntity(String.class);
            r.close();
            if ("true".equals(value)) {
                out.print(SUCCESS);
            } else if ("false".equals(value)) {
                out.print(FAIL);
            } else {
                out.print(EXCEPTION);
            }
        } catch (Exception e) {
            out.print(EXCEPTION);
            logException(e);
        } finally {
            out.flush();
            out.close();
        }

    }

    /**
     * 部门查询
     *
     * @param orgId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/findDepartsByOrg.do")
    public void findDepartsByOrg(String orgId, HttpServletResponse response) throws Exception {
        if (orgId == null || orgId.equals("")) {
            out(response, null);
            return;
        }
        WebTarget target = client.target(departmentServiceUrl + "queryDepartsByOrg?orgId=" + orgId);
        List<Department> list = target.request().get(new GenericType<List<Department>>() {
        });

        out(response, JSONArray.fromObject(list));
    }

    //保存头像图片
    private void savePhoto(MultipartFile photoUrl, HttpServletRequest request, UserInfo user) throws IOException {
        //保存头像图片
        if (photoUrl != null && photoUrl.getSize() > 0 && photoUrl.getName() != null) {
            String name = photoUrl.getOriginalFilename();
            String subffix = name.substring(name.lastIndexOf("."), name.length());
            String filePath = UUID.randomUUID().toString() + subffix;
            String path = request.getSession().getServletContext().getRealPath(prefix);
            File localFile = new File(path + filePath);
            photoUrl.transferTo(localFile);
            user.setPhotoUrl(prefix + filePath);
        }
    }

}
