package serviceTester.core.domain.xStream;


import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MIPCalculation871")	
public class MIPCalculation871{
		ErrorNode errorNode;
		CommonFields CommonFields;
		
		List<Field> Fields;
		

		public List<Field> getFields() {
			return Fields;
		}

		public void setFields(List<Field> fields) {
			Fields = fields;
		}

		public CommonFields getCommonFields() {
			return CommonFields;
		}

		public void setCommonFields(CommonFields commonFields) {
			CommonFields = commonFields;
		}

		public ErrorNode getErrorNode() {
			return errorNode;
		}

		public void setErrorNode(ErrorNode errorNode) {
			this.errorNode = errorNode;
		}
	}	
		

