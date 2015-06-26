import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serviceTester.core.constants.MIPScenarioConstants;
import serviceTester.core.domain.AbstractScenario;
import serviceTester.test.config.ServiceTestPropertiesConfig;
import serviceTester.utils.ExelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ServiceTestPropertiesConfig.class})
public class ExelUtilsTest {

	List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
	Workbook wb;
	String fileName;
	
	@Value(value="${mipScenarios.resultsfile}")
	private String resultsFileName;
	
	@Value(value="${mipScenarios.resultsfileext}")
	private String resultsFileExt;
	
	
	@Value(value="${mipScenarios.resultslocation}")
	private String resultsLocation;
	
	@Before
	public void setUp(){
		fileName = createFileName();
		wb = new XSSFWorkbook();
		
		scenarioList = generateTestScenario();
	}
	
		
	@Test
	public void testExportSheetToExcel() throws Exception {
			
		Assert.assertTrue(ExelUtils.exportSheetToExcel(scenarioList, wb, fileName));
	}

	
	private String createFileName(){
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddhhmmss");
		String ts = dt.format(new Date());
		
		StringBuilder sb = new StringBuilder(resultsLocation);
		sb.append(resultsFileName);
		sb.append(ts);
		sb.append(resultsFileExt);
		
		return sb.toString();
	}
	
	
	private List<AbstractScenario> generateTestScenario(){
		List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
		
		AbstractScenario scenario = createSingleTestScenario();
		
		scenarioList.add(scenario);
		
		return scenarioList;
		
	}
	
	private AbstractScenario createSingleTestScenario(){
		AbstractScenario scenario = new AbstractScenario();
		scenario.setDesc("test scenario1");
		
		scenario.setScenarioInput(getScenarioInputs());
		scenario.setScenarioOutput(getScenarioOutputs());
		
		
		return scenario;
	}
	
	
	private LinkedHashMap<String, String> getScenarioInputs(){
		LinkedHashMap<String, String> inputs = new LinkedHashMap<String, String>();
		
		inputs.put(MIPScenarioConstants.MIP_LOAN_TYPE, "054002");
		inputs.put(MIPScenarioConstants.MIP_PROPERTY_TYPE, "284001");
		inputs.put(MIPScenarioConstants.MIP_FIRST_TIME_BUYER_1, "Y");
		inputs.put(MIPScenarioConstants.MIP_FIRST_TIME_BUYER_2, "Y");
		inputs.put(MIPScenarioConstants.MIP_APPLIC_TYPE_1, "M");
		inputs.put(MIPScenarioConstants.MIP_APPLIC_TYPE_2, "M");
		inputs.put(MIPScenarioConstants.MIP_SELF_EMPLOYED_1, "Y");
		inputs.put(MIPScenarioConstants.MIP_SELF_EMPLOYED_2, "Y");
		inputs.put(MIPScenarioConstants.MIP_NUM_DEPENDENTS_1, "0");
		inputs.put(MIPScenarioConstants.MIP_APP_1_SALARY_1, "04111000");
		inputs.put(MIPScenarioConstants.MIP_PERS_LOANS_1, null);
		inputs.put(MIPScenarioConstants.MIP_CHILDCARE_1, null);
		inputs.put(MIPScenarioConstants.MIP_PURCHASE_PRICE, "10000000");
		inputs.put(MIPScenarioConstants.MIP_LOAN_AMOUNT, "9000000");
		inputs.put(MIPScenarioConstants.MIP_TERM, "20");
		inputs.put(MIPScenarioConstants.MIP_ADJUST_LOAN_AMOUNT, "N");
		inputs.put(MIPScenarioConstants.MIP_NDI_USE_REPAY, "N");
		
		
		return inputs;
		
	}
	
	private LinkedHashMap<String, String> getScenarioOutputs(){
		LinkedHashMap<String, String> outputs = new LinkedHashMap<String, String>();
		
		outputs.put("DBKNSC", "933384");
		outputs.put("DBKACN", "12345678");
		outputs.put("MO_GEN_TOTAL_MTHLY_SALARY_APPT_1", "0");
		outputs.put("MO_GEN_TOTAL_MTHLY_SALARY_APPT_2", "10000");
		outputs.put("MO_GEN_TOTAL_MTHLY_SALARY_APPT_3", "20000");
		outputs.put("MO_GEN_APPLIC_MTHLY_SALARY_APPT_1_1", "111111");
		outputs.put("MO_GEN_APPLIC_MTHLY_SALARY_APPT_1_2", "222222");
		outputs.put("MO_GEN_APPLIC_MTHLY_SALARY_APPT_1_3", "333333");
		outputs.put("MO_GEN_APPLIC_MTHLY_SALARY_APPT_2_1", "8484848");
		outputs.put("MO_GEN_APPLIC_MTHLY_SALARY_APPT_2_3", "1000000");
		outputs.put("MO_GEN_INT_RATE_STRESS", "7250");
		
		return outputs;
		
	}
	
	
	
}






