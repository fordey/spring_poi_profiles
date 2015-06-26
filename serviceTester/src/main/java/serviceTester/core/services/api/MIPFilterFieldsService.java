package serviceTester.core.services.api;

import java.util.List;

import serviceTester.core.domain.MIPScenario;

public interface MIPFilterFieldsService {

	public boolean inFilter(String itemToCheck);
	public List<String> getFilteredFieldNames();
	public MIPScenario returnFilteredMIP(MIPScenario mip);
}
