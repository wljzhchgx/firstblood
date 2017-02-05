/**
 * 
 */
package cn.net.firstblood.web.api.wechat;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.net.firstblood.biz.cmd.CmdFacade;
import cn.net.firstblood.biz.model.InputMessage;
import cn.net.firstblood.biz.model.OutputMessage;
import cn.net.firstblood.framework.enums.CmdType;
import cn.net.firstblood.framework.util.LoggerUtil;
import cn.net.firstblood.framework.util.SerializeXmlUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author gangxiang.chengx
 *
 */
@Controller("wechatIndex")
public class Index {
//	@Autowired
	private CmdFacade	cmdFacade;
	
	@RequestMapping(value="/api/wechat/index.htm",method = RequestMethod.GET)
    public String executeToken(HttpServletRequest request,ModelMap model) {
		model.put("echostr", request.getParameter("echostr"));
		return "/api/wechat/index";
	}
	
	@RequestMapping(value="/api/wechat/index.htm",method = RequestMethod.POST)
    public String execute(HttpServletRequest request,ModelMap model,HttpServletResponse response) throws IOException {
		// 处理接收消息  
        ServletInputStream in = request.getInputStream();  
        // 将POST流转换为XStream对象  
        XStream xs = SerializeXmlUtil.createXstream();  
        xs.processAnnotations(InputMessage.class);  
        xs.processAnnotations(OutputMessage.class);  
        // 将指定节点下的xml节点数据映射为对象  
        xs.alias("xml", InputMessage.class);  
        // 将流转换为字符串  
        StringBuilder xmlMsg = new StringBuilder();  
        byte[] b = new byte[4096];  
        for (int n; (n = in.read(b)) != -1;) {  
            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
        }  
        // 将xml内容转换为InputMessage对象  
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());  
        // 取得消息类型  
        LoggerUtil.COMMON.info(LoggerUtil.object4Log(inputMsg));
        
        String servername = inputMsg.getToUserName();// 服务端  
        String custermname = inputMsg.getFromUserName();// 客户端  
        long createTime = inputMsg.getCreateTime();// 接收时间  
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间 
        String msgType = inputMsg.getMsgType(); 
        
     // 文本消息  
        System.out.println("开发者微信号：" + inputMsg.getToUserName());  
        System.out.println("发送方帐号：" + inputMsg.getFromUserName());  
        System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));  
        System.out.println("消息内容：" + inputMsg.getContent());  
        System.out.println("消息Id：" + inputMsg.getMsgId());  

        StringBuffer str = new StringBuffer();  
        str.append("<xml>");  
        str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");  
        str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");  
        str.append("<CreateTime>" + returnTime + "</CreateTime>");  
        str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");  
        str.append("<Content><![CDATA["+cmdFacade.exeCmd(CmdType.getByKey(inputMsg.getContent()))+"]]></Content>");  
        str.append("</xml>");  
        LoggerUtil.COMMON.info(str.toString());
        //response.getWriter().write(str.toString());
        model.put("result", str.toString());
        return "/api/wechat/message";
	}
}
