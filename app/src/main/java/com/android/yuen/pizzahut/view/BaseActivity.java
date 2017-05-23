package com.android.yuen.pizzahut.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yuen.pizzahut.R;
import com.android.yuen.pizzahut.presenter.BasePresenter;
import com.android.yuen.pizzahut.util.SessionUtil;

public class BaseActivity extends AppCompatActivity implements BaseView {

    // Navigation Drawer
    private ListView drawerList;
    protected DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String activityTitle;
    private String[] titles;

    // ActionBar
    private Menu menu;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // ActionBar
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_HOME_AS_UP |
                ActionBar.DISPLAY_USE_LOGO);
        // Set Logo
        getSupportActionBar().setLogo(R.drawable.logo);

        // Navigation Drawer
        drawerList = (ListView) findViewById(R.id.lvDrawers);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        activityTitle = getTitle().toString();

        if (SessionUtil.getUser(this) == null) { // guess
            titles = getResources().getStringArray(R.array.guess_navs);
        } else {
            titles = getResources().getStringArray(R.array.member_navs);
        }

        addDrawerItems();
        setupDrawer();
    }

    /*
     * Navigation Drawer
     */
    private void addDrawerItems() {
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_drawer, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void setupDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

                // fix Navigation drawer items not registering click event error
                drawerList.bringToFront();
                drawerLayout.requestLayout();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Activate the navigation drawer toggle
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        String title = titles[position];

        switch (title) {
            case "Menu":
                startActivity(new Intent(this, MainActivity.class));
                break;
            case "Đăng nhập/Đăng ký":
                startActivity(new Intent(this, UserActivity.class));
                break;
            case "Giỏ hàng":
                startActivity(new Intent(this, CartActivity.class));
                break;
            case "Đặt hàng":
                if (getCount() == 0) {
                    Toast.makeText(this, R.string.checkout_cart_empty, Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, CheckoutActivity.class));
                }
                break;
            case "Bản đồ":
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case "Thoát":
                SessionUtil.removeUser(this);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }

    /*
     * ActionBar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        this.menu = menu;

        getMenuInflater().inflate(R.menu.menu, menu);

        // Search Menu
        MenuItem menuSearch = menu.findItem(R.id.menuSearch);
        menuSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                search();
                return true;
            }
        });

        // Cart Menu
        View menuCart = menu.findItem(R.id.menuCart).getActionView();
        new BasePresenter(this);
        menuCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart();
            }
        });

        return true;
    }

    private void search() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("Tìm kiếm").setIcon(R.drawable.search2);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_search, null);

        builder.setView(view)
            .setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    EditText txtKeyword = (EditText) view.findViewById(R.id.txtKeyword);
                    String keyword = txtKeyword.getText().toString();
                    if (keyword.isEmpty()) {
                        Toast.makeText(BaseActivity.this, R.string.search_keyword_empty, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(BaseActivity.this, SearchActivity.class);
                        intent.putExtra("KEYWORD", keyword);
                        startActivity(intent);
                    }
                }
            })
            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        builder.create().show();
    }

    private void showCart() {
        startActivity(new Intent(BaseActivity.this, CartActivity.class));
    }
    
    /*
     * BaseView implements
     */
    @Override
    public int getCount() {
        View menuCart = menu.findItem(R.id.menuCart).getActionView();
        tvCount = (TextView) menuCart.findViewById(R.id.tvCount);
        return Integer.parseInt(tvCount.getText().toString());
    }

    @Override
    public void setCount(int count) {
        View menuCart = menu.findItem(R.id.menuCart).getActionView();
        tvCount = (TextView) menuCart.findViewById(R.id.tvCount);
        tvCount.setText(String.valueOf(count));
    }

}
