package cn.lframe.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author home-pc
 * @create2018 -03 -22 -10:20
 */
@Component
@ServerEndpoint("/webSocket")//其中的值代表URL
@Slf4j
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet =new CopyOnWriteArraySet<>();

    /**
     * 这些方法是对应于前端的webSocket建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
//        定义websocket容器，用来存储session。
        webSocketSet.add(this);
        log.info("【websocket消息】 有新的连接 , 总数：{}",webSocketSet.size());

    }

    /**
     * 对应于前端的webSocket的断开连接
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("【websocket消息】 连接断开，总数：{}",webSocketSet.size());
    }

    /**
     * 用于收到客户端发来的消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】 收到客户端发来的消息：{}",message);
    }

    /**
     * 发送消息不是那么重要，最好不要影响我们主要的业务，所以直接在方法里面把异常消化掉。
     * @param message
     */
    public void sendMessage(String message){
//        遍历我们存储的websocket。
        for (WebSocket webSocket:webSocketSet){
            log.info("【websocket消息】广播消息，message={}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
