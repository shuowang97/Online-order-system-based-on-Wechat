package com.imooc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jws.Oneway;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    //创建一个容器保存所有session 这里不能是普通的set
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("【websocket消息】有新的连接，总数={}", webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开，总数={}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到客户端发来的消息：{}", message);
    }
/*
    @OnError
    public void onError(){
        log.error("【websocket异常】{}");
    }*/

    //发送消息 使用广播
    public void sendMessage(String message){
        for(WebSocket webSocket : webSocketSet) {
            log.info("【websocket消息】广播消息，message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                //打印出现的异常
                e.printStackTrace();
            }
        }
    }





}
