package com.android.yuen.pizzahut.view;

import com.android.yuen.pizzahut.model.Product;

import java.util.List;

public interface CategoryView {

    void showName();

    void showProducts(List<Product> products);

    void startLoading();

    void stopLoading();

}
