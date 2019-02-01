package com.example.akanshisrivastava.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.slanglabs.platform.SlangBuddy;

public class CustomerCare extends AppCompatActivity {

    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care);

        button =findViewById(R.id.send_button);
        editText =findViewById(R.id.editTextFeedback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"help@bank.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Digital Bank");
                i.putExtra(Intent.EXTRA_TEXT, "Message : "+ editText.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send feedback..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(CustomerCare.this, "Thank you for your valuable feedback.",
                        Toast.LENGTH_LONG).show();
            }
        });
        try {
            SlangBuddy.getBuiltinUI().hide();
        } catch (Exception e) {
            Log.d("SplashScreen", "SlangBuddy not yet initialized");
        }
    }

    @Override
    public void onBackPressed() {
        try {
            SlangBuddy.getBuiltinUI().show();
        } catch (Exception e) {
            Log.d("SplashScreen", "SlangBuddy not yet initialized");
        }
        finish();
    }
}
