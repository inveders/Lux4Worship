package com.inved.lux4worship.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;
import com.inved.lux4worship.R;
import com.inved.lux4worship.base.BaseActivity;
import com.inved.lux4worship.firebase.UserHelper;
import com.inved.lux4worship.utils.ManageChurchPreferences;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;

public class FindMyChurchActivity extends BaseActivity {

    private static final String TAG = "Debago";
    String churchAddress;
    String churchId;
    String churchName;

    Context context;


    @BindView(R.id.activity_find_my_church_btn_validation)
    Button btnValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Places.initialize(getApplicationContext(), getString(R.string.google_api_key));
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_church);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS));
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteFragment.setCountry("FR");
        autocompleteFragment.setCountry("LU");


// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.d(TAG, "Place: " + place.getName() + ", " + place.getId());
                churchAddress = place.getAddress();
                churchId = place.getId();
                churchName = place.getName();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.d(TAG, "An error occurred: " + status);
            }
        });

        btnValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(churchAddress)) {
                    Log.d(TAG, "Church Address est nul " + churchAddress);
                    Toast.makeText(getApplicationContext(), "Choisissez un lieu", Toast.LENGTH_SHORT).show();
                } else {

                    String firebaseAuthUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    UserHelper.getUserWithSameUid(firebaseAuthUid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //    Log.d("Debago", "task successful");
                                if (task.getResult().getDocuments().size() != 0) {

                                    Log.d("Debago", "findMyChurch already exist " + task.getResult().getDocuments());
                                    startConnexionActivity();
                                } else {
                                    Log.d("Debago", "findMyChurch create user in firestore " + churchAddress + " " + churchName + " " + churchId);
                                    ManageChurchPreferences.saveChurchId(context, churchId);
                                    ManageChurchPreferences.saveChurchName(context, churchName);
                                    ManageChurchPreferences.saveChurchAddress(context, churchAddress);
                                    startConnexionActivity();
                                }

                            }


                        }
                    });


                }
            }
        });
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_find_my_church;
    }


    private void startConnexionActivity() {
        Intent intent = new Intent(this, ConnexionActivity.class);
        Log.d("Debago", "FindMyChurch we go in connexionActivity");
        startActivity(intent);
    }
}
