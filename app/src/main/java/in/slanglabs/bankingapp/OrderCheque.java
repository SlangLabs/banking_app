package in.slanglabs.bankingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.slanglabs.bankingapp.slang.ActivityDetector;

public class OrderCheque extends AppCompatActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cheque);
        getSupportActionBar().setTitle("Order Cheque Book");

        Intent intent = getIntent();
        String mode = intent.getStringExtra(ActivityDetector.ORDER_CHEQUE_MODE);

        submit = findViewById(R.id.submit_button_cheque);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderCheque.this);
                builder.setMessage("Please confirm your cheque book order.");
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked cancel button
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        if(mode.equals(ActivityDetector.ORDER_CHEQUE_SLANG)) {
            submit.callOnClick();
        }
    }
}
