package serviceTester.core.domain;

import static serviceTester.core.constants.MIPScenarioConstants.COLLECTION_MIP;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = COLLECTION_MIP)
public class TestRun {

	@Id
	private String id;

	List<MIPScenario> testScenarios;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MIPScenario> getTestScenarios() {
		return testScenarios;
	}

	public void setTestScenarios(List<MIPScenario> testScenarios) {
		this.testScenarios = testScenarios;
	}
}
