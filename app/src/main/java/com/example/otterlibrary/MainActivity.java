package com.example.otterlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button cancel, create, place, manage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LibraryDb db = LibraryDb.getInstance(this);
        db.loadDatabase();

        cancel = findViewById(R.id.CancelHold);
        create = findViewById(R.id.CreateAccount);
        place = findViewById(R.id.PlaceHold);
        manage = findViewById(R.id.ManageSystem);
        cancel.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Login.class)));
        create.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateAccount.class)));
        place.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PlaceHold.class)));
        manage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ManageSystem.class)));
    }
}