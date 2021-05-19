package com.example.otterlibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, Log.class, User.class}, version = 2, exportSchema = false)
public abstract class LibraryDb extends RoomDatabase {


    private static LibraryDb userDatabase;
    public abstract LibraryDao AppDatabase();
    public static final String BOOK_TABLE = "books";
    public static final String USER_TABLE = "users";
    public static final String LOG_TABLE = "logs";


    public static synchronized LibraryDb getInstance(Context context) {
        if (userDatabase == null) {
            userDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    LibraryDb.class,
                    "library.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return userDatabase;
    }

    public void loadDatabase() {
        if (AppDatabase().countUsers() == 0) {
            runInTransaction(() -> {
                User admin = new User("!admin2", "!admin2");

                User anton = new User("anton", "t3nn1sch@mp2021");

                User bernie = new User("bernie", "k3ralaf@n");

                User shirley = new User("shirleybee", "carmel2chicago");

                AppDatabase().insertUsers(admin, anton, bernie, shirley);
            });
        }

        if (AppDatabase().count() == 0) {
            runInTransaction(() -> {
                Book KITCHEN = new Book("Kitchen Confidential", "Anthony Bourdain", "Memoir", 0);

                Book IDAPRO = new Book("The IDA Pro Book", "Chris Eagle", "Computer Science", 0);

                Book frankenstein = new Book("Frankenstein", "Mary Shelley", "Fiction", 0);

                AppDatabase().insertBooks(KITCHEN, IDAPRO, frankenstein);
            });
        }
    }
}
