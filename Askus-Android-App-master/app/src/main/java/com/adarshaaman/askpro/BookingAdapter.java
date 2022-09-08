package com.adarshaaman.askpro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder> {
    LayoutInflater inflater;
    Activity activity;

    public ArrayList<Booking> bookings =  new ArrayList<Booking>();

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BookingHolder i = new BookingHolder(inflater, parent);
        return i;

    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        Booking i = bookings.get(position);
        View v = holder.itemView;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullprofileActivity.mentor = i.getMentor();
                Intent intent = new Intent(activity,FullprofileActivity.class);
                activity.startActivity(intent);
            }
        });


        ImageView iv =(ImageView) v.findViewById(R.id.full_image2);
        TextView name = (TextView) v.findViewById(R.id.name_text2);
        TextView date = (TextView) v.findViewById(R.id.date_text2);
        TextView month = (TextView) v.findViewById(R.id.month_text2);
        TextView start= (TextView) v.findViewById(R.id.start2);
        TextView end = (TextView) v.findViewById(R.id.end2);

        Glide.with(activity).load(i.mentor.getImageurl()).into(iv);
        name.setText(i.mentor.getName());
       date.setText(""+SlotAdapter.getdate(i.begintime));
        month.setText(BookingActivity.returnMoth(SlotAdapter.getmonth(i.begintime)));
        int h = SlotAdapter.gethour(i.begintime); int m = SlotAdapter.getminute(i.begintime);
        start.setText(BookingActivity.convertime(h,m));
        String str="";
        for(int j=0;j<i.noOfSlots;j++){
         str = BookingActivity.getNextSlot(h,m);
        h= BookingActivity.getNextHour(h,m);
        m= BookingActivity.getNextMinute(h,m);}
        end.setText(BookingActivity.convertime(SlotAdapter.getmonth(str),SlotAdapter.getdate(str)));



    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }


    public class BookingHolder extends RecyclerView.ViewHolder{
        public BookingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.booking_adapter, parent, false));


        }

    }}

