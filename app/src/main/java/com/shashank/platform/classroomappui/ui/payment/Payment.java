package com.shashank.platform.classroomappui.ui.payment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import com.shashank.platform.classroomappui.ui.students.ViewStudents;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Payment extends AppCompatActivity {

    DatePickerDialog pickerdate;
    int selectedRadioButtonId, selectedRadioButtonId2;
    Button button;
    Spinner spinner;
    String UserID;
    int planID = 0;
    //    private List<Payment.Plan> planList;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private EditText amtEditText, toDateEditText, fromDateEditText, remark;
    private TextView nameErrorText, remarkErrorText, endErrorText, startDateErrorText, typeErrorText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Payment");

        requestQueue = Volley.newRequestQueue(this);

        amtEditText = findViewById(R.id.amt_edit_text);
        fromDateEditText = findViewById(R.id.fromDate_edit_text);
        toDateEditText = findViewById(R.id.endDate_edit_text);
        spinner = findViewById(R.id.payType);
        remark = findViewById(R.id.remark_edit_text);
        button = findViewById(R.id.submit);


        nameErrorText = findViewById(R.id.name_error_text);
        remarkErrorText = findViewById(R.id.remark_error_text);
        startDateErrorText = findViewById(R.id.start_error_text);
        endErrorText = findViewById(R.id.endDate_error_text);
        typeErrorText = findViewById(R.id.type_error_text);

        UserID = getIntent().getStringExtra("UserID");
        fromDateEditText.setInputType(InputType.TYPE_NULL);
        fromDateEditText.setOnClickListener(view -> {


            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            pickerdate = new DatePickerDialog(Payment.this, (view1, year1, monthOfYear, dayOfMonth) -> fromDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            pickerdate.show();
        });

        toDateEditText.setInputType(InputType.TYPE_NULL);
        toDateEditText.setOnClickListener(view -> {


            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            pickerdate = new DatePickerDialog(Payment.this, (view1, year1, monthOfYear, dayOfMonth) -> toDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            pickerdate.show();
        });

        List<String> values = Arrays.asList("-Select Payment Type-", "Cash", "Cheque", "Card Payment", "Online Payment");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        button.setOnClickListener(v -> {
            validateFromDate();
            validateAmt();
            validateRemarks();
            validateType();
            validateToDate();
            if (isFormValid()) {

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String amt = amtEditText.getText().toString().trim();
                String fromdate = fromDateEditText.getText().toString().trim();
                String todate = toDateEditText.getText().toString();
                String remarke = remark.getText().toString().trim();
                String type = spinner.getSelectedItem().toString();


                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                String registration_id = sharedPreferences.getString("registration_id", "");

                JSONObject jsonBody = new JSONObject();
                try {


                    jsonBody.put("AccountCreditDebitID", "0");
                    jsonBody.put("RegistrationID", registration_id);
                    jsonBody.put("CreditDebit", "Credit");
                    jsonBody.put("Amount", amt);
                    jsonBody.put("Remarks", remarke);
                    jsonBody.put("CashChequeNEFT", type);
                    jsonBody.put("UserID", UserID);
                    jsonBody.put("LastBalance", "0");
                    jsonBody.put("FromDate", convertDate(fromdate));
                    jsonBody.put("Todate", convertDate(todate));
                    jsonBody.put("Result", "");


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Payment_Expense_Insert_Update, jsonBody, response -> {


                    progressDialog.dismiss();

                    Toast.makeText(this, "Payment Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Payment.this, ViewStudents.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }, error -> {
                    // Handle errors
                    Log.e("Volley Error", error.toString());
                    progressDialog.dismiss();
                    Toast.makeText(Payment.this, error.toString(), Toast.LENGTH_SHORT).show();
                });


                requestQueue.add(jsonObjectRequest);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(Payment.this, ViewStudents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }


    private void validateAmt() {
        String name = amtEditText.getText().toString().trim();
        if (name.isEmpty()) {
            nameErrorText.setText("Please enter amount");
            nameErrorText.setVisibility(View.VISIBLE);
        } else {
            nameErrorText.setVisibility(View.GONE);
        }
    }

    private void validateRemarks() {
        String name = remark.getText().toString().trim();
        if (name.isEmpty()) {
            remarkErrorText.setText("Please enter remark");
            remarkErrorText.setVisibility(View.VISIBLE);
        } else {
            remarkErrorText.setVisibility(View.GONE);
        }
    }

    private void validateFromDate() {
        String type = fromDateEditText.getText().toString().trim();
        if (type.equalsIgnoreCase("")) {
            startDateErrorText.setText("Please select from date");
            startDateErrorText.setVisibility(View.VISIBLE);

        } else {
            startDateErrorText.setVisibility(View.GONE);
        }
    }

    private void validateToDate() {
        String type = toDateEditText.getText().toString().trim();
        if (type.equalsIgnoreCase("")) {
            endErrorText.setText("Please select to date");
            endErrorText.setVisibility(View.VISIBLE);

        } else {
            endErrorText.setVisibility(View.GONE);
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
        return nameErrorText.getVisibility() == View.GONE
                && remarkErrorText.getVisibility() == View.GONE
                && startDateErrorText.getVisibility() == View.GONE
                && endErrorText.getVisibility() == View.GONE
                && typeErrorText.getVisibility() == View.GONE;
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
