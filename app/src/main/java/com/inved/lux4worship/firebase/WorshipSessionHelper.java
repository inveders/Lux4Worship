package com.inved.lux4worship.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WorshipSessionHelper {

    private static final String COLLECTION_NAME = "restaurants";
    private static final String COLLECTION_GENERAL = "location";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getRestaurantsCollection(String jobPlaceId){
        return FirebaseFirestore.getInstance().collection(COLLECTION_GENERAL).document(jobPlaceId).collection(COLLECTION_NAME);

    }

    // --- CREATE ---

    public static Task<Void> createRestaurant(String id, String restaurantPlaceId, int restaurantCustomers, int restaurantLike, String jobPlaceId) {
        // 1 - Create Obj

        WorshipSession worshipSessionToCreate = new WorshipSession(id, restaurantPlaceId,restaurantCustomers,restaurantLike,jobPlaceId);

        return WorshipSessionHelper.getRestaurantsCollection(jobPlaceId).document(id).set(worshipSessionToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getRestaurant(String id, String jobPlaceId){
        return WorshipSessionHelper.getRestaurantsCollection(jobPlaceId).document(id).get();
    }


    public static Query getAllRestaurantsLike(String jobPlaceId){
        return WorshipSessionHelper.getRestaurantsCollection(jobPlaceId);

    }

    // --- UPDATE ---

    public static Task<Void> updateRestaurant(String restaurantPlaceId, String id, String jobPlaceId) {
        return WorshipSessionHelper.getRestaurantsCollection(jobPlaceId).document(id).update("restaurantPlaceId", restaurantPlaceId);
    }

    public static Task<Void> updateRestaurantCustomers(int restaurantCustomers, String id, String jobPlaceId) {
        return WorshipSessionHelper.getRestaurantsCollection(jobPlaceId).document(id).update("restaurantCustomers", restaurantCustomers);
    }

    public static Task<Void> updateRestaurantLike(int restaurantLike, String id, String jobPlaceId) {
        return WorshipSessionHelper.getRestaurantsCollection(jobPlaceId).document(id).update("restaurantLike", restaurantLike);
    }

}
