<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="cn.net.firstblood.biz.model.ZcbPO,java.util.List"%>

<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>zcb</title>
		<script type="text/javascript" src="/js/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="/js/highcharts.js"></script>
		<script type="text/javascript" src="/js/modules/exporting.js"></script>
	</head>
	<body>
		<jsp:include page="/common/nav.htm"/>
		<div style="height: 400px;width:100%;margin:0 auto;">
			<jsp:include page="/zcb/splineChart.htm">
			  <jsp:param  name="type" value="ZCB_M0306_MAX_RATE" />
			</jsp:include>
			
			<jsp:include page="/zcb/splineChart.htm">
			  <jsp:param  name="type" value="ZCB_M0612_MAX_RATE" />
			</jsp:include>
		</div>
		
		<div style="height: 400px;width:100%;margin:0 auto;">
			<jsp:include page="/zcb/splineChart.htm">
			  <jsp:param  name="type" value="ZCB_M1224_MAX_RATE" />
			</jsp:include>
			
			<jsp:include page="/zcb/splineChart.htm">
			  <jsp:param  name="type" value="ZCB_M249999_MAX_RATE" />
			</jsp:include>
		</div>
		<a href="/zcb/index.htm">配置</a>
	</body>

</html>