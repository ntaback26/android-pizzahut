package com.android.yuen.pizzahut.view;

import com.android.yuen.pizzahut.model.Category;
import com.android.yuen.pizzahut.model.Product;

import java.util.List;


public interface MainView {

    void showLatestProducts(List<Product> products);

    void showCategories(List<Category> categories);

    void startLoading();

    void stopLoading();

}
