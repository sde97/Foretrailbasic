package com.thetripod.foretrailbasic;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class MissingActivity extends AppCompatActivity /*implements
        AdapterView.OnItemSelectedListener */{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference nDatabase;


    private String location,name,last_seen,missing_from;
    private TextView cancelNewBooking, confirmNewBooking;
    private EditText missingLocation,missingName,missingStatus,missingFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_missing_entry);


        //branch=getIntent().getStringExtra("BRANCH_NAME");
        //city="Bangalore";


        cancelNewBooking = findViewById(R.id.cancel_booking);
        confirmNewBooking = findViewById(R.id.confirm_booking);

        cancelNewBooking.setClickable(true);
        confirmNewBooking.setClickable(true);

        mAuth = FirebaseAuth.getInstance();

        confirmNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataEntry();
                finish();
            }
        });

        cancelNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void dataEntry(){
        /*newBookingSlot = findViewById(R.id.new_booking_slot);*/
        /*newBookingServiceId = findViewById(R.id.new_booking_service_id);*/
        /*String bookingSlot = String.valueOf(newBookingSlot.getSelectedItem());*/
        /*String bookingServiceId = String.valueOf(newBookingServiceId.getSelectedItem());*/
        missingName=findViewById(R.id.set_missing_name);
        missingLocation=findViewById(R.id.set_missing_location);
        missingStatus=findViewById(R.id.set_missing_last_seen);
        missingFrom=findViewById(R.id.set_missing_date);
        location=missingLocation.getText().toString().trim();
        name=missingName.getText().toString().trim();
        last_seen=missingStatus.getText().toString().trim();
        missing_from=missingFrom.getText().toString().trim();
        long timestamp = System.currentTimeMillis();

        String userId = mAuth.getCurrentUser().getUid();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String bookingDate = DateFormat.format("dd-MM-yyyy", cal).toString();

        //String bookingStatus = "waiting in queue";
        String missingId ="M" + DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString().replaceAll("-","").replaceAll(" ","").replaceAll(":","");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("missing").child(location);
        NewMissingEntry newMissingEntry = new NewMissingEntry(name,location,last_seen,missing_from,missingId);
        mDatabase.child(missingId).setValue(newMissingEntry);

        Log.i("Misssing Details",newMissingEntry.toString());
       /* nDatabase = FirebaseDatabase.getInstance().getReference().child("bookings").child(city).child(branch).child("Booking_Queue").child(bookingDate).child(bookingSlot);
        BookingCurrent bookingQueueItem = new BookingCurrent(userId, bookingServiceId, bookingSlot, branch, timestamp+"","ongoing","20 mins","10", bookingId,null);
        nDatabase.child(""+timestamp).setValue(bookingQueueItem);*/


    }


}
