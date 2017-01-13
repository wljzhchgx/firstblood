<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="cn.net.firstblood.biz.model.ZcbPO,java.util.List"%>
<%@ page import="java.util.HashMap"%> 
<%@ page import="java.util.Map"%> 
<%@ page import="java.util.Map.Entry"%>
<%@ page import="cn.net.firstblood.dal.model.RecordDO"%>
<%@ page import="cn.net.firstblood.framework.util.DateUtil"%>
<%@ page import="cn.net.firstblood.dal.enums.RecordType"%>
<%@ page import="cn.net.firstblood.framework.notifier.model.WeChatIMConfPO"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>zcb</title>

		<script type="text/javascript" src="/js/jquery/jquery-1.8.3.min.js"></script>
		<style type="text/css">
jQuery{demo.css}
		</style>
		<script type="text/javascript">
		jQuery(function () {
			jQuery("#config").hide();
			jQuery("#config_btn").click(function(){
				if(jQuery(this).hasClass("show")){
					jQuery("#config").show(1000);
					jQuery(this).removeClass("show");
					jQuery(this).addClass("hide");
					jQuery(this).html("隐藏");
				}else{
					jQuery("#config").hide(1000);
					jQuery(this).removeClass("hide");
					jQuery(this).addClass("show");
					jQuery(this).html("显示");
				}
				
			});
			
			jQuery('#container').highcharts({
        chart: {
            type: 'spline'
        },
        credits: {
            text: '源自 firstblood.net.cn',
            href: 'http://firstblood.net.cn',
            style:{
                color:"#755757"
            },
            position: {
                x: -30,
                y: -25
            }
        },
        title: {
            text: '地摊最高利率走势'
        },
        subtitle: {
            text: '<%=((RecordType)request.getAttribute("type")).getDesc()%>'
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
            	millisecond: '%H:%M:%S.%L',
            	second: '%H:%M:%S',
            	minute: '%H:%M',
            	hour: '%H:%M',
            	day: '%e. %b',
            	week: '%e. %b',
            	month: '%b \'%y',
            	year: '%Y'
            },
            title: {
                text: '时间'
            }
        },
        yAxis: {
            title: {
                text: '利率 (%)'
            },
            min: <%=request.getAttribute("min")%>,
            max: <%=request.getAttribute("max")%>
        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '{point.x:%H:%M}: {point.y:.2f} %'
        },
        plotOptions: {
            spline: {
                marker:{  
                    enabled:false,//是否显示点 
                    states: {
                        hover: {
                            enabled: true,
                            radius: 3,
                        }
                    }

                }

            },
            
        },

        series: [
		<%
			List<RecordDO> chartsData = (List<RecordDO>)request.getAttribute("chartsData");

			%>
			{
				name: "<%=((RecordType)request.getAttribute("type")).getDesc()%>",
				data: [
					<%
					int i = 0;
					for(RecordDO record : chartsData){%>
					[Date.UTC(<%=DateUtil.format(record.getGmtCreate(),"yyyy,MM,dd,HH,mm,00")%>),<%=record.getValue()%>]<%if(i<chartsData.size()-1){%>,<%}i++;%>
					<%}%>
					]
			}
			
			
        ]
    });
});
		</script>
	</head>
	<body>
	<jsp:include page="/common/nav.htm"/>
	<button type="button" id="config_btn" class="show">显示</button>
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
		
		
		<table border="1">
		  <tr>
		    <th>产品名称</th>
		    <th>当前最大利率</th>
		    <th>设置提醒利率</th>
		    <th>是否QQ提醒</th>
		    <th>是否微信提醒</th>
		    <th>操作</th>
		  </tr>
		  
		  <% 
		 	List<ZcbPO> zcbPOList = (List<ZcbPO>)request.getAttribute("zcbList");
		 	for(ZcbPO zcbPO : zcbPOList){%>
		 	<tr>
		 		<form name="myform" action="/zcb/action/doUpdateConfig.do" method="post">
					<input type="hidden" value="<%=zcbPO.getEnName()%>" name="enName">
					<td><%=zcbPO.getName()%></td>
				    <td><%=zcbPO.getCurMaxRate()%></td>
				    <td><input type="text" name="warnRate" value="<%=zcbPO.getWarnRate()%>" style="height:20px;width:30px;"></td>
				    <td><input type="radio" name="isQQNotify" value="true" <% if(zcbPO.getIsQQNotify()){%>checked<%} %>>是<input type="radio" name="isQQNotify" value="false" <% if(!zcbPO.getIsQQNotify()){%>checked<%} %>>否</td>
				    <td><input type="radio" name="isWeChatNotify" value="true" <% if(zcbPO.getIsWeChatNotify()){%>checked<%} %>>是<input type="radio" name="isWeChatNotify" value="false" <% if(!zcbPO.getIsWeChatNotify()){%>checked<%} %>>否</td>
				    <td><input type="submit"  name="submit1" value="提 交">&nbsp;&nbsp;&nbsp;&nbsp;<a href="/zcb/index.htm?type=<%=zcbPO.getEnName()%>">查看走势</a></td>
				</form>
			</tr>
		 	<%}
		 %>
		  
		</table>

		 <br/>
		<hr/>
		<br/>
		<script src="/js/highcharts.js"></script>
		<script src="/js/modules/exporting.js"></script>
		
		<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	</body>
</html>