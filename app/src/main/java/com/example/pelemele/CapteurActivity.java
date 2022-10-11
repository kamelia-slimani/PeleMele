package com.example.pelemele;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CapteurActivity extends AppCompatActivity {

    protected SensorManager sensorManager;
    protected Sensor accelerometre, magnetometre;

    protected Boolean active;

    protected Switch capteur;

    protected TextView valeurA, valeurM;
    protected ImageView boussole;

    protected Paint paint;
    private float[] gravity = new float[3];
    private float[] magnetique = new float[3];

    private float[] orientation = new float[3];
    private float[] matrix = new float[9];

    protected  CanvasAccel canvasAccel;
    protected RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.capteur = findViewById(R.id.on_off);
        this.valeurA = findViewById(R.id.valAccel);
        this.valeurM = findViewById(R.id.valMagneto);
        this.boussole = findViewById(R.id.boussole);
        this.rl = findViewById(R.id.fleche);

        this.boussole.setVisibility(View.INVISIBLE);

        this.active = false;
        this.paint = new Paint();
        this.paint.setStrokeWidth(10);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnetometre = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);



        SensorEventListener sensorEventListenerAccelerometre = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                gravity = event.values;
                valeurA.setText(getResources().getText(R.string.Accelerometre)+" : \nx = "+gravity[0] + "\ny = "+gravity[1] +"\nz = "+gravity[2]);
                SensorManager.getRotationMatrix(matrix, null, gravity, magnetique);
                SensorManager.getOrientation(matrix, orientation);

                Canvas c = new Canvas();
                Paint p = new Paint();
                canvasAccel = new CanvasAccel(CapteurActivity.this,p, c, (int)gravity[0],(int)gravity[1], (int) gravity[2]);
                rl.addView(canvasAccel);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };


        SensorEventListener sensorEventListenerMagnetometre = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                magnetique = event.values;

                valeurM.setText(getResources().getText(R.string.Magnetometre)+" : \nx = "+magnetique[0] + "\ny = "+magnetique[1] +"\nz = "+magnetique[2]);
                SensorManager.getRotationMatrix(matrix, null, gravity, magnetique);
                SensorManager.getOrientation(matrix, orientation);

                boussole.setRotation((float) (-orientation[0] * 180 / 3.14159));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        this.capteur.setOnClickListener(v -> {

            if (!active) {

                sensorManager.registerListener(sensorEventListenerAccelerometre, accelerometre, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(sensorEventListenerMagnetometre, magnetometre, SensorManager.SENSOR_DELAY_NORMAL);
                capteur.setText(getResources().getText(R.string.desactivation));
                boussole.setVisibility(View.VISIBLE);



            } else {

                sensorManager.unregisterListener(sensorEventListenerAccelerometre);
                sensorManager.unregisterListener(sensorEventListenerMagnetometre);
                capteur.setText(getResources().getText(R.string.activation));
                boussole.setVisibility(View.INVISIBLE);
                valeurA.setText("");
                valeurM.setText("");

            }
            active = !active;
        });


    }
}

/*
    public void onClickSwitch() {
        if(!this.active){

            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
            this.capteur.setText("Désactivation");

        }
        else {
            sensorManager.unregisterListener(this);
            this.capteur.setText("Activation");

        }
        this.active = !this.active;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mx = event.values[0];
            my = event.values[1];
            mz = event.values[2];
        }

        double magnitude = Math.sqrt((mx * mx) + (my * my) + (mz * mz));
        CanvasAccel c = new CanvasAccel(this, (int) ax, (int) ay);
        // this.imageView.draw(c.canvas);
        Log.i("CapteurActivity", "x "+ ax +" y"+ ay +" z"+ az);
        Log.i("CapteurActivity","mag " + magnitude);



        double m=0;

        if(mx < 0.0 && (my < 0.01 && my > 0.0))
            m = 180;
        else if(mx > 0.0 && (my < 0.01 && my > 0.0))
            m = 0;
        else if(my > 0.0)
            m = 90 - (Math.atan(mx/my))*(180/Math.PI);
        else if(my < 0.0)
            m = 270 - (Math.atan(mx/my))*(180/Math.PI);


    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}*/
