package cn.edu.szu.file.resolver;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
@ServerEndpoint("/websocket/{sid}")
@Data
@EqualsAndHashCode
public class WebsocketEndpoint {

    static Log log = LogFactory.getLog(WebsocketEndpoint.class);

    public static final AtomicInteger ONLINE_COUNT =  new AtomicInteger(0);
    public static final CopyOnWriteArraySet<WebsocketEndpoint> WEB_SOCKET_SET = new CopyOnWriteArraySet<>();

    private Session session;
    private String sessionId;

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        WEB_SOCKET_SET.add(this);
        addOnlineCount();
        log.info("有新窗口开始监听:" + sid + ",当前上传人数为" + getOnlineCount());
        this.sessionId = sid;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    @OnClose
    public void onClose() {
        WEB_SOCKET_SET.remove(this);
        subOnlineCount();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized Integer getOnlineCount() {
        return Integer.valueOf(ONLINE_COUNT.toString());
    }

    public static synchronized void addOnlineCount() {
        ONLINE_COUNT.set(getOnlineCount() + 1);
    }

    public static synchronized void subOnlineCount() {
        ONLINE_COUNT.set(getOnlineCount() - 1);
    }
}
