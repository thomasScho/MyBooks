package fr.schollaert.mybooks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.schollaert.mybooks.model.Book;
import fr.schollaert.mybooks.model.User;

import static java.lang.System.in;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookRatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookRatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookRatesFragment extends Fragment {
    private static final String ARG_PARAM1 = "book";

    private Book mBook;
    int mbookRate = 0;
    private OnFragmentInteractionListener mListener;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference booksRef = database.getReference("Books");
    DatabaseReference usersRef = database.getReference("Users");
    User user = new User();
    DatabaseReference thisBookRef;
    DatabaseReference thisUserRef;
    TextView tvEmptyRate;
    RatingBar rbBook;
    Book mBookDB;
    RatingBar rbYourRate;
    Button sendRateButton;

    public BookRatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookRatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookRatesFragment newInstance(Book book) {
        BookRatesFragment fragment = new BookRatesFragment();
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
        View view = inflater.inflate(R.layout.fragment_book_rates, container, false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mBook.getTitle() + ", " + mBook.getAuthor());

        tvEmptyRate = (TextView) view.findViewById(R.id.tvEmptyRate);
        rbBook = (RatingBar) view.findViewById(R.id.ratingBarBook);
        rbYourRate = (RatingBar) view.findViewById(R.id.ratingBarYour);
        sendRateButton = (Button) view.findViewById(R.id.btnValider);


        mAuth = FirebaseAuth.getInstance();
        thisUserRef = usersRef.child(mAuth.getCurrentUser().getUid());

        thisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if (user != null && user.getUserLibrary() != null && user.getUserLibrary().contains(mBook)) {
                    for (Book b : user.getUserLibrary()) {
                        if (b.equals(mBook) && b.getYourRate() != null) {
                            rbYourRate.setRating(b.getYourRate());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thisBookRef = booksRef.child(mBook.getGoogleID());
        thisBookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mBookDB = dataSnapshot.getValue(Book.class);
                mbookRate = 0;
                if (mBookDB == null) {
                    mBookDB = new Book(mBook);
                    mBookDB.setRates(new ArrayList<Float>());
                } else if (mBookDB.getRates() == null) {
                    mBookDB.setRates(new ArrayList<Float>());
                } else {
                    for (int i = 0; i < mBookDB.getRates().size(); i++) {
                        mbookRate += mBookDB.getRates().get(i);
                    }
                    mbookRate /= mBookDB.getRates().size();
                    tvEmptyRate.setVisibility(View.GONE);
                    rbBook.setVisibility(View.VISIBLE);
                    rbBook.setRating(mbookRate);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dejaNote = false;
                if (user != null && user.getUserLibrary() != null && user.getUserLibrary().contains(mBook)) {
                    for (Book b : user.getUserLibrary()) {
                        if (b.equals(mBook) && b.getYourRate() != null) {
                            Snackbar.make(v, "Vous avez déjà noté ce livre", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            dejaNote = true;

                        }
                    }
                    if (!dejaNote) {
                        float rate = rbYourRate.getRating();
                        mBookDB.getRates().add(rate);
                        booksRef.child(mBook.getGoogleID()).setValue(mBookDB);
                        for (Book b : user.getUserLibrary()) {
                            if (b.equals(mBook)) {
                                b.setYourRate(rate);
                                dejaNote = true;
                                usersRef.child(user.getIdUtilisateur()).setValue(user);
                                Snackbar.make(v, "Note ajoutée, merci", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    }
                } else {
                    Snackbar.make(v, "Il faut avoir lu un livre pour le juger", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
