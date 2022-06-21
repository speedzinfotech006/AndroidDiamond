package com.dnk.shairugems.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Class.DashboardClass;
import com.dnk.shairugems.R;

import java.util.List;

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.ViewHolder> {

    Context context;
    List<DashboardClass> mainlist;
    DashboardAdapterListener dlistener;

    public interface DashboardAdapterListener {
        void onclick(DashboardClass obj,int pos);
    }


    public AdapterDashboard(Context context, List<DashboardClass> mainlist, DashboardAdapterListener dlistener) {
        this.context = context;
        this.mainlist = mainlist;
        this.dlistener= dlistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_dashboard, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.Txtmain.setText(mainlist.get(position).getMainData());
        holder.Txtsub.setText(mainlist.get(position).getSubData());
        holder.img.setImageResource(mainlist.get(position).getImg());

        holder.maincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlistener.onclick(mainlist.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView maincard;
        TextView Txtmain,Txtsub;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            maincard = itemView.findViewById(R.id.MainCard);
            Txtmain =itemView.findViewById(R.id.TxtMain);
            Txtsub=itemView.findViewById(R.id.Txtsub);
            img= itemView.findViewById(R.id.imgDashboard);

        }
    }
}
