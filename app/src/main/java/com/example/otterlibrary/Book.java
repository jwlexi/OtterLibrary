package com.example.otterlibrary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = LibraryDb.BOOK_TABLE)
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "isOnHold")
    private int isOnHold;

    public Book(String title, String author, String genre, int isOnHold) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isOnHold = isOnHold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public String getAuthor() {
        return author;
    }


    public String getGenre() {
        return genre;
    }


    public int isOnHold() {
        return isOnHold;
    }



    @Override
    public String toString() {
        return title + '\t' + author + '\t' + genre;
    }
}
