package com.hyzc.bee.client.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.github.johnpersano.supertoasts.SuperToast;
import com.hyzc.bee.client.R;
import com.hyzc.bee.client.layout.BeeApplication;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by 伟平 on 2015/10/16.
 */

// Todo make BeeUtil singleton
// Todo floating labels in english

public class BeeUtil {

    public static int editRecordPosition = -1;

    public static String LOGO_PATH = "/sdcard/logo/";

    public static String LOGO_NAME = "logo.jpg";

    public static SuperToast.Animations TOAST_ANIMATION = SuperToast.Animations.FLYIN;

    public static int beeBlue;


    public static String PASSWORD = "1234";


    public static String[] FLOATINGLABELS = {
            "",
            "",
            "十",
            "百",
            "千",
            "万",
            "十万",
            "百万",
            "千万",
            "亿",
            "十亿"};

    public static String[] BUTTONS = {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "D",
            "0",
            "Y"
    };


    public static String[] TAG_HEADER_URL = {
            "http://file.bmob.cn/M02/5A/B5/oYYBAFajMcmAWxDmAAAAX54YFR4292.png",
            "http://file.bmob.cn/M02/5A/B6/oYYBAFajMguAMEjaAACp5TYCw2c161.jpg",
            "http://file.bmob.cn/M02/5A/B7/oYYBAFajMjeAUJ6bAACp5TYCw2c909.jpg",
            "http://file.bmob.cn/M02/5A/B9/oYYBAFajMsCASQSTAAST9c5hnKk541.jpg",
            "http://file.bmob.cn/M02/5A/B9/oYYBAFajMt6ACKvpAAFCadMFDZ0001.jpg",
            "http://file.bmob.cn/M02/5A/BA/oYYBAFajMxaAcCHVAAFMn10yiSc776.jpg",
            "http://file.bmob.cn/M02/5A/BB/oYYBAFajM0WAeSXuAAPMSTPknmg198.jpg",
            "http://file.bmob.cn/M02/5A/BB/oYYBAFajM2uAF2dAAAERt2oghvQ131.jpg",
            "http://file.bmob.cn/M02/5A/BD/oYYBAFajM9mAfvhbAAEAaJskdeI618.jpg",
            "http://file.bmob.cn/M02/5A/BE/oYYBAFajM_GAY_sdAAIiUMzp8aA126.jpg",
            "http://file.bmob.cn/M02/5A/BF/oYYBAFajNBCAfPCYAAIXneirgI8186.jpg",
            "http://file.bmob.cn/M02/5A/C0/oYYBAFajNEuAdQRXAAEeDe0Ytew460.jpg",
            "http://file.bmob.cn/M02/5A/C1/oYYBAFajNGeAHKBYAACR7VAH6tY388.jpg",
            "http://file.bmob.cn/M02/5A/C4/oYYBAFajNOOANYPbAAIMih4I730340.jpg",
            "http://file.bmob.cn/M02/5A/C4/oYYBAFajNP6AREhrAAFRYvfSI1o564.jpg",
            "http://file.bmob.cn/M02/5A/C5/oYYBAFajNRyASQs4AABNrzBmPsU695.jpg",
            "http://file.bmob.cn/M02/5A/C5/oYYBAFajNTCAZJWYAACFnWVbblw284.jpg",
            "http://file.bmob.cn/M02/5A/C6/oYYBAFajNUaACmmCAACd5x8slZY981.jpg",
            "http://file.bmob.cn/M02/5A/C6/oYYBAFajNWKALgknAAHaJgXlKLI169.jpg",
            "http://file.bmob.cn/M02/5A/C7/oYYBAFajNZyAVA0mAAX6uqgz5os812.png",
            "http://file.bmob.cn/M02/5A/CC/oYYBAFajNoeAWF96AAYHTWlvWA8779.png",
            "http://file.bmob.cn/M02/5A/D0/oYYBAFajN4uAcFDhAAbKaer4urk522.png",
            "http://file.bmob.cn/M02/5A/D3/oYYBAFajODSAe5iEAAgDnDRtjG0045.png",
            "http://file.bmob.cn/M02/5A/D6/oYYBAFajOh6AH4ZKAANQpB8MUBQ569.png",
            "http://file.bmob.cn/M02/5A/D6/oYYBAFajOmSAZHFRAAbpe8mI4v0254.png",
            "http://file.bmob.cn/M02/5A/D7/oYYBAFajOpWAKl8gAAZ8d8Z0BAM967.png",
            "http://file.bmob.cn/M02/5A/D7/oYYBAFajOr-Aapj2AAaQOo6Zvzs039.png",
            "http://file.bmob.cn/M02/5A/D8/oYYBAFajOv2ANQlZAAg-CYhjjLQ254.png",
            "http://file.bmob.cn/M02/5A/D9/oYYBAFajOyyAaEqvAAavKIaVqfs654.png",
            "http://file.bmob.cn/M02/5A/DA/oYYBAFajO3WAMt9LAAdYtJz1cCE193.png",
            "http://file.bmob.cn/M02/5A/E1/oYYBAFajPwqAAVQaAAd1j6hRHZw363.png",
            "http://file.bmob.cn/M02/5A/E9/oYYBAFajQruAdJYeAAd3DzoZwNk311.png",
            "http://file.bmob.cn/M02/5A/F4/oYYBAFajRpiAAczeAAgXftIUsqk135.png"
    };


