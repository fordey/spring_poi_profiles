package serviceTester.core.domain.xStream;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("errorNode")
public class ErrorNode{
	private String errorCode;
	private String errorMsg;
	private String errorSev;
	private String errorSource;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorSev() {
		return errorSev;
	}
	public void setErrorSev(String errorSev) {
		this.errorSev = errorSev;
	}
	public String getErrorSource() {
		return errorSource;
	}
	public void setErrorSource(String errorSource) {
		this.errorSource = errorSource;
	}
	
	
	
}