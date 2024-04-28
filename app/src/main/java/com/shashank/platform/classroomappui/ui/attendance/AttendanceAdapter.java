package com.shashank.platform.classroomappui.ui.attendance;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.platform.classroomappui.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.viewholder> {

    ArrayList<AttendanceModel> datalist;

    public AttendanceAdapter(ArrayList<AttendanceModel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_attendance_single, parent, false);
        return new AttendanceAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.name.setText(datalist.get(position).getName());
        holder.amt.setText(convertDate(datalist.get(position).getDate()));


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
//            type = itemView.findViewById(R.id.planType);
//            desc = itemView.findViewById(R.id.planDesc);
            amt = itemView.findViewById(R.id.planAmount);
//            duration = itemView.findViewById(R.id.planDuration);
//            edit = itemView.findViewById(R.id.updatePlan);


        }
    }

    public String convertDate(String originalDateStr) {
        try {
            // Parse the original date string
            @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date originalDate = originalFormat.parse(originalDateStr);

            // Format the date in the desired format
            @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM//yyyy");
            return newFormat.format(originalDate);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle parse exception if necessary
            return null;
        }
    }
}
