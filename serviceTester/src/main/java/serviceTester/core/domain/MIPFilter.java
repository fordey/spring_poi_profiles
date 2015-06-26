package serviceTester.core.domain;

import static serviceTester.core.constants.MIPScenarioConstants.COLLECTION_FILTER;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = COLLECTION_FILTER)
public class MIPFilter {
	@Id
	private String id;

	List<String> filterFieldNames;

	public List<String> getFilterFieldNames() {
		return filterFieldNames;
	}

	public void setFilterFieldNames(List<String> filterFieldNames) {
		this.filterFieldNames = filterFieldNames;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
