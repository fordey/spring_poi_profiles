package serviceTester.test.repository;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serviceTester.core.constants.MIPScenarioConstants;
import serviceTester.core.domain.MIPFilter;
import serviceTester.mongo.config.MongoConfig;
import serviceTester.mongo.repository.MIPServiceTesterFilterRepository;
import serviceTester.test.config.ServiceTestPropertiesConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MongoConfig.class, ServiceTestPropertiesConfig.class})
public class MIPFilterRepositoryTest {

	@Autowired
	MongoTemplate mongoTemplate;
	
	
	@Autowired
	MIPServiceTesterFilterRepository filterRepo;
	
	MIPFilter mipFilter = new MIPFilter();
	
	@Before
	public void setUp(){
		mongoTemplate.dropCollection(MIPScenarioConstants.COLLECTION_FILTER);
		
		setUpFilter();		
		
	}
	
	
	private void setUpFilter(){
		mipFilter.setFilterFieldNames(Arrays.asList(
				"MO_GEN_TOTAL_MTHLY_SALARY_APPT_1",
				"MO_NDI_POL_CRITERIA_ACTUAL_3",
				"MO_NDI_POL_ACTUAL_REPAY_AMT_STRESS_3",
				"MO_GEN_INT_RATE_STRESS",
				"MO_MSR_CRITERIA_MAX_3",
				"MO_MSR_CRITERIA_ACTUAL_3",
				"MO_MSR_COMPLIED_3",
				"MO_MSR_MAX_LOAN_CAPACITY_3",
				"MO_NDI_POL_CRITERIA_ACTUAL_3",
				"MO_NDI_POL_CRITERIA_MIN",
				"MO_NDI_POL_COMPLIED_3",
				"MO_NDI_POL_MAX_LOAN_CAPACITY_3",
				"MO_LTI_POL_CRITERIA_MAX_3",
				"MO_LTI_POL_CRITERIA_ACTUAL_3",
				"MO_LTI_POL_COMPLIED_3",
				"MO_LTI_POL_MAX_LOAN_CAPACITY_3",
				"MO_NDI_POL_LTV_CRITERIA_MAX_3",
				"MO_NDI_POL_LTV_CRITERIA_ACTUAL_3",
				"MO_NDI_POL_LTV_MAX_LOAN_CAPACITY_3",
				"MO_NDI_POL_LTV_COMPLIED_3",
				"MO_FIN_POL_COMPLIED_3",
				"MO_NDI_EXC_LTV_COMPLIED_3",
				"MO_NDI_EXC_LTV_LTV_CRITERIA_MAX_3",
				"MO_NDI_EXC_LTV_LTV_MAX_LOAN_CAPACITY_3",
				"MO_NDI_EXC_LTV_CRITERIA_MIN",
				"MO_NDI_EXC_LTV_CRITERIA_ACTUAL_3",
				"MO_NDI_EXC_LTV_MAX_LOAN_CAPACITY_3",
				"MO_LTI_EXC_L_CRITERIA_MAX_3",
				"MO_LTI_EXC_L_CRITERIA_ACTUAL_3",
				"MO_LTI_EXC_L_MAX_LOAN_CAPACITY_3",
				"MO_LTI_EXC_H_COMPLIED_3",
				"MO_LTI_EXC_H_CRITERIA_MAX_3",
				"MO_LTI_EXC_H_CRITERIA_ACTUAL_3",
				"MO_LTI_EXC_H_MAX_LOAN_CAPACITY_3",
				"MO_NDI_EXC_LTI_CRITERIA_MIN",
				"MO_NDI_EXC_LTI_CRITERIA_ACTUAL_3",
				"MO_NDI_EXC_LTI_MAX_LOAN_CAPACITY_3",
				"MO_OTHER_ERROR"
));
		
	}
	
	
	@Test
	public void testInsert(){
		filterRepo.insert(mipFilter);
		mipFilter = filterRepo.findAll().get(0);
		
		assertEquals(38, mipFilter.getFilterFieldNames().size());
		
		assertEquals("MO_GEN_TOTAL_MTHLY_SALARY_APPT_1", mipFilter.getFilterFieldNames().get(0));
		
	}
	
}
