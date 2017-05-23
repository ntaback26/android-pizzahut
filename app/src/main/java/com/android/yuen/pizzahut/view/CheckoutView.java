package com.android.yuen.pizzahut.view;

public interface CheckoutView {

    boolean isValidOrder(String name, String phone, String address);

    void handleSuccess();

    void handleError();

    void startLoading();

    void stopLoading();

}
