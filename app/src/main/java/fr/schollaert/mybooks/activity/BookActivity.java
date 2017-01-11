package fr.schollaert.mybooks.activity;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import fr.schollaert.mybooks.fragment.BookCommentsFragment;
import fr.schollaert.mybooks.fragment.BookDescriptionFragment;
import fr.schollaert.mybooks.fragment.BookRatesFragment;
import fr.schollaert.mybooks.R;
import fr.schollaert.mybooks.model.Book;
import fr.schollaert.mybooks.model.User;


public class BookActivity extends AppCompatActivity implements BookDescriptionFragment.OnFragmentInteractionListener, BookCommentsFragment.OnFragmentInteractionListener, BookRatesFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");
    User user = new User();
    FloatingActionButton fabSee;
    FloatingActionButton fabCancelSee;
    private Book m_bookSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference thisUserRef = usersRef.child(mAuth.getCurrentUser().getUid());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fabSee = (FloatingActionButton) findViewById(R.id.fabSee);
        fabCancelSee = (FloatingActionButton) findViewById(R.id.fabCancelSee);
        m_bookSelected = (Book) getIntent().getSerializableExtra("item");


        thisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user == null){
                    user = new User(mAuth.getCurrentUser().getUid(),mAuth.getCurrentUser().getEmail());
                    user.setUserLibrary(new ArrayList<Book>());
                    usersRef.child(user.getIdUtilisateur()).setValue(user);
                    fabCancelSee.setVisibility(View.GONE);
                    fabSee.setVisibility(View.VISIBLE);
                }
                else if (user.getUserLibrary() == null) {
                    user.setUserLibrary(new ArrayList<Book>());
                    fabCancelSee.setVisibility(View.GONE);
                    fabSee.setVisibility(View.VISIBLE);
                } else if (user.getUserLibrary().contains(m_bookSelected)) {
                    fabCancelSee.setVisibility(View.VISIBLE);
                    fabSee.setVisibility(View.GONE);
                } else {
                    fabCancelSee.setVisibility(View.GONE);
                    fabSee.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Partager ce livre avec vos amis", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        fabCancelSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getUserLibrary().contains(m_bookSelected)) {
                    user.getUserLibrary().remove(m_bookSelected);
                }
                usersRef.child(user.getIdUtilisateur()).setValue(user);
                Snackbar.make(view, "Livre supprimé de votre bibliothèque", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fabSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user.getUserLibrary().contains(m_bookSelected)) {
                    user.getUserLibrary().add(m_bookSelected);
                }
                usersRef.child(user.getIdUtilisateur()).setValue(user);
                Snackbar.make(view, "Livre ajouté à votre bibliothèque", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_book, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private Context context;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    BookDescriptionFragment tab = BookDescriptionFragment.newInstance(m_bookSelected);
                    return tab;
                case 1:
                    BookCommentsFragment tab2 = BookCommentsFragment.newInstance(m_bookSelected);
                    return tab2;
                case 2:
                    BookRatesFragment tab3 = BookRatesFragment.newInstance(m_bookSelected);
                    return tab3;
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }

        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Description";
                case 1:
                    return "Commentaires";
                case 2:
                    return "Notes";
            }
            return null;
        }
    }
}
