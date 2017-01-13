/**
 * 
 */
package cn.net.firstblood.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.net.firstblood.biz.job.CheckZcbJob;

/**
 * @author gangxiang.chengx
 *
 */
@Controller
public class ConfigAction {
	@RequestMapping("/set/config.asp")
    public String doSetConfig(HttpServletRequest request,ModelMap model) {
		//CheckZcbJob.setConfig(request.getParameter("config"));
		model.put("result", "恭喜设置成功");
		return "/result";
	}
}
