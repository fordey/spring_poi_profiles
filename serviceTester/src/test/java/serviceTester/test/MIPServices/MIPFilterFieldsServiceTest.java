package serviceTester.test.MIPServices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import serviceTester.core.domain.MIPFilter;
import serviceTester.core.services.impl.MIPFilterFieldsServiceImpl;
import serviceTester.mongo.repository.MIPServiceTesterFilterRepository;



//not using SpringJUnit4ClassRunner as this is an initialising bean so i dont want the
//afterPropertiesSet() method being called by Spring as will inject the real dao..


@RunWith(MockitoJUnitRunner.class)
public class MIPFilterFieldsServiceTest {

	@InjectMocks
	MIPFilterFieldsServiceImpl mipFilterService;
	
	@Mock
	MIPServiceTesterFilterRepository filterRepo;
	
	List<MIPFilter> filters = new ArrayList<MIPFilter>();
	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		mipFilterService.setFilteredFieldNames(createFilterFieldList());
	}
	
	@Test
	public void inFilterTestTrue(){
		
		assertTrue(mipFilterService.inFilter("filtername1"));
		
	}
	
	@Test
	public void inFilterTestFalse(){
		assertFalse(mipFilterService.inFilter("filtername3"));
		
	}
	
	private List<String> createFilterFieldList(){
		return Arrays.asList("filtername1", "filtername2");
	}
	
}
