package fr.schollaert.mybooks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    EditText etComment;
    Button btnValider;
    View view;
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
        view = inflater.inflate(R.layout.fragment_book_comments, container, false);
        mAuth = FirebaseAuth.getInstance();
        thisUserRef = usersRef.child(mAuth.getCurrentUser().getUid());
        thisBookRef = booksRef.child(mBook.getGoogleID());

        thisBookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mBookDB = dataSnapshot.getValue(Book.class);
                ListView lv = (ListView) view.findViewById(R.id.lvBookComments);
                TextView tvMessage = (TextView) view.findViewById(R.id.tvCommentTitle);
                System.out.println("Avant test ");
                if (mBookDB != null && mBookDB.getComments() != null) {
                    System.out.println("il y a le livre en db au moins ");
                    System.out.println(mBookDB);
                    CommentAdapter commentAdapter = new CommentAdapter(getContext(), mBookDB.getComments());
                    lv.setAdapter(commentAdapter);
                    lv.setVisibility(View.VISIBLE);
                    tvMessage.setVisibility(View.GONE);
                } else {
                    System.out.println("Pas de com  en db ");
                    lv.setVisibility(View.GONE);
                    tvMessage.setText("Ce livre n'a pas encore de critique ");
                    tvMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mBook.getTitle() + ", " + mBook.getAuthor());

        etComment = (EditText) view.findViewById(R.id.et_yourComment);
        btnValider = (Button) view.findViewById(R.id.btnValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etComment.getText().toString();
                etComment.setText("");

                if (user != null && user.getUserLibrary() != null) {
                    if (user.getUserLibrary().contains(mBook)) {
                        if (mBookDB == null) {
                            mBookDB = new Book(mBook);
                        }
                        if (mBookDB.getComments() == null) {
                            mBookDB.setComments(new ArrayList<Comment>());
                        }
                        Comment com = new Comment(user.getIdUtilisateur(), msg);
                        mBookDB.getComments().add(com);
                        System.out.println(mBookDB);
                        booksRef.child(mBook.getGoogleID()).setValue(mBookDB);
                        Snackbar.make(v, "Commentaire ajouté", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(v, "Il faut avoir lu un livre pour le juger", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

            }
        });


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
