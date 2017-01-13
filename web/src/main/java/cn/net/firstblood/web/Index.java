/**
 * 
 */
package cn.net.firstblood.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.net.firstblood.dal.dao.RecordDao;
import cn.net.firstblood.dal.enums.RecordType;
import cn.net.firstblood.dal.model.RecordDO;
import cn.net.firstblood.dal.param.RecordParam;
import cn.net.firstblood.framework.util.DateUtil;

/**
 * @author gangxiang.chengx
 *
 */
@Controller
public class Index {
	@Autowired
	private RecordDao recordDao;
	
	@RequestMapping("/index.htm")
    public String execute(HttpServletRequest request,ModelMap model) {
		System.out.println(request.getParameter("url"));
		return "/index";
	}
}
