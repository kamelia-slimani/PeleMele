package com.example.pelemele;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

public class CanvasAccel extends View {

    protected Paint paint;
    protected Canvas canvas;

    protected int x,y,z;

    public CanvasAccel(Context context, Paint paint, Canvas canvas, int x, int y, int z) {
        super(context);
        this.paint = paint;
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
       // paint.setAlpha(100);
        canvas.drawLine(0,0,x,y, paint);
        invalidate();


    }
}
