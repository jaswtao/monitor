package com.tripleaxe.monitor.repository;

import com.tripleaxe.monitor.model.MongoRequestDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCustomerRepository extends MongoRepository<MongoRequestDetail, String> {

    public MongoRequestDetail findByProjectCode(String projectCode);

}
