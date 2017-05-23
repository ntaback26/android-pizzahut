package com.android.yuen.pizzahut.model;

import java.io.Serializable;

public class Item implements Serializable {

    private int productId;
    private String productImage;
    private String productName;
    private int productPrice;
    private int quantity;

    public Item() {
        super();
    }

    public Item(int productId, String productImage, String productName, int productPrice, int quantity) {
        super();
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSubTotal() {
        return quantity * productPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "productId=" + productId +
                ", productImage='" + productImage + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                '}';
    }
}
