package serviceTester.core.domain;


import org.springframework.data.annotation.Transient;


public class MIPScenario extends AbstractScenario {	
		
	@Transient
	private String scenarioInputAsString; //used for storing payload will be removed after refactor
	
	@Transient
	private String scenarioOutOutAsString; // used for model display
	

	public String getScenarioInputAsString() {
		return scenarioInputAsString;
	}

	public void setScenarioInputAsString(String scenarioInputAsString) {
		this.scenarioInputAsString = scenarioInputAsString;
	}

	public String getScenarioOutOutAsString() {
		return scenarioOutOutAsString;
	}

	public void setScenarioOutOutAsString(String scenarioOutOutAsString) {
		this.scenarioOutOutAsString = scenarioOutOutAsString;
	}
	
}
