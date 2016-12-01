package com.hyzc.bee.client.util;

import android.graphics.Color;

import com.github.johnpersano.supertoasts.SuperToast;
import com.hyzc.bee.client.layout.BeeApplication;

/**
 * Created by Weiping on 2015/11/30.
 */
public class BeeToast {
    private static BeeToast ourInstance = new BeeToast();

    public static BeeToast getInstance() {
        return ourInstance;
    }

    private BeeToast() {
    }

    public void showToast(int text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(BeeApplication.getAppContext());
        superToast.setAnimations(BeeUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(BeeApplication.getAppContext().getResources().getString(text));
        superToast.setBackground(color);
        superToast.getTextView().setTypeface(BeeUtil.typefaceLatoLight);
        superToast.show();
    }

    public void showToast(String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(BeeApplication.getAppContext());
        superToast.setAnimations(BeeUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(color);
        superToast.getTextView().setTypeface(BeeUtil.typefaceLatoLight);
        superToast.show();
    }
}
