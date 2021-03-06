package in.slanglabs.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.slanglabs.bankingapp.slang.ActivityDetector;

public class Accounts extends AppCompatActivity {

    Button services, recent, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        getSupportActionBar().setTitle("Account");

        services = findViewById(R.id.services);
        recent = findViewById(R.id.recent);
        account = findViewById(R.id.account_statement);

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accounts.this, Services.class);
                startActivity(intent);
            }
        });

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accounts.this, RecentTransactions.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accounts.this, AccountStatement.class);
                intent.putExtra(ActivityDetector.VIEW_STATEMENT_MODE, ActivityDetector.VIEW_STATEMENT_DEFAULT);
                startActivity(intent);
            }
        });

    }
}
