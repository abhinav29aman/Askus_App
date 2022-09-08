package com.adarshaaman.askpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity implements PaymentResultListener {
    public static String transactionId;
    public String orderId;
    TextView t;
    public static Booking booking;

    public static boolean isEmptyString(String s) {
        if (s == (null)) {
            return true;
        }
        int m = s.length();
        if (s.equals("")) {
            return true;
        }
        for (int i = 0; i < m; i++) {
            if (!(s.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());
        setContentView(R.layout.payment);

        ImageView iv =(ImageView) findViewById(R.id.full_image3);
        TextView name = (TextView) findViewById(R.id.name_text3);
        TextView date = (TextView) findViewById(R.id.date_text3);
        TextView month = (TextView) findViewById(R.id.month_text3);
        TextView start= (TextView) findViewById(R.id.start3);
        TextView end = (TextView) findViewById(R.id.end3);

        Glide.with(this).load(booking.mentor.getImageurl()).into(iv);
        name.setText(booking.mentor.getName());
        date.setText(""+SlotAdapter.getdate(booking.begintime));
        month.setText(BookingActivity.returnMoth(SlotAdapter.getmonth(booking.begintime)));
        int h = SlotAdapter.gethour(booking.begintime); int m = SlotAdapter.getminute(booking.begintime);
        start.setText(BookingActivity.convertime(h,m));
        String str="";
        for(int j=0;j<booking.noOfSlots;j++){
            str = BookingActivity.getNextSlot(h,m);
            h= BookingActivity.getNextHour(h,m);
            m= BookingActivity.getNextMinute(h,m);}
        end.setText(BookingActivity.convertime(SlotAdapter.getmonth(str),SlotAdapter.getdate(str)));

        t= (TextView) findViewById(R.id.pay_amount);
        t.setText("Rs" + (booking.getPrice()));
        Button paybtn = (Button)findViewById(R.id.pay_btn);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startPayment();


            }
        });


    }

    @Override
    public void onPaymentSuccess(String s) {
        ArrayList<String> str = SlotAdapter.bookedSlots;
        String topper = booking.mentor.getPhone();
        MainActivity.mDataBase.getReference("Bookings").push().setValue(booking);
        MainActivity.mDataBase.getReference("Topperbookings").child(topper).child(booking.getBegintime()).setValue(booking);
        MainActivity.mDataBase.getReference("Userbookings" ).child(MainActivity.userPhone).child(booking.getBegintime()).setValue(booking);
        for(int i=0;i< str.size(); i++){
        MainActivity.mDataBase.getReference("Slots").child(topper).child(str.get(i)).setValue("booked");}
        Toast.makeText(this,"Booking successful",Toast.LENGTH_LONG).show();


        Intent intent = new Intent(PayActivity.this,FullprofileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment couldn't be completed",Toast.LENGTH_LONG).show();

    }




    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_JP7UN19BsSYnAy");
        /**
         * Instantiate Checkout
         */

        /**
         * Set your logo here
         */
        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Askus");


            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Reference No. #123456");


            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", (booking.getPrice() + "00"));

            checkout.open(activity, options);
        } catch(Exception e) {

        }
    }



        }


