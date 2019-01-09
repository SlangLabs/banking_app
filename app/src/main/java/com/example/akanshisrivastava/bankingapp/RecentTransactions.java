package com.example.akanshisrivastava.bankingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.akanshisrivastava.bankingapp.adapters.RecentTransactionsAdapter;
import com.example.akanshisrivastava.bankingapp.network.RecentTransactionsPojo;

import java.util.List;

public class RecentTransactions extends AppCompatActivity {

    private RecentTransactionsAdapter adapter;
    private RecyclerView recyclerView;
    private List<RecentTransactionsPojo> recentTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_transactions);
        recentTransactions = RecentTransactionsPojo.initRecentList(getResources());

        recyclerView = findViewById(R.id.recent_transactions_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecentTransactionsAdapter(this, recentTransactions);
        recyclerView.setAdapter(adapter);
    }
}
