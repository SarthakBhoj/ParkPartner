package com.example.parkpartner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkpartner.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<History> historyArrayList;
    Context context;

    public HistoryAdapter(Context ctx, ArrayList<History> historyArrayList){
        inflater = LayoutInflater.from(ctx);
        this.historyArrayList = historyArrayList;
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.booking_receipt, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.alloterName.setText(historyArrayList.get(position).alloter_name);
        holder.alloterEmail.setText(historyArrayList.get(position).alloter_mail);
        holder.alloterMob.setText(historyArrayList.get(position).alloter_no);
        holder.alloterEmail.setText(historyArrayList.get(position).alloter_mail);
        holder.alloterPrice.setText("Amount Paid : "+historyArrayList.get(position).alloter_price);

        }

    @Override
    public int getItemViewType(int position){
        return position;
        }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
        }

    class MyViewHolder extends RecyclerView.ViewHolder {

    TextView alloterName,alloterEmail,alloterMob,alloterPrice;
    LinearLayout layoutHistory;


    public MyViewHolder(View view) {
        super(view);
        context = itemView.getContext();
        alloterName = (TextView) view.findViewById(R.id.AlloterName);
        alloterEmail = (TextView) view.findViewById(R.id.AlloterEmail);
        alloterMob = (TextView) view.findViewById(R.id.AlloterMob);
        alloterPrice = (TextView) view.findViewById(R.id.AmountPaid);
        layoutHistory = (LinearLayout) view.findViewById(R.id.layoutHistory);
    }
}

}