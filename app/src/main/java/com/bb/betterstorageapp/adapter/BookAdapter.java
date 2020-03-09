package com.bb.betterstorageapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.betterstorageapp.R;
import com.bb.betterstorageapp.model.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookLibrary;
    private BookInterface bookInterface;

    public interface BookInterface {
        void deleteBook(Book deleteBook);
    }

    public BookAdapter(List<Book> bookLibrary, BookInterface bookInterface) {
        this.bookLibrary = bookLibrary;
        this.bookInterface = bookInterface;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item_layout, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        holder.bookNameTextView.setText(bookLibrary.get(position).getBookName());
        holder.pageCountTextView.setText(bookLibrary.get(position).getBookPages() + "");

        holder.itemView.setOnClickListener(view -> {
            bookInterface.deleteBook(bookLibrary.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return bookLibrary.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bookname_textview)
        TextView bookNameTextView;

        @BindView(R.id.page_count_textview)
        TextView pageCountTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
