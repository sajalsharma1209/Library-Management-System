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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.ui.plans.AddPlans;
import com.shashank.platform.classroomappui.ui.plans.ViewPlans;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class AddStudent extends AppCompatActivity {
    DatePickerDialog pickerdate;
    RadioGroup radioGroup, radioGroup2;
    int selectedRadioButtonId, selectedRadioButtonId2;
    RadioButton selectedRadioButton, selectedRadioButton2;
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

                                if (body.equals("Mobile No already registered.")) {
                                    mobileErrorText.setText(body);
                                    mobileEditText.requestFocus();
                                    mobileEditText.setSelection(mobileEditText.getText().length());
                                    mobileErrorText.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();

                                } else if (body.equals("EmailID already registered.")) {
                                    emailErrorText.setText(body);
                                    emailEditText.requestFocus();
                                    emailEditText.setSelection(emailEditText.getText().length());
                                    emailErrorText.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();

                                } else {
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
            nameErrorText.setText("Please enter your Library name");
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
            emailErrorText.setText("Please enter your email");
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
            mobileErrorText.setText("Please enter your mobile number");
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
}