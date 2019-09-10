package com.thetripod.foretrailbasic;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


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

         public void setMissingPhoto(String missingUrl) {

             ImageView imageView =  mView.findViewById(R.id.missing_photo);
             Picasso.get()
                     .load(missingUrl)
                     .placeholder(R.mipmap.ic_launcher)
                     .fit()
                     .centerCrop()
                     .into(imageView);


        }


         /* public void setMissingImage(String missingId) {
                final ImageView field =  mView.findViewById(R.id.missing_photo);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference mRef2 = mDatabase.child("missing").child("San Francisco").child(missingId);
                mRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MissingPost missingPost = dataSnapshot.getValue(MissingPost.class);

                        field.setImage(missingPost.getmImageUrl());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

        }


