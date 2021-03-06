package fr.schollaert.mybooks.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import fr.schollaert.mybooks.R;

public class MenuActivity extends AppCompatActivity implements  View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.meButton).setOnClickListener(this);
        findViewById(R.id.myBooksButton).setOnClickListener(this);
        findViewById(R.id.myFriendsButton).setOnClickListener(this);
        findViewById(R.id.findBookButton).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.meButton) {
            startActivity(new Intent(this, MeActivity.class));
        } else if (i == R.id.myBooksButton) {
            startActivity(new Intent(this, MyLibraryActivity.class));
        } else if (i == R.id.myFriendsButton) {
            startActivity(new Intent(this, MyFriendsActivity.class));
        } else if (i == R.id.findBookButton) {
            startActivity(new Intent(this, FindBooksActivity.class));
        } else if(i == R.id.sign_out_button){
            mAuth.signOut();
            startActivity(new Intent(this, EmailPasswordActivity.class));
        }
    }
}
