package fr.schollaert.mybooks;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MeActivity extends BaseActivity implements
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
