package serviceTester.test.repository;

import static serviceTester.core.constants.MIPScenarioConstants.COLLECTION_MIP;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serviceTester.core.domain.MIPScenario;
import serviceTester.core.domain.TestRun;
import serviceTester.mongo.config.MongoConfig;
import serviceTester.mongo.repository.MIPServiceTesterRepository;
import serviceTester.test.config.ServiceTestPropertiesConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MongoConfig.class, ServiceTestPropertiesConfig.class})

public class MIPRepositoryTest {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	MIPServiceTesterRepository mipRepository;
	
	MIPScenario mipScenario = new MIPScenario();
	LinkedHashMap<String, String> inputs;
	LinkedHashMap<String, String> outputs;
	
	TestRun testRun = new TestRun();
		
	@Before
	public void setUp(){
	
		dropCollection(COLLECTION_MIP);
			
		inputs = new LinkedHashMap<String, String>();
		inputs.put("inputField1", "100");
		inputs.put("inputField2", "200");
		
		outputs = new LinkedHashMap<String, String>();
		outputs.put("inputField1", "300");
		outputs.put("inputField2", "400");
		
		mipScenario.setScenarioInput(inputs);
		mipScenario.setScenarioOutput(outputs);
		
		
		List<MIPScenario>testScenarios = new ArrayList<MIPScenario>();
		testScenarios.add(mipScenario);
		
		testRun.setTestScenarios(testScenarios);
		
	}
	
	
	private void dropCollection(String collectionName){
		mongoTemplate.dropCollection(collectionName);
	}
	
	
	@Test
	public void testSaveTestRun(){
		mipRepository.save(testRun);
		
	}
}
