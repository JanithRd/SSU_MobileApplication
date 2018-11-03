package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.id;
import static srilankainstituteofinformationtechnology.smartsecureduniversity.R.id.regTweetId;

public class UserRegister extends AppCompatActivity implements View.OnClickListener {

    private EditText regEmail,regUsername,regPassword,regTweetId;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        regEmail=(EditText)findViewById(R.id.regEmail);
        regUsername=(EditText)findViewById(R.id.regUsername);
        regPassword=(EditText)findViewById(R.id.regPassword);
        regTweetId=(EditText)findViewById(R.id.regTweetId);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.bRegister).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }



    private void registerUser() {
        final   String userId = regUsername.getText().toString().trim();
        final  String email = regEmail.getText().toString().trim();
        String password = regPassword.getText().toString().trim();
        final String tweetId = regTweetId.getText().toString().trim();
        final   String profileImageUrl ="";
        final  String name ="";


        if (userId.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter Student Id Details",Toast.LENGTH_LONG).show();
            return;
        }

        if (tweetId.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter Twitter Id Details",Toast.LENGTH_LONG).show();
            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter Email Details",Toast.LENGTH_LONG).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter Password Details",Toast.LENGTH_LONG).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        Task<AuthResult> users = mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Users user = new Users(
                                    name,
                                    profileImageUrl,
                                    userId,
                                    tweetId
                            );

                            FirebaseDatabase.getInstance().getReference("Users").child("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), Home.class);
                                        startActivity(intent);
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bRegister:
                registerUser();
                break;
        }
    }
}
