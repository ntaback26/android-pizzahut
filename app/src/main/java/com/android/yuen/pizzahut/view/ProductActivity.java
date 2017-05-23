package com.android.yuen.pizzahut.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.model.Category;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.presenter.CartPresenter;
import com.android.yuen.pizzahut.presenter.CategoryPresenter;
import com.android.yuen.pizzahut.presenter.ProductPresenter;
import com.android.yuen.pizzahut.util.Const;
import com.android.yuen.pizzahut.util.ImageUtil;
import com.android.yuen.pizzahut.util.NumberUtil;
import com.android.yuen.pizzahut.util.SessionUtil;

public class ProductActivity extends BaseActivity implements ProductView {

    ProgressBar progressBar;
    LinearLayout productMain;
    ImageView imgProduct;
    TextView tvPrice;
    TextView tvName;
    TextView tvDescription;
    Button btnAddToCart;
    Button btnBack;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_product, null, false);
        drawerLayout.addView(contentView, 0);

        addControls();
        addEvents();
    }

    private void addControls() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        productMain = (LinearLayout) findViewById(R.id.productMain);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
        btnBack = (Button) findViewById(R.id.btnBack);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("PRODUCT");
        new ProductPresenter(this);
    }

    private void addEvents() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NumberPicker numberPicker = new NumberPicker(ProductActivity.this);
                numberPicker.setMinValue(Const.MIN_QUANTITY);
                numberPicker.setMaxValue(Const.MAX_QUANTITY);

                AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
                builder.setTitle("Chọn số lượng cần mua").setIcon(R.drawable.cart);

                builder.setView(numberPicker)
                        .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                CartPresenter cartPresenter = new CartPresenter(ProductActivity.this);
                                int quantity = numberPicker.getValue();
                                cartPresenter.addToCart(product, quantity);
                                startActivity(new Intent(ProductActivity.this, CartActivity.class));
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showProduct() {
        imgProduct.setImageBitmap(ImageUtil.getImage(product.getImageUrl()));
        tvPrice.setText(NumberUtil.getFormattedPrice(product.getPrice()));
        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        productMain.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        productMain.setVisibility(View.VISIBLE);
    }
}
