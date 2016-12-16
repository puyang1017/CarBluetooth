package Util;

import android.content.Context;

/**
 * Created by puy on 2016-11-16.
 */

public class Util {

    public static int dp2px(float dp, Context mContext) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
