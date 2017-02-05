package cn.net.firstblood.framework.notifier.model.wechatim;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author gangxiang.chengx
 * @version $Id: WeChatIMUpLoadResPO.java, v 0.1 2017年2月4日 下午3:20:45 gangxiang.chengx Exp $
 */
public class UpLoadMediaResPO {
	@JSONField(name = "BaseResponse")
	private BaseResponsePO baseResponse;
	
	@JSONField(name = "MediaId")
	private String mediaId;
	
	@JSONField(name = "StartPos")
	private long startPos;
	
	@JSONField(name = "CDNThumbImgHeight")
	private long cDNThumbImgHeight;
	
	@JSONField(name = "CDNThumbImgWidth")
	private long cDNThumbImgWidth;

	public BaseResponsePO getBaseResponse() {
		return baseResponse;
	}

	public void setBaseResponse(BaseResponsePO baseResponse) {
		this.baseResponse = baseResponse;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Long getStartPos() {
		return startPos;
	}

	public void setStartPos(Long startPos) {
		this.startPos = startPos;
	}

	public Long getcDNThumbImgHeight() {
		return cDNThumbImgHeight;
	}

	public void setcDNThumbImgHeight(Long cDNThumbImgHeight) {
		this.cDNThumbImgHeight = cDNThumbImgHeight;
	}

	public Long getcDNThumbImgWidth() {
		return cDNThumbImgWidth;
	}

	public void setcDNThumbImgWidth(Long cDNThumbImgWidth) {
		this.cDNThumbImgWidth = cDNThumbImgWidth;
	}
	
	public static UpLoadMediaResPO getObjectFromJson(String jsonString){
		return JSON.parseObject(jsonString, UpLoadMediaResPO.class);
	}
	
	public static String getJsonFromObject(UpLoadMediaResPO obj){
		return  JSON.toJSONString(obj);
	}
}
