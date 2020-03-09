package com.bb.betterstorageapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bb.betterstorageapp.R;
import com.bb.betterstorageapp.adapter.BookAdapter;
import com.bb.betterstorageapp.database.BookDatabaseHelper;
import com.bb.betterstorageapp.model.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements BookAdapter.BookInterface {

    private BookDatabaseHelper databaseHelper;

    private List bookLibrary = new ArrayList<Book>();

    @BindView(R.id.books_recyclerview)
    RecyclerView booksRecyclerView;

    @BindView(R.id.book_name_edittext)
    EditText bookNameEditText;

    @BindView(R.id.book_pages_edittext)
    EditText bookPagesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        databaseHelper = new BookDatabaseHelper(this,
                null, null, 0);
        readFromDatabase();

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(booksRecyclerView);
    }

    @OnClick(R.id.save_book_button)
    public void saveNewBook(View view) {
        String bookName = bookNameEditText.getText().toString().trim();
        int bookPages = Integer.parseInt(bookPagesEditText.getText().toString().trim());

        bookNameEditText.setText("");
        bookPagesEditText.setText("");

        Book newBook = new Book(bookName, bookPages);
        databaseHelper.addNewBook(newBook);
        Toast.makeText(this, "Thank you for adding a new book: " + bookName, Toast.LENGTH_SHORT).show();
        readFromDatabase();
    }

    public void readFromDatabase() {
        Cursor booksCursor = databaseHelper.readAllBooks();
        booksCursor.move(-1); //Move to -1 so that when list is empty (bookCursor(0) is null..)

        bookLibrary.clear();//to avoid duplicate items clear the list..

        while (booksCursor.moveToNext()) {

            String bookName = booksCursor.getString(booksCursor.getColumnIndex(BookDatabaseHelper.COLUMN_BOOK_NAME));
            int bookId = booksCursor.getInt(booksCursor.getColumnIndex(BookDatabaseHelper.COLUMN_BOOK_ID));
            int pageNumbers = booksCursor.getInt(booksCursor.getColumnIndex(BookDatabaseHelper.COLUMN_BOOK_PAGES));

            bookLibrary.add(new Book(bookName, pageNumbers, bookId));

            Log.d("TAG_X", "Book : " + bookName);
        }

        setUpRecyclerView();

        booksCursor.close();
    }

    private void setUpRecyclerView() {
        BookAdapter bookAdapter = new BookAdapter(bookLibrary, this);
        booksRecyclerView.setAdapter(bookAdapter);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


    }


    @Override
    public void deleteBook(Book deleteBook) {
        databaseHelper.deleteBook(deleteBook);
        readFromDatabase();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
