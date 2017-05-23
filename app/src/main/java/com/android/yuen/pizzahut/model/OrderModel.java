package com.android.yuen.pizzahut.model;

import com.android.yuen.pizzahut.util.Const;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class OrderModel {

    public boolean save(Order order) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String url = Const.API_ROOT + "order/save";

        // request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // send request and handle response
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
        System.out.println(entity.getBody().toString());
        Boolean res = restTemplate.postForObject(url, entity, Boolean.class);

        return res;
    }

}
