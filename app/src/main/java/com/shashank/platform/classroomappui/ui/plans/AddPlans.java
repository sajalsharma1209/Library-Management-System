package com.shashank.platform.classroomappui.ui.plans;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class AddPlans extends AppCompatActivity {

    Button button;
    EditText nameEditText, amountEditText, descEditText;
    private ProgressDialog progressDialog;
    private TextView nameErrorText, typeErrorText, amountErrorText, durationErrorText, descErrorText;
    private Spinner spinner, spinner2;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Plan");

        spinner = findViewById(R.id.plan_type);

        spinner2 = findViewById(R.id.duration);

        nameEditText = findViewById(R.id.plan_name);
        amountEditText = findViewById(R.id.plan_amount);
        descEditText = findViewById(R.id.description);
        button = findViewById(R.id.addBtn);


        nameErrorText = findViewById(R.id.name_error_text);
        typeErrorText = findViewById(R.id.type_error_text);
        amountErrorText = findViewById(R.id.amount_error_text);
        durationErrorText = findViewById(R.id.duration_error_text);
        descErrorText = findViewById(R.id.desc_error_text);

        List<String> values = Arrays.asList("-Select Plan Type-", "Morning", "AfterNoon", "Evening", "Night", "FullDay");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        List<String> values2 = Arrays.asList("-Select Plan Duration-", "7 Days", "15 Days", "Monthly", "Quarterly", "Half Year", "Yearly");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter2);

        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(v -> {
            validateName();
            validateType();
            validateamount();
            validateduration();
            validatedesc();
            if (isFormValid()) {


                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String name = nameEditText.getText().toString().trim();
                String amount = amountEditText.getText().toString().trim();
                String duration = spinner2.getSelectedItem().toString();
                String desc = descEditText.getText().toString().trim();
                String type = spinner.getSelectedItem().toString();
                String pduration = spinner2.getSelectedItem().toString();


                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                String registration_id = sharedPreferences.getString("registration_id", "");

                JSONObject jsonBody = new JSONObject();
                try {

                    jsonBody.put("PlanID", "0");
                    jsonBody.put("PlanName", name);
                    jsonBody.put("Amount", amount);
                    jsonBody.put("RegistrationID", registration_id);
                    jsonBody.put("PlanTypeID", values.indexOf(type));
                    jsonBody.put("Duration", duration);
                    jsonBody.put("PlanDesc", desc);
                    jsonBody.put("Result", "");


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.plan_Insert_Update, jsonBody,
                        response -> {


                            progressDialog.dismiss();

                            Toast.makeText(this, "Plan Added Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddPlans.this, ViewPlans.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                        }, error -> {
                    // Handle errors
                    Log.e("Volley Error", error.toString());
                    progressDialog.dismiss();
                    Toast.makeText(AddPlans.this, error.toString(), Toast.LENGTH_SHORT).show();
                });


                requestQueue.add(jsonObjectRequest);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AddPlans.this, ViewPlans.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }


    private void validateName() {
        String name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            nameErrorText.setText("Please enter plan name");
            nameErrorText.setVisibility(View.VISIBLE);
        } else if (!name.matches("[a-zA-Z ]+")) {
            nameErrorText.setText("Invalid name format");
            nameErrorText.setVisibility(View.VISIBLE);
        } else {
            nameErrorText.setVisibility(View.GONE);
        }
    }

    private void validateType() {
        String type = spinner.getSelectedItem().toString().trim();
        if (type.equalsIgnoreCase("-Select Plan Type-")) {
            typeErrorText.setText("Please select plan type");
            typeErrorText.setVisibility(View.VISIBLE);

        } else {
            typeErrorText.setVisibility(View.GONE);
        }
    }

    private void validateamount() {
        String amount = amountEditText.getText().toString().trim();
        if (amount.isEmpty()) {
            amountErrorText.setText("Please enter amount");
            amountErrorText.setVisibility(View.VISIBLE);
        } else {
            amountErrorText.setVisibility(View.GONE);
        }
    }

    private void validateduration() {
        String duration = spinner2.getSelectedItem().toString().trim();
        if (duration.equalsIgnoreCase("-Select Plan Duration-")) {
            durationErrorText.setText("Please select a duration");
            durationErrorText.setVisibility(View.VISIBLE);
        } else {
            durationErrorText.setVisibility(View.GONE);
        }
    }

    private void validatedesc() {
        String desc = descEditText.getText().toString().trim();
        if (desc.isEmpty()) {
            descErrorText.setText("Please enter plan description");
            descErrorText.setVisibility(View.VISIBLE);
        } else {
            descErrorText.setVisibility(View.GONE);
        }
    }

    private boolean isFormValid() {
        return nameErrorText.getVisibility() == View.GONE &&
                typeErrorText.getVisibility() == View.GONE &&
                amountErrorText.getVisibility() == View.GONE &&
                durationErrorText.getVisibility() == View.GONE &&
                descErrorText.getVisibility() == View.GONE;
    }


}