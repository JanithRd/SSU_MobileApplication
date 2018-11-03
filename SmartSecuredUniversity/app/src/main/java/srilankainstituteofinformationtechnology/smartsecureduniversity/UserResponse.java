package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserResponse extends AppCompatActivity {

    Button userResposeBtn;
    TextView userResponseCommentTf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_response);

        //Added by JanithRd
        userResposeBtn = (Button) findViewById(R.id.btn_UserResponse);
        userResponseCommentTf = (TextView) findViewById(R.id.tf_UserResponseComment);

        userResposeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"You responed to the alert successfully with your comment", Toast.LENGTH_LONG).show();

            }
        });
    }


}
