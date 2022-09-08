package com.adarshaaman.askpro;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.calling.CallState;

import org.w3c.dom.Text;

import java.util.List;

public class CallActivity extends AppCompatActivity {

    public static SinchClient sinchClient;
    public static  Booking booking;
    public ImageView callbtn;
    public  TextView statustext;
    public  TextView tapTo;
    public  ImageView callendbtn;
    public  Call call;
    public   int callstate;//  1 if calling , 2 if receiving , 0 if none
    ImageView onspeaker ;
    ImageView offspeaker ;

    private class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            //call ended by either party
            call = null;
            statustext.setText("");
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            onspeaker.setVisibility(View.GONE);
            offspeaker.setVisibility(View.GONE);
            Toast.makeText(CallActivity.this, "Call ended", Toast.LENGTH_LONG).show();
            MainActivity.sinchClient.startListeningOnActiveConnection();
            CallActivity.this.finish();}



        public void onCallEstablished(Call establishedCall) {
            //incoming call was picked up
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            callendbtn.setVisibility(View.VISIBLE);
            onspeaker.setVisibility(View.VISIBLE);
            callbtn.setVisibility(View.GONE);
            statustext.setText("Connected");


        }

        @Override
        public void onCallProgressing(Call progressingCall) {
            //call is ringing
            statustext.setText("Ringing");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            //don't worry about this right now
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_call);

        Intent i = getIntent();

        callstate = i.getIntExtra("callstate",1);

        if(callstate==2){


        String id = i.getStringExtra("id");
        call = sinchClient.getCallClient().getCall(id);}




        callendbtn = (ImageView) findViewById(R.id.callend_btn2);
        tapTo = (TextView)findViewById(R.id.tap_to2);
        callbtn = (ImageView) findViewById(R.id.call_btn);
        statustext = (TextView)findViewById(R.id.call_text);
        onspeaker = (ImageView) findViewById(R.id.on_speaker);
        offspeaker = (ImageView) findViewById(R.id.off_speaker);


        onspeaker.setVisibility(View.GONE);
        offspeaker.setVisibility(View.GONE);

        if(callstate==2){tapTo.setText("Tap to receive");}
        else {tapTo.setText("Tap to call");}


        callendbtn.setVisibility(View.GONE);

        getAudioNormal();


        onspeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onspeaker.setVisibility(View.GONE);
                offspeaker.setVisibility(View.VISIBLE);
                getAudioSpeaker();

            }
        });


        offspeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offspeaker.setVisibility(View.GONE);
                onspeaker.setVisibility(View.VISIBLE);
                getAudioNormal();
            }
        });


        callendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.hangup();
            }
        });

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tapTo.setText("");

            if (callstate ==2) { call.answer();}
            else {call=   sinchClient.getCallClient().callUser(booking.mentor.getPhone());

                callendbtn.setVisibility(View.VISIBLE);
                callbtn.setVisibility(View.GONE);


                }

                call.addCallListener(new SinchCallListener());

            }
        });
    }

    public void getAudioNormal(){
        AudioController audioController = sinchClient.getAudioController();
        audioController.disableSpeaker();
    }

    public void getAudioSpeaker(){
        AudioController audioController = sinchClient.getAudioController();
        audioController.enableSpeaker();
    }


    @Override
    public void onBackPressed() {
        if(call==null){super.onBackPressed();}

        else {
            if (call.getState().equals(CallState.ENDED)) {
                super.onBackPressed();
            }

            else{  if(!(call.getState().equals(CallState.ESTABLISHED)))
            {call.hangup();
                super.onBackPressed();}}

        }


    }
}
