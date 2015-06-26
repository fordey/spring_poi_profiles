package serviceTester.mongo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@EnableMongoRepositories(basePackages="serviceTester.mongo.repository")
@Configuration
public class MongoConfig {

	@Value(value="${mongodb.host}")
	private String host;
	
	@Value(value="${mongodb.db}")
	private String db;
	
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(host), db);
	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws Exception{
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		
		return mongoTemplate;
	}
}
