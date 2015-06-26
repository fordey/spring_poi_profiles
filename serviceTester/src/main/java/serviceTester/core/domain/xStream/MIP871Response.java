package serviceTester.core.domain.xStream;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import serviceTester.core.domain.xStream.Field;

@XStreamAlias("Response")
public class MIP871Response {
	
	MIPCalculation871 MIPCalculation871;
	
	public MIPCalculation871 getMipCalculation871() {
		return MIPCalculation871;
	}
	
	public void setMipCalculation871(MIPCalculation871 mipCalculation871) {
		this.MIPCalculation871 = mipCalculation871;
	}
	
	public List<Field> getFields(){
		return MIPCalculation871.getFields();
	}
}
