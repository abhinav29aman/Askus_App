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

public class ProAdapter extends RecyclerView.Adapter<ProAdapter.ProHolder> {
    LayoutInflater inflater;
    public int exam;
    Activity activity;
    public int color ;
    public int[] colors = {R.color.color1,R.color.color2,R.color.color3,R.color.color4};

    public ArrayList<Mentor> proMentors =  new ArrayList<Mentor>();

    public static ArrayList<Mentor> sortMentors(ArrayList<Mentor> arr, int exam){

        ArrayList<Mentor> ret = new ArrayList<>();
        int s = arr.size();
        int n = s;

        for(int i=0 ; i< s ;i++){
            int p =0;
            Mentor m = arr.get(p);
            for (int j=0; j< n; j++){

                if(exam==1){
                    if(arr.get(j).getJeeadv()<m.getJeeadv()){m = arr.get(j); p= j;}
                }

                if(exam==2){
                    if(arr.get(j).getJeem()<m.getJeem()){m = arr.get(j); p= j;}
                }

                if(exam==3){
                    if(arr.get(j).getCbse()> m.getCbse()){m = arr.get(j); p= j;}
                }
            }

            ret.add(m);
            arr.remove(p);
            n-- ;


        }

        return  ret;


    }

    @NonNull
    @Override
    public ProHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ProHolder i = new ProHolder(inflater, parent);
        return i;

    }

    @Override
    public void onBindViewHolder(@NonNull ProHolder holder, int position) {
        Mentor i = proMentors.get(position);
        View v = holder.itemView;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullprofileActivity.mentor = i;
                Intent intent = new Intent(activity,FullprofileActivity.class);
                activity.startActivity(intent);
            }
        });


        color= (color +1)% 4;
        View colorFrame = v.findViewById(R.id.color_frame);
        colorFrame.setBackgroundResource(colors[color]);


        ImageView iv = v.findViewById(R.id.pro_image);
        TextView name = v.findViewById(R.id.pro_name);
        TextView rank = v.findViewById(R.id.pro_rankandyear);
        TextView coach = v.findViewById(R.id.pro_coaching);
        Glide.with(activity).load(i.getImageurl()).into(iv);
        name.setText(i.getName());
       // name.setText(MainActivity.getCurrentTime());

        if(exam==1)
        {rank.setText(i.getYear() + " AIR " + i.getJeeadv());}
        if(exam ==2)
        {rank.setText(i.getYear() + " AIR " + i.getJeem());}
        if(exam==3)
        {rank.setText(i.getYear() + " " + i.getCbse()+ " %");}
        coach.setText(i.getCoaching());

    }

    @Override
    public int getItemCount() {
        return proMentors.size();
    }


   public class ProHolder extends RecyclerView.ViewHolder{
        public ProHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.pro_profile, parent, false));


        }

}}
