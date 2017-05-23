package com.android.yuen.pizzahut.model;

import com.android.yuen.pizzahut.util.Const;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class CategoryModel {

    public List<Category> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String url = Const.API_ROOT + "categories";

        // send request and handle response
        Category[] res = restTemplate.getForObject(url, Category[].class);
        List<Category> categories = Arrays.asList(res);

        return categories;
    }

}
