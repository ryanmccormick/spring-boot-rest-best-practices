package com.example.integration.contacts;

import com.example.model.Contact;
import org.json.JSONException;
import org.json.JSONObject;
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

    public JSONObject constructContact(String firstName, String lastName, String phone) {
        JSONObject contactBody = new JSONObject();

        try {
            if(null != firstName) {
                contactBody.put("firstName", firstName);
            }
            contactBody.put("lastName", lastName);
            contactBody.put("phone", phone);

            return contactBody;
        } catch(JSONException e) {
            return null;
        }
    }

}
