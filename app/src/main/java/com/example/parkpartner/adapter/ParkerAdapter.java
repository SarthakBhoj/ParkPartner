package com.example.parkpartner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkpartner.AlloterDetailActivity;
import com.example.parkpartner.CurrentLocationActivity;
import com.example.parkpartner.FindParkingActivity;
import com.example.parkpartner.MainActivity;
import com.example.parkpartner.R;

import java.util.ArrayList;

public class ParkerAdapter extends RecyclerView.Adapter<ParkerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Parker> parkerArrayList;
    Context context;

    public ParkerAdapter(Context ctx, ArrayList<Parker> parkerArrayList){

        inflater = LayoutInflater.from(ctx);
        this.parkerArrayList = parkerArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.alloter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.userName.setText(parkerArrayList.get(position).alloter_name);
        holder.userGmail.setText(parkerArrayList.get(position).alloter_mail);

        holder.layoutAlloter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(context, AlloterDetailActivity.class);
                    intent.putExtra("id", "" + parkerArrayList.get(position).alloter_id);
                    Toast.makeText(context, parkerArrayList.get(position).alloter_id, Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                catch (Exception ex) {
                    Log.e("Ex", "onClick: " + ex);
                }
            }
        });
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public int getItemCount() {
        return parkerArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName,userGmail;
        LinearLayout layoutAlloter;

        public MyViewHolder(@NonNull View view) {
            super(view);
            context = itemView.getContext();
            userName = (TextView) view.findViewById(R.id.userName);
            userGmail = (TextView) view.findViewById(R.id.userGmail);
            layoutAlloter = (LinearLayout) view.findViewById(R.id.layoutAlloter);
        }
    }


}