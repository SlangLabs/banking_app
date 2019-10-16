package in.slanglabs.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import in.slanglabs.platform.SlangBuddy;

public class MainActivity extends AppCompatActivity {

    Button account, transfer, care, bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = findViewById(R.id.account);
        transfer = findViewById(R.id.mtransfer);
        care = findViewById(R.id.care);
        bills = findViewById(R.id.bills);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Accounts.class);
                startActivity(intent);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoneyTransfer.class);
                startActivity(intent);
            }
        });
        care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerCare.class);
                startActivity(intent);
            }
        });
        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Bills.class);
                startActivity(intent);
            }
        });
        try {
            SlangBuddy.getBuiltinUI().show(this);
        } catch (Exception e) {
            Log.d("MainActivity", "SlangBuddy not yet initialized");
        }
    }
}
