package com.Get2Work.public_transport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.here.mobility.sdk.demand.PublicTransportRideOffer;
import com.Get2Work.R;

/**********************************************************
 * Copyright © 2018 HERE Global B.V. All rights reserved. *
 **********************************************************/


/**
 * Display Public transport info for a specific ride.
 */
public class PublicTransportActivity extends AppCompatActivity {


    /**
     * Public transport, Intent.extra key.
     */
    private static String EXTRA_PUBLIC_TRANSPORT_OFFER = "PUBLIC_TRANSPORT_OFFER";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_transport_activity);
        updateUI();
    }


    private void updateUI(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.public_transport);
        }

        PublicTransportRideOffer ptOffer = getPTOffer();
        RecyclerView rideRecordList = findViewById(R.id.public_transport_details_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rideRecordList.setLayoutManager(layoutManager);
        rideRecordList.setItemAnimator(new DefaultItemAnimator());
        PublicTransportAdapter adapter = new PublicTransportAdapter();
        adapter.setDataSource(ptOffer.getLegs());
        rideRecordList.setAdapter(adapter);

    }


    /**
     * Start {@link PublicTransportActivity} to display details of the given {@link PublicTransportRideOffer}
     * @param context context of the sender
     * @param publicTransportRideOffer an object of public transport offer.
     * @return An intent with extra params the pass PublicTransportRideOffer to this activity.
     */
    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull PublicTransportRideOffer publicTransportRideOffer){
        Intent intent = new Intent(context,PublicTransportActivity.class);
        intent.putExtra(EXTRA_PUBLIC_TRANSPORT_OFFER,publicTransportRideOffer);
        return intent;
    }


    /**
     * Getter get PublicTransportRideOffer from Intent.extra.
     * @return PublicTransportRideOffer if exist otherwise throw RuntimeException.
     * @throws RuntimeException for cases that PublicTransportRideOffer not provided.
     */
    @NonNull
    public PublicTransportRideOffer getPTOffer(){
        if (getIntent().hasExtra(EXTRA_PUBLIC_TRANSPORT_OFFER)){
            return getIntent().getParcelableExtra(EXTRA_PUBLIC_TRANSPORT_OFFER);
        }else {
            throw new RuntimeException("PublicTransportRideOffer is mandatory to start PublicTransportActivity");
        }
    }
}
