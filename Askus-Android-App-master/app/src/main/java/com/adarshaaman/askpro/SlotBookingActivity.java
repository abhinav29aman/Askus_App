package com.adarshaaman.askpro;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SlotBookingActivity extends AppCompatActivity {
    public static int type ; // 1 for audio , 2 for video
    public static  Mentor mentor;
   static TextView totalpric;

    public ArrayList<String> slot = new ArrayList<>();
    public static String topperphone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slot_choosing);
        View available = findViewById(R.id.available);
        View notavailable = findViewById(R.id.not_available);
        View rv1 = findViewById(R.id.linearrv);
        totalpric =(TextView) findViewById(R.id.cost);

        available.setVisibility(View.GONE);
        notavailable.setVisibility(View.GONE);

        RecyclerView rv = (RecyclerView)findViewById(R.id.slot_recyclerview);
        rv1.setVisibility(View.GONE);
        TextView rate=(TextView)findViewById(R.id.rate);
        TextView cost = (TextView) findViewById(R.id.cost);
        int rat ;
        if(type==1){rat = mentor.getAudioprice();} else {rat = mentor.getVideoprice();}

        rate.setText("Rs " + rat +" per 10 minutes");


        LinearLayoutManager layoutManager1 =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager1);
        rv.setItemAnimator(new DefaultItemAnimator());
        Button gotopay = (Button)findViewById(R.id.gotopay);
        gotopay.setVisibility(View.GONE);
        final SlotAdapter slotAdapter = new SlotAdapter();
        slotAdapter.rate = mentor.getAudioprice();
        slotAdapter.btn = gotopay;
        cost.setText("Rs " +(rat*(slotAdapter.numberofSlots)));

        gotopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = slotAdapter.stattime;

                int h = SlotAdapter.gethour(start); int m = SlotAdapter.getminute(start);
                int d = SlotAdapter.getdate(start);int mon = SlotAdapter.getmonth(start);
                String sss= BookingActivity.conver(mon,d);
                String endtime="";
                for(int j=0;j<slotAdapter.numberofSlots;j++){
                    endtime = BookingActivity.getNextSlot(h,m);
                    h= BookingActivity.getNextHour(h,m);
                    m= BookingActivity.getNextMinute(h,m);}

                Booking b = new Booking(type,slotAdapter.numberofSlots,start,mentor,MainActivity.userPhone,((mentor.getAudioprice())*slotAdapter.numberofSlots),(sss+endtime));
                PayActivity.booking = b;
                Intent intent = new Intent(SlotBookingActivity.this , PayActivity.class);
                startActivity(intent);

            }
        });

        slotAdapter.activity= this;
        slotAdapter.inflater = this.getLayoutInflater();
        rv.setAdapter(slotAdapter);

        MainActivity.mDataBase.getReference("Slots").child(topperphone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                slot.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String i = postSnapshot.getValue(String.class);
                    if(i.equals("unbooked")){

                        String j = postSnapshot.getKey();
                        if(MainActivity.isAfterCurrentTime(j))  //show only slots in the future
                        {slot.add(j);}}

                }
                SlotAdapter.slots= slot;
                slotAdapter.notifyDataSetChanged();


                if(slot.size()==0){
                    available.setVisibility(View.GONE);
                    notavailable.setVisibility(View.VISIBLE);
                    rv1.setVisibility(View.GONE);

                }
                else {

                    available.setVisibility(View.VISIBLE);
                    notavailable.setVisibility(View.GONE);
                    rv1.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
