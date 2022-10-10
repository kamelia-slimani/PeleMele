package com.example.pelemele;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;


public class CanvasRect extends View {

    protected Paint paint;
    protected Canvas canvas;

    protected int x1,y1,x2,y2;

    public CanvasRect(Context context, Paint paint, Canvas canvas, int x1, int y1, int x2, int y2) {
        super(context);
        this.paint = paint;
        this.canvas = canvas;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setAlpha(100);
        canvas.drawRect(x1,y1,x2,y2, paint);
        invalidate();


    }


}
