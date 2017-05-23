package com.android.yuen.pizzahut.view;


public interface UserView {

    boolean isValidLogin(String email, String password);

    boolean isValidRegister(String name, String email, String password, String password2);

    void handleLoginSuccess();

    void handleLoginError();

    void handleRegisterSuccess();

    void handleRegisterError();

    void startLoginLoading();

    void stopLoginLoading();

    void startRegisterLoading();

    void stopRegisterLoading();
}
