package com.android.yuen.pizzahut.presenter;

import android.app.Activity;
import android.os.AsyncTask;

import com.android.yuen.pizzahut.model.CartModel;
import com.android.yuen.pizzahut.model.Order;
import com.android.yuen.pizzahut.model.OrderModel;
import com.android.yuen.pizzahut.util.SessionUtil;
import com.android.yuen.pizzahut.view.CheckoutActivity;

public class CheckoutPresenter {

    CheckoutActivity checkoutView;
    OrderModel orderModel;
    CartModel cartModel;

    String name;
    String phone;
    String address;
    String note;

    public CheckoutPresenter(CheckoutActivity checkoutView, String name, String phone, String address, String note) {
        this.checkoutView = checkoutView;
        this.orderModel = new OrderModel();
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.note = note;

        this.cartModel = CartModel.getInstance(checkoutView);

        new CheckoutLoad().execute();
    }

    private class CheckoutLoad extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            checkoutView.startLoading();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Order order = new Order(name, phone, address, note);
            order.setItems(cartModel.findAll());
            order.setUser(SessionUtil.getUser((Activity) checkoutView));
            return orderModel.save(order);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            checkoutView.stopLoading();
            if (success) {
                checkoutView.handleSuccess();
            } else {
                checkoutView.handleError();
            }
        }
    }
}
