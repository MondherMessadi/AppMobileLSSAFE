package tn.esprit.lssafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Statistique extends AppCompatActivity {


    private TextView temp, batt, dist, Lalt, Longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);


        temp = findViewById(R.id.laltitude);
        batt = findViewById(R.id.longitude);
        dist = findViewById(R.id.distance);
        Lalt = findViewById(R.id.maposition);
        Longi = findViewById(R.id.dronita);


        DatabaseReference gpsRef = FirebaseDatabase.getInstance().getReference("Drone");


        gpsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dronitaSnapshot : dataSnapshot.getChildren()) {
                    String tempe = dronitaSnapshot.child("Temperature").getValue(String.class);
                    String batte = dronitaSnapshot.child("Battery").getValue(String.class);
                    String diqto = dronitaSnapshot.child("Range").getValue(String.class);
                    String latitude = dronitaSnapshot.child("Laltitude").getValue(String.class);
                    String longitude = dronitaSnapshot.child("Longitude").getValue(String.class);
                    temp.setText(tempe);
                    batt.setText(batte);
                    dist.setText(diqto);
                    //Lalt.setText(placeName);
                    //Longi.setText(longitude + " " + latitude);
                    Geocoder geocoder = new Geocoder(Statistique.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses != null && addresses.size() > 0) {
                        String placeName = addresses.get(0).getAddressLine(0);
                        Longi.setText(placeName);
                    }

                    Lalt.setText(longitude + " " + latitude);
                }

                /*
                    Double latitude = dronitaSnapshot.child("Laltitude").getValue(Double.class);
                    Double longitude = dronitaSnapshot.child("Longitude").getValue(Double.class);
                    if (latitude != null | longitude != null) {
                        String latitudeStr = latitude.toString();
                        String longitudeStr = longitude.toString();
                        Longi.setText(latitudeStr + " " + longitudeStr);}
                */


                }


            @Override
            public void onCancelled(DatabaseError error) {

            }

        });


    }


}
