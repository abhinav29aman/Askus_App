package com.adarshaaman.askpro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jaeger.library.StatusBarUtil;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth ;
    public static  String userPhone;
    public static FirebaseDatabase mDataBase;
    boolean doubleBackToExitPressedOnce = false;

    private Toolbar mainToolbar;
    private DrawerLayout drawer;
    NavigationView navigationView ;
    public static boolean isMentor = false;
    public static FirebaseStorage mFireBaseStorage;
    public static  StorageReference imageStorage;
    public static DatabaseReference jeeadvancedpros;
    public static DatabaseReference jeemainpros;
    public static DatabaseReference cbsepros;
    public static DatabaseReference jeeadvancedmentors;
    public static DatabaseReference jeemainmentors;
    public static DatabaseReference cbsementors;
    public static DatabaseReference allmentors;
    public static SinchClient sinchClient;
    public static String sinchappkey = "f50f19a5-a4a5-4701-bc1e-4d6f3666fa2c";
    public static String sinchsecretkey =  "0GhnuDK7EkqD1eriJiTAiQ==";
    public static ArrayList<Booking> bookings= new ArrayList<>();
    public static String callId = "adarsha";
    ImageView iitImage ;
    TextView  iitText ;;

    public static int getRcSignIn() {
        return RC_SIGN_IN;
    }

    //method to check if this topper is booked
    public static boolean  isTopperBooked(String phone){
        for(int i=0; i <bookings.size();i++){

            if ((bookings.get(i).mentor.getPhone()).equals(phone)){return  true;}

        }
        return false;
    }

    //method to get the booking with topper

    public static Booking  getBooking(String phone){
        for(int i=0; i <bookings.size();i++){

            if ((bookings.get(i).mentor.getPhone()).equals(phone)){return  bookings.get(i);}

        }
        return null;
    }
    //////

    //===========================================methods for mentors ==============================================

    public static Booking currentBooking(){
        String t = getCurrentTime();
        for(int i=0; i <bookings.size();i++){
           Booking b = bookings.get(i);

           if(isBeforeCurrentTime(b.getBegintime())&&(isAfterCurrentTime(b.getEndtime()))){
               return b;
           }

        }
        return null;

    }

    //==============================================================================================================

    public static String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        String str = BookingActivity.conver(month,date) + BookingActivity.conver(hour,minutes);
     return str;
    }


    //method to tell if string represents time after current time

      public static boolean isAfterCurrentTime(String s){
        int m = Integer.parseInt(s);

          int n = Integer.parseInt(getCurrentTime());
          if(m>=n){return  true ;}

          return  false;

      }

    public static boolean isBeforeCurrentTime(String s){
        int m = Integer.parseInt(s);

        int n = Integer.parseInt(getCurrentTime());
        if(m<=n){return  true ;}

        return  false;

    }

    //-------permisssions -----------------------------------------------------------------------




