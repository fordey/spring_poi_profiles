package serviceTester.core.services.api;

import java.util.List;

import serviceTester.core.domain.MIPScenario;

public interface MipScenariosService {

	List<MIPScenario> getAllScenariosFromExcel() throws Exception;
	
	int processTestScenarios(List<MIPScenario> mipScenarios) throws Exception;
	
	public String exportMIPTestResultsExcel(List<MIPScenario> mipScenarios, boolean applyFilter)  throws Exception;
}
