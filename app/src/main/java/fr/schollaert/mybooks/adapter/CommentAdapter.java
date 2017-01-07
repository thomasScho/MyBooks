package fr.schollaert.mybooks.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

import fr.schollaert.mybooks.R;
import fr.schollaert.mybooks.model.Comment;
import fr.schollaert.mybooks.model.User;

/**
 * Created by Thomas on 06/01/2017.
 */

public class CommentAdapter extends BaseAdapter {
    Context context;
    List<Comment> commentList;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");
    User user = new User();
    DatabaseReference thisUserRef;

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

        mAuth = FirebaseAuth.getInstance();
        thisUserRef = usersRef.child(com.getIdUtil());

        final TextView tvAuthor = (TextView) view.findViewById(R.id.tvCommentAuthor);
        tvAuthor.setText(com.getIdUtil());

        TextView tvComment = (TextView) view.findViewById(R.id.tvCommentText);
        tvComment.setText(com.getComment());

        thisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user.getPseudo() != null && user.getPseudo() != ""){
                    tvAuthor.setText(user.getPseudo());
                }
                else{
                    tvAuthor.setText("Utilisateur inconnu");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }
}
