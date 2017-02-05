package cn.net.firstblood.framework.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
//
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.Velocity;
//
//import com.alifi.aps.common.exception.ApsRuntimeException;
//import com.alifi.aps.common.resultcode.common.CommonResultCode;

/**
 * vm模板渲染工具
 * @author gangxiang.chengx
 *
 */
public class VelocityUtil {

//    private static VelocityUtil util = new VelocityUtil();
//
//    private VelocityUtil() {
//        try {
//            Velocity.init();
//        } catch (Exception e) {
//            throw new ApsRuntimeException(CommonResultCode.DATA_ERROR, e);
//        }
//    }
//
//    public static VelocityUtil getUtil() {
//        return util;
//    }
//
//    public static String merge(Map<String, Object> map, String template) {
//        if (map == null || map.isEmpty()) {
//            return template;
//        }
//        VelocityContext context = new VelocityContext();
//        for (Map.Entry<String, Object> entity : map.entrySet()) {
//            context.put(entity.getKey(), entity.getValue());
//        }
//        Writer writer = new StringWriter();
//        try {
//            Velocity.evaluate(context, writer, "Velocity Content Error.", template);
//            return writer.toString();
//        } catch (Exception e) {
//        	throw new ApsRuntimeException(CommonResultCode.DATA_ERROR, e);
//        }
//    }
//    
//    public static String mergeString(Map<String, String> map, String template) {
//        if (map == null || map.isEmpty()) {
//            return template;
//        }
//        VelocityContext context = new VelocityContext();
//        for (Map.Entry<String, String> entity : map.entrySet()) {
//            context.put(entity.getKey(), entity.getValue());
//        }
//        Writer writer = new StringWriter();
//        try {
//            Velocity.evaluate(context, writer, "Velocity Content Error.", template);
//            return writer.toString();
//        } catch (Exception e) {
//        	throw new ApsRuntimeException(CommonResultCode.DATA_ERROR, e);
//        }
//    }
}
