package com.example.pelemele;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.pelemele.R.id.start;
import static java.time.LocalTime.now;

public class ChronometreActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageButton start,stop;
    protected Chronometer chrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);
        Toast.makeText(this, "Chronomètre", Toast.LENGTH_SHORT).show() ;

        //CHRONO
        this.chrono = (Chronometer)findViewById(R.id.chrono);

        //START
        this.start = findViewById(R.id.start) ;
        this.start.setOnClickListener(this);

        //STOP
        this.stop = findViewById(R.id.stop) ;
        this.stop.setOnClickListener(this);
        this.stop.setEnabled(false);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.start:

                Toast.makeText(ChronometreActivity.this,"HEURE : "+ now().getHour() +"h "+ now().getMinute() + "min " + now().getSecond() + "s", Toast.LENGTH_SHORT).show();
                this.chrono.setBase(SystemClock.elapsedRealtime());
                this.chrono.start();

                //start inaccessible
                this.start.setEnabled(false);
                this.stop.setEnabled(true);
                break;

            case R.id.stop:

                Toast.makeText(ChronometreActivity.this, "TEMPS ÉCOULÉ : "+ (SystemClock.elapsedRealtime()-this.chrono.getBase())/1000+ " s", Toast.LENGTH_SHORT).show();
                this.chrono.setBase(SystemClock.elapsedRealtime());
                this.chrono.stop();

                // stop inaccessible
                this.stop.setEnabled(false);
                this.start.setEnabled(true);
                break;

        }
    }
}