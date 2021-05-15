package cn.edu.szu.file.resolver;

import cn.edu.szu.file.config.UploadProgressTimerConfig;
import cn.edu.szu.file.listener.UploadProgressListener;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class CustomMultipartResolver extends CommonsMultipartResolver {

    @Autowired private UploadProgressListener uploadProgressListener;

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {

        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);

//        uploadProgressListener.setSession(request.getSession());
//        UploadProgressTimerConfig.CONCURRENT_HASH_MAP.put(request.getRequestedSessionId(), request.getSession());
//        fileUpload.setProgressListener(uploadProgressListener);
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadBase.FileSizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getFileSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Failed to parse multipart servlet request", ex);
        }
    }
}
