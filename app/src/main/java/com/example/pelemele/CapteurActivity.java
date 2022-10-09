package com.example.pelemele;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CapteurActivity extends AppCompatActivity implements SensorEventListener{

    protected SensorManager sensorManager;
    protected Sensor accelerometre, magnetometre;

    protected Boolean active;

    protected Switch capteur;


    protected Paint paint;

    protected float ax, ay, az, mx, my, mz;

    protected ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.capteur = findViewById(R.id.on_off);
        this.active = false;
        this.paint = new Paint();
        this.paint.setStrokeWidth(10);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.capteur.setOnClickListener(v -> onClickSwitch());
        this.imageView = findViewById(R.id.canvas);


    }

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
}