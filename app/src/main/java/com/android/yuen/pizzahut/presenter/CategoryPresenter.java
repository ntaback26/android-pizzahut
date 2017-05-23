package com.android.yuen.pizzahut.presenter;

import android.os.AsyncTask;

import com.android.yuen.pizzahut.model.Category;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.model.ProductModel;
import com.android.yuen.pizzahut.view.CategoryActivity;

import java.util.List;

public class CategoryPresenter {

    ProductModel model;
    CategoryActivity view;

    Category category;

    public CategoryPresenter(CategoryActivity view, Category category) {
        this.view = view;
        this.model = new ProductModel();
        this.category = category;
        new CategoryLoad().execute();
    }

    private class CategoryLoad extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected void onPreExecute() {
            view.showName();
            view.startLoading();
        }

        @Override
        protected List<Product> doInBackground(Void... params) {
            return model.findByCategory(category);
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            view.stopLoading();
            view.showProducts(products);
        }
    }
}
