package com.example.edunomics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewholder>
{
    Context context;
    ArrayList<Users> list;
    public  AdapterClass(Context context,ArrayList<Users> list)
    {
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new MyViewholder(LayoutInflater.from(context).inflate(R.layout.person,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.status.setText(list.get(position).getStatus());
        Picasso.get().load(list.get(position).getImage()).into(holder.image1);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView status;
        ImageView image1;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            TextView tvName,tvStatus;
            ImageView image;

            name=itemView.findViewById(R.id.name);
            status=itemView.findViewById(R.id.status);
            image1=itemView.findViewById(R.id.image);

        }
    }

}

