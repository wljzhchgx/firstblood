/*
*noticeCycle 向上翻动内容，实现轮播效?
*objArray 需要轮播的对象数组
*speed 移动速度，数值越大速度越慢，建议20
*outTime 轮播间隔，毫秒计算。
*divHeight 轮播内容展示高度。即objArray中，元素对象高度。
*/
function noticeCycle(objArray,speed,outTime,divHeight){
	this.divHeight=divHeight;
	this.speed=speed;
	this.outTime=outTime;
	this.lastID=0;
	this.divObjArray=objArray;
	this.counter=0;
	this.timer;
	this.nextID=this.lastID+1;
	if(this.nextID>=this.divObjArray.length){
		this.nextID=0;
	}
	this.divObjArray[0].style.top="0px";
	this.doCycle=function (objName){

		var nextCounter=this.divHeight-this.counter;

		this.divObjArray[this.lastID].style.top="-"+this.counter+"px";
		this.divObjArray[this.nextID].style.top=nextCounter+"px";
		this.counter++;
		if(this.counter>this.divHeight){
			this.counter=0;
			this.clearTimer();
			this.lastID=this.nextID;
			if(this.lastID>=this.divObjArray.length){
				this.lastID=0;
			}
			this.nextID=this.lastID+1;
			if(this.nextID>=this.divObjArray.length){
				this.nextID=0;
			}

			this.clearTimer();
			this.timer=setTimeout(objName+".doCycle('"+objName+"');",this.outTime);
			return;

		}
		this.clearTimer();
		this.timer=setTimeout(objName+".doCycle('"+objName+"');",this.speed);

	}


	this.stop=function(){
		this.clearTimer();
		return;
	}

	this.start=function(objName){
		this.clearTimer();
		this.timer=setTimeout(objName+".doCycle('"+objName+"')",this.outTime);
		return;
	}

	this.clearTimer=function(){
		if(this.timer){
			clearTimeout(this.timer);
			return;
		}
	}
}