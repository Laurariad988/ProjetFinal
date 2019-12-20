package com.example.projertfinall;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.ContentValues.TAG;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMaps;
    MapView mMapView;
    View mView;
    private Object ImageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map,container, false);
        return mView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState ) {

        super.onViewCreated(view,savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if( mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);


            mMapView = (MapView) mView.findViewById(R.id.map);

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        GoogleMap mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Statue of Liberty").snippet("The Best Place To Catch Pokemon In NYC"));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.689247,-74.044502)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }
    }






