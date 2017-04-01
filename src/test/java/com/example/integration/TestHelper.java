package com.example.integration;

import com.example.model.Contact;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 3/31/17.
 */
@Component
public class TestHelper {


    public HttpEntity getRequestHeaders() {
        List<MediaType> acceptTypes = new ArrayList<MediaType>();
        acceptTypes.add(MediaType.APPLICATION_JSON_UTF8);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity<String>("parameters", reqHeaders);
    }

    public HttpEntity getPostRequestHeaders(String jsonPostBody) {
        List<MediaType> acceptTypes = new ArrayList<MediaType>();
        acceptTypes.add(MediaType.APPLICATION_JSON_UTF8);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity<String>(jsonPostBody, reqHeaders);
    }

    public String contactUrlHelper(String resourceUrl, String resourceId) {
        return resourceUrl + "/" + resourceId;
    }

}
