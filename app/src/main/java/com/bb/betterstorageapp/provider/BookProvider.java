package com.bb.betterstorageapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bb.betterstorageapp.database.BookDatabaseHelper;

public class BookProvider extends ContentProvider {
    private String authority = "com.bb.betterstorageapp.provider.BookProvider";
    private String url = "content://"+authority+"/"+BookDatabaseHelper.TABLE_NAME;

//    content://com.bb.betterstorageapp.provider.BookProvider/books/2
//    content://com.bb.betterstorageapp.provider.BookProvider/books
//TODO: to be done or not..
    private static final int SINGLE_BOOK = 1;
    private static final int ALL_BOOKS = 2;

    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    private BookDatabaseHelper databaseHelper = null;

    @Override
    public boolean onCreate() {

        uriMatcher.addURI(authority, "books/#", SINGLE_BOOK);
        uriMatcher.addURI(authority, "books", ALL_BOOKS);

        try {
            databaseHelper = new BookDatabaseHelper(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (databaseHelper != null);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor bookCursor = null;

        switch (uriMatcher.match(uri)){
            case ALL_BOOKS:
                bookCursor = databaseHelper.readAllBooks();
                break;

            case SINGLE_BOOK:
                Log.d("TAG_X", "LPSegment: " + uri.getLastPathSegment());
                bookCursor = databaseHelper.readSingleBook(uri.getLastPathSegment());
                break;
        }
        return bookCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


//content://authority(com.bb.betterstorageapp.provider.BookProvider)


}



