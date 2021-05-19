package com.example.otterlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CreateAccount extends AppCompatActivity {

    private LibraryDb db;
    private int btnCount = 0;
    Button done, submitAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);
        db = LibraryDb.getInstance(this);
        db.loadDatabase();
        done = findViewById(R.id.done);
        done.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        });
        submitAccount = findViewById(R.id.submitAccount);
        submitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCount++;
                boolean isValid = true;
                String username, password;

                EditText Tusername = findViewById(R.id.username);
                username = Tusername.getText().toString();
                EditText Tpassword = findViewById(R.id.password);
                password = Tpassword.getText().toString();

                List<User> userList = db.AppDatabase().getUserByUsername(username);

                if (username.isEmpty() || password.isEmpty()) {
                    isValid = false;
                    if (btnCount <= 1) {
                        String text = "Neither fields can be left blank. Please try again";
                        Toast.makeText(CreateAccount.this, text, Toast.LENGTH_LONG).show();
                    } else {
                        String text = "Too many failed attempts.";
                        Toast.makeText(CreateAccount.this, text, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateAccount.this, MainActivity.class));
                    }
                }

                if (!userList.isEmpty()) {
                    isValid = false;
                    if (btnCount <= 1) {
                        String text = "That username is already taken";
                        Toast.makeText(CreateAccount.this, text, Toast.LENGTH_LONG).show();
                    } else {
                        String text = "Too many failed attempts.";
                        Toast.makeText(CreateAccount.this, text, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateAccount.this, MainActivity.class));
                    }
                }

                if (isValid) {
                    btnCount = 0;
                    String text = "Account Created Successfully";
                    Toast.makeText(CreateAccount.this, text, Toast.LENGTH_LONG).show();

                    User newUser = new User(username, password);
                    db.AppDatabase().addUser(newUser);
                    Log newLog = new Log("Transaction type: New account\nUsername: " + username);
                    db.AppDatabase().addLog(newLog);
                    startActivity(new Intent(CreateAccount.this, MainActivity.class));
                }
            }
        });
    }

}