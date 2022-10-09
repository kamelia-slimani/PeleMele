package com.example.pelemele;


import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity  extends AppCompatActivity{
    protected Button bonjour, chrono, photo, image, activite, meteo, contact, capteur, selection;

    protected MenuItem quitter, suspendre, l_chrono;
    protected ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bonjour = findViewById(R.id.bonjour);
        this.chrono = findViewById(R.id.chronometre);
        this.photo = findViewById(R.id.photo);
        this.image = findViewById(R.id.image);
        this.activite = findViewById(R.id.activitelongue);
        this.meteo = findViewById(R.id.meteo);
        this.contact = findViewById(R.id.contact);
        this.capteur = findViewById(R.id.capteur_activity);
        this.selection = findViewById(R.id.selection);


        //PRENDRE UNE PHOTO
        this.launcher = registerForActivityResult(
                // Contrat qui détermine le type de l'interaction
                new ActivityResultContracts.StartActivityForResult(),
                // Callback appelé lorsque le résultat sera disponible
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        Toast.makeText(MainActivity.this, "Hauteur : " + bitmap.getHeight(), Toast.LENGTH_SHORT).show();
                        FileOutputStream fos;
                        try {
                            fos = openFileOutput("image.data", MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        );

        //AFFICHAGE INFO
        Log.i("MainActivity", "une info");

        //MENU

        this.quitter = findViewById(R.id.quitter);
        this.l_chrono = findViewById(R.id.lancer_chrono);
        this.suspendre = findViewById(R.id.suspendre);


        }
        public void onClickBonjour(View view) {
            Toast.makeText(MainActivity.this, "Bonjour", Toast.LENGTH_SHORT).show();
        }
        public void onClickPhoto(View view) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            launcher.launch(i);
            }
        public void onClickChrono(View view) {
            Intent i = new Intent(MainActivity.this, ChronometreActivity.class);
            startActivity(i);
        }
        public void onClickImage(View view) {
            Intent i = new Intent(MainActivity.this, ImageActivity.class);
            startActivity(i);
        }

        public void onClickActivity(View view) {
            Intent i = new Intent(MainActivity.this, LongActivity.class);
            startActivity(i);
        }

    public void onClickMeteo(View view) {
        Intent i = new Intent(MainActivity.this, MeteoActivity.class);
        startActivity(i);
    }

    public void onClickContact(View view) {
        Intent i = new Intent(MainActivity.this, ContactActivity.class);
        startActivity(i);
    }

    public void onClickCapteur(View view) {
        Intent i = new Intent(MainActivity.this, CapteurActivity.class);
        startActivity(i);
    }

    public void onClickSelection(View view) {
        Intent i = new Intent(MainActivity.this, SelectionActivity.class);
        startActivity(i);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quitter:
                finish();
                onDestroy();
                return true;
            case R.id.suspendre:
                finish();
                return true;
            case R.id.lancer_chrono:
                onClickChrono(this.chrono);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}