    public static Typeface typefaceLatoRegular = null;
    public static Typeface typefaceLatoHairline = null;
    public static Typeface typefaceLatoLight = null;

    public static void init(Context context) {

        typefaceLatoRegular = Typeface.createFromAsset(
                context.getAssets(), "fonts/Ping-Fang.ttf");
        typefaceLatoHairline = Typeface.createFromAsset(
                context.getAssets(), "fonts/Lato-Hairline.ttf");
        typefaceLatoLight = Typeface.createFromAsset(
                context.getAssets(), "fonts/LatoLatin-Light.ttf");


        relativeSizeSpan = new RelativeSizeSpan(2f);
        redForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ff5252"));
        greenForegroundSpan = new ForegroundColorSpan(Color.parseColor("#4ca550"));
        whiteForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ffffff"));

        lastColor0 = "";
        lastColor1 = "";
        lastColor2 = "";

        random = new Random();

    }

    public static Typeface GetTypeface() {
        if (typefaceLatoLight == null) init(BeeApplication.getAppContext());
        if ("en".equals(Locale.getDefault().getLanguage()))
            return typefaceLatoLight;
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return Typeface.DEFAULT;
        return typefaceLatoLight;
    }

    public static String GetLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String GetWhetherBlank() {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "";
        else
            return " ";
    }

    public static String GetWhetherFuck() {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "日";
        else
            return "";
    }

    public static String GetInMoney(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "¥" + money;
        else
            return "$" + money + " ";
    }

    public static String GetInRecords(int records) {
        return records + "'s";
    }

    public static String GetSpendString(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "消费 ¥" + money;
        else
            return "Spend $" + money + " ";
    }

    public static String GetSpendString(double money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "消费 ¥" + (int) money;
        else
            return "Spend $" + (int) money + " ";
    }

