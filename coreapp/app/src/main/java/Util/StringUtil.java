package Util;

/**
 * Created by alancosta on 5/20/16.
 */
public class StringUtil {

    public static final String EMPTY_STRING = "";

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals(EMPTY_STRING);
    }
}
