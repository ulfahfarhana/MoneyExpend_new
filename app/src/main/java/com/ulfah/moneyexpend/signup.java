package com.ulfah.moneyexpend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    private EditText mPersonName;
    private EditText mEmail;
    private EditText mTextPhone;
    private EditText mPassword;
    private CheckBox mTNC;
    private Button btnReg;
    private TextView mLogin;

    private ProgressDialog mDialog;

    //firebase

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);

        registration();
    }

    private void registration() {
        mPersonName=findViewById(R.id.PersonName);
        mEmail=findViewById(R.id.email_reg);
        mTextPhone=findViewById(R.id.TextPhone);
        mPassword=findViewById(R.id.password_reg);
        mTNC=findViewById(R.id.tncpp);
        btnReg=findViewById(R.id.signup_submit);
        mLogin=findViewById(R.id.login_reg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=mPersonName.getText().toString().trim();
                String email=mEmail.getText().toString().trim();
                Integer phone=mTextPhone.getEditableText().length();
                String pass=mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    mPersonName.setError("name required");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("email required");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    mPassword.setError("password required");
                }

                mDialog.setMessage("PROCESSING");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "SIGNUP SUCCEED", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"SIGNUP FAILED", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }


}