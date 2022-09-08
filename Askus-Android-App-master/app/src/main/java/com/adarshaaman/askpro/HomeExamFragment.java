package com.adarshaaman.askpro;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class HomeExamFragment extends Fragment {

    public int exam;

    public HomeExamFragment(int exam){
    super(R.layout.homeexam_fragment);
        this.exam = exam;}

    // 1 for jee adv , 2 for jee main , 3 for board exams

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.homeexam_fragment, container,false) ;



        //manage floating action button------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) (v.findViewById(R.id.fab));
        if (MainActivity.userPhone.equals("+918851825178")){}else{ fab.setVisibility(View.GONE);}


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),InputActivity.class);
                startActivity(intent);
            }
        });


        //-set up the pro recyclerview-------------------------------------------------------------------------------

        RecyclerView rv = v.findViewById(R.id.pro_recyclerview);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        final ProAdapter proAdapter = new ProAdapter();
        proAdapter.activity= getActivity();
        proAdapter.inflater = getActivity().getLayoutInflater();
        proAdapter.exam = exam;
        rv.setAdapter(proAdapter);

        if(exam ==1){
        DatabaseReference ref = MainActivity.jeeadvancedpros;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                proAdapter.proMentors.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Mentor i = postSnapshot.getValue(Mentor.class);
                    proAdapter.proMentors.add(i);

                }
                proAdapter.proMentors= ProAdapter.sortMentors(proAdapter.proMentors,exam);
                proAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}


        if(exam ==2){
            DatabaseReference ref = MainActivity.jeemainpros;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    proAdapter.proMentors.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Mentor i = postSnapshot.getValue(Mentor.class);
                        proAdapter.proMentors.add(i);

                    }
                   proAdapter.proMentors= ProAdapter.sortMentors(proAdapter.proMentors,exam);
                    proAdapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}

        if(exam ==3){
            DatabaseReference ref = MainActivity.cbsepros;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    proAdapter.proMentors.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Mentor i = postSnapshot.getValue(Mentor.class);
                        proAdapter.proMentors.add(i);

                    }
                    proAdapter.proMentors= ProAdapter.sortMentors(proAdapter.proMentors,exam);
                    proAdapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}


        //--------------------------------------------------------------------------------------------------------

        RecyclerView rv1 = v.findViewById(R.id.mentor_recyclerview);
        LinearLayoutManager layoutManager1 =new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv1.setLayoutManager(layoutManager1);
        rv1.setItemAnimator(new DefaultItemAnimator());
        final MentorAdapter mentorAdapter = new MentorAdapter();
        mentorAdapter.activity= getActivity();
        mentorAdapter.inflater = getActivity().getLayoutInflater();
        mentorAdapter.exam = exam;
        rv1.setAdapter(mentorAdapter);

        if(exam==1){
        DatabaseReference ref1 = MainActivity.jeeadvancedmentors;
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mentorAdapter.Mentors.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Mentor i = postSnapshot.getValue(Mentor.class);
                    mentorAdapter.Mentors.add(i);

                }
                mentorAdapter.Mentors= ProAdapter.sortMentors(mentorAdapter.Mentors,exam);
                mentorAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}

        if(exam==2){
            DatabaseReference ref1 = MainActivity.jeemainmentors;
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mentorAdapter.Mentors.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Mentor i = postSnapshot.getValue(Mentor.class);
                        mentorAdapter.Mentors.add(i);

                    }
                    mentorAdapter.Mentors= ProAdapter.sortMentors(mentorAdapter.Mentors,exam);
                    mentorAdapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}


        if(exam==3){
            DatabaseReference ref1 = MainActivity.cbsementors;
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mentorAdapter.Mentors.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Mentor i = postSnapshot.getValue(Mentor.class);
                        mentorAdapter.Mentors.add(i);

                    }
                    mentorAdapter.Mentors= ProAdapter.sortMentors(mentorAdapter.Mentors,exam);
                    mentorAdapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}




        //---set up the mentorrecyclerView----------------------------------------------



        return v;
    }
}
