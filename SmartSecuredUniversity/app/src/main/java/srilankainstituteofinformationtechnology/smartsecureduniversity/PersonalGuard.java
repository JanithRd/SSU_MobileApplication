package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PersonalGuard extends AppCompatActivity implements LocationListener {

    /*Added by JanithR*/
    public ToggleButton togglebutton_PSG;
    public Handler handler = new Handler();
    public Calendar calendar;
    public Runnable testRunnable;
    private LocationManager locationManager;
    double longitude;
    double latitude;
    private DatabaseReference mLocationDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_guard);

        togglebutton_PSG = (ToggleButton) findViewById(R.id.btn_PSGToggle);

        //Location Permission from Manifest
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        try {

            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            onLocationChanged(location);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("Try Catch Exception", String.valueOf(e));
        }


    }

    /*============================================================================================*/

    public void LoadEmergencyContacts(View v){
        Intent intent = new Intent(getApplicationContext(), EmergecyContacts.class);
        startActivity(intent);
    }

    public void LoadSendAlerts(View v){
        Intent intent = new Intent(getApplicationContext(), SendAlerts.class);
        startActivity(intent);
    }

    /*============================================================================================*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {

        try{

            longitude = location.getLongitude();
            latitude = location.getLatitude();

        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("Try Catch Exception", String.valueOf(e));
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /*============================================================================================*/

    //Toggle button functionality
    public void toggleclick(View v){
        //Checking the Toggle button is Checked or not
        if(togglebutton_PSG.isChecked()){

            //Toast.makeText(PersonalGuard.this, "Personal Security Guard - ON", Toast.LENGTH_SHORT).show();

            handler.post(sendData);
        }

        else{
            Toast.makeText(PersonalGuard.this, "Personal Security Guard - OFF", Toast.LENGTH_SHORT).show();
            handler.removeCallbacks(sendData);
        }

    }

    /*============================================================================================*/

    private final Runnable sendData = new Runnable() {
        @Override
        public void run() {
            try {
                //Toast.makeText(PersonalGuard.this, "Personal Security Guard - ON", Toast.LENGTH_SHORT).show();

                String userLongitude = String.valueOf(longitude);
                String userLatitude = String.valueOf(latitude);;

                Toast.makeText(PersonalGuard.this, "Longitude: "+ userLongitude + " | Latitude: " + userLatitude, Toast.LENGTH_SHORT).show();

                handler.postDelayed(this, 4000);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

    };


}
