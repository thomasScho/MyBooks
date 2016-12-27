package fr.schollaert.mybooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity implements  View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.meButton).setOnClickListener(this);
        findViewById(R.id.myBooksButton).setOnClickListener(this);
        findViewById(R.id.findBookButton).setOnClickListener(this);
        findViewById(R.id.myCommentButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        System.out.println("TERST " + i);
        if (i == R.id.meButton) {
            startActivity(new Intent(this, MeActivity.class));
        } else if (i == R.id.myBooksButton) {
            startActivity(new Intent(this, MyBooksActivity.class));
        } else if (i == R.id.findBookButton) {
            startActivity(new Intent(this, FindBooksActivity.class));
        }else if (i == R.id.myCommentButton) {
            startActivity(new Intent(this, MyCommentsActivity.class));
        }
    }
}
