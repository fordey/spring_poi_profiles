package serviceTester.core.domain;

import java.util.LinkedHashMap;
import java.util.UUID;

public class AbstractScenario {
	private String scenarioId;
	private LinkedHashMap<String , String> scenarioInput;
	private LinkedHashMap<String, String> scenarioOutput;
	private String desc;
	
	public AbstractScenario() {
		this.scenarioId = generateUniqueTestID();
	}
	
	
	public LinkedHashMap<String, String> getScenarioOutput() {
		return scenarioOutput;
	}

	public void setScenarioOutput(LinkedHashMap<String, String> scenarioOutput) {
		this.scenarioOutput = scenarioOutput;
	}

	public LinkedHashMap<String, String> getScenarioInput() {
		return scenarioInput;
	}

	public void setScenarioInput(LinkedHashMap<String, String> scenarioInput) {
		this.scenarioInput = scenarioInput;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	
	private String generateUniqueTestID(){
		return UUID.randomUUID().toString();
	}


	public String getScenarioId() {
		return scenarioId;
	}

}
