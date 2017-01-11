package fr.schollaert.mybooks.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.schollaert.mybooks.activity.BookActivity;
import fr.schollaert.mybooks.R;
import fr.schollaert.mybooks.model.Book;


public class LibraryAdapter extends BaseAdapter {

    Context context;
    List<Book> booksList;

    public LibraryAdapter(Context context, List<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    public Book getBookAtPosition(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = parent.inflate(context, R.layout.listitem_book, null);

        final Book book = (Book) getItem(position);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(book.getTitle());

        TextView tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
        tvAuthor.setText(book.getAuthor());

        ImageView ivCover = (ImageView) view.findViewById(R.id.ivCover);
        if(book.getImageUrl() != "" && book.getImageUrl() != null){
            Glide.with(context).load(book.getImageUrl()).into(ivCover);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("item", book);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }
}

