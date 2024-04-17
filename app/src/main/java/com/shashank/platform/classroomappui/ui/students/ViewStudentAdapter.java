package com.shashank.platform.classroomappui.ui.students;

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
import com.shashank.platform.classroomappui.ui.plans.UpdatePlans;

import java.util.ArrayList;

public class ViewStudentAdapter extends RecyclerView.Adapter<ViewStudentAdapter.viewholder> {

    ArrayList<ViewStudentModel> datalist;

    public ViewStudentAdapter(ArrayList<ViewStudentModel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_single, parent, false);
        return new ViewStudentAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(datalist.get(position).getName());
        holder.mobile.setText(datalist.get(position).getMobileNo());
        holder.gender.setText(datalist.get(position).getGender());
        holder.email.setText(datalist.get(position).getEmailID());
        holder.martial.setText(datalist.get(position).getMaritalStatus());
        holder.dob.setText(datalist.get(position).getDOB());
        holder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(holder.name.getContext(), UpdateStudent.class);

            intent.putExtra("UserID", datalist.get(position).getUserID());
            intent.putExtra("Name", datalist.get(position).getName());
            intent.putExtra("MobileNo", datalist.get(position).getMobileNo());
            intent.putExtra("EmailID", datalist.get(position).getEmailID());
            intent.putExtra("Gender", datalist.get(position).getGender());
            intent.putExtra("MaritalStatus", datalist.get(position).getMaritalStatus());
            intent.putExtra("DOB", datalist.get(position).getDOB());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            holder.name.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    public static class viewholder extends RecyclerView.ViewHolder {
        TextView name, mobile, martial, dob, gender, email;
        ImageView edit;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            dob = itemView.findViewById(R.id.userDob);
            gender = itemView.findViewById(R.id.userGender);
            mobile = itemView.findViewById(R.id.userMobile);
            martial = itemView.findViewById(R.id.userMaritalStatus);
            edit = itemView.findViewById(R.id.updateStudent);


        }
    }


}
