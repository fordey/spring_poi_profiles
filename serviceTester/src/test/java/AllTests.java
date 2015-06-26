import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import serviceTester.test.MIPServices.MIPFilterFieldsServiceTest;
import serviceTester.test.MIPServices.MIPScenarioServiceImplTest;
import serviceTester.test.MIPServices.ResponseProcessServiceTest;
import serviceTester.test.repository.MIPFilterRepositoryTest;
import serviceTester.test.repository.MIPRepositoryTest;


@RunWith(Suite.class)
@SuiteClasses({ ModelConverterTest.class, MIPScenarioServiceImplTest.class, MIPRepositoryTest.class, 
	ExelUtilsTest.class, ResponseProcessServiceTest.class, MIPFilterRepositoryTest.class, MIPFilterFieldsServiceTest.class })
public class AllTests {

}
