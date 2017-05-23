package com.android.yuen.pizzahut.presenter;

import android.app.Activity;
import android.os.AsyncTask;

import com.android.yuen.pizzahut.model.User;
import com.android.yuen.pizzahut.model.UserModel;
import com.android.yuen.pizzahut.util.SessionUtil;
import com.android.yuen.pizzahut.view.UserActivity;


public class UserPresenter {

    UserActivity view;
    UserModel model;

    User user;

    public UserPresenter(UserActivity view, User user, boolean isLogin) {
        this.view = view;
        this.model = new UserModel();
        this.user = user;
        if (isLogin) {
            new LoginLoad().execute();
        } else {
            new RegisterLoad().execute();
        }
    }

    private class LoginLoad extends AsyncTask<Void, Void, User> {
        @Override
        protected void onPreExecute() {
            view.startLoginLoading();
        }

        @Override
        protected User doInBackground(Void... params) {
            return model.checkLogin(user);
        }

        @Override
        protected void onPostExecute(User user) {
            view.stopLoginLoading();
            if (user != null) {
                SessionUtil.setUser((Activity) view, user);
                view.handleLoginSuccess();
            } else {
                view.handleLoginError();
            }
        }
    }

    private class RegisterLoad extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            view.startRegisterLoading();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return model.register(user);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            view.stopRegisterLoading();
            if (success) {
                view.handleRegisterSuccess();
            } else {
                view.handleRegisterError();
            }
        }
    }
}
