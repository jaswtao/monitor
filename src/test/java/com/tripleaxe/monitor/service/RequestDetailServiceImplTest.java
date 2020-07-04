package com.tripleaxe.monitor.service;

import com.google.gson.Gson;
import com.tripleaxe.monitor.BaseTest;
import com.tripleaxe.monitor.model.MongoRequestDetail;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Generated Comments
 * <p>
 *
 * @author : terrytao
 * @date : 1/4/2020
 */
public class RequestDetailServiceImplTest extends BaseTest {

    @Autowired
    private IRequestDetailService requestDetailService;

    private Gson gson = new Gson();

    /** 日志记录对象 */
    private static final Logger logger = LoggerFactory.getLogger(RequestDetailServiceImplTest.class);

    @Test
    public void getRequestDetailById() {
        MongoRequestDetail requestDetail = requestDetailService.getRequestDetailById("5b446de9e2e7083e872a29c0");
        System.out.println(gson.toJson(requestDetail));
        // logger.debug("RequestDetailServiceImplTest#getRequestDetailById, {}", gson.toJson(requestDetail));
    }

    @Test
    public void countAll() {
        System.out.println(requestDetailService.countAll());
        ;
    }

    @Test
    public void save() {
        MongoRequestDetail requestDetail = new MongoRequestDetail();
        // requestDetail.setId("");
        requestDetail.setDuration(0L);
        requestDetail.setMethodName("1");
        requestDetail.setIpAddress("1");
        requestDetail.setServerIp("1");
        requestDetail.setRequestUri("1");
        requestDetail.setRequestUrl("1");
        requestDetail.setQueryString("1");
        requestDetail.setRequestTime(new Date());
        requestDetail.setSource("1");
        requestDetail.setUserAgent("1");
        requestDetail.setOsName("1");
        requestDetail.setDeviceName("1");
        requestDetail.setAgentTypeName("1");
        requestDetail.setProjectCode("1");
        requestDetail.setCreateTime(new Date());

        requestDetailService.save(requestDetail);
        logger.debug("RequestDetailServiceImplTest#save, requestDetail: {}", gson.toJson(requestDetail));
    }
}