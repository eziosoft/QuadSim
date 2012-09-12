package com.eziosoft.quadsim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class MainView extends View {

	int			ww, hh;
	Rect		dim				= new Rect();
	Paint		p;
	Paint		p1, p3;

	int			textSizeSmall	= 20;
	int			textSizeMedium	= 20;
	int			textSizeBig		= 20;

	String		D				= "";
	String		D1				= "";
	String		D2				= "";

	public Quad	quad;

	float		GroundLevel		= 600;

	public MainView(Context context) {
		super(context);

		getWindowVisibleDisplayFrame(dim);
		ww = dim.width();
		hh = dim.height();

		p = new Paint();
		p.setColor(Color.GREEN);
		p.setAntiAlias(true);
		p.setStyle(Style.FILL);
		// p.setStrokeWidth(1);
		p.setTextSize(textSizeSmall);

		p1 = new Paint();
		p1.setColor(Color.YELLOW);
		p1.setAntiAlias(true);
		p1.setStyle(Style.FILL);
		p1.setStrokeWidth(5);
		p1.setTextSize(textSizeBig);

		p3 = new Paint();
		p3.setColor(Color.GRAY);
		p3.setAntiAlias(true);
		p3.setStyle(Style.STROKE);
		// p3.setStrokeWidth(5);
		p3.setTextSize(textSizeSmall);
		p3.setPathEffect(new DashPathEffect(new float[] { 10, 20 }, 0));
		this.setBackgroundColor(Color.BLACK);

		GroundLevel = hh - 100;
		quad = new Quad(getContext(), GroundLevel, ww, hh);

	}

	public void Set() {
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);

		D = "roll=" + String.valueOf(quad.Roll) + " alt=" + String.valueOf(quad.alt) + " pos=" + String.valueOf(quad.x);
		c.drawText(D, 0, 2 * textSizeBig, p1);
		c.drawText(D1, 0, 3 * textSizeBig, p1);
		c.drawText(D2, 0, 4 * textSizeBig, p1);

		c.drawLine(0, GroundLevel, ww, GroundLevel, p1);

		quad.DrawQuad(c);

	}

}
