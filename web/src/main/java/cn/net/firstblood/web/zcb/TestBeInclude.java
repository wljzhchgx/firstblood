/**
 * 
 */
package cn.net.firstblood.web.zcb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gangxiang.chengx
 *
 */
@Controller
public class TestBeInclude {

	
	@RequestMapping("/zcb/testBeInclude.htm")
    public String execute(HttpServletRequest request,ModelMap model) {
		System.out.println(request.getParameter("url"));
		model.put("result", "aa");
		return "/zcb/testBeInclude";
	}
	

}
