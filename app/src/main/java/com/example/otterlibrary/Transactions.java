package com.example.otterlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Transactions extends AppCompatActivity {

    private LibraryDb db;
    private List<Log> logsList;
    private ListView logListView;
    private ArrayAdapter<Log> logAdapter;
    Button done, prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions);

        db = LibraryDb.getInstance(this);
        db.loadDatabase();
        logsList = db.AppDatabase().getAllLogs();
        logListView = findViewById(R.id.transactions);
        updateUI();
        done = findViewById(R.id.done);
        done.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
        prompt = findViewById(R.id.prompt);
        prompt.setOnClickListener(v -> {
            Intent i = new Intent(Transactions.this, AddBook.class);
            startActivity(i);
        });
    }

    public void updateUI() {
        logsList = db.AppDatabase().getAllLogs();

        if (logAdapter == null) {
            logAdapter = new ArrayAdapter<>(this, R.layout.log, R.id.log, logsList);
            logListView.setAdapter(logAdapter);
        } else {
            logAdapter.clear();
            logAdapter.addAll(logsList);
            logAdapter.notifyDataSetChanged();
        }
    }

}