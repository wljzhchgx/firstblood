package cn.net.firstblood.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.net.firstblood.dal.dao.ApplicationDao;
import cn.net.firstblood.dal.model.ApplicationDO;
import cn.net.firstblood.framework.util.LoggerUtil;

@Controller
public class UserAction {
	private static final String APPLY_JSP = "/school/apply";
	
	private static final String RESULT_KEY = "result";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAction.class);
	@Autowired
	private ApplicationDao	applicationDao;
	
	@RequestMapping("/plus/form.asp")
    public String doSave(HttpServletRequest request,ModelMap model) {
		LOGGER.info("开始报名");
		LoggerUtil.COMMON.warn("开始报名common");
//		if(getString(request, "KS_Age") != null && !isInteger(getString(request, "KS_Age"))){
//			model.put(RESULT_KEY, "年龄必须为数字");
//			return APPLY_JSP;
//		}
//		
//		ApplicationDO application = buildApplicationDO(request);
//		if(StringUtils.isBlank(application.getName())){
//			model.put(RESULT_KEY, "姓名不能为空");
//			return APPLY_JSP;
//		}
//		if(StringUtils.isBlank(application.getSex())){
//			model.put(RESULT_KEY, "请选择性别");
//			return APPLY_JSP;
//		}
//		if(StringUtils.isBlank(application.getPhone())){
//			model.put(RESULT_KEY, "联系电话不能为空");
//			return APPLY_JSP;
//		}
//		if(StringUtils.isBlank(application.getEmail())){
//			model.put(RESULT_KEY, "电子邮箱不能为空");
//			return APPLY_JSP;
//		}
//		if(StringUtils.isBlank(application.getAddress())){
//			model.put(RESULT_KEY, "联系地址不能为空");
//			return APPLY_JSP;
//		}
//		applicationDao.insert(application);
        model.put(RESULT_KEY, "恭喜您报名成功");
        LOGGER.info("结束报名");
        LoggerUtil.COMMON.warn("结束报名common");
        return APPLY_JSP;
    }
	
	private ApplicationDO buildApplicationDO(HttpServletRequest request){
		ApplicationDO application = new ApplicationDO();
		
		application.setAddress(getString(request,"KS_Address"));
		application.setAge(getInteger(request,"KS_Age"));
		application.setEmail(getString(request,"KS_Email"));
		application.setMessage(getString(request,"KS_Message"));
		application.setName(getString(request,"KS_Name"));
		application.setPhone(getString(request,"KS_Phone"));
		application.setQq(getString(request,"KS_QQ"));
		application.setSex(getString(request,"KS_Gender"));
		
		return application;
	}
	
	private String getString(HttpServletRequest request,String key){
		return request.getParameter(key)==null?null:String.valueOf(request.getParameter(key));
	}
	
	private Integer getInteger(HttpServletRequest request,String key){
		return request.getParameter(key)==null?null:Integer.valueOf(request.getParameter(key).toString());
	}
	
	/**
	 * 是否为数字
	 * @param value
	 * @return
	 */
	private boolean isInteger(String value) {
		 try {
			 Integer.parseInt(value);
			 return true;
		 } catch (NumberFormatException e) {
			 return false;
		 }
	 }
}
