package com.adarshaaman.askpro;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.calling.CallState;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;

import java.util.List;

public class VideocallActivity extends AppCompatActivity {

public static Booking booking;
    public static SinchClient sinchClient;
    public ImageView callbtn;
    public ImageView callendbtn;
    public TextView statustext;
    public  Call call;
    public  int callstate;//  1 if calling , 2 if receiving , 0 if none
    public LinearLayout videoView;
    public LinearLayout localvideoview;
    public  boolean mVideoViewsAdded= false;

      TextView tapTo;

    VideoController vc = sinchClient.getVideoController();




    //------------adding removing videoo---------------------

    private void addVideoViews() {
        if (mVideoViewsAdded || sinchClient == null) {
            return; //early
        }

        final VideoController vc = sinchClient.getVideoController();
        if (vc != null) {
            LinearLayout localView = (LinearLayout) findViewById(R.id.self_video);
            localView.addView(vc.getLocalView());

            localView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //this toggles the front camera to rear camera and vice versa
                    vc.toggleCaptureDevicePosition();
                }
            });

            LinearLayout view = (LinearLayout) findViewById(R.id.video_view);
            view.addView(vc.getRemoteView());
            if ((vc.getLocalView() != null) && (vc.getRemoteView() != null)) {
                mVideoViewsAdded = true;
            }
            }
        }




    private void removeVideoViews() {
        if (sinchClient == null) {
            return; // early
        }


        if (vc != null) {
            LinearLayout view = (LinearLayout) findViewById(R.id.video_view);
            view.removeView(vc.getRemoteView());

            LinearLayout localView = (LinearLayout) findViewById(R.id.self_video);
            localView.removeView(vc.getLocalView());
            mVideoViewsAdded = false;
        }
    }

  public class Videocalllistener implements VideoCallListener{

      @Override
      public void onVideoTrackAdded(Call call) {


          addVideoViews();


      }

      @Override
      public void onVideoTrackPaused(Call call) {

      }

      @Override
      public void onVideoTrackResumed(Call call) {

      }

      @Override
      public void onCallProgressing(Call call) {
          statustext.setText("Ringing");
      }

      @Override
      public void onCallEstablished(Call call) {
          statustext.setText("Connected");
          setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
          AudioController audioController = sinchClient.getAudioController();
          audioController.enableSpeaker();
          callendbtn.setVisibility(View.VISIBLE);
          callbtn.setVisibility(View.GONE);
      }

      @Override
      public void onCallEnded(Call callended) {
          call = null;
          if(mVideoViewsAdded){removeVideoViews();}
          statustext.setText("");
          AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
          setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
          Toast.makeText(VideocallActivity.this, "Call ended", Toast.LENGTH_LONG).show();
          MainActivity.sinchClient.startListeningOnActiveConnection();
          VideocallActivity.this.finish();

      }

      @Override
      public void onShouldSendPushNotification(Call call, List<PushPair> list) {

      }
  }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_video);
        //callid = call.getCallId();
        Intent i = getIntent();

        callstate= i.getIntExtra("callstate",1);




        if(callstate==2){


            String id = i.getStringExtra("ide");
            call = sinchClient.getCallClient().getCall(id);
        }



        callendbtn = (ImageView) findViewById(R.id.callend_btn);
        tapTo = (TextView)findViewById(R.id.tap_to);
        callbtn = (ImageView) findViewById(R.id.vidcall_btn);
        statustext = (TextView)findViewById(R.id.vidcall_text);
        videoView= (LinearLayout) findViewById(R.id.video_view);
        localvideoview= (LinearLayout) findViewById(R.id.self_video);


        callendbtn.setVisibility(View.GONE);
        if(callstate==2){tapTo.setText("Tap to receive");}
        else{tapTo.setText("Tap to call");}


        callendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoViewsAdded){removeVideoViews();}
                call.hangup();
               // finish();
            }
        });


        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapTo.setText("");

                if (callstate ==2) { call.answer();}
                else {callendbtn.setVisibility(View.VISIBLE);
                    callbtn.setVisibility(View.GONE);

                    call=   sinchClient.getCallClient().callUserVideo(booking.mentor.getPhone());



                }

                call.addCallListener(new VideocallActivity.Videocalllistener());

            }
        });
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
