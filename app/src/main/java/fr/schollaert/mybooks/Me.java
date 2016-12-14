package fr.schollaert.mybooks;

import android.os.Bundle;
import android.view.View;

public class Me extends BaseActivity implements
        View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
    }

    @Override
    public void onClick(View view) {

    }
}
