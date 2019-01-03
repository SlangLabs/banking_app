package com.example.akanshisrivastava.bankingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class Statement extends AppCompatActivity {

    CalendarView calendar;
    Button seldate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        calendar = findViewById(R.id.calendar);
        seldate = findViewById(R.id.select_date);

        calendar.setVisibility(View.GONE);

        seldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
            }
        });
    }
}
