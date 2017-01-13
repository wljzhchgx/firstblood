package cn.net.firstblood.framework.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import org.springframework.util.StringUtils;

/**
 * 
 * @author gangxiang.chengx
 *
 */
public class JsonUtil {

    public static final String YYYY_MM_DD_HH_MM_SS  = "yyyy-MM-dd HH:mm:ss";
    
    public static final String YYYY_MM_DD  = "yyyy-MM-dd";
    

    private JsonUtil() {
    }

    /**
     * 从一个JSON 对象字符格式中得到一个java对象<br>
     * 其中beansList是一类的集合，形如： {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}}<br>
     * 
     * @param jsonString
     * @param clazz
     * @param map 集合属性的类型 (key : 集合属性名, value : 集合属性类型class) eg: ("aBean" : Bean.class)
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonString, Class<T> clazz, Map<Object,Object> map) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        JSONObject jsonObject = null;
        setDataFormat();
        jsonObject = JSONObject.fromObject(jsonString);
        return (T) JSONObject.toBean(jsonObject, clazz, map);
    }

    @SuppressWarnings("unchecked")
    public static <T> T jsonToBean(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        JSONObject jsonObject = null;
        setDataFormat();
        jsonObject = JSONObject.fromObject(jsonString);
        return (T) JSONObject.toBean(jsonObject, clazz);
    }
    

    public static String beanToJson(Object object) {
        return beanToJson(object, YYYY_MM_DD_HH_MM_SS);
    }

    private static void setDataFormat() {
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { YYYY_MM_DD_HH_MM_SS }));
    }

    public static String beanToJson(Object object, String dateFormt) {
        String jsonString = null;
        // 日期值处理器
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor(dateFormt));
        if (object != null) {
            if (object instanceof Collection || object instanceof Object[]) {
                jsonString = JSONArray.fromObject(object, jsonConfig).toString();
            } else {
                jsonString = JSONObject.fromObject(object, jsonConfig).toString();
            }
        }
        return jsonString == null ? "{}" : jsonString;
    }
    
    
    
    /**
     * json转换成map
     * @param strContent
     * @return
     */
    @SuppressWarnings("unchecked")
    public static final Map<String, Object> jsonToMap(String strContent) {
        return jsonToBean(strContent,Map.class);

    }

    /**
     * map转换成Json
     * @param mapContext
     * @return
     */
    public static String mapToJson(Map<String, Object> mapContext) {
        return beanToJson(mapContext);
    }
    
    
   
   
}

class JsonDateValueProcessor implements JsonValueProcessor {

    private String format = "yyyy-MM-dd";

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return null;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        if (value instanceof Date) {
            return dateFormat.format((Date) value);
        }
        return value == null ? "" : value.toString();
    }

    public JsonDateValueProcessor() {
        super();
    }

    public JsonDateValueProcessor(String format) {
        super();
        this.format = format;
    }

}
