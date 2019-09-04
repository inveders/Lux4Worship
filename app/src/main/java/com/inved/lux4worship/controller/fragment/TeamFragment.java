package com.inved.lux4worship.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.inved.lux4worship.R;

public class TeamFragment extends Fragment {

    private RecyclerView mRecyclerTeam;


    public static TeamFragment newInstance() {
        TeamFragment fragment = new TeamFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);
      //  unbinder = ButterKnife.bind(this, rootView);



        //RecyclerView initialization
        mRecyclerTeam = rootView.findViewById(R.id.fragment_team_recycler_view);

        displayTeamForOneDate();

        return rootView;
    }






    private void displayTeamForOneDate() {

        WorkmatesAdapter mRecyclerWorkmatesAdapter = new WorkmatesAdapter(generateOptionsForAdapter(UserHelper.getAllUsers(jobPlaceId)), Glide.with(this), this, getContext());
        //Choose how to display the list in the RecyclerView (vertical or horizontal)
        mRecyclerTeam.setHasFixedSize(true); //REVOIR CELA
        mRecyclerTeam.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecyclerTeam.addItemDecoration(new DividerItemDecoration(mRecyclerTeam.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerTeam.setAdapter(mRecyclerWorkmatesAdapter);
    }

    // Create options for RecyclerView from a Query
    private FirestoreRecyclerOptions<User> generateOptionsForAdapter(Query query){
        return new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .setLifecycleOwner(this)
                .build();
    }

    // --------------------
    // CALLBACK
    // --------------------

    @Override
    public void onDataChanged() {
        // 7 - Show TextView in case RecyclerView is empty
        //  textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
