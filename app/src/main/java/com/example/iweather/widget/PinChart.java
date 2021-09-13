package com.example.iweather.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

import com.example.iweather.activity.Main2Activity;
import com.example.iweather.activity.MainActivity;
import com.example.iweather.util.MySharedpreference;

@SuppressLint("DrawAllocation") public class PinChart extends View {

	static Canvas c;
	private Paint[] mPaints;
	private Paint mTextPaint;
	private RectF mBigOval;
	private String cityname;
	private SharedPreferences.Editor  editor;       //共享参数
	public float[] mSweep = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final float SWEEP_INC = 1;

	public static float[] humidity = { 110, 40, 50, 60, 50, 50 };

	public PinChart(Context context) {
		super(context);

	}

	public PinChart(Context context, AttributeSet atr) {
		super(context, atr);
	}

	@SuppressLint("DrawAllocation") @Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);// 设置背景颜色(透明)

		mPaints = new Paint[humidity.length];
		for (int i = 0; i < humidity.length; i++) {
			mPaints[i] = new Paint();
			mPaints[i].setAntiAlias(true);
			mPaints[i].setStyle(Paint.Style.FILL);
			mPaints[i].setColor(0x880FF000 + i * 0x019e8860);
		}

		mBigOval = new RectF(40, 30, 220, 200);// 饼图的四周边界-->左，上，右，下

		mTextPaint = new Paint();// 绘制文本
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(80F);// 设置温度值的字体大小
		float start = 0;
		for (int i = 0; i < humidity.length; i++) {
			canvas.drawArc(mBigOval, start, mSweep[i], true, mPaints[i]);
			start += humidity[i];
			if (mSweep[i] < humidity[i]) {
				mSweep[i] += SWEEP_INC;
			}
			canvas.drawRect(new RectF(300, 30 + 15 * i, 312, 42 + 15 * i),
					mPaints[i]);
			cityname=MySharedpreference.preferences.getString("City","西安");
			canvas.drawText(cityname , 420, 40 + 15 * 20, mTextPaint);
		}
		invalidate();
	}

}
