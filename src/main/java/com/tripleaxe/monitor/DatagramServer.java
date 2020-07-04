package com.tripleaxe.monitor;

import com.tripleaxe.monitor.common.PropertiesUtils;
import com.tripleaxe.monitor.task.HandleDatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on : 6/29/2018
 * Author     : TerryTao
 */
@Component
public class DatagramServer {

    private static final Logger logger = LoggerFactory.getLogger(DatagramServer.class);
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @PostConstruct
    public void initialize() {
        new Thread(() -> {
            try {
                DatagramSocket server = new DatagramSocket(PropertiesUtils.getInt("socket.port"));
                byte[] receiveData;
                while (true) {
                    receiveData = new byte[4096];
                    DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                    server.receive(packet);
                    String receiveMessage = new String(receiveData, StandardCharsets.UTF_8);
                    if (logger.isDebugEnabled()) {
                        logger.debug(receiveMessage);
                    }
                    executorService.submit(new HandleDatagramPacket(receiveMessage));
                }
            } catch (IOException e) {
                logger.error("启动服务出错", e);
            }
        }).start();
        logger.info("程序启动成功");
    }
}
