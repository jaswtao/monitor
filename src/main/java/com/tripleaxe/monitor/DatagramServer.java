package com.tripleaxe.monitor;

import com.tripleaxe.monitor.common.PropertiesUtils;
import com.tripleaxe.monitor.common.ThreadPool;
import com.tripleaxe.monitor.task.HandleDatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Created on : 6/29/2018
 *
 * @author Tao
 */
@Component
public class DatagramServer {

    private static final Logger logger = LoggerFactory.getLogger(DatagramServer.class);
    private final ThreadPool threadPool = new ThreadPool(60, TimeUnit.SECONDS, "Monitor");

    @PostConstruct
    public void initialize() {

        while (true) {
            try {
                DatagramSocket server = new DatagramSocket(PropertiesUtils.getInt("socket.port"));
                byte[] receiveData;
                receiveData = new byte[4096];
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                server.receive(packet);
                String receiveMessage = new String(receiveData, StandardCharsets.UTF_8);
                if (logger.isDebugEnabled()) {
                    logger.debug(receiveMessage);
                }
                threadPool.submit(new HandleDatagramPacket(receiveMessage));

            } catch (IOException e) {
                logger.error("server start failed", e);
            }
        }
    }
}
