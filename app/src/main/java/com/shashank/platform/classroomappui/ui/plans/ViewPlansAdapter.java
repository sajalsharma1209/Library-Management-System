package com.shashank.platform.classroomappui.ui.plans;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.platform.classroomappui.R;

import java.util.ArrayList;

public class ViewPlansAdapter extends RecyclerView.Adapter<ViewPlansAdapter.viewholder> {

    ArrayList<ViewPlansModel> datalist;

    public ViewPlansAdapter(ArrayList<ViewPlansModel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_plan_single, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(datalist.get(position).getPlanName());
        holder.type.setText(datalist.get(position).getPlanType());
        holder.desc.setText(datalist.get(position).getPlanDesc());
        holder.amt.setText(datalist.get(position).getAmount());
        holder.duration.setText(datalist.get(position).getDuration());
        holder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(holder.name.getContext(), UpdatePlans.class);

            intent.putExtra("PlanName", datalist.get(position).getPlanName());
            intent.putExtra("PlanID", datalist.get(position).getPlanID());
            intent.putExtra("Amount", datalist.get(position).getAmount());
            intent.putExtra("PlanTypeID", datalist.get(position).getPlanTypeID());
            intent.putExtra("PlanType", datalist.get(position).getPlanType());
            intent.putExtra("Duration", datalist.get(position).getDuration());
            intent.putExtra("PlanDesc", datalist.get(position).getPlanDesc());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            holder.name.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView name, type, desc, amt, duration;
        ImageView edit;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.planName);
            type = itemView.findViewById(R.id.planType);
            desc = itemView.findViewById(R.id.planDesc);
            amt = itemView.findViewById(R.id.planAmount);
            duration = itemView.findViewById(R.id.planDuration);
            edit = itemView.findViewById(R.id.updatePlan);


        }
    }
}
