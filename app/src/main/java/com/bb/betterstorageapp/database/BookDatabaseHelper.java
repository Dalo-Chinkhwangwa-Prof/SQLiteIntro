package com.bb.betterstorageapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bb.betterstorageapp.model.Book;

public class BookDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";
    public static final String TABLE_NAME = "books";
    public static final String COLUMN_BOOK_ID = "book_id";
    public static final String COLUMN_BOOK_NAME = "book_name";
    public static final String COLUMN_BOOK_PAGES = "book_pages";
    public static final int DB_VERSION = 1;

    public BookDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_NAME + " TEXT, " +
                COLUMN_BOOK_PAGES + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String updateTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(updateTable);
        onCreate(db);
    }

    public void addNewBook(Book book) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_NAME, book.getBookName());
        contentValues.put(COLUMN_BOOK_PAGES, book.getBookPages());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db = null;
    }

    public Cursor readAllBooks() {
//        Cursor allBooks = getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME, new String [] {COLUMN_BOOK_ID, COLUMN_BOOK_NAME}, null);
        Cursor allBooks = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null, null);
        return allBooks;
    }

    public void deleteBook(Book deleteBook) {
        String delete = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_BOOK_ID + " = " + deleteBook.getBookId();
        getWritableDatabase().execSQL(delete);
    }
}
