package cn.edu.szu.file.listener;

import entity.ProgressEntity;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class UploadProgressListener implements ProgressListener {

    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        ProgressEntity status = new ProgressEntity();
        session.setAttribute("status", status);
    }

    @Override
    public void update(long bytesRead, long contentLength, int item) {
        ProgressEntity status = (ProgressEntity) session.getAttribute("status");
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItem(item);
        System.out.println(status);
    }
}
