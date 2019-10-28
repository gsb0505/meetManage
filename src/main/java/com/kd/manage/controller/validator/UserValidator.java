package com.kd.manage.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kd.manage.entity.UserInfo;
/**
 * 前台用户信息验证
 * @author kd003
 *
 */
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserInfo.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		UserInfo user = (UserInfo) obj;
		if (null == user.getUserId() || "".equals(user.getUserId())) {
			errors.rejectValue("userId", null, "v:用户名不能为空");
		}
		ValidationUtils.rejectIfEmpty(errors, "loginPSW", null,
				"v:用户密码不能为空!");
		/*ValidationUtils.rejectIfEmpty(errors, "userName", null,
				"validator:真实姓名不能为空!");*/
		ValidationUtils.rejectIfEmpty(errors, "email", null,
				"v:用户email不能为空!");
		ValidationUtils.rejectIfEmpty(errors, "phone", null,
				"v:用户电话不能为空!");
	}

}
