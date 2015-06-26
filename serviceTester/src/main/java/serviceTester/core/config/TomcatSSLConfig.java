package serviceTester.core.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

@Configuration
public class TomcatSSLConfig {
	private static String CONNECTOR_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";

	@Value("${ssl.port}")
	String securePort;

	@Bean
	public EmbeddedServletContainerFactory servletContainer(){
		TomcatEmbeddedServletContainerFactory tomcat  = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		
		return tomcat;
		
	}
	
	private Connector createSslConnector(){
		Connector connector = new Connector(CONNECTOR_PROTOCOL);
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		try{
			
			File keyStore = getKeystoreFile();
			File trustStore = keyStore;
			
			connector.setScheme("https");
			connector.setSecure(true);
			connector.setPort(Integer.valueOf(securePort));
			connector.setProtocol("TLS");
			
			protocol.setSSLEnabled(true);
			protocol.setKeystoreFile(keyStore.getAbsolutePath());
			protocol.setKeystorePass("changeit");
			protocol.setTruststoreFile(trustStore.getAbsolutePath());
			protocol.setTruststorePass("changeit");
			protocol.setKeyAlias("playpen");
			
			return connector;
			
			
		}catch(IOException ex){
			throw new IllegalStateException("cant access keystore: [" + "keystore"
					+ "] or truststore: [" + "keystore" + "]", ex);
		}

	}
	
	
	private File getKeystoreFile() throws IOException{
		
		ClassPathResource resource = new ClassPathResource("keystore");
		try {
			return resource.getFile();
		}
		catch (Exception ex) {
			File temp = File.createTempFile("keystore", ".tmp");
			FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(temp));
			return temp;
		}
		
	}

}
