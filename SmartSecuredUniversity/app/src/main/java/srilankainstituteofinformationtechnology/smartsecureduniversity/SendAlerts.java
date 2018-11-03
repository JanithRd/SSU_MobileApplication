package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;




public class SendAlerts extends AppCompatActivity  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private static final int LOCATION_REQUEST_CODE = 1;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    Button send;
    Button saveLocationToFirebase;
    LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private String eContact1;
    private String eContact2;
    private String eContact3;

    private TextView emergencyContact1;
    private TextView emergencyContact2;
    private TextView emergencyContact3;

    private FirebaseAuth mAuth;
    private DatabaseReference mDriverDatabase;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mLocationDatabaseReference;
    String value_lat = null;
    String value_lng=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alerts);
        FirebaseApp.initializeApp(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mLocationDatabaseReference= mFirebaseDatabase.getReference().child("AlertTeam");

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userID);

        send = (Button) findViewById(R.id.sendSMS);

        emergencyContact1 = (TextView) findViewById(R.id.textView18);
        emergencyContact2 = (TextView) findViewById(R.id.textView19);
        emergencyContact3 = (TextView) findViewById(R.id.textView20);
        saveLocationToFirebase = (Button) findViewById(R.id.button12);
        buildGoogleApiClient();
        getUserInfo();
        // Toast.makeText(this,"Phone Number "+phoneNumber1,Toast.LENGTH_SHORT).show();



        send.setEnabled(false);
        if (checkPermission(android.Manifest.permission.SEND_SMS)){
            send.setEnabled(true);
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }



    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    //Added by JanithR
    public void onSend(View v){


        String messageBody = "Action Required! Please contact SLIIT Security Team on: 0117 544 801";

        eContact1 = emergencyContact1.getText().toString();
        eContact2 = emergencyContact2.getText().toString();
        eContact3 = emergencyContact3.getText().toString();


        if (eContact1 == null || eContact1.length() == 0 ||
                messageBody == null || messageBody.length() == 0){
            return;
        }

        if (eContact2 == null || eContact2.length() == 0 ||
                messageBody == null || messageBody.length() == 0){
            return;
        }

        if (eContact3 == null || eContact3.length() == 0 ||
                messageBody == null || messageBody.length() == 0){
            return;
        }

        if (checkPermission(android.Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(eContact1,null,messageBody,null,null);
            smsManager.sendTextMessage(eContact2,null,messageBody,null,null);
            smsManager.sendTextMessage(eContact3,null,messageBody,null,null);


            Toast.makeText(this,"Message Sent!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Permission Denied!",Toast.LENGTH_SHORT).show();
        }
    }


    private void getUserInfo() {
        mDriverDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("eContact1")!=null){
                        eContact1 = map.get("eContact1").toString();
                        emergencyContact1.setText(eContact1);

                    }
                    if(map.get("eContact2")!=null){
                        eContact2 = map.get("eContact2").toString();
                        emergencyContact2.setText(eContact2);
                    }
                    if(map.get("eContact3")!=null){
                        eContact3 = map.get("eContact3").toString();
                        emergencyContact3.setText(eContact3);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //Added by JanithR
    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);

        return (check == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {



        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SendAlerts.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            value_lat= String.valueOf(mLastLocation.getLatitude());
            value_lng =String.valueOf(mLastLocation.getLongitude());

            saveLocationToFirebase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* mLocationDatabaseReference.push().setValue("Latitude : "+value_lat +"  &amp; Longitude : "+value_lng);
                    Toast.makeText(SendAlerts.this ,"Location saved to the Firebasedatabase",Toast.LENGTH_LONG).show();*/


                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                   /* GeoFire geoFireAvailable = new GeoFire(mLocationDatabaseReference);
                    geoFireAvailable.setLocation(userId, new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
*/

                    Map userInfo = new HashMap();
                    userInfo.put("lat", mLastLocation.getLatitude());
                    userInfo.put("lng", mLastLocation.getLongitude());
                    userInfo.put("userId", userId);

                    mLocationDatabaseReference.updateChildren(userInfo);


                }
            });
        }

    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {



        /*DatabaseReference refWorking = FirebaseDatabase.getInstance().getReference("EmergencyTracking");
        GeoFire geoFireAvailable = new GeoFire(refWorking);
        geoFireAvailable.setLocation("location", new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    Toast.makeText(SendAlerts.this, "There was an error saving the location to GeoFire: " + error, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SendAlerts.this, "Location saved on server successfully!", Toast.LENGTH_LONG).show();

                }
            }
        });*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void LoadUserResponseView(View v){
        Intent intent = new Intent(getApplicationContext(), UserResponse.class);
        startActivity(intent);
    }
}
