package tn.esprit.lssafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profil extends AppCompatActivity {

    private FirebaseUser user ;
    private FirebaseAuth fAuth ;
    private DatabaseReference reference ;
    private String userID ;
    private Button Logout,camera,drone,barchar;
    private TextView greetingTextView ,fullNameTextView ,serialTextView,emailTextView , stat,edit,mapss,linechar,change;
    CircleImageView profile_image ;
    StorageReference storageReference ;
    private static final int IMAGE_REQUEST =1 ;
    private Uri imageUri ;
    private StorageTask uploadTask ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().setTitle("Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        fAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        final TextView greetingTextView =findViewById(R.id.greeting);
        final TextView fullNameTextView =findViewById(R.id.fullName);
        final TextView phoneTextView =findViewById(R.id.phone);
        final TextView emailTextView =findViewById(R.id.emailAddress);
        final CircleImageView profile_imageView =findViewById(R.id.profile_image) ;

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
                    if(userProfile.getImageURL().equals("default")){
                        profile_imageView.setImageResource(R.mipmap.ic_launcher);
                    }else {
                        Glide.with(Profil.this).load(userProfile.getImageURL()).into(profile_imageView);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profil.this,"Something wrong happened!",Toast.LENGTH_LONG).show();

            }
        });
/*



*/


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
        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage() ;
            }
        });

    }

    @Override
    public void onBackPressed() {

    }


        //startActivity(new Intent(Profil.this, Cameratyha.class));
        //startActivity(new Intent(Profil.this, Statistique.class));
        //startActivity(new Intent(Profil.this, Heartbeat.class));

    ///// Image change methods
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(Profil.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(Profil.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profil.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
    }


