package com.example.otterlibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddBook extends AppCompatActivity {

    private LibraryDb db;
    Button home, sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        db = LibraryDb.getInstance(this);
        db.loadDatabase();
        home = findViewById(R.id.home);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
        sub = findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ctitle, cgenre, cauthor;
                String title, genre, author;

                ctitle = findViewById(R.id.title);
                title = ctitle.getText().toString();
                cgenre = findViewById(R.id.genre);
                genre = cgenre.getText().toString();
                cauthor = findViewById(R.id.author);
                author = cauthor.getText().toString();


                List<Book> bookList = db.AppDatabase().getBookByTitle(title);

                if (!bookList.isEmpty()) {
                    AlertDialog dialog = new AlertDialog.Builder(AddBook.this)
                            .setTitle("Invalid information")
                            .setMessage("Book is either in the database already or a field is empty.")
                            .setPositiveButton("Go Home", (dialog1, which) -> {
                                startActivity(new Intent(AddBook.this, MainActivity.class));
                            })
                            .create();
                    dialog.show();
                } else {
                    Book newBook = new Book(title, author, genre, 0);
                    db.AppDatabase().addBook(newBook);

                    String text = "Book successfully added.";
                    Toast.makeText(AddBook.this, text, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddBook.this, MainActivity.class));
                }
            }
        });
    }

}