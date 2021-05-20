package cn.edu.szu.file.config;

import cn.edu.szu.file.resolver.WebsocketEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.ProgressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class UploadProgressTimerConfig {

    @Autowired private ObjectMapper objectMapper;

    public static final Map<String, HttpSession> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>(2);

    @Scheduled(cron = "0/1 * * * * *")
    public void sendMsg() {
        CopyOnWriteArraySet<WebsocketEndpoint> copyOnWriteArraySet = WebsocketEndpoint.WEB_SOCKET_SET;
        copyOnWriteArraySet.forEach(c -> {
            String sid = c.getSessionId();
            if (CONCURRENT_HASH_MAP.containsKey(sid)) {
                HttpSession session = CONCURRENT_HASH_MAP.get(sid);
                ProgressEntity progressEntity = (ProgressEntity) session.getAttribute("status");
                if (progressEntity.getBytesRead() == progressEntity.getContentLength()) {
                    CONCURRENT_HASH_MAP.remove(sid);
                }

                try {
                    c.sendMessage(objectMapper.writeValueAsString(objectMapper));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
