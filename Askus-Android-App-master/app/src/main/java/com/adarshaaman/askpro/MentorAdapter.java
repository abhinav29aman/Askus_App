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

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorHolder> {
    LayoutInflater inflater;
    Activity activity;
    int exam;

    public ArrayList<Mentor> Mentors =  new ArrayList<Mentor>();

    @NonNull
    @Override
    public MentorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MentorHolder i = new MentorHolder(inflater, parent);
        return i;

    }

    @Override
    public void onBindViewHolder(@NonNull MentorHolder holder, int position) {
        Mentor i = Mentors.get(position);
        View v = holder.itemView;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullprofileActivity.mentor = i;
                Intent intent = new Intent(activity,FullprofileActivity.class);
                 activity.startActivity(intent);
            }
        });


        ImageView iv = v.findViewById(R.id.mentor_image);
        TextView name = v.findViewById(R.id.mentor_name);
        TextView rank = v.findViewById(R.id.mentor_rank);

        Glide.with(activity).load(i.getImageurl()).into(iv);
        name.setText(i.getName());
        if(exam==1)
        { rank.setText(i.getYear() + " AIR " + i.getJeeadv());}

        if(exam==2)
        { rank.setText(i.getYear() + " AIR " + i.getJeem());}

        if(exam==3)
        { rank.setText(i.getYear() + " " + i.getCbse() + " %");}




    }

    @Override
    public int getItemCount() {
        return Mentors.size();
    }


    public class MentorHolder extends RecyclerView.ViewHolder{
        public MentorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.mentor_profile, parent, false));


        }

    }}

