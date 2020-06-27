package com.example.edunomics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout textInputUsername;
    TextInputLayout textInputPassword;
    TextInputLayout email;
    Button submit,btnlogin;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textInputUsername=findViewById(R.id.username);
        textInputPassword=findViewById(R.id.password);
        email=findViewById(R.id.email);
        submit=findViewById(R.id.btnsubmit);
        btnlogin=findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(RegisterActivity.this,loginActivity.class);
                startActivity(a);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=textInputUsername.getEditText().getText().toString();
                String email1=email.getEditText().getText().toString();
                String pwd=textInputPassword.getEditText().getText().toString();
                //register(name,email1,pwd);

                if (!validateUsername() | !validatePassword() | !(validateEmail()))
                {
                    Toast.makeText(RegisterActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(name,email1,pwd);
                }

                }


        });


    }

    private void register(final String name, String email, String pwd)
    {
        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID=user.getUid();

                            myRef= FirebaseDatabase.getInstance().getReference("user").child(userID);


                            HashMap<String,String> hm=new HashMap<>();
                            hm.put("id",userID);
                            hm.put("name",name);
                            hm.put("image","https://firebasestorage.googleapis.com/v0/b/edunomics-d2eda.appspot.com/o/pro.png?alt=media&token=def9dcfd-75d2-459c-9f78-768b46883db8");
                            hm.put("status","hey there,i am using this app");

                            myRef.setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Intent a=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(a);
                                        finish();
                                    }

                                }
                            });






                        }
                    }
                });



    }

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 20) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    public boolean confirmInput(View v) {
        if (!validateUsername() | !validatePassword() | !(validateEmail())) {
            return false;
        }
        String input="";
        input += "Username: " + textInputUsername.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        return true;
    }

}
