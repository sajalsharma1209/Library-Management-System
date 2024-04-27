package com.shashank.platform.classroomappui.ui.students;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddStudent extends AppCompatActivity {
    DatePickerDialog pickerdate;
    RadioGroup radioGroup, radioGroup2;
    int selectedRadioButtonId, selectedRadioButtonId2;
    RadioButton selectedRadioButton, selectedRadioButton2;
    Spinner spinner;
    int planID = 0;
    private List<Plan> planList;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private EditText nameEditText, emailEditText, mobileEditText, dobEditText;
    private TextView nameErrorText, emailErrorText, mobileErrorText, dobErrorText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Student");


        requestQueue = Volley.newRequestQueue(this);

        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        mobileEditText = findViewById(R.id.mobile_edit_text);
        dobEditText = findViewById(R.id.dob_edit_text);
        spinner = findViewById(R.id.plans);
        getPlan();

        radioGroup = findViewById(R.id.radiogroup);

        radioGroup2 = findViewById(R.id.radiogroup1);


        nameErrorText = findViewById(R.id.name_error_text);
        emailErrorText = findViewById(R.id.email_error_text);
        mobileErrorText = findViewById(R.id.mobile_error_text);
        dobErrorText = findViewById(R.id.dob_error_text);


        dobEditText.setInputType(InputType.TYPE_NULL);
        dobEditText.setOnClickListener(view -> {


            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            pickerdate = new DatePickerDialog(AddStudent.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> dobEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            pickerdate.show();
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected PlanID
                int selectedPlanID = planList.get(position).getPlanID();
                planID = selectedPlanID;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        Button submitButton = findViewById(R.id.sign_up);
        submitButton.setOnClickListener(v -> {
            validateName();
            validateEmail();
            validateMobile();
            validateDob();
            if (isFormValid()) {

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Adding...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String dob = dobEditText.getText().toString().trim();

                selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                selectedRadioButton = findViewById(selectedRadioButtonId);
                selectedRadioButtonId2 = radioGroup2.getCheckedRadioButtonId();
                selectedRadioButton2 = findViewById(selectedRadioButtonId2);

                String selectedOptionText = selectedRadioButton.getText().toString();
                String selectedOptionText2 = selectedRadioButton2.getText().toString();


                // Create a JSON object to hold the parameters
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("RegistrationID", "0");
                    jsonBody.put("RefID", getRegistrationId());
                    jsonBody.put("Name", name);
                    jsonBody.put("EmailID", email);
                    jsonBody.put("MobileNo", mobile);
                    jsonBody.put("Password", "");
                    jsonBody.put("gender", selectedOptionText);
                    jsonBody.put("MaritalStatus", selectedOptionText2);
                    jsonBody.put("DOB", dob);
                    jsonBody.put("PlanID", "" + planID);
                    jsonBody.put("Result", "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Student_Insert_Update, jsonBody,
                        response -> {
                            // Handle the response


                            try {
                                String body = response.getString("d");

                                if (body.substring(1, body.length() - 1).equals("Mobile No already registered.")) {
                                    mobileErrorText.setText(body);
                                    mobileEditText.requestFocus();
                                    mobileEditText.setSelection(mobileEditText.getText().length());
                                    mobileErrorText.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();

                                }
                                if (body.substring(1, body.length() - 1).equals("EmailID already registered.")) {
                                    emailErrorText.setText(body);
                                    emailEditText.requestFocus();
                                    emailEditText.setSelection(emailEditText.getText().length());
                                    emailErrorText.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();

                                }
                                if (body.substring(1, body.length() - 1).equals("Registration has been successfully completed.")) {
                                    nameEditText.setText("");
                                    emailEditText.setText("");
                                    mobileEditText.setText("");
                                    mobileErrorText.setVisibility(View.GONE);
                                    emailErrorText.setVisibility(View.GONE);
                                    progressDialog.dismiss();
                                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddStudent.this, ViewStudents.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                throw new RuntimeException(e);
                            }

                        }, error -> {
                    // Handle errors
                    Log.e("Volley Error", error.toString());
                    progressDialog.dismiss();
                    Toast.makeText(AddStudent.this, error.toString(), Toast.LENGTH_SHORT).show();
                });

                // Add the request to the RequestQueue.
                requestQueue.add(jsonObjectRequest);
            }
        });


    }


    @SuppressLint("SetTextI18n")
    private void validateName() {
        String name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            nameErrorText.setText("Please enter student name");
            nameErrorText.setVisibility(View.VISIBLE);
        } else if (!name.matches("[a-zA-Z ]+")) {
            nameErrorText.setText("Invalid name format");
            nameErrorText.setVisibility(View.VISIBLE);
        } else {
            nameErrorText.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void validateEmail() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailErrorText.setText("Please enter student email");
            emailErrorText.setVisibility(View.VISIBLE);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailErrorText.setText("Invalid email format");
            emailErrorText.setVisibility(View.VISIBLE);
        } else {
            emailErrorText.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void validateMobile() {
        String mobile = mobileEditText.getText().toString().trim();
        if (mobile.isEmpty()) {
            mobileErrorText.setText("Please enter student  mobile number");
            mobileErrorText.setVisibility(View.VISIBLE);
        } else if (!mobile.matches("[0-9]{10}")) {
            mobileErrorText.setText("Invalid mobile number format");
            mobileErrorText.setVisibility(View.VISIBLE);
        } else {
            mobileErrorText.setVisibility(View.GONE);
        }
    }


    @SuppressLint("SetTextI18n")
    private void validateDob() {
        String email = dobEditText.getText().toString().trim();
        if (email.isEmpty()) {
            dobErrorText.setText("Please select date of birth");
            dobErrorText.setVisibility(View.VISIBLE);
        } else {
            dobErrorText.setVisibility(View.GONE);
        }
    }

    private boolean isFormValid() {
        return nameErrorText.getVisibility() == View.GONE &&
                emailErrorText.getVisibility() == View.GONE &&
                mobileErrorText.getVisibility() == View.GONE &&
                dobErrorText.getVisibility() == View.GONE;
    }

    private String getRegistrationId() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("registration_id", "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AddStudent.this, ViewStudents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }

    private void getPlan() {

        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("PlanID", "0");
            jsonBody.put("RegistrationID", getRegistrationId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        planList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.get_Plan, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse JSON object and populate planList

                            JSONArray jsonArrayRequest = new JSONArray(response.getString("d"));

                            for (int i = 0; i < jsonArrayRequest.length(); i++) {
                                JSONObject jsonObject = jsonArrayRequest.getJSONObject(i);

                                int planID = jsonObject.getInt("PlanID");
                                String planName = jsonObject.getString("PlanName");

                                planList.add(new Plan(planID, planName));
                            }


                            // Create ArrayAdapter using planList
                            ArrayAdapter<Plan> adapter = new ArrayAdapter<>(AddStudent.this, android.R.layout.simple_spinner_item, planList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // Set adapter to spinner
                            spinner.setAdapter(adapter);

                            // Set spinner item selected listener


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue
        requestQueue.add(request);

    }

    // Model class to hold plan data
    private class Plan {
        private int planID;
        private String planName;

        public Plan(int planID, String planName) {
            this.planID = planID;
            this.planName = planName;
        }

        public int getPlanID() {
            return planID;
        }

        @Override
        public String toString() {
            return planName;
        }
    }
}