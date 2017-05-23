package com.android.yuen.pizzahut.model;


import com.android.yuen.pizzahut.util.Const;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class UserModel {

    public User checkLogin(User user) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String url = Const.API_ROOT + "login";

        // request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // send request and handle response
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        User res = restTemplate.postForObject(url, entity, User.class);

        return res;
    }

    public boolean register(User user) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String url = Const.API_ROOT + "register";

        // request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // send request and handle response
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        Boolean res = restTemplate.postForObject(url, entity, Boolean.class);

        return res;
    }

}
