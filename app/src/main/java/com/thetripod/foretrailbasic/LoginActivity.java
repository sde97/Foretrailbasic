package com.thetripod.foretrailbasic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends FragmentActivity  implements NumberFragment.OnFragmentInteractionListener,OTPFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // the fragment_container FrameLayout
        if (findViewById(R.id.container_login) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            NumberFragment firstFragment = new NumberFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_login, firstFragment).commit();

        }





    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
