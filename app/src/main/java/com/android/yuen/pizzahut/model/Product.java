package com.android.yuen.pizzahut.model;

import com.android.yuen.pizzahut.util.Const;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;


public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private String image;
    private int price;
    private Category category;

    public Product() {
        super();
    }

    public Product(int id, String name, String description, String image, int price, Category category) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return Const.IMAGE_ROOT + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
