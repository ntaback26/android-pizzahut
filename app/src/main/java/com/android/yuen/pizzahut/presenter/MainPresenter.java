package com.android.yuen.pizzahut.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.yuen.pizzahut.model.Category;
import com.android.yuen.pizzahut.model.CategoryModel;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.model.ProductModel;
import com.android.yuen.pizzahut.view.MainActivity;

import java.util.List;

public class MainPresenter {

    MainActivity view;
    ProductModel productModel;
    CategoryModel categoryModel;

    public MainPresenter(MainActivity view) {
        this.view = view;
        this.productModel = new ProductModel();
        this.categoryModel = new CategoryModel();
        new MainLoad().execute();
    }

    private class MainLoad extends AsyncTask<Void, Void, Void> {
        List<Product> latestProducts;
        List<Category> categories;

        @Override
        protected void onPreExecute() {
            view.startLoading();
        }

        @Override
        protected Void doInBackground(Void... params) {
            latestProducts = productModel.findLatest();
            categories = categoryModel.findAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            view.stopLoading();
            view.addTab();
            view.showLatestProducts(latestProducts);
            view.showCategories(categories);
        }
    }
}
