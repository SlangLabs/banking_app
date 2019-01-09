package com.example.akanshisrivastava.bankingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akanshisrivastava.bankingapp.R;
import com.example.akanshisrivastava.bankingapp.network.RecentTransactionsPojo;

import java.util.List;

public class RecentTransactionsAdapter extends RecyclerView.Adapter<RecentTransactionsAdapter.ViewHolder> {
    private List<RecentTransactionsPojo> recentTransactions;
    private Context context;

    public RecentTransactionsAdapter(Context context, List<RecentTransactionsPojo> recentTransactions) {
        this.recentTransactions = recentTransactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recent_transactions_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (recentTransactions != null && i < recentTransactions.size()) {
            RecentTransactionsPojo recentTransaction = recentTransactions.get(i);
            viewHolder.date.setText(recentTransaction.date);
            viewHolder.info.setText(recentTransaction.info);
            if(recentTransaction.debit > 0) {
                viewHolder.amount.setText(String.valueOf(recentTransaction.debit));
                viewHolder.type.setText("Dr");
            }
            else {
                viewHolder.amount.setText(String.valueOf(recentTransaction.credit));
                viewHolder.type.setText("Cr");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (recentTransactions == null)
            return 0;
        else
            return recentTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView info;
        public TextView amount;
        public TextView type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.recent_date);
            info = itemView.findViewById(R.id.recent_info);
            amount = itemView.findViewById(R.id.recent_amount);
            type = itemView.findViewById(R.id.recent_type);
        }
    }
}