    public static String GetPercentString(double percent) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return " (占" + String.format("%.2f", percent) + "%)";
        else
            return " (takes " + String.format("%.2f", percent) + "%)";
    }

    public static String GetPurePercentString(double percent) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return " " + String.format("%.2f", percent) + "%";
        else
            return " " + String.format("%.2f", percent) + "%";
    }


    public static boolean WEEK_START_WITH_SUNDAY = false;


    public static Calendar GetTodayLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetTodayRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetYesterdayLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetYesterdayRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (BeeUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        }
        return calendar;
    }

    public static Calendar GetThisWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetLastWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (BeeUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        }
        return calendar;
    }

    public static Calendar GetLastWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetNextWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (BeeUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        }
        return calendar;
    }

    public static Calendar GetNextWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetNextWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetThisWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetLastWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetThisMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisYearRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastYearRightRange(Calendar today) {
        Calendar calendar = (Calendar) today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static boolean ClickButtonDelete(int position) {
        return position == 9;
    }

    public static boolean ClickButtonCommit(int position) {
        return position == 11;
    }

    public static boolean ClickButtonIsZero(int position) {
        return position == 10;
    }

    public static double ToDollas(double money, String currency) {

        return 1.0 * money;

    }

    public static boolean IsStringRelation(String s1, String s2) {
        return true;
    }

    public static RelativeSizeSpan relativeSizeSpan;
    public static ForegroundColorSpan redForegroundSpan;
    public static ForegroundColorSpan greenForegroundSpan;
    public static ForegroundColorSpan whiteForegroundSpan;

    private static String lastColor0, lastColor1, lastColor2;

    private static Random random;

    private static String[] Colors = {"#F44336",
            "#E91E63",
            "#9C27B0",
            "#673AB7",
            "#3F51B5",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};

    public static int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        while (Colors[p].equals(lastColor0)
                || Colors[p].equals(lastColor1)
                || Colors[p].equals(lastColor2)) {
            p = random.nextInt(Colors.length);
        }
        lastColor0 = lastColor1;
        lastColor1 = lastColor2;
        lastColor2 = Colors[p];
        return Color.parseColor(Colors[p]);
    }


    public static String GetTagUrl(int tagId) {
        return TAG_HEADER_URL[tagId + 3];
    }


    public static <K, V extends Comparable<V>> Map<K, V> SortTreeMapByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k1).compareTo(map.get(k2));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    private static final int[] EMPTY_STATE = new int[]{};

    public static void clearState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(EMPTY_STATE);
        }
    }


    public static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = BeeApplication.getAppContext()
                .getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = BeeApplication.getAppContext()
                    .getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getDeeperColor(int color) {
        int alpha = Color.alpha(color);
        int red = (int) (Color.red(color) * 0.8);
        int green = (int) (Color.green(color) * 0.8);
        int blue = (int) (Color.blue(color) * 0.8);
        return Color.argb(alpha, red, green, blue);
    }

    public static int getAlphaColor(int color) {
        int alpha = 6;
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static void getKeyBoard(MaterialEditText editText, Context context) {
        editText.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void getKeyBoard(EditText editText) {
        editText.requestFocus();
        InputMethodManager keyboard = (InputMethodManager)
                BeeApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = BeeApplication.getAppContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int getToolBarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return mActionBarSize;
    }


    public static void showToast(Context context, String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(color);
        superToast.show();
    }

    private static String lastToast = "";

    public static void showToast(Context context, String text) {
        if (context == null) return;
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.VERY_SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(SuperToast.Background.BLUE);
        superToast.show();
    }

    public static void showToast(Context context, int textId) {
        String text = context.getResources().getString(textId);
        if (context == null) return;
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.VERY_SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(SuperToast.Background.BLUE);
        superToast.show();
    }

    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

    public static int GetScreenWidth(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int GetScreenHeight(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static Point GetScreenSize(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Double INPUT_MIN_EXPENSE = 0d;
    public static Double INPUT_MAX_EXPENSE = 99999d;

    public static String GetCurrentVersion() {
        return "Bee V" + BeeApplication.VERSION / 100 + "." + BeeApplication.VERSION % 100 / 10 + "." + BeeApplication.VERSION % 10;
    }

    public static String GetString(Context context, int i) {
        return context.getResources().getString(i);
    }


    // the tagId is clothes, food, house and traffic
    public static int IsCFHT(int tagId) {
        if (tagId == 2) {
            return 0;
        } else if (tagId == -3 || tagId == -2 || tagId == -1 || tagId == 0 || tagId == 15 || tagId == 19 || tagId == 20) {
            return 1;
        } else if (tagId == 3 || tagId == 24) {
            return 2;
        } else if (tagId == 4 || tagId == 5) {
            return 3;
        }
        return -1;
    }

    public static int textCounter(String s) {
        int counter = 0;
        for (char c : s.toCharArray()) {
            if (c < 128) {
                counter++;
            } else {
                counter += 2;
            }
        }
        return counter;
    }

    public static void copyToClipboard(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }


    private static BeeUtil ourInstance = new BeeUtil();

    public static BeeUtil getInstance() {
        if (ourInstance == null || typefaceLatoLight == null || typefaceLatoHairline == null) {
            ourInstance = new BeeUtil();
            init(BeeApplication.getAppContext());
        }
        return ourInstance;
    }

    private BeeUtil() {
    }
}
