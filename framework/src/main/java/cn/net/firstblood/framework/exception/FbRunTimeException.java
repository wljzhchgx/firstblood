package cn.net.firstblood.framework.exception;

public class FbRunTimeException extends RuntimeException {

	private static final long serialVersionUID = -3775628101083907998L;
	
	public FbRunTimeException(Throwable cause){
    	super(cause);
    }
	
	public FbRunTimeException(String msg){
    	super(msg);
    }
}
