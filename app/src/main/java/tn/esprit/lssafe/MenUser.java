package tn.esprit.lssafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MenUser extends AppCompatActivity implements View.OnClickListener {

    private ImageView chtat ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_user);

        chtat=findViewById(R.id.chati);
        chtat.setOnClickListener(this);


            CardView profile =findViewById(R.id.cardd1);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MenUser.this,Profil.class));            }
            });

            CardView Cameratyha =findViewById(R.id.cardd2);
            Cameratyha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MenUser.this,Cameratyha.class));            }
            });

            CardView Maps =findViewById(R.id.cardd4);
            Maps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MenUser.this,Maps.class));            }
            });

            CardView AboutUs  =findViewById(R.id.cardd3);
            AboutUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MenUser.this,Heartbeat.class));            }
            });
            CardView Barchart  =findViewById(R.id.cardd5);
            Barchart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MenUser.this,AboutUs.class));            }
            });
            CardView Logout  =findViewById(R.id.cardd6);
            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            });

        }
    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chati:
                startActivity(new Intent(MenUser.this,Chat.class));
                break;
        }
    }
}