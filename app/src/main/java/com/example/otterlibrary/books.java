package com.example.otterlibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class books extends AppCompatActivity {

    private LibraryDb db;
    private List<Book> bookList;
    private ListView bookListView;
    private ArrayAdapter<Book> bookAdapter;
    private String passedString = "";
    Button EnterBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        db = LibraryDb.getInstance(this);
        db.loadDatabase();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            passedString = extras.getString("genre");
        }

        bookList = db.AppDatabase().getUnHeldBooks(passedString);
        bookListView = findViewById(R.id.books);
        updateUI();

        EnterBook = findViewById(R.id.EnterBook);
        EnterBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText titleEditText = new EditText(books.this);
                final String[] title = new String[1];

                AlertDialog holdBook = new AlertDialog.Builder(books.this)
                        .setTitle("What book from the list would you like to place on hold?")
                        .setMessage("Enter title here")
                        .setView(titleEditText)
                        .setPositiveButton("Place Hold", (dialog1, which) -> {
                            title[0] = String.valueOf(titleEditText.getText());
                            bookList = db.AppDatabase().getBookByTitle(title[0]);

                            if (bookList.isEmpty() || bookList.get(0).isOnHold() == 1) {
                                AlertDialog dialog = new AlertDialog.Builder(books.this)
                                        .setTitle("The book entered is not available")
                                        .setMessage("Please try again")
                                        .setPositiveButton("Go Home", (dialog2, which2) -> {
                                            startActivity(new Intent(books.this, MainActivity.class));
                                        })
                                        .create();
                                dialog.show();
                            } else {
                                Intent i = new Intent(books.this, Login.class);
                                Bundle extraInfo = new Bundle();
                                extraInfo.putString("title", title[0]);
                                i.putExtras(extraInfo);
                                startActivity(i);
                            }
                        })
                        .create();
                holdBook.show();
            }
        });
    }

    public void updateUI() {
        bookList = db.AppDatabase().getUnHeldBooks(passedString);

        if (bookAdapter == null) {
            bookAdapter = new ArrayAdapter<>(this, R.layout.book, R.id.book, bookList);
            bookListView.setAdapter(bookAdapter);
        } else {
            bookAdapter.clear();
            bookAdapter.addAll(bookList);
            bookAdapter.notifyDataSetChanged();
        }
    }

}