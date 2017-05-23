package com.android.yuen.pizzahut.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.presenter.CartPresenter;
import com.android.yuen.pizzahut.util.Const;
import com.android.yuen.pizzahut.util.ImageUtil;
import com.android.yuen.pizzahut.util.NumberUtil;
import com.android.yuen.pizzahut.view.BaseActivity;
import com.android.yuen.pizzahut.view.CartActivity;
import com.android.yuen.pizzahut.view.ProductActivity;

import java.util.List;


public class ProductAdapter extends ArrayAdapter<Product> {

    Activity context;
    int resource;
    List<Product> objects;

    public ProductAdapter(Activity context, int resource, List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(resource, null);

        ImageView imgProduct = (ImageView) row.findViewById(R.id.imgProduct);
        TextView tvName = (TextView) row.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) row.findViewById(R.id.tvPrice);
        ImageButton btnView = (ImageButton) row.findViewById(R.id.btnViewDetails);
        ImageButton btnAddToCart = (ImageButton) row.findViewById(R.id.btnAddToCart);

        final Product product = objects.get(position);
        imgProduct.setImageBitmap(ImageUtil.getImage(product.getImageUrl()));
        tvName.setText(product.getName());
        tvPrice.setText(NumberUtil.getFormattedPrice(product.getPrice()));

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("PRODUCT", product);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NumberPicker numberPicker = new NumberPicker(context);
                numberPicker.setMinValue(Const.MIN_QUANTITY);
                numberPicker.setMaxValue(Const.MAX_QUANTITY);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Chọn số lượng cần mua").setIcon(R.drawable.cart);

                builder.setView(numberPicker)
                    .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            CartPresenter cartPresenter = new CartPresenter(context);
                            int quantity = numberPicker.getValue();
                            cartPresenter.addToCart(product, quantity);
                            context.startActivity(new Intent(context, CartActivity.class));
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

        return row;
    }
}
