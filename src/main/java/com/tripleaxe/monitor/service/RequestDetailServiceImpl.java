package com.tripleaxe.monitor.service;

import com.tripleaxe.monitor.model.MongoRequestDetail;
import com.tripleaxe.monitor.repository.MongoCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Generated Comments
 * <p>
 *
 * @author : terrytao
 * @date : 1/4/2020
 */
@Service
public class RequestDetailServiceImpl implements IRequestDetailService {

    @Autowired
    private MongoCustomerRepository mongoCustomerRepository;

    @Override
    public MongoRequestDetail getRequestDetailById(String id) {
        return mongoCustomerRepository.findById(id).orElse(null);
    }

    @Override
    public long countAll() {
        return mongoCustomerRepository.count();
    }

    public MongoRequestDetail save(MongoRequestDetail mongoRequestDetail) {
        return mongoCustomerRepository.save(mongoRequestDetail);
    }
}
