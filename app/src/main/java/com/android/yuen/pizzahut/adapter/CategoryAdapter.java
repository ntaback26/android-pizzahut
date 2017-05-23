package com.android.yuen.pizzahut.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.model.Category;
import com.android.yuen.pizzahut.model.Product;
import com.android.yuen.pizzahut.presenter.CategoryPresenter;
import com.android.yuen.pizzahut.view.CategoryActivity;
import com.android.yuen.pizzahut.view.CategoryView;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    Activity context;
    int resource;
    List<Category> objects;

    public CategoryAdapter(Activity context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(resource, null);

        TextView tvCategoryName = (TextView) row.findViewById(R.id.tvName);
        ImageButton btnViewProducts = (ImageButton) row.findViewById(R.id.btnViewProducts);

        final Category category = objects.get(position);
        tvCategoryName.setText(category.getName());

        btnViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("CATEGORY", category);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return row;
    }

}
