package com.example.pelemele;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class LongActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button lancer;
    protected TextView text;
    protected ProgressBar progression;

    protected Handler handler;
    protected Runnable runnable;

    protected Timer t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long);

        //Lancer
        this.lancer = findViewById(R.id.lancer);
        this.lancer.setOnClickListener(this);

        //Progression
        this.progression = findViewById(R.id.progressBar);
        this.progression.setVisibility(View.INVISIBLE);

        //Text
        this.text = findViewById(R.id.temps);

        //Timer
        this.t = new Timer();

        //Handler
        this.handler = new Handler();

        //Thread
        this.runnable = new Runnable() {
            @Override
            public void run() {
                int MAX = 100;
                progression.setMax(MAX);
                runOnUiThread(() -> {
                    progression.setVisibility(View.VISIBLE);
                    lancer.setEnabled(false);
                });
                for (int i = 0; i < MAX; i++) {
                    int progress = i + 1;

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    runOnUiThread(() -> progression.setProgress(progress));
                    int p = (progress * 100) / MAX;

                    runOnUiThread(() -> text.setText("Progression: " + p + " %"));
                }
                runOnUiThread(() ->{
                    text.setText("Fini!");
                    lancer.setEnabled(true);
                    progression.setVisibility(View.INVISIBLE);
                });
                afficherToast();
            }
        };
    }

    @Override
    public void onClick(View view) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(runnable);

    }

    public void afficherToast(){
        runOnUiThread(() -> Toast.makeText(this, "Tâche longue terminé", Toast.LENGTH_SHORT).show());
    }
}