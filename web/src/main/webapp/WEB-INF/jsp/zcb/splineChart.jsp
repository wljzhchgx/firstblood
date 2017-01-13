<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List"%>
<%@ page import="cn.net.firstblood.dal.enums.RecordType"%>
<%@ page import="cn.net.firstblood.dal.model.RecordDO"%>
<%@ page import="cn.net.firstblood.framework.util.DateUtil"%>

<script type="text/javascript">
jQuery(function () {
	<%RecordType type = (RecordType)request.getAttribute("type");%>
	jQuery('#<%=type.name()%>').highcharts({
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
            text: '<%=type.getDesc()%>'
        },
        subtitle: {
            text: ''
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
				name: "<%=type.getDesc()%>",
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
<div id="<%=type.name()%>" style="min-width: 50%; height: 400px;float:left"></div>