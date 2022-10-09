package com.example.pelemele;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.MotionEventCompat;

import static android.view.MotionEvent.actionToString;

public class SelectionActivity extends AppCompatActivity {

    protected ImageView photoSelect;
    protected View view;
    protected MotionEvent mv;
    protected SurfaceView sv;
    protected SurfaceHolder sh;
    double x,y, w,z;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        this.photoSelect = findViewById(R.id.photoSelect);
        this.sv = findViewById(R.id.surfaceView);
        sv.getHolder().setFormat(PixelFormat.TRANSPARENT);
        sv.setZOrderOnTop(true);

        this.photoSelect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                    Log.i("SelectionActivity", "Multitouch event");
                    if(motionEvent.getPointerCount() > 1) {
                        if(motionEvent.getActionIndex() == MotionEvent.ACTION_DOWN) {
                            x = motionEvent.getX(0);
                            y = motionEvent.getY(0);
                        }
                        if(motionEvent.getActionIndex() == MotionEvent.ACTION_POINTER_DOWN) {
                            w = motionEvent.getX(motionEvent.getActionIndex());
                            z = motionEvent.getY(motionEvent.getActionIndex());
                        }
                    }


                Log.i("SelectionActivity.java", "x = " + x + "\ny = " + y + "\nw = " + w + "\nz = " + z );
                creerRectangle();

                return true;
            }
        });

    }
    public void creerRectangle() {

        Rect rect = new Rect();
        rect.set((int) x, (int) y, (int) w, (int) z);
        Log.i("SelectionActivity.java", rect.toString());
        Canvas c = new Canvas();
        Paint p = new Paint();
        p.setStrokeWidth(10);
        c.drawRect(rect, p);
        sv.draw(c);


    }


}