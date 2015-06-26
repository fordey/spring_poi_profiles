package serviceTester.core.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import serviceTester.core.constants.MIPScenarioConstants;
import serviceTester.core.domain.MIPScenario;
import serviceTester.core.services.api.MIPFilterFieldsService;
import serviceTester.mongo.repository.MIPServiceTesterFilterRepository;

@Service
public class MIPFilterFieldsServiceImpl implements InitializingBean, MIPFilterFieldsService {

	@Autowired
	MIPServiceTesterFilterRepository filterRepo;
	
	private List<String> filteredFieldNames = new ArrayList<String>();
	
	private static final String DUPLICATE_REGEX = "^DUPLICATE-.*";
	private static final int DUPLICATE_TAG_END_POS = 10;
				
			
	@Override
	public void afterPropertiesSet() throws Exception {
		filteredFieldNames = filterRepo.findAll().get(0).getFilterFieldNames();
		
	}
	
	public boolean inFilter(String itemToCheck){
		return filteredFieldNames.contains(itemToCheck);
	}

	public List<String> getFilteredFieldNames() {
		return filteredFieldNames;
	}

	public void setFilteredFieldNames(List<String> filteredFieldNames) {
		this.filteredFieldNames = filteredFieldNames;
	}

	public MIPScenario returnFilteredMIP(MIPScenario mip){
		MIPScenario filteredScenario = new MIPScenario();
		
		filteredScenario.setScenarioInput(mip.getScenarioInput()); //inputs remain same
		
		List<String>filteredNames = this.filteredFieldNames;
		
		LinkedHashMap<String, String> completeOutputs =  mip.getScenarioOutput();
		
		LinkedHashMap<String, String> filteredOutputs = new LinkedHashMap<String, String>();
		
		if(completeOutputs != null){
		
			for(String filterInstance:filteredNames){
				String filterToCheck = checkForDuplication(filterInstance);
				if(completeOutputs.containsKey(filterToCheck)){
					String value = completeOutputs.get(filterToCheck);
					filteredOutputs.put(filterInstance, value);
				}
			}
		}
		filteredScenario.setScenarioOutput(filteredOutputs);
		
		return filteredScenario;
	}
	
	//this is to get over a problem where duplicate fields are to be in the output
	//so will be preceded with'DUPLICATE-hashmap wont handle duplicate keys
	private String checkForDuplication(String filterInstance){
		String fieldName = filterInstance;
		boolean dupRequired = Pattern
				.matches(DUPLICATE_REGEX, filterInstance);
		
		if(dupRequired){
			fieldName = filterInstance.substring(DUPLICATE_TAG_END_POS);
		}
		
		return fieldName;
	}
	
}
