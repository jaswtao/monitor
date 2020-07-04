package com.tripleaxe.monitor.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created on : 7/9/2018
 * Author     : TerryTao
 */
@Document(collection = "request_detail")
public class MongoRequestDetail extends RequestDetail {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
