package tn.esprit.lssafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profil extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user ;
    private FirebaseAuth fAuth ;
    private DatabaseReference reference ;
    private String userID ;
    private Button Logout,camera,drone,barchar;
    private TextView greetingTextView ,fullNameTextView ,serialTextView,emailTextView , stat,edit,mapss,linechar,change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        fAuth = FirebaseAuth.getInstance();


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();
        camera= findViewById(R.id.Camerabobo);
        camera.setOnClickListener(this);
        drone= findViewById(R.id.dronita);
        drone.setOnClickListener(this);
        mapss= findViewById(R.id.maps);
        mapss.setOnClickListener(this);
        barchar= findViewById(R.id.BarChar);
        barchar.setOnClickListener(this);
        linechar= findViewById(R.id.LineChar);
        linechar.setOnClickListener(this);
        change= findViewById(R.id.change);
        change.setOnClickListener(this);
        final TextView greetingTextView =findViewById(R.id.greeting);
        final TextView fullNameTextView =findViewById(R.id.fullName);
        final TextView phoneTextView =findViewById(R.id.phone);
        final TextView emailTextView =findViewById(R.id.emailAddress);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile!=null){
                    String fullName = userProfile.fullName;
                    String serial = userProfile.serial;
                    String email = userProfile.email;
                    String phone = userProfile.phone;

                    greetingTextView.setText("Welcome, " + fullName + " ! " );
                    fullNameTextView.setText(fullName);
                    phoneTextView.setText(phone);
                    emailTextView.setText(email);




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profil.this,"Something wrong happened!",Toast.LENGTH_LONG).show();

            }
        });

        Logout = (Button)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });





        edit = findViewById(R.id.editp);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profil.this, EditProfil.class);
                String fullName = fullNameTextView.getText().toString();
                //String serial = serialTextView.getText().toString();
                String phone = phoneTextView.getText().toString();
                String email = emailTextView.getText().toString();


                intent.putExtra("name", fullName);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);



                startActivity(intent);
                //startActivity(new Intent(Profil.this, EditProfil.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.maps:
                startActivity(new Intent(Profil.this, Maps.class));
                break;
            case R.id.BarChar:
                startActivity(new Intent(Profil.this, BarChar.class));
                break;
            case R.id.Camerabobo:
                startActivity(new Intent(Profil.this, Cameratyha.class));
                break;
            case R.id.LineChar:
                startActivity(new Intent(Profil.this, LineChart.class));
                break;
            case R.id.change:
                startActivity(new Intent(Profil.this, Heartbeat.class));
                break;
            case R.id.dronita:
                startActivity(new Intent(Profil.this, Statistique.class));
                break;

        }
        //startActivity(new Intent(Profil.this, Cameratyha.class));
        //startActivity(new Intent(Profil.this, Statistique.class));
        //startActivity(new Intent(Profil.this, Heartbeat.class));
    }


}