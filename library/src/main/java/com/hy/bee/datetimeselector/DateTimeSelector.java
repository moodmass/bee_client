package com.hy.bee.datetimeselector;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.hyzc.bee.library.R;

/**
 * Created by Administrator on 2016/12/9.
 */

public class DateTimeSelector extends View {
    private boolean mShowText;
    private int mTextPos;

    public DateTimeSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DateTimeSelector,
                0, 0);

        try {
            mShowText = a.getBoolean(R.styleable.DateTimeSelector_showText, false);
            mTextPos = a.getInteger(R.styleable.DateTimeSelector_labelPosition, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    public boolean isShowText() {
        return mShowText;
    }

    public void setShowText(boolean mShowText) {
        this.mShowText = mShowText;
    }
}
