package com.shashank.platform.classroomappui.ui.paymentdetails;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.platform.classroomappui.R;

import java.util.ArrayList;

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.viewholder> {

    ArrayList<PaymentDetailsModel> datalist;

    public PaymentDetailsAdapter(ArrayList<PaymentDetailsModel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_payment_details_single, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.Amount.setText(datalist.get(position).getAmount() + " (" + datalist.get(position).getCashChequeNEFT() + ")");
        holder.CreditDebitDate.setText(datalist.get(position).getCreditDebitDate());
        // holder.Remarks.setText(datalist.get(position).getRemarks());
        // holder.CashChequeNEFT.setText(datalist.get(position).getCashChequeNEFT());
        holder.FromDate.setText(datalist.get(position).getFromDate().substring(0, 10));
        holder.ToDate.setText(datalist.get(position).getToDate().substring(0, 10));


//        holder.edit.setOnClickListener(v -> {
//            Intent intent = new Intent(holder.name.getContext(), UpdatePlans.class);
//
//            intent.putExtra("PlanName", datalist.get(position).getPlanName());
//            intent.putExtra("PlanID", datalist.get(position).getPlanID());
//            intent.putExtra("Amount", datalist.get(position).getAmount());
//            intent.putExtra("PlanTypeID", datalist.get(position).getPlanTypeID());
//            intent.putExtra("PlanType", datalist.get(position).getPlanType());
//            intent.putExtra("Duration", datalist.get(position).getDuration());
//            intent.putExtra("PlanDesc", datalist.get(position).getPlanDesc());
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            holder.name.getContext().startActivity(intent);
//        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView Amount, CreditDebitDate, Remarks, CashChequeNEFT, FromDate, ToDate;
        ImageView edit;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            Amount = itemView.findViewById(R.id.amt);
            CreditDebitDate = itemView.findViewById(R.id.date);
            // Remarks = itemView.findViewById(R.id.remark);
            // CashChequeNEFT = itemView.findViewById(R.id.payType);
            FromDate = itemView.findViewById(R.id.fromDate);
            ToDate = itemView.findViewById(R.id.toDate);
            // edit = itemView.findViewById(R.id.updatePlan);


        }
    }
}
