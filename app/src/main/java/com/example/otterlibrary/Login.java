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

public class Login extends AppCompatActivity {

    private LibraryDb db;
    private List<Book> bookList;

    private int btnCount = 0;
    private String passedString = "";

    Button done, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = LibraryDb.getInstance(this);
        db.loadDatabase();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            passedString = extras.getString("title");
        }

        bookList = db.AppDatabase().getBookByTitle(passedString);
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCount++;
                boolean isValid = true;
                EditText cusername, cpassword;
                String username, password;

                cusername = findViewById(R.id.username);
                username = cusername.getText().toString();
                cpassword = findViewById(R.id.password);
                password = cpassword.getText().toString();

                List<User> userList = db.AppDatabase().getUserByUsername(username);

                if (username.isEmpty() || password.isEmpty()) {
                    isValid = false;
                    if (btnCount <= 1) {
                        String text = "Neither fields can be left blank. Please try again";
                        Toast.makeText(Login.this, text, Toast.LENGTH_SHORT).show();
                    } else {
                        String text = "Too many failed attempts.";
                        Toast.makeText(Login.this, text, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }
                }

                if (userList.isEmpty()) {
                    isValid = false;
                    if (btnCount <= 1) {
                        String text = "That username does not exist. Please choose another";
                        Toast.makeText(Login.this, text, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String text = "Too many failed attempts.";
                        Toast.makeText(Login.this, text, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }
                }

                if (isValid) {
                    btnCount = 0;

                    db.AppDatabase().update(passedString);
                    Log newLog = new Log("Transaction type: Place Hold\nUsername: " + username);
                    db.AppDatabase().addLog(newLog);

                    String output = String.format("Username: %s%nBook Title: %s%nReservation Number: %d", username, passedString, bookList.get(0).getId());

                    AlertDialog dialog = new AlertDialog.Builder(Login.this)
                            .setTitle("Logged In Successfully, Placing Hold.")
                            .setMessage(output)
                            .setPositiveButton("Go Home", (dialog1, which) -> {
                                startActivity(new Intent(Login.this, MainActivity.class));
                            })
                            .create();
                    dialog.show();
                }
            }
        });
    }



}