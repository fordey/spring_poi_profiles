import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import serviceTester.core.domain.MIPScenario;

public class ModelConverterTest {
	MIPScenario mipScenario;
	LinkedHashMap<String, String> inputs;
	LinkedHashMap<String, String> outputs;
	
	
	@Before
	public void setUp(){
		mipScenario = new MIPScenario();
		
		inputs = new LinkedHashMap<String, String>();
		inputs.put("inputField1", "100");
		inputs.put("inputField2", "200");
		
		outputs = new LinkedHashMap<String, String>();
		outputs.put("inputField1", "300");
		outputs.put("inputField2", "400");
		
		mipScenario.setScenarioInput(inputs);
		mipScenario.setScenarioOutput(outputs);
		
	}
	
	@Test
	public void test(){
		//to do
	}
}
