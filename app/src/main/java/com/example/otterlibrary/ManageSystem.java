package com.example.otterlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ManageSystem extends AppCompatActivity {

    private LibraryDb db;
    private int countOfBtnPresses = 0;
    Button done, manage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managesystem);

        db = LibraryDb.getInstance(this);
        db.loadDatabase();
        done = findViewById(R.id.done);
        done.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        });
        manage = findViewById(R.id.submitAccount);
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countOfBtnPresses++;
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
                    if (countOfBtnPresses <= 1) {
                        String text = "Neither fields can be left blank. Please try again";
                        Toast.makeText(ManageSystem.this, text, Toast.LENGTH_SHORT).show();
                    } else {
                        String text = "Too many failed attempts.";
                        Toast.makeText(ManageSystem.this, text, Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_main);
                    }
                }

                if (!userList.get(0).getUsername().equals("!admin2") || !userList.get(0).getPassword().equals(password)) {
                    isValid = false;
                    if (countOfBtnPresses <= 1) {
                        String text = "Invalid information. Please try again.";
                        Toast.makeText(ManageSystem.this, text, Toast.LENGTH_SHORT).show();
                    } else {
                        String text = "Too many failed attempts.";
                        Toast.makeText(ManageSystem.this, text, Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_main);
                    }
                }

                if (isValid) {
                    countOfBtnPresses = 0;

                    String text = "Logged In Successfully";
                    Toast.makeText(ManageSystem.this, text, Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(ManageSystem.this, Transactions.class);
                    startActivity(i);
                }
            }
        });
    }

}