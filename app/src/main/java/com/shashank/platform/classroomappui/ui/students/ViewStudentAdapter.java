package com.shashank.platform.classroomappui.ui.students;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.ui.payment.Payment;
import com.shashank.platform.classroomappui.ui.paymentdetails.PaymentDetails;
import com.shashank.platform.classroomappui.ui.seat.AddSeat;
import com.shashank.platform.classroomappui.ui.seat.UpdateSeat;

import java.util.ArrayList;
import java.util.Collection;

public class ViewStudentAdapter extends RecyclerView.Adapter<ViewStudentAdapter.viewholder> implements Filterable {

    ArrayList<ViewStudentModel> datalist;
    private ArrayList<ViewStudentModel> dataListFiltered;
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<ViewStudentModel> filterdata = new ArrayList<>();

            if (keyword.toString().isEmpty()) {
                filterdata.addAll(dataListFiltered);
            } else {
                for (ViewStudentModel obj : dataListFiltered) {
                    if (obj.getMobileNo().contains(keyword.toString().trim())) {
                        filterdata.add(obj);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterdata;
            results.count = filterdata.size();
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            datalist.clear();
            datalist.addAll((Collection<? extends ViewStudentModel>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public ViewStudentAdapter(ArrayList<ViewStudentModel> datalist) {
        this.datalist = datalist;
        this.dataListFiltered = new ArrayList<>(datalist);
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
        holder.planname.setText(datalist.get(position).getPlanName());
        holder.seatno.setText(datalist.get(position).getSeatNo());
        holder.shift.setText(datalist.get(position).getPlanType());
        holder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(holder.name.getContext(), UpdateStudent.class);

            intent.putExtra("UserID", datalist.get(position).getUserID());
            intent.putExtra("Name", datalist.get(position).getName());
            intent.putExtra("MobileNo", datalist.get(position).getMobileNo());
            intent.putExtra("EmailID", datalist.get(position).getEmailID());
            intent.putExtra("Gender", datalist.get(position).getGender());
            intent.putExtra("MaritalStatus", datalist.get(position).getMaritalStatus());
            intent.putExtra("DOB", datalist.get(position).getDOB());
            intent.putExtra("PlanName", datalist.get(position).getPlanName());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            holder.name.getContext().startActivity(intent);
        });

        holder.seat.setOnClickListener(v -> {
            if (datalist.get(position).getSeatNo().equalsIgnoreCase("0")) {
                Intent intent = new Intent(holder.name.getContext(), AddSeat.class);
                intent.putExtra("UserID", datalist.get(position).getUserID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                holder.name.getContext().startActivity(intent);
            } else {
                Intent intent = new Intent(holder.name.getContext(), UpdateSeat.class);
                intent.putExtra("UserID", datalist.get(position).getUserID());
                intent.putExtra("seat", datalist.get(position).getSeatNo());
                intent.putExtra("Shift", datalist.get(position).getPlanType());
                intent.putExtra("SeatID", datalist.get(position).getSeatID());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                holder.name.getContext().startActivity(intent);
            }
        });

        holder.pay.setOnClickListener(v -> {
            Intent intent = new Intent(holder.name.getContext(), Payment.class);

            intent.putExtra("UserID", datalist.get(position).getUserID());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            holder.name.getContext().startActivity(intent);
        });
        holder.paymentDetails.setOnClickListener(v -> {
            Intent intent = new Intent(holder.name.getContext(), PaymentDetails.class);

            intent.putExtra("UserID", datalist.get(position).getUserID());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            holder.name.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView name, mobile, martial, dob, gender, email, planname, seatno, shift;
        CardView edit, seat, pay,paymentDetails;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            dob = itemView.findViewById(R.id.userDob);
            gender = itemView.findViewById(R.id.userGender);
            mobile = itemView.findViewById(R.id.userMobile);
            martial = itemView.findViewById(R.id.userMaritalStatus);
            planname = itemView.findViewById(R.id.planName);
            edit = itemView.findViewById(R.id.updateStudent);
            seatno = itemView.findViewById(R.id.seatno);
            shift = itemView.findViewById(R.id.shiftType);
            seat = itemView.findViewById(R.id.seatAllot);
            pay = itemView.findViewById(R.id.payment);
            paymentDetails = itemView.findViewById(R.id.paymentDetails);


        }
    }


}
