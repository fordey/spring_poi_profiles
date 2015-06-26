package serviceTester.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import serviceTester.core.domain.TestRun;

public interface MIPServiceTesterRepository extends MongoRepository<TestRun, String> {

}
