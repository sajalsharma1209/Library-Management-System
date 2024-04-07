package com.shashank.platform.classroomappui.ui.Books.Fragments.Books_Details;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.shashank.platform.classroomappui.R;

import java.util.ArrayList;


public class BookDetailsFragment extends Fragment {
    RecyclerView recview;
    ArrayList<Book_Details_Model> datalist;
    Book_Details_Adapter adapter;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;


    public BookDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
//
//
//    @SuppressLint("NotifyDataSetChanged")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_book_details, container, false);
//
//        progressBar = v.findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);
//
//        recview = v.findViewById(R.id.recview);
//        recview.setVisibility(View.GONE);
//
//        swipeRefreshLayout = v.findViewById(R.id.swiperefresh);
//
//        recview.setLayoutManager(new LinearLayoutManager(getContext()));
//        datalist = new ArrayList<>();
//
////        adapter = new Book_Details_Adapter(datalist);
//
//        Conn conn = new Conn(getContext());
//        Cursor cursor = conn.get_book_details();
//
//        if (cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                Book_Details_Model obj = new Book_Details_Model(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
//                datalist.add(obj);
//
//                progressBar.setVisibility(View.GONE);
//                recview.setVisibility(View.VISIBLE);
//            }
//            adapter = new Book_Details_Adapter(datalist);
//            recview.setAdapter(adapter);
//        } else {
//            Toasty.info(getContext(), "There's no book ", Toasty.LENGTH_SHORT).show();
//
//            progressBar.setVisibility(View.GONE);
//            recview.setVisibility(View.GONE);
//        }
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Cursor cursor = conn.get_book_details();
//
//                if (cursor != null && cursor.getCount() > 0) {
//                    datalist.clear();
//                    while (cursor.moveToNext()) {
//                        Book_Details_Model obj = new Book_Details_Model(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
//                        datalist.add(obj);
//
//                        progressBar.setVisibility(View.GONE);
//                        recview.setVisibility(View.VISIBLE);
//                    }
//                    adapter = new Book_Details_Adapter(datalist);
//                    recview.setAdapter(adapter);
//                }
//
//
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//        });
//
//        return v;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//        inflater.inflate(R.menu.search_bar, menu);
//        MenuItem item = menu.findItem(R.id.searchbooks);
//        //  item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//
//        SearchView searchView = (SearchView) item.getActionView();
//
//        searchView.setQueryHint("Search Book by id");
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                // Here is where we are going to implement the filter logic
//                adapter.getFilter().filter(s);
//                return true;
//            }
//
//        });
//
//
//    }
