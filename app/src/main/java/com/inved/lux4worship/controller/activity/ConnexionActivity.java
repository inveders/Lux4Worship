package com.inved.lux4worship.controller.activity;

import com.inved.lux4worship.base.BaseActivity;
import com.inved.lux4worship.firebase.UserHelper;

public class ConnexionActivity extends BaseActivity {


    @Override
    public int getFragmentLayout() {
        return R.layout.;
    }


    private void createUserInFirestore(String jobAddress,String jobPlaceId, String jobName) {

        if (this.getCurrentUser() != null) {

            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
            String firstname = this.getCurrentUser().getDisplayName();
            String lastname = null;
            String uid = this.getCurrentUser().getUid();
            String restaurantPlaceId = null;
            String restaurantName = null;
            String restaurantType = null;
            String restaurantVicinity = null;
            ManageJobPlaceId.saveJobPlaceId(this,jobPlaceId);
            UserHelper.createUser(uid, firstname, lastname, urlPicture, restaurantPlaceId, restaurantType, restaurantName, restaurantVicinity, jobAddress, jobPlaceId, jobName).addOnFailureListener(this.onFailureListener());


        }
    }

}
