package com.qiaosoftware.crm.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public class TestDownloadUtil {
    
    /**
     * 也可以使用HttpMessageConverter实现文件下载
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/testDownload")
    public ResponseEntity<byte[]> testDownload(HttpSession session) throws IOException {
        //把文件写入到一个byte数组中
        ServletContext servletContext = session.getServletContext();
        InputStream in = servletContext.getResourceAsStream("/WEB-INF/abc.txt");
        byte[] body = new byte[in.available()];
        in.read(body);
        
        //设置文件下载相关的响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=abc.txt");
        
        //设置状态码
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(body, headers, statusCode);
        
        return responseEntity;
    }

}
