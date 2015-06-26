package serviceTester.test.MIPServices;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serviceTester.core.domain.MIPScenario;
import serviceTester.core.services.api.MipScenariosService;
import serviceTester.mongo.config.MongoConfig;
import serviceTester.test.config.ServiceTestMIPServiceBeans;
import serviceTester.test.config.ServiceTestPropertiesConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ServiceTestPropertiesConfig.class, ServiceTestMIPServiceBeans.class, MongoConfig.class})
public class MIPScenarioServiceImplTest {

	@Autowired
	MipScenariosService mipsScenarioService;
	
	@Test
	public void testGetAllScenariosFromExcel() throws Exception {
		
		List<MIPScenario> loadedScenarios = mipsScenarioService.getAllScenariosFromExcel();
		
		assertNotNull(loadedScenarios);
		
	}

}
