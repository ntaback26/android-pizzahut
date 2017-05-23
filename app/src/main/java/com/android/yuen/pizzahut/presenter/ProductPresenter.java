package com.android.yuen.pizzahut.presenter;

import android.os.AsyncTask;

import com.android.yuen.pizzahut.view.ProductActivity;

public class ProductPresenter {

    ProductActivity view;

    public ProductPresenter(ProductActivity view) {
        this.view = view;
        new ProductLoad().execute();
    }

    private class ProductLoad extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            view.startLoading();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            view.stopLoading();
            view.showProduct();
        }
    }

}
