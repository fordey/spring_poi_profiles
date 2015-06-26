package serviceTester.web.controllers;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import serviceTester.core.domain.MIPScenario;
import serviceTester.core.services.api.MIPFilterFieldsService;
import serviceTester.core.services.api.MipScenariosService;
import serviceTester.utils.ModelConverter;

@Controller
//these are conversation scope with the handler i.e persists model attributes between requests .. specific to handler i.e in this case
//the controller ...

@SessionAttributes(value= {"mipScenarios", "filter_status"})
public class HomeController extends AbstractSiteController {
	
	private static final Logger logger = LoggerFactory.getLogger("HomeController.class");
	
	@Value("${filter.default}")
	boolean filterDefault;
	
	
	@Autowired
	MipScenariosService mipScenarioService;
	
	@Autowired
	MIPFilterFieldsService mipFilterService;
	
		
	@RequestMapping(value ="/",method = RequestMethod.GET)
	public String renderMessageSender(Model model){
		logger.info("Rendering Home Page"); 
		return "serviceTests";
		
	}
	
	@Override
	public String setPanelTitle(){
		return "Service Tester";
	}
	

	
	@ModelAttribute("mipScenarios")
	public List<MIPScenario> getMIPScenariosFromExcel() throws Exception{
		logger.info("Retrieving scenarios from excel file ...");
		
		List<MIPScenario> currentScenarios = mipScenarioService.getAllScenariosFromExcel();
		
		for(MIPScenario mipScenario : currentScenarios){
			mipScenario.setScenarioInputAsString(ModelConverter.extractFieldsFromHashMap(mipScenario.getScenarioInput()));
		}
		
		logger.info("Finished retrieving scenarios from excel file ...");
		
		return currentScenarios;
		
		
	}
	
	@ModelAttribute("filter_status")
	public boolean initFilterStatus(){
		return filterDefault;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/triggerTests",method = RequestMethod.GET)
	public String triggerScenarioTests(Model model) throws Exception{
		//the list of mipScenarios available on model - was attached to session as a session attribute
		
		logger.info("triggering tests ... ");
		Map<String, Object> modelMap = model.asMap();
		
		List<MIPScenario> scenariosOnModel = (List<MIPScenario>) modelMap.get("mipScenarios");
		
		int processedCount = mipScenarioService.processTestScenarios(scenariosOnModel);
		
		
		model.addAttribute("response", String.format("%s Scenarios processed", processedCount)  );
		
		logger.info("{} tests processed", processedCount);
		
		return "serviceTests::test_response";
	}
	
	
	@RequestMapping(value="/toggleFilter", method = RequestMethod.GET)
	public @ResponseBody String toggleFilter(@RequestParam("filter_status") boolean filter_status, Model model){
		
		//want to update the modelAttribute and the modelAttribute on session-attribute for further interactions 
		model.addAttribute("filter_status", filter_status);
		
		String response = String.valueOf(filter_status);
		
		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/exportToExcel", method = RequestMethod.GET)
	public String exportToExcel(Model model) throws Exception{
		logger.info("Exporting scenarios to excel....");
		Map<String, Object> modelMap = model.asMap();
		
		List<MIPScenario> scenariosOnModel = (List<MIPScenario>) modelMap.get("mipScenarios");
		boolean applyFilter = (boolean) modelMap.get("filter_status");
		
		String exportFileName = mipScenarioService.exportMIPTestResultsExcel(scenariosOnModel, applyFilter);
		
		model.addAttribute("response", String.format("Exported to file %s",  exportFileName ));
		
		logger.info("Completed exporting scenarios to excel file {} ....", exportFileName);
		
		return "serviceTests::test_response";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/scenarioDetails", method=RequestMethod.GET)
	public String showScenarioDetails(@RequestParam("scenarioId") String scenarioId, ModelMap model) throws Exception{
		
		List<MIPScenario> scenariosOnModel = (List<MIPScenario>) model.get("mipScenarios");
		
		boolean applyFilter = (boolean) model.get("filter_status");
		MIPScenario selectedScenario = getScenarioFromMap(scenariosOnModel, scenarioId, applyFilter);
			
		
		model.addAttribute("selectedScenario", selectedScenario);
		
		return "scenarioDetails";
	}
	
	
	@RequestMapping(value="/reloadTests", method=RequestMethod.GET)
	public String reloadScenarios(Model model) throws Exception{
		model.addAttribute("mipScenarios", getMIPScenariosFromExcel());
		model.addAttribute("response", "Reload successful");
		return "serviceTests::reload_frag";
	}
	
	
	private MIPScenario getScenarioFromMap(List<MIPScenario> scenarios, String testId, boolean applyFilter){
		MIPScenario mip = new MIPScenario();
		
		for(MIPScenario s : scenarios){
			if(s.getScenarioId().equalsIgnoreCase(testId)){
				mip = s;
				break;
			}
		}
		
		if(applyFilter){
			mip  = mipFilterService.returnFilteredMIP(mip);
		}
		
		return mip;
	}
	
}
