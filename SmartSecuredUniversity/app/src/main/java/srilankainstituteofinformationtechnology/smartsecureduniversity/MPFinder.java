package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MPFinder extends AppCompatActivity {

    Button findPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpfinder);

        findPerson =(Button) findViewById(R.id.button6);

        Globals g = Globals.getInstance();
        String data=g.getData();

        boolean correct = "admin@gmail.com".equals(data);
        if(correct==false){
            findPerson.setVisibility(View.GONE);
        }

        /*if(data!="admin@gmail.com"){
            findPerson.setVisibility(View.GONE);
        }
*/


    }



    public void LoadFindPerson(View v){
        Intent intent = new Intent(getApplicationContext(), AdminHome.class);
        startActivity(intent);
    }

    public void LoadReportPerson(View v){
        Intent intent = new Intent(getApplicationContext(), UserHome.class);
        startActivity(intent);
    }

}
