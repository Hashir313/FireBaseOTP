package com.example.firebaseotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextPhoneNumber;
    Button buttonRegister;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        editTextPhoneNumber = findViewById(R.id.editPhone);
        buttonRegister = findViewById(R.id.registerButton);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                GetOTPCode();
        }
    }

    private void GetOTPCode() {
        if (!editTextPhoneNumber.getText().toString().isEmpty()) {
            if ((editTextPhoneNumber.getText().toString().trim()).length() == 10) {


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+92" + editTextPhoneNumber.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        MainActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String backEndOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Intent intent = new Intent(getApplicationContext(), SendOTP.class);
                                intent.putExtra("mobile", editTextPhoneNumber.getText().toString());
                                intent.putExtra("backEndOTP", backEndOTP);
                                startActivity(intent);
                            }
                        }
                );


            } else {
                Toast.makeText(this, "Enter valid number", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Phone number is required", Toast.LENGTH_LONG).show();
        }
    }
}