package com.adarshaaman.askpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InputActivity extends AppCompatActivity {

    EditText ename;
    EditText ecoaching;
    EditText ejeeadv;
    EditText ejeemain;
    EditText ecbse;
    EditText eyear ;
    EditText ephone;
    EditText ehometown;
    EditText edescription;
    EditText ecollege;
    EditText vidprice;
    EditText audprice;
    Button postImage;
    Button postMentor;

    String name;
    String coaching;
    int jeeadv;
    int jeemain;
    float cbse;
    String year ;
    String phone;
    String hometown;
    String description;
    String college;
    String imageUrl;
    int audioprice;
    int videoprice;

    public static String photoUrl;
    private static final int RC_MENTOR_PHOTO = 5;

    boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        postImage = findViewById(R.id.post_image);
        postMentor = findViewById(R.id.post_mentor);
        ename = findViewById(R.id.etname);
        ecoaching = findViewById(R.id.etcoaching);
        ejeeadv = findViewById(R.id.etjeeadvanced);
        ejeemain = findViewById(R.id.etjeemain);
        ecbse = findViewById(R.id.etcbse);
        eyear = findViewById(R.id.etyear);
        ephone = findViewById(R.id.etphone);
        ehometown = findViewById(R.id.ethometown);
        edescription = findViewById(R.id.etdescription);
        ecollege = findViewById(R.id.etcollege);
        vidprice = (EditText) findViewById(R.id.video_price);
        audprice =(EditText) findViewById(R.id.audio_price);



        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Complete Action Using"), RC_MENTOR_PHOTO);

            }
        });

        postMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                name = ename.getText().toString();
                coaching = ecoaching.getText().toString();
                college = ecollege.getText().toString();
                description = edescription.getText().toString();
                hometown = ehometown.getText().toString();
                jeeadv = Integer.parseInt(ejeeadv.getText().toString());
                jeemain = Integer.parseInt(ejeemain.getText().toString());
                cbse = Float.parseFloat(ecbse.getText().toString());
                year = eyear.getText().toString();
                phone = "+91" + ephone.getText().toString();
                boolean jeeadvpro = (jeeadv > 0) && (jeeadv < 100);
                boolean jeemainpro = (jeemain > 0) && (jeemain < 100);
                boolean cbsepro = (cbse > 97);
                audioprice = Integer.parseInt(audprice.getText().toString());
                videoprice = Integer.parseInt(vidprice.getText().toString());

                Mentor m = new Mentor(phone, name, coaching, year, college, jeeadv, jeemain, cbse, hometown, 0, 0,
                        imageUrl, description, 0, 0, 0, 0, 0, 0, 0, jeeadvpro, jeemainpro, cbsepro,audioprice,videoprice);




                if (jeeadv != 0) {

                    if (jeeadvpro) {
                        MainActivity.jeeadvancedpros.child(phone).setValue(m);
                    }
                    else{
                        MainActivity.jeeadvancedmentors.child(phone).setValue(m);
                    }
                }

                if (jeemain != 0) {

                    if (jeemainpro) {
                        MainActivity.jeemainpros.child(phone).setValue(m);
                    }
                    else {

                        MainActivity.jeemainmentors.child(phone).setValue(m);
                    }

                }
                    if (cbse != 0) {


                        if (cbsepro) {
                            MainActivity.cbsepros.child(phone).setValue(m);
                        }
                        else{

                            MainActivity.cbsementors.child(phone).setValue(m);


                        }
                    }

                MainActivity.allmentors.child(phone).setValue(m);
                Toast.makeText(getApplicationContext(),"Mentor Posted ",Toast.LENGTH_SHORT).show();}
            catch (Exception e){Toast.makeText(getApplicationContext(),"An error occured ,try again ",Toast.LENGTH_SHORT).show();}
            }});

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if (requestCode == RC_MENTOR_PHOTO  && resultCode == RESULT_OK){
                Uri imageUri = data.getData();
                final StorageReference photoReference = MainActivity.imageStorage.child(imageUri.getLastPathSegment());
                UploadTask uploadTask=  photoReference.putFile(imageUri);
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),"Failed to Load Image",Toast.LENGTH_SHORT).show();
                            throw task.getException();

                        }

                        // Continue with the task to get the download URL
                        return photoReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            imageUrl = downloadUri.toString();
                            Toast.makeText(getApplicationContext(),"Image Loaded",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
}
catch (Exception e){Toast.makeText(getApplicationContext(),"Couldn't load image",Toast.LENGTH_SHORT).show();} }}