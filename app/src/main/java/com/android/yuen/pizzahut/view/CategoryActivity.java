package com.android.yuen.pizzahut.view;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.adapter.ProductAdapter;
import com.android.yuen.pizzahut.model.Category;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.presenter.CategoryPresenter;

import java.util.List;

public class CategoryActivity extends BaseActivity implements CategoryView {

    TextView tvName;
    ProgressBar progressBar;
    ListView lvProducts;

    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_category, null, false);
        drawerLayout.addView(contentView, 0);

        // Necessarry to send request to webservice
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("CATEGORY");
        new CategoryPresenter(this, category);
    }

    @Override
    public void showName() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(category.getName());
    }

    @Override
    public void showProducts(List<Product> products) {
        ProductAdapter productAdapter =
                new ProductAdapter(CategoryActivity.this, R.layout.list_products, products);

        lvProducts = (ListView) findViewById(R.id.lvProducts);
        lvProducts.setAdapter(productAdapter);
    }

    @Override
    public void startLoading() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
}
