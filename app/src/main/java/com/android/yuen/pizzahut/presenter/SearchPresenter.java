package com.android.yuen.pizzahut.presenter;

import android.os.AsyncTask;

import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.model.ProductModel;
import com.android.yuen.pizzahut.view.SearchActivity;

import java.util.List;

public class SearchPresenter {

    SearchActivity view;
    ProductModel model;

    String keyword;

    public SearchPresenter(SearchActivity view, String keyword) {
        this.view = view;
        this.model = new ProductModel();
        this.keyword = keyword;
        new SearchLoad().execute();
    }

    private class SearchLoad extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected void onPreExecute() {
            view.showTitle();
            view.startLoading();
        }

        @Override
        protected List<Product> doInBackground(Void... params) {
            return model.search(keyword);
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            view.stopLoading();
            view.showProducts(products);
        }
    }

}