//audio--------------------------------------------------------------------------------------------------
    //Requesting run-time permissions

    //Create placeholder for user's consent to record_audio permission.
    //This will be used in handling callback
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private final int MY_PERMISSIONS_REQUEST_CAMERA =2;

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

        }
    }

    private void requestCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Please grant permissions to access camera", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

        }
    }

    //Handling callback
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }



                break;
            }

            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to access camera", Toast.LENGTH_LONG).show();
                }



                break;
            }


        }}





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            // already signed in

            //initialise phone number
            //---------------------------------------------------------------------------------------------

            userPhone = auth.getCurrentUser().getPhoneNumber();



            setContentView(R.layout.activity_main);
            requestAudioPermissions();
            requestCameraPermissions();

            mDataBase = FirebaseDatabase.getInstance();
            jeeadvancedmentors = mDataBase.getReference("JeeAdvancedMentors");
            jeeadvancedpros= mDataBase.getReference("JeeAdvancedPros");
            jeemainmentors = mDataBase.getReference("JeeMainMentors");
            jeemainpros = mDataBase.getReference("JeeMainPros");
            cbsementors = mDataBase.getReference("CbseMentors");
            cbsepros = mDataBase.getReference("CbsePros");
            allmentors = mDataBase.getReference("AllMentors");
            mFireBaseStorage = FirebaseStorage.getInstance();
            imageStorage = mFireBaseStorage.getReference("ShopPhotos");

            //---mentor differnetiation-------------------------------------------------
            View bookslot = findViewById(R.id.no_slot);
            bookslot.setVisibility(View.GONE);
            allmentors.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Mentor i = postSnapshot.getValue(Mentor.class);
                        String s = i.getPhone();
                        if (userPhone.equals(s)){isMentor = true;



               //------------IF MENTOR SHOW DIALOG TO SET SLOTS-------------------------------------------
                            bookslot.setVisibility(View.VISIBLE);
                            Button ignore = (Button) findViewById(R.id.ignore);
                            Button cont = (Button)findViewById(R.id.cont);
                           ignore.setOnClickListener(new View.OnClickListener() {
                                @Override
                               public void onClick(View v) {
                                   bookslot.setVisibility(View.GONE);
                               }
                           });

                        cont.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               BookingActivity.mentor = userPhone;
                               Intent i = new Intent(MainActivity.this,BookingActivity.class);
                                startActivity(i);
                           }
                        });
                            }
                   }

                    //------------------------------------------------------------------------------------------------
                    //getting bookings
                    DatabaseReference ref1;
                    if(MainActivity.isMentor){

                        ref1 = MainActivity.mDataBase.getReference("Topperbookings").child(MainActivity.userPhone);}
                    else {ref1 = MainActivity.mDataBase.getReference("Userbookings").child(MainActivity.userPhone);}
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            bookings.clear();

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Booking i = postSnapshot.getValue(Booking.class);
                                if(isAfterCurrentTime(i.endtime))
                                { bookings.add(i);}

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            //--------------setting up sinch client ---------------------------------------------------------


            android.content.Context context = this.getApplicationContext();
            sinchClient = Sinch.getSinchClientBuilder().context(context)
                    .applicationKey(sinchappkey)
                    .applicationSecret(sinchsecretkey)
                    .environmentHost("clientapi.sinch.com")
                    .userId(MainActivity.userPhone)
                    .build();

            sinchClient.setSupportCalling(true);
         //   ote: By default, the Sinch SDK hangs up any Sinch call if the regular phone app has an active call. This functionality requires the permission READ_PHONE_STATE. However, if this default functionality isn’t wanted, turn it off by calling
            sinchClient.setSupportManagedPush(true);
            sinchClient.getCallClient().setRespectNativeCalls(false);
