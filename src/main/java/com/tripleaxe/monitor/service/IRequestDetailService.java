package com.tripleaxe.monitor.service;

import com.tripleaxe.monitor.model.MongoRequestDetail;

/**
 * Generated Comments
 * <p>
 *
 * @author : terrytao
 * @date : 1/4/2020
 */
public interface IRequestDetailService {

    MongoRequestDetail getRequestDetailById(String id);

    long countAll();

    MongoRequestDetail save(MongoRequestDetail mongoRequestDetail);
}
