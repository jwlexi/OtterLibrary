package com.example.otterlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class PlaceHold extends AppCompatActivity {

    private LibraryDb AppDatabase;
    Button done, sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placehold);

        AppDatabase = LibraryDb.getInstance(this);
        AppDatabase.loadDatabase();
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        sub = findViewById(R.id.SubmitBook);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cgenre;
                String genre;

                cgenre = findViewById(R.id.genre);
                genre = cgenre.getText().toString();

                List<Book> bookList = AppDatabase.AppDatabase().getUnHeldBooks(genre);

                if (bookList.isEmpty()) {
                    String text = "No books available from this genre.";
                    Toast.makeText(PlaceHold.this, text, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PlaceHold.this, MainActivity.class));
                } else {
                    Intent i = new Intent(PlaceHold.this, books.class);
                    i.putExtra("genre", genre);
                    startActivity(i);
                }
            }
        });
    }

}
