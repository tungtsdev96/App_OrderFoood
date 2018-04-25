package com.ptpmcn.orderfood.utils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by tungts on 11/13/2017.
 */

public class Utils {

    public static boolean checkValuesInt(Object values){
        return Pattern.matches("\\d+", (CharSequence) values);
    }

}
