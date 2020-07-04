package com.tripleaxe.monitor.common;

import com.tripleaxe.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

/**
 * Created on : 7/2/2018
 * Author     : TerryTao
 */
@Configuration
public class PropertiesUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    private static Properties properties;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * 获取string型的属性
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        if (properties == null) {
            throw new ServiceException("没找到配置文件");
        }
        return properties.getProperty(key);
    }

    /**
     * 获取整形属性配置
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        if (properties == null) {
            throw new ServiceException("没找到配置文件");
        }
        String value = properties.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            logger.error("类型转换出错", e);
            return 0;
        }
    }

    @PostConstruct
    public void init() {
        if (properties == null) {
            properties = new Properties();
        }
        try {
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("config-" + activeProfile + ".properties"));
        } catch (IOException e) {
            logger.error("加载配置文件失败", e);
        }
    }

}
