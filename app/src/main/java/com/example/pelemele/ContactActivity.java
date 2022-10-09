package com.example.pelemele;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ContactActivity extends AppCompatActivity {
    ArrayList<Contact> contacts;
    RecyclerView rvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Log.i("ContactActivity", "Avant la permission");
        checkPermission();
        Log.i("ContactActivity", "findViewById");
        rvContacts = findViewById(R.id.rvContacts);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PERMISSION_GRANTED){
            Log.i("ContactActivity", "permission granted");
        } else {
            Log.i("ContactActivity", "permission not granted");
        }/*
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Contacts permission NOT granted");
            ActivityCompat.requestPermissions(ContactActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST);
        }
        else {
            Log.i(TAG, "Contacts permission granted");
        }*/
        contacts = new ArrayList<Contact>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext()){
            contacts.add(new Contact(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER) )));


        }
        ContactsAdapter adapter = new ContactsAdapter(contacts, this);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(adapter);
    }
    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.READ_CONTACTS)!= PERMISSION_GRANTED)
        {
            Log.i("ContactActivity", "permission not granted");
            //permission is not granted
            ActivityCompat.requestPermissions(ContactActivity.this,new String[]{Manifest.permission.READ_CONTACTS},100);
        }else{
            Log.i("ContactActivity", "permission granted");        }
    }
}