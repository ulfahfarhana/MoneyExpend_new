package com.ulfah.moneyexpend;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button btnLogin;
    private TextView mSignUp;

    private ProgressDialog mDialog;

    //firebase

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);

        loginDetails();

    }

    private void loginDetails(){

        mEmail=findViewById(R.id.enter_email);
        mPass=findViewById(R.id.enter_password);
        btnLogin=findViewById(R.id.login_button);
        mSignUp=findViewById(R.id.signup_reg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=mEmail.getText().toString().trim();
                String pass=mPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("email required");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    mPass.setError("password required");
                    return;
                }

                mDialog.setMessage("PROCESSING");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            mDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            Toast.makeText(getApplicationContext(),"LOGIN SUCCEED",Toast.LENGTH_SHORT).show();
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });

    }

}