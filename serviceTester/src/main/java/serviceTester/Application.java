package serviceTester;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication 
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	
	public static void main(String[] args) {
       ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
       logger.info("------------------------------------------------");
       logger.info("Spring Beans created by Spring Boot : ");
       logger.info("------------------------------------------------");
       
       String[] beanNames = ctx.getBeanDefinitionNames();
       Arrays.sort(beanNames);
       
       for(String beanName : beanNames){
    	   logger.info(beanName);
       }
        
    }
	
}
