package com.example.pelemele;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;



public class CanvasAccel extends View {

    protected Paint paint ;



    protected Canvas canvas;


    public CanvasAccel(Context context,int x1, int y1 ) {
        super(context);
        paint = new Paint();
        canvas = new Canvas();

        paint.setStrokeWidth(10);
        canvas.drawLine(0, 0, x1, y1, paint);

    }
}