// or
            sinchClient.setSupportActiveConnectionInBackground(true);
            sinchClient.startListeningOnActiveConnection();

            sinchClient.addSinchClientListener(new SinchClientListener() {
                // acts according to state of sinch client
                public void onClientStarted(SinchClient client) { }

                public void onClientStopped(SinchClient client) { }

                public void onClientFailed(SinchClient client, SinchError error) { }

                public void onRegistrationCredentialsRequired(SinchClient client, ClientRegistration registrationCallback) { }

                public void onLogMessage(int level, String area, String message) { }
            });

            sinchClient.start();

            sinchClient.getCallClient().addCallClientListener(new CallClientListener() {
                @Override
                public void onIncomingCall(CallClient callClient, Call icall) {
                    if(callId.equals(icall.getCallId())){return;}
                                 callId = icall.getCallId();
                    sinchClient.stopListeningOnActiveConnection();

                    if(icall.getDetails().isVideoOffered()){
                        if(currentBooking()==null){}
                        else{
                        if(icall.getRemoteUserId().equals(currentBooking().userPhone))//doesn't allow users to manipulate time on phone
                        { //VideocallActivity.callstate=2;

                       // VideocallActivity.call =icall;
                        VideocallActivity.sinchClient = MainActivity.this.sinchClient;
                        Intent i = new Intent(MainActivity.this,VideocallActivity.class);
                        i.putExtra("ide",icall.getCallId());
                            i.putExtra("callstate",2);

                        startActivity(i);}}
                    }
                else{
                    if(currentBooking()==null){}
                       else{ if(icall.getRemoteUserId().equals(currentBooking().userPhone))//doesn't allow users to manipulate time on phone
                        {
                   // CallActivity.call = icall;
                    CallActivity.sinchClient= MainActivity.this.sinchClient;
                    Intent i = new Intent(MainActivity.this,CallActivity.class);
                    i.putExtra("id",icall.getCallId());
                    i.putExtra("callstate",2);
                    startActivity(i);}}
                }}
            });

          //  ImageView search = (ImageView)(findViewById(R.id.search_button));

           //      search.setOnClickListener(new View.OnClickListener() {
             //        @Override
             //        public void onClick(View v) {
               //          onSearchRequested();
                       //  int  max = 10000000; int min = 1000000;
                        // int random_int = (int)(Math.random() * (max - min + 1) + min);
                       //  int random_in = (int)(Math.random() * (max - min + 1) + min);
                       //  String tid = "" + random_in + random_int;
                       //  Payment payment = new Payment(tid , 500,"","");
                       //  mDataBase.getReference("Payments").child(tid).setValue(payment);
                       //  PayActivity.transactionId = tid ;
                        // Intent i = new Intent(MainActivity.this,PayActivity.class);
                        //  startActivity(i);


                       //  VideocallActivity.sinchClient= MainActivity.this.sinchClient;
                       //  Intent i = new Intent(MainActivity.this,VideocallActivity.class);
                        // startActivity(i);

                         //CallActivity.sinchClient= MainActivity.this.sinchClient;
                         //Intent i = new Intent(MainActivity.this,CallActivity.class);
                         //startActivity(i);
                 //    }
                 //});

            //----------------------------------------------------------------------------------------------------
           iitImage = (ImageView) findViewById(R.id.exam_image);
           iitText = (TextView)  findViewById(R.id.text_iit);

        mainToolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(mainToolbar);
        drawer = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.nav_view);
            View headerLayout = navigationView.getHeaderView(0);
         TextView p= headerLayout.findViewById(R.id.nav_user_phone);
            p.setText(userPhone);



            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeExamFragment(1)).commit();
            navigationView.setCheckedItem(R.id.jee_advanced);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.jee_advanced :

                        setExamImageText(1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeExamFragment(1)).commit();

                        break ;

                    case R.id.jee_main :

                        setExamImageText(2);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeExamFragment(2)).commit();
                        break ;

                    case R.id.cbse_boards :

                        setExamImageText(3);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeExamFragment(3)).commit();
                        break ;

                    case R.id.bookings :
                        setExamImageText(4);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new BookingFragment()).commit();


                        break ;

                    case R.id.about :
                        setExamImageText(4);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new AboutFragment()).commit();


                        break ;


                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
      //  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,mainToolbar,R.string.open_drawer,R.string.close_drawer);

      //  toggle.setDrawerIndicatorEnabled(false);
       // Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.nav_icon, this.getTheme());
       // toggle.setHomeAsUpIndicator(drawable);
        //
        // toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
          /*  @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
 drawer.addDrawerListener(toggle);
 toggle.syncState();*/
        ImageView navButton = findViewById(R.id.nav_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }

            }
        });

        } else {
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build())).build(),
                    RC_SIGN_IN);
            // not signed in


        }

        //load mentor view ---------------------------------------------------------------

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){

            drawer.closeDrawer(GravityCompat.START);
        }
        else{

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
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){finish();
            moveTaskToBack(true);
        }}


        public void setExamImageText(int n){

        switch (n){

            case 1 :String uri = "@drawable/iit";  // where myresource (without the extension) is the file

                int imageResource = getResources().getIdentifier(uri, null, getPackageName());

                Drawable res = getResources().getDrawable(imageResource);
                iitImage.setImageDrawable(res);
                iitText.setText("JEE Advanced");
                iitText.setVisibility(View.VISIBLE);
                iitImage.setVisibility(View.VISIBLE);

                break;

            case 2 :String uri1 = "@drawable/nit";  // where myresource (without the extension) is the file

                int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());

                Drawable res1 = getResources().getDrawable(imageResource1);
                iitImage.setImageDrawable(res1);
                iitText.setText("JEE Main");
                iitImage.setVisibility(View.VISIBLE);
                iitText.setVisibility(View.VISIBLE);

                break;

            case 3 : String uri2 = "@drawable/cbse";  // where myresource (without the extension) is the file

                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());

                Drawable res2 = getResources().getDrawable(imageResource2);
                iitImage.setImageDrawable(res2);
                iitText.setText("CBSE Boards");
                iitText.setVisibility(View.VISIBLE);
                iitImage.setVisibility(View.VISIBLE);


                break;


            default: iitImage.setVisibility(View.GONE);
            iitText.setVisibility(View.GONE);
                break;


        }

        }
}
