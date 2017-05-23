package com.android.yuen.pizzahut.model;

import com.android.yuen.pizzahut.util.Const;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class ProductModel {

    public List<Product> findLatest() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        String url = Const.API_ROOT + "products/latest";

        // send request and handle response
        Product[] res = restTemplate.getForObject(url, Product[].class);
        List<Product> products = Arrays.asList(res);

        return products;
    }

    public List<Product> findByCategory(Category category) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder sb = new StringBuilder();
        sb.append(Const.API_ROOT).append("category/").append(category.getId()).append("/products");
        String url = sb.toString();

        // send request and handle response
        Product[] res = restTemplate.getForObject(url, Product[].class);
        List<Product> products = Arrays.asList(res);

        return products;
    }

    public List<Product> search(String keyword) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringBuilder sb = new StringBuilder();
        sb.append(Const.API_ROOT).append("product/search?keyword=").append(keyword);
        String url = sb.toString();

        // send request and handle response
        Product[] res = restTemplate.getForObject(url, Product[].class);
        List<Product> products = Arrays.asList(res);

        return products;
    }

}
