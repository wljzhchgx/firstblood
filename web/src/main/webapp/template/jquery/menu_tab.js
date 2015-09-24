//==============================
//获取findObj
//==============================
function findObj(theObj, theDoc)
{
  var p, i, foundObj;
  
  if(!theDoc) theDoc = document;
  if( (p = theObj.indexOf("?")) > 0 && parent.frames.length)
  {
    theDoc = parent.frames[theObj.substring(p+1)].document;
    theObj = theObj.substring(0,p);
  }
  if(!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
  for (i=0; !foundObj && i < theDoc.forms.length; i++) 
    foundObj = theDoc.forms[i][theObj];
  for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++) 
    foundObj = findObj(theObj,theDoc.layers[i].document);
  if(!foundObj && document.getElementById) foundObj = document.getElementById(theObj);
  
  return foundObj;
}
//==============================
//导航焦点（固定样式名称activedTab，nTab）
//==============================
function doclick(srcObj)
	{
	var tabList = srcObj.parentNode.getElementsByTagName("li");
	if(srcObj.className=="activedTab")
		return;
	for(var i=0;i<tabList.length;i++){
		if(tabList[i].className=="activedTab")
			tabList[i].className="nTab";
	}
	srcObj.className = "activedTab";//TAB切换
}

$(document).ready(function() {
	$("form[name='myform']").submit(function() {
		var _self = jQuery(this);
		if (_self.hasClass('double-click')) {
			return false;
		}
		_self.addClass('double-click');
		jQuery.ajax({
  			url:'/plus/form.asp',
  			data:$("form[name='myform']").serialize(),
  			type:"POST",
  			dataType:"text",
  			complete: function(data) {
                _self.removeClass('double-click');
            },
  			success:function(data){
  				alert(data);
  			}
  		});
  		return false;
	});
}); 

