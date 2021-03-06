package srilankainstituteofinformationtechnology.smartsecureduniversity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        e1=(EditText)findViewById(R.id.logUsername);
        e2=(EditText)findViewById(R.id.logPassword);
        auth = FirebaseAuth.getInstance();
    }



    public void login(View v) {

        if(e1.getText().toString().isEmpty()||e2.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter Valid Details",Toast.LENGTH_LONG).show();
        }
        Globals g = Globals.getInstance();
        g.setData(e1.getText().toString());

            auth.signInWithEmailAndPassword(e1.getText().toString(),e2.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"User logged in successfully",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(), Main.class);
                                startActivity(intent);
                            }

                            else{

                                Toast.makeText(getApplicationContext(),"Check Email, Password or Network",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }




    public void LoadRegisterView(View v){
        Intent intent = new Intent(getApplicationContext(), UserRegister.class);
        startActivity(intent);
    }
}
