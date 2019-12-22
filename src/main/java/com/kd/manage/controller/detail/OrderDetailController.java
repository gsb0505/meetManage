package com.kd.manage.controller.detail;

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

import com.kd.manage.entity.GoodsDetail;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.ManageUtil;
import com.kd.manage.entity.MeetRoom;
import com.kd.manage.entity.OrderDetail;
import com.kd.manage.entity.PageCount;
import com.kd.manage.util.Mail;

/**
 * 订单明细-Action
 *
 * @author zlm
 */
@Controller
@RequestMapping("orderDetailAction")
public class OrderDetailController extends BaseController {
    private static WebTarget odsu;
    private static WebTarget usu;

    static {
        odsu = BaseUri.webTarget.get(BaseUri.orderDetailServerUri);
        usu = BaseUri.webTarget.get(BaseUri.meetRoomServiceUri);
    }

    /**
     * 终端名称唯一验证
     */
    @RequestMapping(value = "/isUnique.do", method = {RequestMethod.POST,RequestMethod.GET},consumes="application/json")
    public void isUnique(@RequestParam(value = "meetDate",required = false) String meetDate, @RequestParam(value ="meetStartTime",required = false)String meetStartTime,
                         @RequestParam(value ="meetEndTime",required = false)String meetEndTime, @RequestParam(value ="meetRoomID",required = false)String meetRoomID,
                         @RequestParam(value ="glideNo",required = false)String glideNo,
                         HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        try {
            if (meetDate == null || meetStartTime == null
                    || meetEndTime == null || meetRoomID == null) {
                out.print(true);
                return;
            }
            OrderDetail odto = new OrderDetail();
            odto.setMeetDate(meetDate);
            odto.setMeetStartTime(meetStartTime);
            odto.setMeetEndTime(meetEndTime);
            odto.setMeetRoomID(meetRoomID);
            String sendData = new Gson().toJson(odto);
            WebTarget target = odsu.path("query").queryParam("orderDetail",
                    URLEncoder.encode(sendData, "utf-8"));
            List<OrderDetail> odList = target.request().get(
                    new GenericType<List<OrderDetail>>() {
                    });
            boolean flag = false;
            if (odList.size() != 0) {
                // out.print(false);
                if (glideNo == null) {
                    for (OrderDetail detail : odList) {
                        if (detail.getErrCode() == 3) {
                            flag = true;
                        } else {
                            flag = false;
                            break;
                        }
                    }
                    if (flag == true) {
                        out.print(true);
                    } else {
                        out.print(false);
                    }

                } else {
                    for (OrderDetail detail : odList) {
                        if (detail.getGlideNo().equals(glideNo) && detail.getErrCode() != 3) {
                            flag = true;

                        } else {
                            flag = false;
                            break;
                        }
                    }
                    if (flag == true) {
                        out.print(true);
                    } else {
                        out.print(false);
                    }

                }
            } else {
                out.print(true);

            }
        } catch (Exception e) {
            logException(e);
            out.print(EXCEPTION);
        } finally {
            out.flush();
            out.close();
        }

    }

    public static CellStyle createCellStyle(HSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框

        return cellStyle;
    }

    public static CellStyle createExportStyle(HSSFWorkbook workbook) {
        CellStyle cellStyle_title = workbook.createCellStyle();
        Font font_title = workbook.createFont();
        font_title.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle_title.setFont(font_title);
        cellStyle_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        return cellStyle_title;
    }

