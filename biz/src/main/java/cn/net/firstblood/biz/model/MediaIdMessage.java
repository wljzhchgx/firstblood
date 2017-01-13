/**
 * 
 */
package cn.net.firstblood.biz.model;

import cn.net.firstblood.framework.annotation.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author gangxiang.chengx
 *
 */
public class MediaIdMessage {  
    @XStreamAlias("MediaId")  
    @XStreamCDATA  
    private String MediaId;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
} 
