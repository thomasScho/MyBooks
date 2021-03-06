package fr.schollaert.mybooks.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.schollaert.mybooks.R;
import fr.schollaert.mybooks.adapter.ParamAdapter;
import fr.schollaert.mybooks.model.Param;
import fr.schollaert.mybooks.model.User;

public class MeActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");
    User user = new User();
    List<Param> paramList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference thisUserRef = usersRef.child(mAuth.getCurrentUser().getUid());

        findViewById(R.id.btn_seeBooks).setOnClickListener(this);
        findViewById(R.id.btn_seeFriends).setOnClickListener(this);

        thisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              user =  dataSnapshot.getValue(User.class);
                paramList = new ArrayList<>();
                Param mail = new Param("Email", user.getMail());
                Param pseudo = new Param("Pseudo", user.getPseudo());
                Param age = new Param("Age", String.valueOf(user.getAge()));
                Param sexe = new Param("Sexe", user.getSexe());
                paramList.add(mail);
                paramList.add(pseudo);
                paramList.add(sexe);
                paramList.add(age);
                ParamAdapter adaptParam = new ParamAdapter(MeActivity.this, paramList);
                ListView lv = (ListView) findViewById(R.id.listParam);
                lv.setAdapter(adaptParam);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_seeBooks) {
            startActivity(new Intent(this, MyLibraryActivity.class));
        }else if(i == R.id.btn_seeFriends){
            startActivity(new Intent(this, MyFriendsActivity.class));
        }
    }
}
