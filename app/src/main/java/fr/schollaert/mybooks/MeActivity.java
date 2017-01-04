package fr.schollaert.mybooks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MeActivity extends AppCompatActivity {
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


        thisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              user =  dataSnapshot.getValue(User.class);
                paramList = new ArrayList<>();
                Param mail = new Param("Email", user.getMail());
                Param pseudo = new Param("Pseudo", user.getPseudo());
                // Param age = new Param("Age", (String) user.getAge());
                Param sexe = new Param("Sexe", user.getSexe());
                paramList.add(mail);
                paramList.add(pseudo);
                paramList.add(sexe);
                ParamAdapter adaptParam = new ParamAdapter(MeActivity.this, paramList);
                ListView lv = (ListView) findViewById(R.id.listParam);
                lv.setAdapter(adaptParam);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
