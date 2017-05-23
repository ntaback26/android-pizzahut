package com.android.yuen.pizzahut.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private String name;
    private String address;
    private String phone;
    private String note;
    private List<Item> items;
    private User user;

    public Order() {
        super();
    }

    public Order(String name, String address, String phone, String note) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.note = note;
    }

    public Order(String name, String address, String phone, String note, List<Item> items, User user) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.note = note;
        this.items = items;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
