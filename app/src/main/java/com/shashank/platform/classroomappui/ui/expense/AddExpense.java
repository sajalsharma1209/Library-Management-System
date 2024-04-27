package com.shashank.platform.classroomappui.ui.expense;

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
import android.view.View;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.ui.expensereport.ExpenseReport;
import com.shashank.platform.classroomappui.ui.home.Home;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddExpense extends AppCompatActivity {

    DatePickerDialog pickerdate;
    RadioGroup radioGroup, radioGroup2;
    int selectedRadioButtonId, selectedRadioButtonId2;
    RadioButton selectedRadioButton, selectedRadioButton2;
    Spinner spinner;
    int planID = 0;

    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private EditText amtEditText, remarkEditText, dateEditText;
    private TextView amtErrorText, typeErrorText, remarkErrorText, dateErrorText, dobErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Expense");


        requestQueue = Volley.newRequestQueue(this);

        amtEditText = findViewById(R.id.amt_edit_text);
        remarkEditText = findViewById(R.id.remark_edit_text);
        dateEditText = findViewById(R.id.date_edit_text);
        spinner = findViewById(R.id.payType);

        radioGroup = findViewById(R.id.radiogroup);


        amtErrorText = findViewById(R.id.amt_error_text);
        remarkErrorText = findViewById(R.id.remark_error_text);
        dateErrorText = findViewById(R.id.date_error_text);
        typeErrorText = findViewById(R.id.type_error_text);


        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.setOnClickListener(view -> {


            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            pickerdate = new DatePickerDialog(AddExpense.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> dateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            pickerdate.show();
        });

        List<String> values = Arrays.asList("-Select Payment Type-", "Cash", "Cheque", "Card Payment", "Online Payment");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        Button submitButton = findViewById(R.id.sign_up);
        submitButton.setOnClickListener(v -> {
            validateAmt();
            validateRemark();
            validateDate();
            validateType();
            if (isFormValid()) {

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Adding...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String amt = amtEditText.getText().toString().trim();
                String remark = remarkEditText.getText().toString().trim();
                String date = dateEditText.getText().toString().trim();
                String type = spinner.getSelectedItem().toString();

                selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                selectedRadioButton = findViewById(selectedRadioButtonId);

                String selectedOptionText = selectedRadioButton.getText().toString();

                Log.e("Details", "" + amt + "\n" + remark + "\n" + convertDate(date) + "\n" + type + "\n" + selectedOptionText);




                // Create a JSON object to hold the parameters
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("AccountCreditDebitID", "0");
                    jsonBody.put("RegistrationID", getRegistrationId());
                    jsonBody.put("CreditDebit", selectedOptionText);
                    jsonBody.put("Amount", amt);
                    jsonBody.put("Remarks", remark);
                    jsonBody.put("CashChequeNEFT", type);
                    jsonBody.put("UserID", "0");
                    jsonBody.put("LastBalance", "0");
                    jsonBody.put("FromDate", convertDate(date));
                    jsonBody.put("Todate", convertDate(date));
                    jsonBody.put("Result", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Payment_Expense_Insert_Update, jsonBody,
                        response -> {


                            try {
                                String body = response.getString("d");


                                if (body.substring(1, body.length() - 1).equals("Save")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(this, "Expense Save", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddExpense.this, ExpenseReport.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                                throw new RuntimeException(e);
                            }

                        }, error -> {
                    // Handle errors
                    Log.e("Volley Error", error.toString());
                    progressDialog.dismiss();
                    Toast.makeText(AddExpense.this, error.toString(), Toast.LENGTH_SHORT).show();
                });

                // Add the request to the RequestQueue.
                requestQueue.add(jsonObjectRequest);
            }
        });


    }

    private String getRegistrationId() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("registration_id", "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AddExpense.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void validateAmt() {
        String name = amtEditText.getText().toString().trim();
        if (name.isEmpty()) {
            amtErrorText.setText("Please enter amount");
            amtErrorText.setVisibility(View.VISIBLE);
        } else {
            amtErrorText.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void validateRemark() {
        String email = remarkEditText.getText().toString().trim();
        if (email.isEmpty()) {
            remarkErrorText.setText("Please enter remark");
            remarkErrorText.setVisibility(View.VISIBLE);
        } else {
            remarkErrorText.setVisibility(View.GONE);
        }
    }


    @SuppressLint("SetTextI18n")
    private void validateDate() {
        String email = dateEditText.getText().toString().trim();
        if (email.isEmpty()) {
            dateErrorText.setText("Please select date");
            dateErrorText.setVisibility(View.VISIBLE);
        } else {
            dateErrorText.setVisibility(View.GONE);
        }
    }

    private void validateType() {
        String type = spinner.getSelectedItem().toString().trim();
        if (type.equalsIgnoreCase("-Select Payment Type-")) {
            typeErrorText.setText("Please select Payment Type");
            typeErrorText.setVisibility(View.VISIBLE);

        } else {
            typeErrorText.setVisibility(View.GONE);
        }

    }

    private boolean isFormValid() {
        return amtErrorText.getVisibility() == View.GONE &&
                remarkErrorText.getVisibility() == View.GONE &&
                dateErrorText.getVisibility() == View.GONE && typeErrorText.getVisibility() == View.GONE;
    }

    public String convertDate(String originalDateStr) {
        try {
            // Parse the original date string
            @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date originalDate = originalFormat.parse(originalDateStr);

            // Format the date in the desired format
            @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
            return newFormat.format(originalDate);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle parse exception if necessary
            return null;
        }
    }
}