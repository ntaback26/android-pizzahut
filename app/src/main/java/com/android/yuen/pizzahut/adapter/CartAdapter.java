package com.android.yuen.pizzahut.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.model.Item;
import com.android.yuen.pizzahut.presenter.CartPresenter;
import com.android.yuen.pizzahut.util.Const;
import com.android.yuen.pizzahut.util.ImageUtil;
import com.android.yuen.pizzahut.util.NumberUtil;
import com.android.yuen.pizzahut.view.CartActivity;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Item> {

    Activity context;
    int resource;
    List<Item> objects;

    public CartAdapter(Activity context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(resource, null);

        ImageView imgProduct = (ImageView) row.findViewById(R.id.imgProduct);
        TextView tvName = (TextView) row.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) row.findViewById(R.id.tvPrice);
        ImageButton btnUpdate = (ImageButton) row.findViewById(R.id.btnUpdate);
        ImageButton btnRemove = (ImageButton) row.findViewById(R.id.btnRemove);

        final Item item = objects.get(position);
        imgProduct.setImageBitmap(ImageUtil.getImage(item.getProductImage()));
        tvName.setText(item.getProductName());
        tvPrice.setText(String.format("%d x %s = %s",
                item.getQuantity(),
                NumberUtil.getFormattedPrice(item.getProductPrice()),
                NumberUtil.getFormattedPrice(item.getSubTotal())));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final NumberPicker numberPicker = new NumberPicker(context);
            numberPicker.setMinValue(Const.MIN_QUANTITY);
            numberPicker.setMaxValue(Const.MAX_QUANTITY);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chọn số lượng cần mua").setIcon(R.drawable.cart);

            builder.setView(numberPicker)
                .setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Update DB
                        CartPresenter cartPresenter = new CartPresenter(context, objects);
                        item.setQuantity(numberPicker.getValue());
                        cartPresenter.updateCart(item);

                        // Reload listview
                        objects.set(position, item);
                        notifyDataSetChanged();
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

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update DB
                CartPresenter cartPresenter = new CartPresenter(context, objects);
                cartPresenter.removeCart(item);

                // Reload listview
                objects.remove(item);
                notifyDataSetChanged();
            }
        });

        return row;
    }
}

