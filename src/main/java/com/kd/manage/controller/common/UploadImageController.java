package com.kd.manage.controller.common;

import com.kd.manage.base.BaseController;
import com.kd.manage.enumerate.PathTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/** 文件上传--Controller
 * @author: latham
 * @Date: 2020/1/4 10:33
 **/
@Controller
@RequestMapping
public class UploadImageController extends BaseController {

    @RequestMapping(value = "/transferTo.do",method = RequestMethod.POST)
    @ResponseBody
    public String imageUpload(@RequestParam(value = "type") PathTypeEnum pathTypeEnum,
                              @RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
                              HttpServletRequest request){
        if(pathTypeEnum == null){
            return FAIL;
        }
        String prefix = pathTypeEnum.getPath();

        try {
            String path = savePhoto(photoFile, request,prefix);
            if(path != null){
                return path;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FAIL;
    }

}
