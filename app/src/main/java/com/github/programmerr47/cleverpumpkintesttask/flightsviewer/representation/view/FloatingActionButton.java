/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Oleksandr Melnykov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package com.github.programmerr47.cleverpumpkintesttask.flightsviewer.representation.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ImageButton;

import cleverpumpkintesttask.programmerr47.github.com.flightsviewer.R;

/**
 * Copied from https://github.com/makovkastar/FloatingActionButton
 *
 * @author Oleksandr Melnykov
 */
public class FloatingActionButton extends ImageButton {

    @IntDef({TYPE_NORMAL, TYPE_MINI})
    public @interface TYPE{}
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MINI = 1;

    protected AbsListView mListView;

    private int mScrollY;
    private boolean mVisible;

    private int mColorNormal;
    private int mColorPressed;
    private boolean mShadow;
    private int mType;

    private final ScrollSettleHandler mScrollSettleHandler = new ScrollSettleHandler();
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private final AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int newScrollY = getListViewScrollY();
            if (newScrollY == mScrollY) {
                return;
            }

            if (newScrollY > mScrollY) {
                // Scrolling up
                hide();
            } else if (newScrollY < mScrollY) {
                // Scrolling down
                show();
            }
            mScrollY = newScrollY;
        }
    };

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = getDimension(
                mType == TYPE_NORMAL ? R.dimen.fab_size_normal : R.dimen.fab_size_mini);
        if (mShadow) {
            int shadowSize = getDimension(R.dimen.fab_shadow_size);
            size += shadowSize * 2;
        }
        setMeasuredDimension(size, size);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.mScrollY = mScrollY;

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            mScrollY = savedState.mScrollY;
            super.onRestoreInstanceState(savedState.getSuperState());
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        mVisible = true;
        mColorNormal = getColor(android.R.color.holo_blue_dark);
        mColorPressed = getColor(android.R.color.holo_blue_light);
        mType = TYPE_NORMAL;
        mShadow = true;
        if (attributeSet != null) {
            initAttributes(context, attributeSet);
        }
        updateBackground();
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.FloatingActionButton);
        if (attr != null) {
            try {
                mColorNormal = attr.getColor(R.styleable.FloatingActionButton_fab_colorNormal,
                        getColor(android.R.color.holo_blue_dark));
                mColorPressed = attr.getColor(R.styleable.FloatingActionButton_fab_colorPressed,
                        getColor(android.R.color.holo_blue_light));
                mShadow = attr.getBoolean(R.styleable.FloatingActionButton_fab_shadow, true);
                mType = attr.getInt(R.styleable.FloatingActionButton_fab_type, TYPE_NORMAL);
            } finally {
                attr.recycle();
            }
        }
    }

    private void updateBackground() {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, createDrawable(mColorPressed));
        drawable.addState(new int[]{}, createDrawable(mColorNormal));
        setBackgroundCompat(drawable);
    }

    private Drawable createDrawable(int color) {
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(ovalShape);
        shapeDrawable.getPaint().setColor(color);

        if (mShadow) {
            LayerDrawable layerDrawable = new LayerDrawable(
                    new Drawable[]{getResources().getDrawable(R.drawable.fab_shadow),
                            shapeDrawable});
            int shadowSize = getDimension(
                    mType == TYPE_NORMAL ? R.dimen.fab_shadow_size : R.dimen.fab_mini_shadow_size);
            layerDrawable.setLayerInset(1, shadowSize, shadowSize, shadowSize, shadowSize);
            return layerDrawable;
        } else {
            return shapeDrawable;
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private int getColor(@ColorRes int id) {
        return getResources().getColor(id);
    }

    private int getDimension(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    protected int getListViewScrollY() {
        View topChild = mListView.getChildAt(0);
        return topChild == null ? 0 : mListView.getFirstVisiblePosition() * topChild.getHeight() -
                topChild.getTop();
    }

    private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

    private class ScrollSettleHandler extends Handler {
        private static final int TRANSLATE_DURATION_MILLIS = 200;

        private int mSettledScrollY;

        public void onScroll(int scrollY) {
            if (mSettledScrollY != scrollY) {
                mSettledScrollY = scrollY;
                removeMessages(0);
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            animate().setInterpolator(mInterpolator)
                    .setDuration(TRANSLATE_DURATION_MILLIS)
                    .translationY(mSettledScrollY);
        }
    }

    public void setColorNormal(int color) {
        if (color != mColorNormal) {
            mColorNormal = color;
            updateBackground();
        }
    }

    public void setColorNormalResId(@ColorRes int colorResId) {
        setColorNormal(getColor(colorResId));
    }

    public int getColorNormal() {
        return mColorNormal;
    }

    public void setColorPressed(int color) {
        if (color != mColorPressed) {
            mColorPressed = color;
            updateBackground();
        }
    }

    public void setColorPressedResId(@ColorRes int colorResId) {
        setColorPressed(getColor(colorResId));
    }

    public int getColorPressed() {
        return mColorPressed;
    }

    public void setShadow(boolean shadow) {
        if (shadow != mShadow) {
            mShadow = shadow;
            updateBackground();
        }
    }

    public boolean hasShadow() {
        return mShadow;
    }

    public void setType(@TYPE int type) {
        if (type != mType) {
            mType = type;
            updateBackground();
        }
    }

    @TYPE
    public int getType() {
        return mType;
    }

    protected AbsListView.OnScrollListener getOnScrollListener() {
        return mOnScrollListener;
    }

    public void show() {
        if (!mVisible) {
            mVisible = true;
            mScrollSettleHandler.onScroll(0);
        }
    }

    public void hide() {
        if (mVisible) {
            mVisible = false;
            mScrollSettleHandler.onScroll(getHeight() + getMarginBottom());
        }
    }

    public void attachToListView(@NonNull AbsListView listView) {
        if (listView == null) {
            throw new NullPointerException("AbsListView cannot be null.");
        }
        mListView = listView;
        mListView.setOnScrollListener(mOnScrollListener);
    }

    /**
     * A {@link android.os.Parcelable} representing the {@link FloatingActionButton}'s
     * state.
     */
    public static class SavedState extends BaseSavedState {

        private int mScrollY;

        public SavedState(Parcelable parcel) {
            super(parcel);
        }

        private SavedState(Parcel in) {
            super(in);
            mScrollY = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mScrollY);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
