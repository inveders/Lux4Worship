package com.inved.lux4worship.firebase;


public class DatesChoosen {


    private String restaurantPlaceId;
    private Boolean isLiked;


    public DatesChoosen(String restaurantPlaceId, Boolean isLiked) {
        this.restaurantPlaceId = restaurantPlaceId;
        this.isLiked = isLiked;
    }

    // --- GETTERS ---

    public String getRestaurantPlaceId() {
        return restaurantPlaceId;
    }

    public Boolean getLiked() {
        return isLiked;
    }


    // --- SETTERS ---

    public void setRestaurantPlaceId(String restaurantPlaceId) {
        this.restaurantPlaceId = restaurantPlaceId;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }
}



