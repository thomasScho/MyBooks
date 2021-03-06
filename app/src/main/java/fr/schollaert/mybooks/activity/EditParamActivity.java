package fr.schollaert.mybooks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fr.schollaert.mybooks.R;
import fr.schollaert.mybooks.model.Param;
import fr.schollaert.mybooks.model.User;

public class EditParamActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> sexe = new ArrayList<>();
    User user = new User();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Param m_paramSelected;
    private EditText etValue;

    public void fillSexeArray() {
        sexe.add("Homme");
        sexe.add("Femme");
        sexe.add("Indéterminé");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_param);
        fillSexeArray();

        m_paramSelected = (Param) getIntent().getSerializableExtra("item");

        TextView tvName = (TextView) findViewById(R.id.tvParamName);
        tvName.setText(m_paramSelected.getName());
        etValue = (EditText) findViewById(R.id.et_paramValue);
        etValue.setHint(m_paramSelected.getDescription());
        findViewById(R.id.btn_saveParam).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference thisUserRef = usersRef.child(mAuth.getCurrentUser().getUid());
        thisUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_saveParam) {
            saveParam(etValue.getText().toString());
            EditParamActivity.this.finish();
        }
    }

    private void saveParam(String newValue) {
        m_paramSelected.setDescription(newValue);
        switch (m_paramSelected.getName()) {
            case "Email":
                break;
            case "Pseudo":
                user.setPseudo(m_paramSelected.getDescription());
                break;
            case "Sexe":
                if (sexe.contains(m_paramSelected.getDescription())) {
                    user.setSexe(m_paramSelected.getDescription());
                }
                break;
            case "Age":
                int ageSelected = Integer.valueOf(m_paramSelected.getDescription());
                if (ageSelected > 0 && ageSelected < 105) {
                    user.setAge(ageSelected);
                }
                break;
            default:
                break;

        }
        usersRef.child(user.getIdUtilisateur()).setValue(user);
        return;
    }
}
