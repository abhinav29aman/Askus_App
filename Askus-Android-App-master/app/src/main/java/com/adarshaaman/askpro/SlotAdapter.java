package com.adarshaaman.askpro;

import android.app.Activity;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotHolder> {
    LayoutInflater inflater;
    Activity activity;
    Button btn;
    int rate;

    public static ArrayList<String> slots =  new ArrayList<String>();
    public static ArrayList<String> bookedSlots = new ArrayList<>();
    int[] ischecked = new int[100] ; // 1 for checked , 0 for unchecked

    public int starttime;
    //it is the minimum position of selected consecutive items , due to alphabet order of keys
    public String stattime;
    public int numberofSlots=0;
    int date ;

    public ArrayList<String> removeString(String s , ArrayList<String> arr){

        for(int i=0;  i< arr.size(); i++){
            String str = arr.get(i);
            if(str.equals(s)){arr.remove(i);}

        }
        return arr;
    }

//---------------checking if new slot selcted is continous or not -----------------


    public boolean isslotcontinuous(int position) {
        int last = slots.size();
        if(position==0){if (ischecked[1]==1) {return true;} else {return  false ;}}
        if(position == (last-1)){if (ischecked[position-1]==1) {return true;} else {return  false;}}
         if((ischecked[position-1]==1)||(ischecked[position+1]==1)){return true;}
         return  false;
    }

    public boolean isslotcontinuous1(int position) { //this one is for deletion
                                                     //only if both up and down are selected , tou return faslse , else true
        int last = slots.size();
        if(position==0){return true;}
        if(position == (last-1)){return true;}
        if((ischecked[position-1]==1)&&(ischecked[position+1]==1)){return false;}
        return  true;
    }



    //-------------------------------------------------------------------



    //processing the strings

    public static int getmonth(String s){
        String m = s.substring(0,2);
        int ret= Integer.parseInt(m);
        return  ret;
    }

    public static int getdate(String s){
        String m = s.substring(2,4);
        int ret= Integer.parseInt(m);
        return  ret;
    }

    public static int gethour(String s){
        String m = s.substring(4,6);
        int ret= Integer.parseInt(m);
        return  ret;
    }

    public static int getminute(String s){
        String m = s.substring(6);
        int ret= Integer.parseInt(m);
        return  ret;
    }



    @NonNull
    @Override
    public SlotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SlotHolder i = new SlotHolder(inflater, parent);
        return i;

    }

    @Override
    public void onBindViewHolder(@NonNull SlotHolder holder, int position) {
        String i = slots.get(position);
        View v = holder.itemView;




        TextView day = v.findViewById(R.id.date_text1);
        TextView month = v.findViewById(R.id.month_text1);
        TextView start = v.findViewById(R.id.start_time_text1);
        TextView end = v.findViewById(R.id.end_time_text1);

        day.setText(getdate(i)+"");
        month.setText(BookingActivity.returnMoth(getmonth(i)));
        int h = gethour(i); int m = getminute(i);
        start.setText(BookingActivity.convertime(h,m));
        String str = BookingActivity.getNextSlot(h,m);
        end.setText(BookingActivity.convertime(getmonth(str),getdate(str)));


        CheckBox cb = (CheckBox)(v.findViewById(R.id.check));
        if(ischecked[position]==1){cb.setChecked(true);}
        else {cb.setChecked(false);}

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sl = slots.get(position);
                boolean checked = ((CheckBox)v).isChecked();
                if(checked){

                    if(numberofSlots==0){date = getdate(sl); starttime = Integer.parseInt(sl);
                    btn.setVisibility(View.VISIBLE); stattime = sl;}
                    if(getdate(sl)== date) {
                        if((numberofSlots==0)||isslotcontinuous(position)){
                            ischecked[position] = 1;

                            if(Integer.parseInt(sl) < starttime){starttime= Integer.parseInt(sl);
                            stattime= sl;}
                            numberofSlots++;
                            SlotBookingActivity.totalpric.setText("Rs " +(numberofSlots*rate));
                            bookedSlots.add(sl);}
                        else {
                            ((CheckBox)v).setChecked(false);
                            Toast.makeText(activity,"Choose continuous slots",Toast.LENGTH_LONG).show();
                        }

                    }else {
                        ((CheckBox)v).setChecked(false);
                        Toast.makeText(activity,"Slots should be on the same day",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    if(isslotcontinuous1(position)){
                    ischecked[position] =0;
                    numberofSlots-- ;
                        SlotBookingActivity.totalpric.setText("Rs " +(numberofSlots*rate));
                        if(numberofSlots==0){btn.setVisibility(View.GONE);}
                    bookedSlots = removeString(sl,bookedSlots);}
                    else {

                        ((CheckBox)v).setChecked(true);
                        Toast.makeText(activity,"Can't remove a middle slot",Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

        //adding same behaviour on clicking the whole view

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb1 = (CheckBox)v.findViewById(R.id.check);
                String sl = slots.get(position);

                boolean checked = (ischecked[position]==1);
                if(checked){

                    if(isslotcontinuous1(position)){
                    ischecked[position]=0;
                    cb1.setChecked(false);
                    numberofSlots-- ;
                        SlotBookingActivity.totalpric.setText("Rs " +(numberofSlots*rate));
                    if(numberofSlots==0){btn.setVisibility(View.GONE);}
                    bookedSlots = removeString(sl,bookedSlots);}

                     else{
                        Toast.makeText(activity,"Can't remove a middle slot",Toast.LENGTH_LONG).show();
                    }



                }
                else {
                    if(numberofSlots==0){date = getdate(sl); starttime = Integer.parseInt(sl);
                    btn.setVisibility(View.VISIBLE); stattime =sl;}
                    if(getdate(sl)== date) {
                     if((numberofSlots==0)||isslotcontinuous(position)){
                        ischecked[position] = 1;
                        cb1.setChecked(true);
                        if(Integer.parseInt(sl) < starttime){starttime= Integer.parseInt(sl); stattime= sl;}
                        numberofSlots++;
                         SlotBookingActivity.totalpric.setText("Rs " +(numberofSlots*rate));
                        bookedSlots.add(sl);}
                     else {
                         Toast.makeText(activity,"Choose continuous slots",Toast.LENGTH_LONG).show();
                     }

                    }else {
                        Toast.makeText(activity,"Slots should be on the same day",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return slots.size();
    }


    public class SlotHolder extends RecyclerView.ViewHolder{
        public SlotHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.booking, parent, false));


        }

    }}

