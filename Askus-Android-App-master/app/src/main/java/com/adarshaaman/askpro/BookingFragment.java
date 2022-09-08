package com.adarshaaman.askpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BookingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.empty_recyclerviewlayout,container,false);
        View emptyshow = v.findViewById(R.id.show_empty);
        RecyclerView rv1 = v.findViewById(R.id.recyclerviewbookings);
        LinearLayoutManager layoutManager1 =new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv1.setLayoutManager(layoutManager1);
        rv1.setItemAnimator(new DefaultItemAnimator());
        final BookingAdapter mentorAdapter = new BookingAdapter();
        mentorAdapter.activity= getActivity();
        mentorAdapter.inflater = getActivity().getLayoutInflater();
        rv1.setAdapter(mentorAdapter);
        DatabaseReference ref1;
        if(MainActivity.isMentor){
            if(MainActivity.userPhone.equals("+918851825178")){
                ref1= MainActivity.mDataBase.getReference("Bookings");
            }
            else{
         ref1 = MainActivity.mDataBase.getReference("Topperbookings").child(MainActivity.userPhone);}}
        else {ref1 = MainActivity.mDataBase.getReference("Userbookings").child(MainActivity.userPhone);}
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mentorAdapter.bookings.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Booking i = postSnapshot.getValue(Booking.class);
                    if(MainActivity.isAfterCurrentTime(i.endtime))
                    {mentorAdapter.bookings.add(i);}

                }
                mentorAdapter.notifyDataSetChanged();
                if(mentorAdapter.bookings.size()==0){rv1.setVisibility(View.GONE);}
                else{ emptyshow.setVisibility(View.GONE);
                 v.findViewById(R.id.main_container).setBackgroundColor(0x190A28);}

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;

    }
}
