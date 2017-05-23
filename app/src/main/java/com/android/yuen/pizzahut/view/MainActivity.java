package com.android.yuen.pizzahut.view;

import android.content.Context;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.adapter.CategoryAdapter;
import com.android.yuen.pizzahut.adapter.ProductAdapter;
import com.android.yuen.pizzahut.model.Category;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.presenter.MainPresenter;
import com.android.yuen.pizzahut.util.Const;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView {

    ProgressBar progressBar;
    TabHost tabHost;
    ListView lvProducts;
    ListView lvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawerLayout.addView(contentView, 0);

        // Necessarry to send request to webservice
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new MainPresenter(MainActivity.this);
    }

    /*
     * Tab
     */
    public void addTab() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        addNewTab("tabLatest", R.id.tabLatest, "Thực đơn mới nhất");
        addNewTab("tabCategories", R.id.tabCategories, "Danh mục");
    }

    public void addNewTab(String tag, int content, String title) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setContent(content);
        tabSpec.setIndicator(getTabIndicator(title));
        tabHost.addTab(tabSpec);
    }

    public View getTabIndicator(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
        TextView tvTabText = (TextView) view.findViewById(R.id.tvTabText);
        tvTabText.setText(title);
        return view;
    }

    /*
     * MainView implements
     */
    @Override
    public void showLatestProducts(List<Product> products) {
        ProductAdapter productAdapter =
                new ProductAdapter(this, R.layout.list_products, products);

        lvProducts = (ListView) findViewById(R.id.lvProducts);
        lvProducts.setAdapter(productAdapter);
    }

    @Override
    public void showCategories(List<Category> categories) {
        CategoryAdapter categoryAdapter =
                new CategoryAdapter(this, R.layout.list_categories, categories);

        lvCategories = (ListView) findViewById(R.id.lvCategories);
        lvCategories.setAdapter(categoryAdapter);
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
