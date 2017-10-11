package com.nevermore.oceans.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;

import com.nevermore.oceans.R;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class CountDownButton extends AppCompatButton {

    private String text;
    private String content;

    public CountDownButton(Context context) {
        this(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.CountDownButton);
        long countDownInterval = typedArray.getInteger(R.styleable.CountDownButton_countDownInterval, 1)*1000;
        long millisInFuture = typedArray.getInteger(R.styleable.CountDownButton_millisInFuture, 5)*1000;


        timer = new MyCount(millisInFuture, countDownInterval);

        typedArray.recycle();

    }


    public void start() {
        content = getText().toString().trim();
        timer.start();
        this.setEnabled(false);
    }

    public void cancel() {
        timer.cancel();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }

    private MyCount timer;

    private class MyCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            String time = "" + (millisUntilFinished / 1000);

            Log.d(TAG, "onTick: "+time);
            CountDownButton.this.setText(time);
        }

        private static final String TAG = "MyCount";

        @Override
        public void onFinish() {
            CountDownButton.this.setText(content);
            CountDownButton.this.setEnabled(true);
        }
    }

}