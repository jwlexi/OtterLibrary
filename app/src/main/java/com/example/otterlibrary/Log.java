package com.example.otterlibrary;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = LibraryDb.LOG_TABLE)
public class Log {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "log")
    private String log;

    public Log(String log) {
        this.log = log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    @Override
    public String toString() {
        return log;
    }
}