    /**
     * 添加视图跳转
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addView.do")
    public String addView(Model model) throws Exception {
        WebTarget target = usu.path("queryList");

        List<MeetRoom> meetRoomList = target.request().get(
                new GenericType<List<MeetRoom>>() {
                });

        model.addAttribute("meetRoomList", meetRoomList);

        return "detail/order/add";
    }

    /**
     * 添加
     *
     * @throws IOException
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public void add(@RequestBody OrderDetail dto, HttpServletResponse response,
                    HttpServletRequest request) throws IOException {
        try {
            WebTarget target = odsu.path("add");

            String user = this.getUserId(request);
            dto.setCreator(user);
            dto.setErrCode(1);
            Response responses = target.request()
                    .buildPost(Entity.entity(dto, MediaType.APPLICATION_XML))
                    .invoke();
            String value = responses.readEntity(String.class);
            responses.close();
            if ("true".equals(value)) {
                out(response,SUCCESS);
            } else if ("false".equals(value)) {
                out(response,FAIL);
            } else {
                out(response,EXCEPTION);
            }
        } catch (Exception e) {
            logException(e);
            out(response,EXCEPTION);
        }
    }

    /**
     * @描述:预定列表
     */
    @RequestMapping(value = "/list.do")
    public void List(OrderDetail dto, PageCount pageCount,
                     HttpServletResponse res) throws Exception {
        dto.setPageCount(pageCount);
        String sendData = new Gson().toJson(dto);
        WebTarget target = odsu.path("queryCheck").queryParam("orderDetail",
                URLEncoder.encode(sendData, "utf-8"));
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
     *
     * @param menuId
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listView.do")
    public String ListView(String menuId, Model model,
                           HttpServletRequest request) throws Exception {
        sendListCommon(menuId, model, request);
        WebTarget target = usu.path("queryList");

        List<MeetRoom> meetRoomList = target.request().get(
                new GenericType<List<MeetRoom>>() {
                });
        model.addAttribute("meetRoomList", meetRoomList);
        return "detail/order/list";
    }

    /**
     * @描述:门店审核
     */
    @RequestMapping(value = "/checkList.do")
    public void checkList(OrderDetail dto, PageCount pageCount,
                          HttpServletResponse res) throws Exception {
        dto.setPageCount(pageCount);
        dto.setErrCode(1);
        String sendData = new Gson().toJson(dto);
        WebTarget target = odsu.path("queryCheck").queryParam("orderDetail",
                URLEncoder.encode(sendData, "utf-8"));
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
     *
     * @param menuId
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkListView.do")
    public String checkListView(String menuId, Model model,
                                HttpServletRequest request) throws Exception {
        sendListCommon(menuId, model, request);
        WebTarget target = usu.path("queryList");

        List<MeetRoom> meetRoomList = target.request().get(
                new GenericType<List<MeetRoom>>() {
                });
        model.addAttribute("meetRoomList", meetRoomList);
        return "detail/order/checkList";
    }

    /**
     * @描述：跳转到审核页面
     */
    @RequestMapping(value = "/checkView.do")
    public String checkView(String ordId, Model model) throws Exception {
        WebTarget target = odsu.path("toCheck" + "/" + ordId);
        Response res = target.request().get();
        OrderDetail orderDetail = res.readEntity(OrderDetail.class);
        model.addAttribute("orderDetail", orderDetail);// 商户对象
        return "detail/order/check";
    }

    /**
     * @描述:商户审核
     */
    @RequestMapping(value = "/check.do", method = RequestMethod.POST)
    public void check(OrderDetail dto, HttpServletResponse response,
                      HttpServletRequest request) throws Exception {
        PrintWriter out = response.getWriter();

        WebTarget target = odsu.path("check").queryParam("glideNo",
                dto.getGlideNo());
        try {
            Response res = target.request().get();

            String value = res.readEntity(String.class);
            res.close();
            if ("true".equals(value)) {
                if (dto.getEmailNotification() != null) {
                    Mail.respMeetNoticeEmail(dto.getEmailNotification(),
                            dto.getMeetName(),
                            dto.getMeetDate() + ' ' + dto.getMeetStartTime(),
                            dto.getMeetRoomName(), dto.getCreator());
                }
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

    /**
     * 注销用户
     *
     * @throws Exception
     */
    @RequestMapping(value = "/logOut.do")
    public void logOut(String glideNo, HttpServletResponse response,
                       HttpServletRequest request) throws Exception {

        OrderDetail orderDetail = new OrderDetail();
        String getId = (String) request.getSession().getAttribute("userId");
        orderDetail.setAuditor(getId);
        orderDetail.setGlideNo(glideNo);
        orderDetail.setErrCode(3);
        PrintWriter out = response.getWriter();

        try {
            WebTarget target = odsu.path("modify");
            Response responses = target
                    .request()
                    .buildPost(
                            Entity.entity(orderDetail,
                                    MediaType.APPLICATION_XML)).invoke();
            String value = responses.readEntity(String.class);
            responses.close();
            if ("true".equals(value)) {

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

    /**
     * 修改视图跳转
     *
     * @param ordId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyView.do")
    public String modifyView(String ordId, Model model) throws Exception {
        WebTarget target = odsu.path("toCheck" + "/" + ordId);
        Response res = target.request().get();
        OrderDetail orderDetail = res.readEntity(OrderDetail.class);
        res.close();
        target = usu.path("queryList");

        List<MeetRoom> meetRoomList = target.request().get(
                new GenericType<List<MeetRoom>>() {
                });

        model.addAttribute("meetRoomList", meetRoomList);
        model.addAttribute("orderDetail", orderDetail);// 商户对象
        return "detail/order/modify";
    }

    /**
     * 修改
     *
     * @param orderDetail
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public void modify(OrderDetail orderDetail, HttpServletResponse response,
                       HttpServletRequest request, String model) throws Exception {
        String getId = (String) request.getSession().getAttribute("userId");
        PrintWriter out = response.getWriter();

        try {
            if (orderDetail == null
                    || StringUtils.isEmpty(orderDetail.getMeetName())
                    || StringUtils.isEmpty(orderDetail.getMeetDate())
                    || StringUtils.isEmpty(orderDetail.getMeetEndTime())
                    || StringUtils.isEmpty(orderDetail.getMeetStartTime())
                    || StringUtils.isEmpty(orderDetail.getMeetRoomID())) {
                out.print(FAIL);
                return;
            }
            orderDetail.setAuditor(getId);
            orderDetail.setErrCode(1);
            WebTarget target = odsu.path("modify");
            Response responses = target
                    .request()
                    .buildPost(
                            Entity.entity(orderDetail,
                                    MediaType.APPLICATION_XML)).invoke();
            String value = responses.readEntity(String.class);
            responses.close();
            if ("true".equals(value)) {

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

}