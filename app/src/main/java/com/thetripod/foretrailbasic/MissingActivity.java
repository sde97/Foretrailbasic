package com.thetripod.foretrailbasic;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

public class MissingActivity extends AppCompatActivity /*implements
        AdapterView.OnItemSelectedListener */{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference nDatabase;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private String location,name,last_seen,missing_from,mDownloadUrl;
    private TextView cancelNewBooking, confirmNewBooking;
    private EditText missingLocation,missingName,missingStatus,missingFrom;
    private ImageView setMissingPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_missing_entry);


        //branch=getIntent().getStringExtra("BRANCH_NAME");
        //city="Bangalore";
        mStorageRef = FirebaseStorage.getInstance().getReference("MISSING");
        mProgressBar = findViewById(R.id.progress_bar);
        cancelNewBooking = findViewById(R.id.cancel_booking);
        confirmNewBooking = findViewById(R.id.confirm_booking);
        setMissingPic=findViewById(R.id.set_missing_pic);
        setMissingPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        setMissingPic.setClickable(true);
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

        uploadFile(name,location,last_seen,missing_from,missingId);

       /* nDatabase = FirebaseDatabase.getInstance().getReference().child("bookings").child(city).child(branch).child("Booking_Queue").child(bookingDate).child(bookingSlot);
        BookingCurrent bookingQueueItem = new BookingCurrent(userId, bookingServiceId, bookingSlot, branch, timestamp+"","ongoing","20 mins","10", bookingId,null);
        nDatabase.child(""+timestamp).setValue(bookingQueueItem);*/


    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(setMissingPic);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(final String name,final String loc,final String status,final String date,final String missingid) {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(missingid
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri downloadUrl = uri;
                                    Toast.makeText(MissingActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    mDownloadUrl=downloadUrl.toString();
                                    Log.i("DownloadUrl",mDownloadUrl);
                                    NewMissingEntry newMissingEntry = new NewMissingEntry(name,loc,status,date,missingid,mDownloadUrl);
                                    mDatabase.child(missingid).setValue(newMissingEntry);
                                    Log.i("Misssing Details",newMissingEntry.toString());
                                }
                            });
                        }
                           /* Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);*/

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MissingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

}
