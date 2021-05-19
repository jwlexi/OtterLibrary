package com.example.otterlibrary;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LibraryDao {
    @Insert
    void addBook(Book book);

    @Insert
    long[] insertBooks(Book... books);

    @Query("SELECT COUNT(*) FROM " + LibraryDb.BOOK_TABLE)
    int count();

    @Query("SELECT * FROM " + LibraryDb.BOOK_TABLE)
    List<Book> getAllBooks();

    @Query("SELECT * FROM " + LibraryDb.BOOK_TABLE + " WHERE genre = :genre AND isOnHold = 0")
    List<Book> getUnHeldBooks(String genre);

    @Query("SELECT * FROM " + LibraryDb.BOOK_TABLE + " WHERE title = :title")
    List<Book> getBookByTitle(String title);

    @Query("UPDATE " + LibraryDb.BOOK_TABLE + " SET isOnHold = 1 WHERE title = :title")
    void update(String title);

    @Insert
    void addUser(User user);

    @Insert
    void insertUsers(User... users);

    @Query("SELECT COUNT(*) FROM " + LibraryDb.USER_TABLE)
    int countUsers();

    @Query("SELECT * FROM " + LibraryDb.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM "+ LibraryDb.USER_TABLE+" WHERE username IS :username")
    List<User> getUserByUsername(String username);

//    @Query("SELECT * FROM " + LibraryDb.USER_TABLE + " WHERE username =:username ")
//    User getUserByUsername(String username);

    @Insert
    void addLog(Log log);

    @Query("SELECT * FROM " + LibraryDb.LOG_TABLE)
    List<Log> getAllLogs();
}
