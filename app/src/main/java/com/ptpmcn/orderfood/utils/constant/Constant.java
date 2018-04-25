package com.ptpmcn.orderfood.utils.constant;

/**
 * Created by tungts on 9/29/2017.
 */

public final class Constant {

    public interface GoogleMap{
        public static final float DEFAULT_ZOOM = 15f;

    }

    public interface Item{
        public static final int ITEM_FOOD = 102;
        public static final int ITEM_FOOD_OF_CART = 555;
        public static final int ITEM_FOOD_OF_RESTAURANT = 101;
        public static final int ITEM_LOAD_MORE = 1000;
    }

    public interface Action{
        public static final String ACTION_FILTER_FOOD = "Filter Food";
        public static final String ACTION_HISTORY_ORDER = "History order";
        public static final String ACTION_WAITING_ORDER = "Waiting order";
    }

    public interface RequestCode{
        public static final int REQUEST_FILTER_FOOD = 999;
        public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    }

    public interface Order{
        public static final int PRICE_SHIP = 5000; ///*5000đ/km
        public static final double PRECENT_SERVICE = 0.1; ///10%
    }

    public interface Key{
        public static final String KEY_CART = "cart";
        public static final String KEY_RESTAURANT = "restaurant";
        public static final int KEY_LIKE = 888;
        public static final int KEY_RES = 111;
    }

}
