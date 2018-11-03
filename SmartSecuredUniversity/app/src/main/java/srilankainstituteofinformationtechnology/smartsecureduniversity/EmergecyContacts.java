package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import srilankainstituteofinformationtechnology.smartsecureduniversity.Service.User;

public class EmergecyContacts extends AppCompatActivity {

    private Button btnAddEM;
    private Button btnEditEM;
    private TextView emergencyContact1;
    private TextView emergencyContact2;
    private TextView emergencyContact3;
    private FirebaseAuth mAuth;
    private DatabaseReference mDriverDatabase;
    private String userID;

    private String eContact1;
    private String eContact2;
    private String eContact3;
    String token = Common.currentToken = FirebaseInstanceId.getInstance().getToken();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergecy_contacts);

        btnAddEM = (Button) findViewById(R.id.btnAddEmergencyContactNumber);

        emergencyContact1 = (TextView) findViewById(R.id.tfEmergencyContactNumber1);
        emergencyContact2 = (TextView) findViewById(R.id.tfEmergencyContactNumber2);
        emergencyContact3 = (TextView) findViewById(R.id.tfEmergencyContactNumber3);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userID);
        getUserInfo();
    }

    public void AddContacts(View view){

        eContact1 = emergencyContact1.getText().toString();
        eContact2 = emergencyContact2.getText().toString();
        eContact3 = emergencyContact3.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("eContact1", eContact1);
        userInfo.put("eContact2", eContact2);
        userInfo.put("eContact3", eContact3);
        userInfo.put("token", token);

        mDriverDatabase.updateChildren(userInfo);
        Toast.makeText(this,"Contacts Added",Toast.LENGTH_SHORT).show();
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



}
