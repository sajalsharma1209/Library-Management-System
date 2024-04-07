package com.shashank.platform.classroomappui.ui.Books.Fragments.Books_Details;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.shashank.platform.classroomappui.R;

import java.util.ArrayList;
import java.util.Collection;

public class Book_Details_Adapter extends RecyclerView.Adapter<Book_Details_Adapter.myviewholder> implements Filterable {
    ArrayList<Book_Details_Model> datalist;
    ArrayList<Book_Details_Model> backup;

    public Book_Details_Adapter(ArrayList<Book_Details_Model> datalist) {
        this.datalist = datalist;
        backup = new ArrayList<>(datalist);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_details_single, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getPublication());
        holder.t3.setText(datalist.get(position).getId());
        holder.t4.setText(datalist.get(position).getAuthor());
        holder.t5.setText(datalist.get(position).getPages());
        holder.t6.setText(datalist.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Book_Details_Model> filterdata = new ArrayList<>();

            if (keyword.toString().isEmpty()) {
                filterdata.addAll(backup);
            } else {
                for (Book_Details_Model obj : backup) {
                    if (obj.getId().contains(keyword.toString().trim())) {
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
            datalist.addAll((Collection<? extends Book_Details_Model>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    static class myviewholder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3, t4, t5, t6;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
//            t1 = itemView.findViewById(R.id.t1);
//            t2 = itemView.findViewById(R.id.t2);
//            t3 = itemView.findViewById(R.id.t3);
//            t4 = itemView.findViewById(R.id.t4);
//            t5 = itemView.findViewById(R.id.t5);
//            t6 = itemView.findViewById(R.id.t6);
        }
    }
}
