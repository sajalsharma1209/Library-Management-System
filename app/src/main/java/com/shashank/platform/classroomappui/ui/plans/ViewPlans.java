package com.shashank.platform.classroomappui.ui.plans;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.ui.home.Home;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPlans extends AppCompatActivity {

    RecyclerView recview;
    ArrayList<ViewPlansModel> datalist;
    ViewPlansAdapter adapter;
    ImageView imageView;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_plans);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Plans");

        requestQueue = Volley.newRequestQueue(this);

        recview = findViewById(R.id.recview);

        imageView = findViewById(R.id.imageView);


        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Plans...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        // Create a JSON object to hold the parameters
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("PlanID", "0");
            jsonBody.put("RegistrationID", getRegistrationId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.get_Plan, jsonBody,
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
                            ViewPlansModel obj = new ViewPlansModel(innerJsonObject.getString("PlanID"), innerJsonObject.getString("PlanName"), innerJsonObject.getString("Amount"), innerJsonObject.getString("RegistrationID"), innerJsonObject.getString("PlanTypeID"), innerJsonObject.getString("PlanType"), innerJsonObject.getString("Duration"), innerJsonObject.getString("PlanDesc"), innerJsonObject.getString("CreateOn"));
                            datalist.add(obj);
                            adapter = new ViewPlansAdapter(datalist);
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

        adapter = new ViewPlansAdapter(datalist);
        recview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String getRegistrationId() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        return sharedPreferences.getString("registration_id", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.planadd, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_plan) {
            startActivity(new Intent(ViewPlans.this, AddPlans.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewPlans.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ViewPlans.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }

}