package com.android.yuen.pizzahut.view;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.adapter.ProductAdapter;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.presenter.SearchPresenter;

import java.util.List;

public class SearchActivity extends BaseActivity implements SearchView {

    TextView tvTitle;
    ProgressBar progressBar;
    ListView lvProducts;

    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        drawerLayout.addView(contentView, 0);

        // Necessarry to send request to webservice
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        keyword = intent.getStringExtra("KEYWORD");
        new SearchPresenter(this, keyword);
    }

    @Override
    public void showTitle() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(String.format("Kết quả tìm kiếm cho \"%s\"", keyword));
    }

    @Override
    public void showProducts(List<Product> products) {
        ProductAdapter productAdapter =
                new ProductAdapter(SearchActivity.this, R.layout.list_products, products);

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
