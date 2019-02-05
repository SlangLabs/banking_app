package in.slanglabs.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.akanshisrivastava.bankingapp.R;

import in.slanglabs.bankingapp.slang.ActivityDetector;

public class Bills extends AppCompatActivity {

    Button credit, elec, postpaid, landline, water, tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        getSupportActionBar().setTitle("Pay Bills");

        credit = findViewById(R.id.bill_credit);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bills.this, BillPayment.class);
                intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_CREDIT);
                startActivity(intent);
            }
        });

        elec = findViewById(R.id.bill_electricity);
        elec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bills.this, BillPayment.class);
                intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_ELEC);
                startActivity(intent);
            }
        });

        postpaid = findViewById(R.id.bill_postpaid);
        postpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bills.this, BillPayment.class);
                intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_POST);
                startActivity(intent);
            }
        });

        landline = findViewById(R.id.bill_landline);
        landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bills.this, BillPayment.class);
                intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_BROADBAND);
                startActivity(intent);
            }
        });

        water = findViewById(R.id.bill_water);
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bills.this, BillPayment.class);
                intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_WATER);
                startActivity(intent);
            }
        });

        tax = findViewById(R.id.bill_tax);
        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bills.this, BillPayment.class);
                intent.putExtra(ActivityDetector.PAYMENT_MODE, ActivityDetector.PAYMENT_TAX);
                startActivity(intent);
            }
        });
    }
}
