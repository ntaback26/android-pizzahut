package com.android.yuen.pizzahut.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.yuen.pizzahut.util.Const;

import java.util.ArrayList;
import java.util.List;

public class CartModel extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "pizzahut";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ITEM = "item";

    // Item Table Columns
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRODUCT_IMAGE = "product_image";
    private static final String KEY_PRODUCT_PRICE = "product_price";
    private static final String KEY_QUANTITY = "quantity";

    private static CartModel instance;

    public static CartModel getInstance(Context context) {
        if (instance == null) {
            instance = new CartModel(context);
        }
        return instance;
    }

    private CartModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM +
            "(" +
                KEY_PRODUCT_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                KEY_PRODUCT_NAME + " VARCHAR(255) NOT NULL, " +
                KEY_PRODUCT_IMAGE + " VARCHAR(255) NOT NULL, " +
                KEY_PRODUCT_PRICE + " INTEGER NOT NULL, " +
                KEY_QUANTITY + " INTEGER NOT NULL" +
            ")";
        db.execSQL(CREATE_ITEM_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
            onCreate(db);
        }
    }

    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();

        String SELECT_ALL_QUERY = String.format("SELECT * FROM %s", TABLE_ITEM);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_QUERY, null);
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setProductId(cursor.getInt(0));
            item.setProductName(cursor.getString(1));
            item.setProductImage(cursor.getString(2));
            item.setProductPrice(cursor.getInt(3));
            item.setQuantity(cursor.getInt(4));
            items.add(item);
        }
        cursor.close();

        return items;
    }

    public Item findOne(int productId) {
        Item item = null;

        String SELECT_ONE_QUERY = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_ITEM, KEY_PRODUCT_ID);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ONE_QUERY, new String[]{String.valueOf(productId)});
        if (cursor.moveToNext()) {
            item = new Item();
            item.setProductId(cursor.getInt(0));
            item.setProductName(cursor.getString(1));
            item.setProductImage(cursor.getString(2));
            item.setProductPrice(cursor.getInt(3));
            item.setQuantity(cursor.getInt(4));
        }
        cursor.close();

        return item;
    }

    public long insert(Product product, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, product.getId());
        values.put(KEY_PRODUCT_NAME, product.getName());
        values.put(KEY_PRODUCT_IMAGE, Const.IMAGE_ROOT + product.getImage());
        values.put(KEY_PRODUCT_PRICE, product.getPrice());
        values.put(KEY_QUANTITY, quantity);
        return db.insert(TABLE_ITEM, null, values);
    }

    public long update(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUANTITY, item.getQuantity());
        return db.update(TABLE_ITEM, values, KEY_PRODUCT_ID + "=?",
                new String[]{String.valueOf(item.getProductId())});
    }

    public long delete(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEM, KEY_PRODUCT_ID + "=?",
                new String[]{String.valueOf(item.getProductId())});
    }

    public int getCount() {
        int count = 0;

        String COUNT_QUERY = String.format("SELECT COUNT(*) FROM %s", TABLE_ITEM);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(COUNT_QUERY, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public int getTotal(List<Item> items) {
        int total = 0;
        for (Item item : items) {
            total += item.getProductPrice() * item.getQuantity();
        }
        return total;
    }
}
