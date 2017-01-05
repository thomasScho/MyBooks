package fr.schollaert.mybooks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.schollaert.mybooks.model.Book;


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

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(mBook.getTitle() + ", " + mBook.getAuthor());

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
