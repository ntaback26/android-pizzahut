package com.android.yuen.pizzahut.presenter;

import android.content.Context;

import com.android.yuen.pizzahut.model.CartModel;
import com.android.yuen.pizzahut.model.Item;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.util.Const;
import com.android.yuen.pizzahut.view.CartActivity;

import java.util.List;

public class CartPresenter {

    CartActivity view;
    CartModel model;

    List<Item> items;

    public CartPresenter(Context context) {
        this.model = CartModel.getInstance(context);
    }

    public CartPresenter(Context context, List<Item> items) {
        this.model = CartModel.getInstance(context);
        this.view = (CartActivity) context;
        this.items = items;
    }

    public CartPresenter(CartActivity view) {
        this.view = view;
        this.model = CartModel.getInstance(view);
    }

    public void loadCart() {
        List<Item> items = model.findAll();
        if (items.size() > 0) {
            view.showCart(items);
            view.showTotal(model.getTotal(items));
        } else {
            view.hideCart();
        }
    }

    public void addToCart(Product product, int quantity) {
        Item item = model.findOne(product.getId());
        if (item == null) { // insert
            model.insert(product, quantity);
        } else { // update
            if (quantity + item.getQuantity() <= Const.MAX_QUANTITY) {
                quantity += item.getQuantity();
            } else {
                quantity = Const.MAX_QUANTITY;
            }
            item.setQuantity(quantity);
            model.update(item);
        }
    }

    public void updateCart(Item item) {
        model.update(item);
        view.showTotal(model.getTotal(items));
    }

    public void removeCart(Item item) {
        model.delete(item);
        items.remove(item);
        view.setCount(items.size());
        view.showTotal(model.getTotal(items));
    }

}
