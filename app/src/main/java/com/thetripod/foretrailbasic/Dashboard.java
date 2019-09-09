
package com.thetripod.foretrailbasic;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {
    FirebaseRecyclerAdapter<MissingPost, HomeHolder> mRecyclerViewAdapter;
    private List<MissingPost> homeList = new ArrayList<MissingPost>();
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView logout, bankerName, slot, serviceId, waitQueue, eta, bookingId, bookingDate;
    private ImageView citizen;
    private ImageView admin;
    //LinearLayout currentBookingInfo;

    private String selectedBranch;

    private String city = "San Francisco";


    private FloatingActionButton fab_add, fab_edit;
    private LinearLayout emptystate, filledstate;
    private TextView currentBookingDate, currentBookingId, currentBookingSlot, currentBookingEta, currentBookingStatus, currentBookingServiceId;
    private Spinner spinBranch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        fab_add = findViewById(R.id.new_booking);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, MissingActivity.class);
                startActivity(intent);
            }
        });




            /*currentBookingDate = findViewById(R.id.current_booking_date);
            currentBookingId = findViewById(R.id.current_booking_id);
            currentBookingSlot = findViewById(R.id.current_booking_slot);
            currentBookingEta = findViewById(R.id.current_booking_eta);
            currentBookingStatus = findViewById(R.id.current_booking_status);
            currentBookingServiceId = findViewById(R.id.current_booking_service_id);*/


        //spinBranch = findViewById(R.id.branch_name);


        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        viewPreviousBookings();

        recyclerView = findViewById(R.id.booking_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mRecyclerViewAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerViewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRecyclerViewAdapter.stopListening();
    }

    void viewPreviousBookings() {

        String branch = selectedBranch;
        String userId = mAuth.getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance()
                .getReference().child("missing").child(city);
            /*Query query = FirebaseDatabase.getInstance()
                    .getReference().child("bookings").child("Booking_Completed").child(userId);
           */
        Log.i("QUERY", query.toString());
        FirebaseRecyclerOptions<MissingPost> options =
                new FirebaseRecyclerOptions.Builder<MissingPost>()
                        .setQuery(query, MissingPost.class)
                        .build();

        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<MissingPost, HomeHolder>(options) {
            @NonNull
            @Override
            public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_entry, parent, false);

                return new HomeHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HomeHolder homeHolder, int i, @NonNull MissingPost bookingCompleted) {
                homeHolder.setMissingName(bookingCompleted.getName());
                homeHolder.setMissingLocation(bookingCompleted.getLocation());
                homeHolder.setMissingTime(bookingCompleted.getMissingFrom());
                homeHolder.setMissingStatus(bookingCompleted.getLastSeen());

            }


        }

        ;

    }
}


