package com.example.otterlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CancelHold extends AppCompatActivity {
    Button done, submit;
    private LibraryDao mLogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelhold);

        LibraryDb db = LibraryDb.getInstance(this);
        db.loadDatabase();
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        submit = findViewById(R.id.submit);


    }
}
