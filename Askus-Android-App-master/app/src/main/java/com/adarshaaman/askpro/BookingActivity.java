package com.adarshaaman.askpro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


public class BookingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    View  set_date ;
    View set_start;
    View set_end ;
    TextView date ;
    TextView month ;
    TextView start_time;
    TextView end_time;
    Button confirm;
    public int whichtimebeingset ;// 1 when start time ,2 when end time
    boolean isdateselected = false;
    boolean isstartselected = false;
    boolean isendselected = false ;
    int dateint ;
    int monthint ;
    int starthourint;
    int endhourint ;
    int startminuteint;
    int endminuteint;
    View vv;
    public static String mentor;





    public static String returnMoth(int month){
        String[] arr = {
                "Jan" , "Feb", "Mar" ,"April" , "May" ,"Jun" ,"Jul" , "Aug" ,"Sep" ,"Oct" ,"Nov","Dec"
        };
        return arr[month];

    }

    public boolean isendtimebigger (int hour , int minute){
        if ((starthourint<hour)){return true;}
        else if ((starthourint ==hour ) && (startminuteint<minute)){return true;}
        return  false;
    }

    public static String conver(int m ,int n){
        String ret = "";
        if (m <10){ret = "0" + m;} else {ret = ""+m;}
        if(n<10){
            ret = ret + "0" + n;}
        else {ret = ret + n;}
        return  ret;

    }

    public static String convertime(int m , int n){

         //returns with a : in between

        String ret = "";
        if (m <10){ret = "0" + m +":";} else {ret = ""+m + ":";}
        if(n<10){
            ret = ret + "0" + n;}
        else {ret = ret + n;}
        return  ret;
    }

    public static String getNextSlot(int hour , int minute){
        int m = hour;
        int n = minute + 10;
        if (n >= 60){n = n%60; m = hour+1;}
        return conver(m,n);
    }

    public static int getNextHour(int hour ,int minute){
        int m = hour;
        int n = minute + 10;
        if (n >= 60){n = n%60; m = hour+1;}
        return  m;
    }

    public static int getNextMinute(int hour ,int minute){
        int m = hour;
        int n = minute + 10;
        if (n >= 60){n = n%60; m = hour+1;}
        return  n;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        set_date = findViewById(R.id.pick_date);
        set_start = findViewById(R.id.pick_start_time);
        set_end = findViewById(R.id.pick_end_time);
        date = (TextView) findViewById(R.id.date_text);
        month = (TextView) findViewById(R.id.month_text);
        start_time= (TextView) findViewById(R.id.start_time_text);
        end_time= (TextView) findViewById(R.id.end_time_text);
        confirm = (Button) findViewById(R.id.confirm_slot);
        confirm.setVisibility(View.GONE);
         vv= findViewById(R.id.date_linear);
        vv.setVisibility(View.GONE);
        start_time.setVisibility(View.GONE);
        end_time.setVisibility(View.GONE);

        set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();

                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        BookingActivity.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
// If you're calling this from a support Fragment
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
// If you're calling this from an AppCompatActivity
// dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });

        set_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isdateselected) {
                    whichtimebeingset = 1;
                    TimePickerDialog tpd = TimePickerDialog.newInstance(BookingActivity.this, 0, 0, true);
                    tpd.show(getSupportFragmentManager(), "Timepickerdialog");
                } else {
                    Toast.makeText(BookingActivity.this,"Choose date first",Toast.LENGTH_SHORT).show();
                } }
        });

        set_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isstartselected) {
                    whichtimebeingset = 2;
                    TimePickerDialog tpd = TimePickerDialog.newInstance(BookingActivity.this, 0, 0, true);
                    tpd.show(getSupportFragmentManager(), "Timepickerdialog");
                }else{
                    Toast.makeText(BookingActivity.this,"Choose start time first",Toast.LENGTH_SHORT).show();

                } }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> slots = new ArrayList<>();
                String s = conver(monthint,dateint);
                int h = starthourint;
                int m = startminuteint;

                while((!(h==endhourint))||(!(m==endminuteint))){
                    slots.add(conver(h,m));
                    h = getNextHour(h,m);
                    m = getNextMinute(h,m);

                }
                for(int i=0; i < slots.size(); i++){

                    MainActivity.mDataBase.getReference("Slots").child(mentor).child(s+(slots.get(i))).setValue("unbooked");


                }

                Toast.makeText(BookingActivity.this,"Slots Added successfully",Toast.LENGTH_LONG).show();



            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        date.setText(""+ dayOfMonth);
        month.setText(returnMoth(monthOfYear));
        isdateselected = true;
        dateint = dayOfMonth;
        monthint = monthOfYear;
        vv.setVisibility(View.VISIBLE);



    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if(whichtimebeingset==1){
            if((minute%10) ==0)
            { start_time.setText(convertime(hourOfDay,minute));
            isstartselected = true;
            start_time.setVisibility(View.VISIBLE);
            starthourint = hourOfDay;
            startminuteint = minute;} else {
                Toast.makeText(BookingActivity.this,"Minutes should be multiple of 10",Toast.LENGTH_LONG).show();

            }
        }
        else{
            if(( minute%10)==0){
                if(isendtimebigger(hourOfDay,minute)){
            end_time.setText(convertime(hourOfDay,minute));
            isendselected = true;
            end_time.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            endhourint = hourOfDay;
            endminuteint = minute;}
            else {
                    Toast.makeText(BookingActivity.this,"End time can't be before start time",Toast.LENGTH_LONG).show();
                }
            }else {

                Toast.makeText(BookingActivity.this,"Minutes should be multiple of 10",Toast.LENGTH_LONG).show();
            }


        }

    }
}
