package fr.schollaert.mybooks.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import fr.schollaert.mybooks.R;
import fr.schollaert.mybooks.model.Comment;

/**
 * Created by Thomas on 06/01/2017.
 */

public class CommentAdapter extends BaseAdapter {
    Context context;
    List<Comment> commentList;

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = parent.inflate(context, R.layout.listitem_comment, null);
        final Comment com = (Comment) getItem(position);

        TextView tvAuthor = (TextView) view.findViewById(R.id.tvCommentAuthor);
        tvAuthor.setText(com.getIdUtil());

        TextView tvComment = (TextView) view.findViewById(R.id.tvCommentText);
        tvComment.setText(com.getComment());

        return view;
    }

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }
}
