package com.android.yuen.pizzahut.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {

    public static String getFormattedPrice(int price) {
        return NumberFormat.getNumberInstance(Locale.US).format(price) + " đ";
    }

}
