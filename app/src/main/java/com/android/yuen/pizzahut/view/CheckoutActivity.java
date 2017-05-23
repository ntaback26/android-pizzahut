package com.android.yuen.pizzahut.view;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.presenter.CheckoutPresenter;
import com.android.yuen.pizzahut.util.Const;

import java.util.concurrent.TimeUnit;

public class CheckoutActivity extends BaseActivity implements CheckoutView {

    ProgressBar progressBar;
    TextView tvTitle;
    EditText txtName;
    EditText txtPhone;
    EditText txtAddress;
    EditText txtNote;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_checkout, null, false);
        drawerLayout.addView(contentView, 0);

        // Necessarry to send request to webservice
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        addControls();
        addEvents();
    }

    private void addControls() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtNote = (EditText) findViewById(R.id.txtNote);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void addEvents() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String phone = txtPhone.getText().toString();
                String address = txtAddress.getText().toString();
                String note = txtNote.getText().toString();
                if (isValidOrder(name, phone, address)) {
                    new CheckoutPresenter(CheckoutActivity.this, name, phone, address, note);
                }
            }
        });
    }

    @Override
    public boolean isValidOrder(String name, String phone, String address) {
        if (name.isEmpty()) {
            txtName.setError(getResources().getString(R.string.checkout_name_empty));
            return false;
        }

        if (phone.isEmpty()) {
            txtPhone.setError(getResources().getString(R.string.checkout_phone_empty));
            return false;
        }

        if (address.isEmpty()) {
            txtName.setError(getResources().getString(R.string.checkout_address_empty));
            return false;
        }

        return true;
    }

    @Override
    public void handleSuccess() {
        Toast.makeText(this, R.string.checkout_success, Toast.LENGTH_LONG).show();
        startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
    }

    @Override
    public void handleError() {
        Toast.makeText(this, R.string.checkout_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        txtName.setVisibility(View.GONE);
        txtPhone.setVisibility(View.GONE);
        txtAddress.setVisibility(View.GONE);
        txtNote.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        txtName.setVisibility(View.VISIBLE);
        txtPhone.setVisibility(View.VISIBLE);
        txtAddress.setVisibility(View.VISIBLE);
        txtNote.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.VISIBLE);
    }
}
