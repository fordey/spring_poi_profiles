package serviceTester.test.MIPServices;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import serviceTester.core.domain.xStream.MIP871Response;
import serviceTester.core.services.api.ResponseProcessService;
import serviceTester.mongo.config.MongoConfig;
import serviceTester.test.config.ServiceTestMIPServiceBeans;
import serviceTester.test.config.ServiceTestPropertiesConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ServiceTestPropertiesConfig.class, ServiceTestMIPServiceBeans.class, MongoConfig.class})
public class ResponseProcessServiceTest {

	String xmlString;
	
	@Autowired
	ResponseProcessService requestProcessor;
	
	
	
	@Before
	public void setUp() throws Exception{
		Resource res = new ClassPathResource("testResponse.xml");
		
		InputStream is = res.getInputStream();
		
		xmlString = IOUtils.toString(is);
		
	}
	
	@Test
	public void test() throws Exception{		
		Object o = requestProcessor.returnMIPObjectFromXML(xmlString);
		
		
		MIP871Response mipResponse = (MIP871Response) o;
		
		Assert.assertNotNull(mipResponse.getMipCalculation871());
		Assert.assertNotNull(mipResponse.getMipCalculation871().getFields());
				
	}
	
}
