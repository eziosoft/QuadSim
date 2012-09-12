package com.eziosoft.quadsim;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Quad {

	float			ww			= 480, hh = 800;
	float			x			= 000, y = 800;

	float			vx			= 0, vy = 0;
	float			g			= 1f;
	float			r			= 20, vr = 0;

	public float	Roll		= 0;
	public float	alt			= 0;

	float			m1			= 0.50001f, m2 = 0.5f;

	Paint			p, p3;

	public float	GroundLevel	= 600;
	Context			context;
	Bitmap			b1, b2;

	public Quad(Context context, float groundLevel, float ww, float hh) {

		this.context = context;
		p = new Paint();
		p.setColor(Color.GREEN);

		GroundLevel = groundLevel;
		this.ww = ww;
		this.hh = hh;

		x = ww / 2;

		p3 = new Paint();
		p3.setColor(Color.WHITE);
		p3.setAntiAlias(true);
		p3.setStyle(Style.FILL_AND_STROKE);
		p3.setStrokeWidth(4);

		b1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.q1);
		b2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.q2);
	}

	public void SET(float m1, float m2) {
		Random rnd = new Random();
		// vr += (rnd.nextDouble() - 0.5f) / 2;

		// vx+=rnd.nextDouble();

		if (m1 > 1)
			m1 = 1;
		if (m1 < 0)
			m1 = 0;
		if (m2 > 1)
			m2 = 1;
		if (m2 < 0)
			m2 = 0;

		if (m1 > this.m1)
			this.m1 += 0.1f;
		if (m1 < this.m1)
			this.m1 -= 0.1f;

		if (m2 > this.m2)
			this.m2 += 0.1f;
		if (m2 < this.m2)
			this.m2 -= 0.1f;

		// this.m1 = (float) (m1);
		// this.m2 = (float) (m2);
	}

	private void Phisics() {
		vr = (m1 - m2) + vr;
		r += vr;
		if (r > 360)
			r = 360 - r;

		vx += Math.cos((r - 90) * Math.PI / 180) * (m1 + m2);
		vy += Math.sin((r - 90) * Math.PI / 180) * (m1 + m2);

		y += vy;
		x += vx;

		if (y > GroundLevel) {
			y = GroundLevel;
			r = 0;
			vr=0;
		}
		else {
			vy += g;
			x += vx;
		}

		alt = GroundLevel - y;
		Roll = r;
	}

	Boolean	a	= false;

	public void DrawQuad(Canvas c) {
		float scale = 0.3f;
		Phisics();

		float x1, y1, x2, y2;
		x1 = (float) (x + b1.getWidth() / 2 * scale / 2 * Math.cos(r * Math.PI / 180));
		x2 = (float) (x + b1.getWidth() / 2 * scale / 2 * Math.cos((180 + r) * Math.PI / 180));

		y1 = (float) (y + b1.getWidth() / 2 * scale / 2 * Math.sin(r * Math.PI / 180));
		y2 = (float) (y + b1.getWidth() / 2 * scale / 2 * Math.sin((180 + r) * Math.PI / 180));

		c.drawCircle(x1, y1, 10 * m2, p);
		c.drawCircle(x2, y2, 10 * m1, p);

		Matrix matrix = new Matrix();

		matrix.preRotate(r, x, y);
		matrix.preTranslate(x - b1.getWidth() / 2 * scale, y - b1.getHeight() * scale);
		matrix.preScale(scale, scale);

		if (a) {
			c.drawBitmap(b1, matrix, new Paint());
			a = !a;
		}
		else {
			c.drawBitmap(b2, matrix, new Paint());
			a = !a;
		}
	}
}
