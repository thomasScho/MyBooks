package fr.schollaert.mybooks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.schollaert.mybooks.adapter.CommentAdapter;
import fr.schollaert.mybooks.model.Book;
import fr.schollaert.mybooks.model.Comment;
import fr.schollaert.mybooks.model.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookCommentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookCommentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookCommentsFragment extends Fragment {
    private static final String ARG_PARAM1 = "book";

    private Book mBook;

    private OnFragmentInteractionListener mListener;

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference booksRef = database.getReference("Books");
    DatabaseReference usersRef = database.getReference("Users");
    User user = new User();
    DatabaseReference thisBookRef;
    DatabaseReference thisUserRef;
    Book mBookDB;

    List<Comment> commentList = new ArrayList<>();

    public BookCommentsFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param book book
     * @return A new instance of fragment BookCommentsFragment.
     */
    public static BookCommentsFragment newInstance(Book book) {
        BookCommentsFragment fragment = new BookCommentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = (Book) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_comments, container,false);
        mAuth = FirebaseAuth.getInstance();
         thisUserRef = usersRef.child(mAuth.getCurrentUser().getUid());
         thisBookRef = booksRef.child(mBook.getGoogleID());

        thisBookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mBookDB = dataSnapshot.getValue(Book.class);
                if(mBookDB != null && mBookDB.getComments()!=null){
                    CommentAdapter commentAdapter = new CommentAdapter(getContext(), mBookDB.getComments());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mBook.getTitle() + ", " + mBook.getAuthor());




        return view;
    }

    // TODORename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
