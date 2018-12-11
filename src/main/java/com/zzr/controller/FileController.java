package com.zzr.controller;

import com.zzr.common.JsonResp;
import com.zzr.util.UploadUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by zhaozhirong on 2018/11/27.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    @ResponseBody
    @RequestMapping("upload")
    public JsonResp uploadFile(
            HttpServletRequest request,
            @RequestParam(value = "file", required = true) MultipartFile file
    ) {
        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());
        System.out.println(request.getSession().getServletContext());
        File uploadFile = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            if (file.isEmpty()) {
                return new JsonResp(JsonResp.FAIL, "Upload file can not be null or empty!");
            }
            is = file.getInputStream();
            String path = request.getSession().getServletContext().getRealPath("/upload/");
            String filePrefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString()+filePrefix;
            String filePath = path+fileName;
            uploadFile = new File(filePath);
            logger.debug("Upload file is: {}", uploadFile.getAbsolutePath());
            fos = new FileOutputStream(uploadFile);
            IOUtils.copy(is, fos);
            fos.flush();
            return new JsonResp("/upload/"+fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return  new JsonResp(JsonResp.FAIL, e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    return  new JsonResp(JsonResp.FAIL, e.getMessage());
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    return  new JsonResp(JsonResp.FAIL, e.getMessage());
                }
            }
        }
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> export(String filePath) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)),
                headers, HttpStatus.CREATED);
    }
}
