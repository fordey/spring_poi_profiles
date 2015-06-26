package serviceTester.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import serviceTester.core.domain.MIPFilter;

public interface MIPServiceTesterFilterRepository extends MongoRepository<MIPFilter, String> {

}
