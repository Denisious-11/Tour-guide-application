package com.example.tour_guide_app;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Cursor cur1;
    private GoogleMap mMap;
    private String TAG = "so47492459";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<String> latitude_list = (ArrayList<String>) getIntent().getSerializableExtra("latitude_list");
        ArrayList<String> longitude_list = (ArrayList<String>) getIntent().getSerializableExtra("longitude_list");

        Log.e("GET LATITUDE LIST : ", String.valueOf(latitude_list));
        Log.e("GET lONGITUDE LIST : ", String.valueOf(longitude_list));

        Intent i1=getIntent();
        String e_lat=i1.getStringExtra("e_lat");
        String e_long=i1.getStringExtra("e_long");

        Log.e("GET e_lat  : ", e_lat);
        Log.e("GET e_long : ", e_long);

        Log.e("Type of e_lat : ",e_lat.getClass().getSimpleName());
        Log.e("Type of Lat List : ",latitude_list.getClass().getSimpleName());

        Log.e("Length of Lat List : ", String.valueOf(latitude_list.size()));
        String len= String.valueOf(latitude_list.size());

        if (len.equals("4"))
        {
            Log.e("HELLO : ","hello");

            mMap = googleMap;

            String slat=e_lat;//"11.5640";
            String slong=e_long;//"75.7564";

            String dlat0 = latitude_list.get(0);
            String dlong0 = longitude_list.get(0);
            String dlat1 = latitude_list.get(1);
            String dlong1 = longitude_list.get(1);
            String dlat2 = latitude_list.get(2);
            String dlong2 = longitude_list.get(2);
            String dlat3 = latitude_list.get(3);
            String dlong3 = longitude_list.get(3);

            Log.e("slat : ",slat);
            Log.e("slong : ",slong);
            Log.e("dlat0 : ",dlat0);
            Log.e("dlong0 : ",dlong0);
            Log.e("dlat1 : ",dlat1);
            Log.e("dlong1 : ",dlong1);
            Log.e("dlat2 : ",dlat2);
            Log.e("dlong2 : ",dlong2);
            Log.e("dlat3 : ",dlat3);
            Log.e("dlong3 : ",dlong3);




//            String dlat="11.6543";
//            String dlong="75.7535";
            String org=slat+","+slong;
            String dest1=dlat1+","+dlong1;
            Float sl= Float.parseFloat(slat);
            Float slg= Float.parseFloat(slong);
            Float dl0= Float.parseFloat(dlat0);
            Float dlg0= Float.parseFloat(dlong0);
            Float dl1= Float.parseFloat(dlat1);
            Float dlg1= Float.parseFloat(dlong1);
            Float dl2= Float.parseFloat(dlat2);
            Float dlg2= Float.parseFloat(dlong2);
            Float dl3= Float.parseFloat(dlat3);
            Float dlg3= Float.parseFloat(dlong3);

            LatLng barcelona = new LatLng(sl,slg);
            mMap.addMarker(new MarkerOptions().position(barcelona).title("You"));

            LatLng madrid0 = new LatLng(dl0,dlg0);
            mMap.addMarker(new MarkerOptions().position(madrid0).title("Destination 1"));

            LatLng madrid1 = new LatLng(dl1,dlg1);
            mMap.addMarker(new MarkerOptions().position(madrid1).title("Destination 2"));

            LatLng madrid2 = new LatLng(dl2,dlg2);
            mMap.addMarker(new MarkerOptions().position(madrid2).title("Destination 3"));

            LatLng madrid3 = new LatLng(dl3,dlg3);
            mMap.addMarker(new MarkerOptions().position(madrid3).title("Destination 4"));

//            LatLng zaragoza = new LatLng(sl,slg);
//
//            //Define list to get all latlng for the route
//            List<LatLng> path = new ArrayList();
//
//
//            //Execute Directions API request
//            GeoApiContext context = new GeoApiContext.Builder()
//                    .apiKey("AIzaSyChoIiaLcRQETng39oiWTsfcfWks2ruZTg")
//                    .build();
//
//            DirectionsApiRequest req = DirectionsApi.getDirections(context, org, dest);
//            try {
//                DirectionsResult res = req.await();
//
//                //Loop through legs and steps to get encoded polylines of each step
//                if (res.routes != null && res.routes.length > 0) {
//                    DirectionsRoute route = res.routes[0];
//
//                    if (route.legs !=null) {
//                        for(int i=0; i<route.legs.length; i++) {
//                            DirectionsLeg leg = route.legs[i];
//                            if (leg.steps != null) {
//                                for (int j=0; j<leg.steps.length;j++){
//                                    DirectionsStep step = leg.steps[j];
//                                    if (step.steps != null && step.steps.length >0) {
//                                        for (int k=0; k<step.steps.length;k++){
//                                            DirectionsStep step1 = step.steps[k];
//                                            EncodedPolyline points1 = step1.polyline;
//                                            if (points1 != null) {
//                                                //Decode polyline and add points to list of route coordinates
//                                                List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
//                                                for (com.google.maps.model.LatLng coord1 : coords1) {
//                                                    path.add(new LatLng(coord1.lat, coord1.lng));
//                                                }
//                                            }
//                                        }
//                                    } else {
//                                        EncodedPolyline points = step.polyline;
//                                        if (points != null) {
//                                            //Decode polyline and add points to list of route coordinates
//                                            List<com.google.maps.model.LatLng> coords = points.decodePath();
//                                            for (com.google.maps.model.LatLng coord : coords) {
//                                                path.add(new LatLng(coord.lat, coord.lng));
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch(Exception ex) {
//                Log.e(TAG, ex.getLocalizedMessage());
//            }
//
//            //Draw the polyline
//            if (path.size() > 0) {
//                PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
//                mMap.addPolyline(opts);
//            }

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barcelona, 9));
        }
        else
        {
            Log.e("HAI : ","hai");
            mMap = googleMap;

            String slat=e_lat;//"11.5640";
            String slong=e_long;//"75.7564";

            String dlat0 = latitude_list.get(0);
            String dlong0 = longitude_list.get(0);
            String dlat1 = latitude_list.get(1);
            String dlong1 = longitude_list.get(1);


            Log.e("slat : ",slat);
            Log.e("slong : ",slong);
            Log.e("dlat0 : ",dlat0);
            Log.e("dlong0 : ",dlong0);
            Log.e("dlat1 : ",dlat1);
            Log.e("dlong1 : ",dlong1);




            String org=slat+","+slong;
            String dest1=dlat1+","+dlong1;
            Float sl= Float.parseFloat(slat);
            Float slg= Float.parseFloat(slong);
            Float dl0= Float.parseFloat(dlat0);
            Float dlg0= Float.parseFloat(dlong0);
            Float dl1= Float.parseFloat(dlat1);
            Float dlg1= Float.parseFloat(dlong1);

            LatLng barcelona = new LatLng(sl,slg);
            mMap.addMarker(new MarkerOptions().position(barcelona).title("You"));

            LatLng madrid0 = new LatLng(dl0,dlg0);
            mMap.addMarker(new MarkerOptions().position(madrid0).title("Destination 1"));

            LatLng madrid1 = new LatLng(dl1,dlg1);
            mMap.addMarker(new MarkerOptions().position(madrid1).title("Destination 2"));


            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barcelona, 9));
        }

//        SharedPreferences prefs = getSharedPreferences("userdetails", MODE_PRIVATE);
//
//        String slat=prefs.getString("lati", "");
//        String slong=prefs.getString("longi", "");
//
//        String slat=prefs.getString("Ulat", "");
//        String slong=prefs.getString("Ulong", "");
//        String storenm=prefs.getString("Store", "");
//        Log.e("Slatlong==>",slat+" "+slong);
//        Log.e("Dlatlong==>",dlat+" "+dlong);
//        String slat=getIntent().getStringExtra("slat");
//        String slong=getIntent().getStringExtra("slong");
//        String dlat=getIntent().getStringExtra("dlat");
//        String dlong=getIntent().getStringExtra("dlong");
//        Log.e("latlong==>",dlat+dlong+slat+slong);

    }
}