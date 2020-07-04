package com.tripleaxe.monitor.task;

import com.google.gson.Gson;
import com.tripleaxe.commons.util.CustomGsonBuilder;
import com.tripleaxe.commons.util.StringUtils;
import com.tripleaxe.monitor.common.ApplicationContextProvider;
import com.tripleaxe.monitor.common.CachedUserAgentStringParser;
import com.tripleaxe.monitor.common.PropertiesUtils;
import com.tripleaxe.monitor.mapper.RequestDetailMapper;
import com.tripleaxe.monitor.model.MongoRequestDetail;
import com.tripleaxe.monitor.model.RequestDetail;
import com.tripleaxe.monitor.repository.MongoCustomerRepository;
import net.sf.uadetector.ReadableUserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * Created on : 7/1/2018
 * Author     : TerryTao
 */
public class HandleDatagramPacket implements Runnable {

    private static final CachedUserAgentStringParser PARSER = new CachedUserAgentStringParser();

    /**
     * 日志记录对象
     */
    private static final Logger logger = LoggerFactory.getLogger(HandleDatagramPacket.class);

    private final String message;

    private final Gson gson = CustomGsonBuilder.createGson();

    public HandleDatagramPacket(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            // String decodedStr = new String(Base64.getDecoder().decode(message.getBytes()), StandardCharsets.UTF_8);
            MongoRequestDetail requestDetail = gson.fromJson(message, MongoRequestDetail.class);
            //UserAgentStringParser parser = new CachedUserAgentStringParser();
            ReadableUserAgent agent = PARSER.parse(requestDetail.getUserAgent());
            requestDetail.setDeviceName(agent.getDeviceCategory().getName());
            requestDetail.setOsName(agent.getOperatingSystem().getName());
            requestDetail.setAgentTypeName(agent.getTypeName());
            if ("mysql".equalsIgnoreCase(PropertiesUtils.getString("data.mode"))) {
                handleWithMysql(requestDetail);
            } else if ("mongo".equalsIgnoreCase(PropertiesUtils.getString("data.mode"))) {
                handleWithMongo(requestDetail);
            }
        } catch (Exception e) {
            logger.error("处理接收到的信息失败,message: " + message, e);
        }
    }

    /**
     * 存储到mongodb
     *
     * @param requestDetail
     */
    private void handleWithMongo(RequestDetail requestDetail) {
        MongoRequestDetail detail = new MongoRequestDetail();
        BeanUtils.copyProperties(requestDetail, detail);
        MongoCustomerRepository repository = (MongoCustomerRepository) ApplicationContextProvider.getContext().getBean("mongoCustomerRepository");
        repository.save(detail);
    }

    /**
     * 存储到mysql
     *
     * @param requestDetail
     */
    private void handleWithMysql(RequestDetail requestDetail) {
        requestDetail.setId(StringUtils.generateUUID());
        RequestDetailMapper mapper = (RequestDetailMapper) ApplicationContextProvider.getContext().getBean("requestDetailMapper");
        mapper.insertSelective(requestDetail);
    }
}
