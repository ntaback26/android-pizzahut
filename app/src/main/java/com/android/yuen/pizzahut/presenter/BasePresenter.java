package com.android.yuen.pizzahut.presenter;

import com.android.yuen.pizzahut.model.CartModel;
import com.android.yuen.pizzahut.view.BaseActivity;

public class BasePresenter {

    BaseActivity view;
    CartModel model;

    public BasePresenter(BaseActivity view) {
        this.view = view;
        model = CartModel.getInstance(view);
        view.setCount(model.getCount());
    }

}
