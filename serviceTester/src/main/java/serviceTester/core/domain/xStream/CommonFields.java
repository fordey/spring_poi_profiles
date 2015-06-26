package serviceTester.core.domain.xStream;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("CommonFields")	
public class CommonFields {
	private String forwardScroll;
	private String backwardScroll;
	
	public String getForwardScroll() {
		return forwardScroll;
	}
	public void setForwardScroll(String forwardScroll) {
		this.forwardScroll = forwardScroll;
	}
	public String getBackwardScroll() {
		return backwardScroll;
	}
	public void setBackwardScroll(String backwardScroll) {
		this.backwardScroll = backwardScroll;
	}
}
