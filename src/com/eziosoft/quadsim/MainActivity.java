package com.eziosoft.quadsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity {

	Handler				mHandler	= new Handler();
	Boolean				killme		= false;

	MainView			v;
	Quad				quad;

	float				dt			= 50;

	PID					pidRoll		= new PID(2f, 0.00001f, 6000f, -100, 100);
	PID					pidAlt		= new PID(3f, 0.0001f, 5000f, 0f, 100f);
	PID					pidPos		= new PID(20f, 0.0001f, 9000f, -4500, 4500);

	float				desiredAlt	= 100;
	float				desiredX	= 200;

	private Runnable	update		= new Runnable() {
										@Override
										public void run() {

											float pos = pidPos.Output(v.quad.x, desiredX, dt);
											float p = pidRoll.Output(v.quad.Roll, pos / 100, dt);
											float t = pidAlt.Output(v.quad.alt, desiredAlt, dt);

											v.quad.SET(p / 100f + t / 100, (1f - p) / 100f + t / 100);

											v.Set();
											// if (!killme)
											mHandler.postDelayed(update, (int) dt);
										}
									};

	float map(float x, float in_min, float in_max, float out_min, float out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new MainView(getApplicationContext());
		setContentView(v);

		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
					case MotionEvent.ACTION_DOWN:

						break;
					case MotionEvent.ACTION_MOVE:
						desiredAlt = v.hh - arg1.getY() - (v.hh - v.GroundLevel);
						desiredX = arg1.getX();
						break;
					case MotionEvent.ACTION_UP:
						break;
				}

				return true;
			}
		});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		killme = false;
		mHandler.removeCallbacks(update);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		killme = true;
		mHandler.postDelayed(update, 100);
	}
}
