package com.example.akanshisrivastava.bankingapp.slang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.akanshisrivastava.bankingapp.R;

public class OrderCheque extends AppCompatActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cheque);

        submit = findViewById(R.id.submit_button_cheque);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderCheque.this, "Your request for ordering a new " +
                        "cheque book has been received", Toast.LENGTH_LONG).show();
            }
        });
    }
}
