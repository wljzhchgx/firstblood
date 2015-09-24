var GetRandomn = 1;
function GetRandom(n){GetRandomn=Math.floor(Math.random()*n+1)}
 var a2=new Array();
var t2=new Array();
var ts2=new Array();
a2[0]="<span onclick=\"addHits2(1,2)\"><a href=\"/notice/zhaosheng.html\" target=\"_blank\"><img  alt=\"少林武术学校招生学习指南\"  border=\"0\"  height=210  width=270  src=\"/uploadfiles/2014-12/administrator/2014121114284768616.jpg\"></a></span>";
t2[0]=0;
ts2[0]="2014-12-11";
var temp2=new Array();
var k=0;
for(var i=0;i<a2.length;i++){
if (t2[i]==1){
if (checkDate2(ts2[i])){
	temp2[k++]=a2[i];
}
	}else{
 temp2[k++]=a2[i];
}
}
if (temp2.length>0){
GetRandom(temp2.length);
document.write(a2[GetRandomn-1]);
}
function addHits2(c,id){if(c==1){try{jQuery.getScript('http://www.dfslszs.com.cn/plus/ajaxs.asp?action=HitsGuangGao&id='+id,function(){});}catch(e){}}}
function checkDate2(date_arr){
 var date=new Date();
 date_arr=date_arr.split("-");
var year=parseInt(date_arr[0]);
var month=parseInt(date_arr[1])-1;
var day=0;
if (date_arr[2].indexOf(" ")!=-1)
day=parseInt(date_arr[2].split(" ")[0]);
else
day=parseInt(date_arr[2]);
var date1=new Date(year,month,day);
if(date.valueOf()>date1.valueOf())
 return false;
else
 return true
}
