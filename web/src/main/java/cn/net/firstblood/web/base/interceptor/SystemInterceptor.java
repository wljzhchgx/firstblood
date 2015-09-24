/**
 * 
 */
package cn.net.firstblood.web.base.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author gangxiang.chengx
 *
 */
public class SystemInterceptor extends HandlerInterceptorAdapter {
	private static final String[] IGNORE_URIS = new String[]{"login.htm"};
	
	@Override  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		
		if(isIgnorePage(uri)){
			return true;
		}
		Object obj = request.getSession().getAttribute("SESSION_USER_INFO");  
        if (null == obj) {
            // 未登录
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
            builder.append("alert(\"页面过期，请重新登录\");");
            builder.append("window.top.location.href=\"");
            //builder.append(Constants.basePath);
            builder.append("http://localhost/login.htm\";</script>");
            out.print(builder.toString());
            out.close();
            return false;
        }
		return true;
	}
	
	/**
	 * 是否为无需登录的uri
	 * @param uri
	 * @return
	 */
	private boolean isIgnorePage(String uri){
		for(String ignorePage : IGNORE_URIS){
			if(uri.indexOf(ignorePage) != -1){
				return true;
			}
		}
		return false;
	}
}
