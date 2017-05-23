package com.android.yuen.pizzahut.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.model.User;
import com.android.yuen.pizzahut.presenter.UserPresenter;

import java.util.concurrent.TimeUnit;

public class UserActivity extends BaseActivity implements UserView {

    TabHost tabHost;

    ProgressBar progressBarLogin;
    EditText txtLoginEmail;
    EditText txtLoginPassword;
    Button btnLogin;

    ProgressBar progressBarRegister;
    EditText txtRegisterName;
    EditText txtRegisterEmail;
    EditText txtRegisterPassword;
    EditText txtRegisterPassword2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_user, null, false);
        drawerLayout.addView(contentView, 0);

        addTab();
        addControls();
        addEvents();
    }

    /*
     * Tab view
     */
    private void addTab() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        addNewTab("tabLogin", R.id.tabLogin, "Đăng Nhập");
        addNewTab("tabRegister", R.id.tabRegister, "Đăng Ký");
    }

    private void addNewTab(String tag, int content, String title) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setContent(content);
        tabSpec.setIndicator(getTabIndicator(title));
        tabHost.addTab(tabSpec);
    }

    private View getTabIndicator(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
        TextView tvTabText = (TextView) view.findViewById(R.id.tvTabText);
        tvTabText.setText(title);
        return view;
    }

    /*
     * Add controls
     */
    private void addControls() {
        txtLoginEmail = (EditText) findViewById(R.id.txtLoginEmail);
        txtLoginPassword = (EditText) findViewById(R.id.txtLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtRegisterName = (EditText) findViewById(R.id.txtRegisterName);
        txtRegisterEmail = (EditText) findViewById(R.id.txtRegisterEmail);
        txtRegisterPassword = (EditText) findViewById(R.id.txtRegisterPassword);
        txtRegisterPassword2 = (EditText) findViewById(R.id.txtRegisterPassword2);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.GONE);
        progressBarRegister = (ProgressBar) findViewById(R.id.progressBarRegister);
        progressBarRegister.setVisibility(View.GONE);
    }

    /*
     * Add events
     */
    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtLoginEmail.getText().toString();
                String password = txtLoginPassword.getText().toString();
                if (isValidLogin(email, password)) {
                    new UserPresenter(UserActivity.this, new User(email, password), true);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtRegisterName.getText().toString();
                String email = txtRegisterEmail.getText().toString();
                String password = txtRegisterPassword.getText().toString();
                String password2 = txtRegisterPassword2.getText().toString();
                if (isValidRegister(name, email, password, password2)) {
                    new UserPresenter(UserActivity.this, new User(name, email, password), false);
                }
            }
        });
    }

    /*
     * UserView implements
     */
    @Override
    public boolean isValidLogin(String email, String password) {
        if (email.isEmpty()) {
            txtLoginEmail.setError(getResources().getString(R.string.login_email_empty));
            return false;
        }

        if (password.isEmpty()) {
            txtLoginPassword.setError(getResources().getString(R.string.login_password_empty));
            return false;
        }

        return true;
    }

    @Override
    public boolean isValidRegister(String name, String email, String password, String password2) {
        if (name.isEmpty()) {
            txtRegisterName.setError(getResources().getString(R.string.register_name_empty));
            return false;
        }

        if (email.isEmpty()) {
            txtRegisterEmail.setError(getResources().getString(R.string.register_email_empty));
            return false;
        }

        if (password.isEmpty()) {
            txtRegisterPassword.setError(getResources().getString(R.string.register_password_empty));
            return false;
        }

        if (password.length() < 8) {
            txtRegisterPassword.setError(getResources().getString(R.string.register_password_length));
            return false;
        }

        if (!password2.equals(password)) {
            txtRegisterPassword2.setError(getResources().getString(R.string.register_password2_match));
            return false;
        }

        return true;
    }

    @Override
    public void handleLoginSuccess() {
        Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void handleLoginError() {
        Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleRegisterSuccess() {
        Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
        tabHost.setCurrentTab(0);
    }

    @Override
    public void handleRegisterError() {
        txtRegisterEmail.setError(getResources().getString(R.string.register_error));
    }

    @Override
    public void startLoginLoading() {
        progressBarLogin.setVisibility(View.VISIBLE);
        txtLoginEmail.setVisibility(View.GONE);
        txtLoginPassword.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
    }

    @Override
    public void stopLoginLoading() {
        progressBarLogin.setVisibility(View.GONE);
        txtLoginEmail.setVisibility(View.VISIBLE);
        txtLoginPassword.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void startRegisterLoading() {
        progressBarRegister.setVisibility(View.VISIBLE);
        txtRegisterName.setVisibility(View.GONE);
        txtRegisterEmail.setVisibility(View.GONE);
        txtRegisterPassword.setVisibility(View.GONE);
        txtRegisterPassword2.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
    }

    @Override
    public void stopRegisterLoading() {
        progressBarRegister.setVisibility(View.GONE);
        txtRegisterName.setVisibility(View.VISIBLE);
        txtRegisterEmail.setVisibility(View.VISIBLE);
        txtRegisterPassword.setVisibility(View.VISIBLE);
        txtRegisterPassword2.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.VISIBLE);
    }
}
