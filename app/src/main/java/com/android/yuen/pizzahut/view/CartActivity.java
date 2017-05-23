package com.android.yuen.pizzahut.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.adapter.CartAdapter;
import com.android.yuen.pizzahut.model.Item;
import com.android.yuen.pizzahut.presenter.CartPresenter;
import com.android.yuen.pizzahut.util.SessionUtil;
import com.android.yuen.pizzahut.util.NumberUtil;

import java.util.List;

public class CartActivity extends BaseActivity implements CartView {

    TextView tvEmpty;
    ListView lvItems;
    TextView tvTotalText;
    TextView tvTotal;
    Button btnBack;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_cart, null, false);
        drawerLayout.addView(contentView, 0);

        addControls();
        addEvents();

        CartPresenter presenter = new CartPresenter(this);
        presenter.loadCart();
    }

    private void addControls() {
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        tvEmpty.setVisibility(View.GONE);
        lvItems = (ListView) findViewById(R.id.lvItems);
        tvTotalText = (TextView) findViewById(R.id.tvTotalText);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionUtil.getUser(CartActivity.this) == null) {
                    Toast.makeText(CartActivity.this, R.string.authentication, Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
                }
            }
        });
    }

    @Override
    public void showCart(List<Item> items) {
        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, R.layout.list_items, items);
        lvItems.setAdapter(cartAdapter);
    }

    @Override
    public void showTotal(int total) {
        tvTotal.setText(NumberUtil.getFormattedPrice(total));
    }

    @Override
    public void hideCart() {
        tvEmpty.setVisibility(View.VISIBLE);
        lvItems.setVisibility(View.GONE);
        tvTotalText.setVisibility(View.GONE);
        tvTotal.setVisibility(View.GONE);
        btnCheckout.setVisibility(View.GONE);
    }
}
