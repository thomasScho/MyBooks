package fr.schollaert.mybooks;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.schollaert.mybooks.adapter.UserAdapter;
import fr.schollaert.mybooks.model.User;

public class FindFriendsActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        findViewById(R.id.envoyerRecherche).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int click = v.getId();
        final List<User> userFound = new ArrayList<>();
        if (click == R.id.envoyerRecherche) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            final String friendPseudo = ((EditText) findViewById(R.id.findingText)).getText().toString();
            if (friendPseudo.length() > 0) {
                System.out.println("Vous cherchez : " + friendPseudo);
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                            User user = userSnap.getValue(User.class);
                            if (user.getPseudo() != null && user.getPseudo() != "" && user.getPseudo().contains(friendPseudo)) {
                                System.out.println(user);
                                userFound.add(user);
                            }
                            else{
                                System.out.println("Il n'y a pas d'utilisateur portant ce pseudo, désolé");
                            }
                        }
                        UserAdapter ua = new UserAdapter(FindFriendsActivity.this, userFound);
                        ListView lv = (ListView) findViewById(R.id.listFriendView);
                        lv.setAdapter(ua);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }
    }
}
