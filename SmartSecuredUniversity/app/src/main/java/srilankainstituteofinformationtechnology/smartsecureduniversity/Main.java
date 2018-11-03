package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MY TOKEN", Common.currentToken);
    }

    public void LoadMPFview(View v){
        Intent intent = new Intent(getApplicationContext(), MPFinder.class);
        startActivity(intent);
    }


    public void LoadPersonalGuardview(View v){
        Intent intent = new Intent(getApplicationContext(), PersonalGuard.class);
        startActivity(intent);
    }

    public void LoadSendAlerts(View v){
        Intent intent = new Intent(getApplicationContext(), SendAlerts.class);
        startActivity(intent);
    }

}
