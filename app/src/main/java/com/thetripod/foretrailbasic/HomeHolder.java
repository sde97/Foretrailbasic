package com.thetripod.foretrailbasic;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeHolder extends RecyclerView.ViewHolder {
        View mView;
        Context mContext;

        public HomeHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mContext = mView.getContext();
        }

        public void setMissingName(String missingName) {
            TextView field = (TextView) mView.findViewById(R.id.missing_name);
            field.setText(missingName);
        }
        public void setMissingStatus(String missingStatus) {
            TextView field = (TextView) mView.findViewById(R.id.missing_status);
            field.setText(missingStatus);
        }

        public void setMissingTime(String missingTime) {
            TextView field = (TextView) mView.findViewById(R.id.missing_since);
            field.setText("Missing since "+missingTime);
        }

    public void setMissingLocation(String missingLocation) {
        TextView field = (TextView) mView.findViewById(R.id.missing_location);
        field.setText(missingLocation);
    }





  /*  public void setBankerId(String bankerId) {
            final TextView field = (TextView) mView.findViewById(R.id.completed_banker_id);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference mRef2 = mDatabase.child("Banker").child(bankerId);
            mRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BankerDetails bankerDetails = dataSnapshot.getValue(BankerDetails.class);

                    field.setText(bankerDetails.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        public void setServiceId(String serviceId) {
            TextView field = (TextView) mView.findViewById(R.id.completed_service_Id);
            field.setText(serviceId);
        }
        public void setBookingTimestamp(String bookingTimestamp){
            TextView field = (TextView)mView.findViewById(R.id.completed_booking_date);
        }
        *//*public void setBranch(String branchName){
            TextView field = (TextView)mView.findViewById(R.id.branch);
        }*/
}
