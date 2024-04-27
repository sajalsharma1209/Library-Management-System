package com.shashank.platform.classroomappui.ui.expensereport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.ui.students.ViewStudents;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ExpenseReport extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<ExpenseReportModel> datalist;
    ExpenseReportAdapter adapter;
    ImageView imageView;

    String UserID;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expense_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Expense Report");

        requestQueue = Volley.newRequestQueue(this);

        recview = findViewById(R.id.recview);

        imageView = findViewById(R.id.imageView);


        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        UserID = getIntent().getStringExtra("UserID");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Expenses...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        // Create a JSON object to hold the parameters
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("UserID", "0");
            jsonBody.put("RegistrationID", getRegistrationId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.get_Expense, jsonBody,
                response -> {
                    try {
                        progressDialog.dismiss();

                        // Get the JSON array from the response object
                        JSONArray jsonArray = new JSONArray(response.getString("d"));

                        if (jsonArray.length() < 1) {
                            // If empty, show the ImageView
                            imageView.setVisibility(View.VISIBLE);
                            recview.setVisibility(View.GONE);
                        } else {
                            // If not empty, hide the ImageView
                            imageView.setVisibility(View.GONE);
                            recview.setVisibility(View.VISIBLE);
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject innerJsonObject = jsonArray.getJSONObject(i);
                            ExpenseReportModel obj = new ExpenseReportModel(innerJsonObject.getString("CreditDebit"),innerJsonObject.getString("Amount"), innerJsonObject.getString("CreditDebitDate"), innerJsonObject.getString("Remarks"), innerJsonObject.getString("CashChequeNEFT"), innerJsonObject.getString("FromDate"), innerJsonObject.getString("ToDate"));
                            datalist.add(obj);
                            adapter = new ExpenseReportAdapter(datalist);
                            recview.setAdapter(adapter);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error Json", Objects.requireNonNull(e.getMessage()));
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();    // Handle error
                });


        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);

        adapter = new ExpenseReportAdapter(datalist);
        recview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String getRegistrationId() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        return sharedPreferences.getString("registration_id", "");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ExpenseReport.this, ViewStudents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ExpenseReport.this, ViewStudents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }

}