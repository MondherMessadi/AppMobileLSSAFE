package tn.esprit.lssafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfil extends AppCompatActivity {

    private EditText editName, editEmail, editSerial;
    private Button saveButton;
    private String nameUser, emailUser, serialUser;
    private FirebaseUser user ;

    private DatabaseReference reference ;
    private String userID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        editName = findViewById(R.id.fullName);
        editEmail = findViewById(R.id.email);
        editSerial = findViewById(R.id.serial);

        saveButton = findViewById(R.id.changeit);
        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Edit()) {
                    Toast.makeText(EditProfil.this, "Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Profil.class));
                } else {
                    Toast.makeText(EditProfil.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean Edit(){
        if (!nameUser.equals(editName.getText().toString()) || !emailUser.equals(editEmail.getText().toString()) || !serialUser.equals(editSerial.getText().toString()) ){
            reference.child(userID).child("email").setValue(editEmail.getText().toString());
            reference.child(userID).child("fullName").setValue(editName.getText().toString());
            reference.child(userID).child("serial").setValue(editSerial.getText().toString());
            nameUser = editName.getText().toString();
            emailUser = editEmail.getText().toString();
            serialUser = editSerial.getText().toString();
            return true;
        } else{
            return false;
        }

    }

    public void showData(){
        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        serialUser = intent.getStringExtra("serial");

        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editSerial.setText(serialUser);

    }

}




