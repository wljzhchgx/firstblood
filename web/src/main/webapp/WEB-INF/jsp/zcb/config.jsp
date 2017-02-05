<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.net.firstblood.framework.notifier.model.WeChatIMConfPO"%>
<div id="config">
	${result}
	<br/>
	qqReceive:${qqReceive}
	<br/>
	weChatReceive:${weChatReceive}
	<br/>
	<h2>微信配置</h2>
	<form name="" action="/zcb/action/doUpdateWeChatConfig.do" method="post">
		<% WeChatIMConfPO weChatIMConfig = (WeChatIMConfPO)request.getAttribute("weChatIMConfig"); %>
		cookie:<input type="text" name="cookie" value="<%=weChatIMConfig.getCookie()%>" style=""><br/>
		sid:<input type="text" name="sid" value="<%=weChatIMConfig.getSid()%>" style=""><br/>
		skey:<input type="text" name="skey" value="<%=weChatIMConfig.getSkey()%>" style=""><br/>
		passTicket:<input type="text" name="passTicket" value="<%=weChatIMConfig.getPassTicket()%>" style=""><br/>
		fromName:<input type="text" name="fromName" value="<%=weChatIMConfig.getFromName()%>" style=""><br/>
		toName:<input type="text" name="toName" value="<%=weChatIMConfig.getToName()%>" style=""><br/>
		receiveAddr:<input type="text" name="receiveAddr" value="<%=weChatIMConfig.getReceiveAddr()%>" style=""><br/>
		<input type="submit"  name="" value="提 交">
	</form>
</div>