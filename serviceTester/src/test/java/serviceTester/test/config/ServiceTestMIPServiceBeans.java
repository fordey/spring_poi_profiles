package serviceTester.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import serviceTester.core.services.api.MIPFilterFieldsService;
import serviceTester.core.services.api.MipScenariosService;
import serviceTester.core.services.api.ResponseProcessService;
import serviceTester.core.services.api.WebServiceClient;
import serviceTester.core.services.impl.MIPFilterFieldsServiceImpl;
import serviceTester.core.services.impl.MIPScenariosServicesImpl;
import serviceTester.core.services.impl.ResponseProcessServiceImpl;
import serviceTester.core.services.impl.WebServiceClientImpl;

@Configuration
public class ServiceTestMIPServiceBeans {

	@Bean
	public static MipScenariosService mipScenariosServices(){
		return new MIPScenariosServicesImpl();
	}
	
	@Bean
	public static WebServiceClient webServiceClient(){
		return new WebServiceClientImpl();
	}
	
	@Bean
	public static ResponseProcessService responseProcessService(){
		return new ResponseProcessServiceImpl();
	}
	
	@Bean
	public static MIPFilterFieldsService mipFilterFieldsService(){
		return new MIPFilterFieldsServiceImpl();
	}
}
