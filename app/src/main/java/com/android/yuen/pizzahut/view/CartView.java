package com.android.yuen.pizzahut.view;

import com.android.yuen.pizzahut.model.Item;

import java.util.List;

public interface CartView {

    void showCart(List<Item> items);

    void showTotal(int total);

    void hideCart();

}
