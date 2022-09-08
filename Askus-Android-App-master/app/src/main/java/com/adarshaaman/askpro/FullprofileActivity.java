package com.adarshaaman.askpro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;

public class FullprofileActivity extends AppCompatActivity {

    public static Mentor mentor;
    public TextView descriptiontext ;
    public ImageView profileImage ;
    public TextView nametext ;
    public FadingTextView fading ;
    public View bookVideo ;
    public View bookAudio;
    public TextView hometown;
    public TextView coaching;
    public TextView college;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.full_profile);


         if(MainActivity.isTopperBooked(mentor.getPhone())){
             findViewById(R.id.booking_part).setVisibility(View.GONE);
         }

         descriptiontext = (TextView) findViewById(R.id.full_text);
         profileImage = (ImageView) findViewById(R.id.full_image);
         nametext = (TextView) findViewById(R.id.full_name);
         fading = (FadingTextView) findViewById(R.id.fading_text);
         bookAudio = findViewById(R.id.audio_btn);
        bookVideo = findViewById(R.id.video_btn);
        hometown = (TextView) findViewById(R.id.full_hometown);
        coaching = (TextView) findViewById(R.id.full_coaching);
        college = (TextView) findViewById(R.id.full_college);
        View showbook = findViewById(R.id.booked_text);
        View showcall = findViewById(R.id.call_service);
        View bookingpart = findViewById(R.id.booking_part);
      showbook.setVisibility(View.GONE);
      showcall.setVisibility(View.GONE);
      //---------------------------setting up booking settings on the page --------------------------------------

        //----------------toppers view----


        //slot creation by me ____-

        if(MainActivity.userPhone.equals("+918851825178")){

            nametext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(FullprofileActivity.this,BookingActivity.class);
                    BookingActivity.mentor= FullprofileActivity.mentor.getPhone();
                    startActivity(i);
                }
            });

        }


        /////////////////////////////////////

        if(MainActivity.userPhone.equals(mentor.getPhone())){
            bookingpart.setVisibility(View.GONE);
            showbook.setVisibility(View.GONE);
            showcall.setVisibility(View.GONE);

        }

        //----------------------------------------------
        if(MainActivity.isTopperBooked(mentor.getPhone())){

            if(MainActivity.userPhone.equals(mentor.getPhone())){
                bookingpart.setVisibility(View.GONE);
                showbook.setVisibility(View.GONE);
                showcall.setVisibility(View.GONE);

            }else{

            bookingpart.setVisibility(View.GONE);

            Booking b = MainActivity.getBooking(mentor.getPhone());
            if((MainActivity.isAfterCurrentTime(b.getEndtime()))&&((MainActivity.isBeforeCurrentTime(b.getBegintime())))){

                showcall.setVisibility(View.VISIBLE);
                showbook.setVisibility(View.GONE);
                View strtaudio = findViewById(R.id.start_audio);
                View strtvideo = findViewById(R.id.start_video);

                if(b.type==1){strtvideo.setVisibility(View.GONE);
                strtaudio.setVisibility(View.VISIBLE);}
                else {

                    strtaudio.setVisibility(View.GONE);
                    strtvideo.setVisibility(View.VISIBLE);
                }
            }
            else {
                showbook.setVisibility(View.VISIBLE);
                showcall.setVisibility(View.GONE);
            }

            showcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((MainActivity.isAfterCurrentTime(b.getEndtime()))&&((MainActivity.isBeforeCurrentTime(b.getBegintime())))) {
                        if(b.type==1){
                            CallActivity.booking=b;
                            CallActivity.sinchClient= MainActivity.sinchClient;
                            Intent i = new Intent(FullprofileActivity.this,CallActivity.class);
                            i.putExtra("callstate",1);
                            startActivity(i);
                        }
                        else {
                                VideocallActivity.booking= b;
                             VideocallActivity.sinchClient= MainActivity.sinchClient;
                            Intent i = new Intent(FullprofileActivity.this,VideocallActivity.class);
                            i.putExtra("callstate",1);
                            startActivity(i);
                        }


                    }
                    else {
                        Toast.makeText(FullprofileActivity.this,"The session time has ended",Toast.LENGTH_LONG);
                    }
                }
            });

        }}



        //-----------------------------------------------------------------------------------------------------------
        hometown.setText("From " + mentor.getHometown());
        coaching.setText("Took coaching from " + mentor.getCoaching());
        college.setText("Studies " + mentor.getCollege());



        bookVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlotBookingActivity.type= 2;
                SlotBookingActivity.topperphone= mentor.getPhone();
                SlotBookingActivity.mentor= FullprofileActivity.mentor;
                Intent i = new Intent(FullprofileActivity.this, SlotBookingActivity.class);
                startActivity(i);
            }
        });

        bookAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlotBookingActivity.type= 1;
                SlotBookingActivity.topperphone= mentor.getPhone();
                SlotBookingActivity.mentor= FullprofileActivity.mentor;
                Intent i = new Intent(FullprofileActivity.this, SlotBookingActivity.class);
                startActivity(i);
            }
        });


         int arraycount ;
         String jeeadv;
         String jeem ;
         String boards ;
        ArrayList<String> examresult = new ArrayList<>();
         if(!(mentor.getJeeadv()==0)) {jeeadv = "AIR " + (mentor.getJeeadv()) +" in JEE Advanced " + mentor.getYear();
         examresult.add(jeeadv);}
        if(!(mentor.getJeem()==0)) {jeem = "AIR " + (mentor.getJeem()) +" in JEE Main " + mentor.getYear();
        examresult.add(jeem);}
        if(!(mentor.getCbse()==0)) {boards= (mentor.getCbse())+" percent in CBSE " + mentor.getYear();
        examresult.add(boards);}

        arraycount = examresult.size();
        String[] texts = new String[arraycount];
        for(int i =0; i<arraycount;i++){

            texts[i] = examresult.get(i);
        }
        fading.setTexts(texts);
        fading.setTimeout(1500,FadingTextView.MILLISECONDS);



        nametext.setText(mentor.getName());
         descriptiontext.setText(mentor.getDescription());
        Glide.with(this).load(mentor.getImageurl()).into(profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FullprofileActivity.this,FullImageActivity.class);
                FullImageActivity.mentor = mentor;
                startActivity(i);
            }
        });


    }
}
