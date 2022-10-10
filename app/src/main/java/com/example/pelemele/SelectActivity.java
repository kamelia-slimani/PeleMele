package com.example.pelemele;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Icon;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static java.time.LocalTime.now;

public class SelectActivity extends AppCompatActivity{
    protected ImageView photoSelect;
    protected View view;
    protected MotionEvent mv;
    protected SurfaceView sv;
    protected SurfaceHolder sh;
    protected  CanvasRect cr;
    protected RelativeLayout rl;


    double x,y, w,z;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        this.photoSelect = findViewById(R.id.photoSelect);
        this.sv = findViewById(R.id.surfaceView);
        this.rl = findViewById(R.id.rl);
        sv.getHolder().setFormat(PixelFormat.TRANSPARENT);
        sv.setZOrderOnTop(true);

        this.photoSelect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                rl.removeView(cr);

                if(motionEvent.getPointerCount() ==2) {
                    if(motionEvent.getActionIndex() == MotionEvent.ACTION_DOWN) {
                        x = motionEvent.getX(0);
                        y = motionEvent.getY(0);
                    }
                        w = motionEvent.getX(1);
                        z = motionEvent.getY(1);


                }


                Log.i("SelectActivity.java", "x = " + x + "\ny = " + y + "\nw = " + w + "\nz = " + z );

                creerRectangle();

                return true;
            }
        });

       /* AlertDialog.Builder builder = new AlertDialog.Builder(SelectActivity.this);
        builder.setView(cr);
        builder.show();*/
    }
    public void creerRectangle() {

        Canvas c = new Canvas();
        Paint p = new Paint();
        cr = new CanvasRect(SelectActivity.this, p,c, (int) w,(int)z,(int)x,(int)y);
        rl.addView(cr);

    }



}
