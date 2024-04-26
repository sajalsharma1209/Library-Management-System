package com.shashank.platform.classroomappui.ui.seat;

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
import com.shashank.platform.classroomappui.ui.students.ViewStudents;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class AddSeat extends AppCompatActivity {

    private RequestQueue requestQueue;
    private EditText seatno;
    private Spinner spinner;
    private Button button;
    private ProgressDialog progressDialog;
    private TextView nameErrorText, typeErrorText;
    private String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Allot Seat");

        seatno = findViewById(R.id.seat_edit_text);
        nameErrorText = findViewById(R.id.name_error_text);
        typeErrorText = findViewById(R.id.type_error_text);
        spinner = findViewById(R.id.shift);
        button = findViewById(R.id.allot);


        List<String> values = Arrays.asList("-Select Shift-", "Morning", "AfterNoon", "Evening", "Night", "FullDay");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        UserID = getIntent().getStringExtra("UserID");

        requestQueue = Volley.newRequestQueue(this);
        button.setOnClickListener(v -> {

            validateName();
            validateType();
            if (isFormValid()) {

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String seat = seatno.getText().toString().trim();
                String shift = spinner.getSelectedItem().toString();


                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                String registration_id = sharedPreferences.getString("registration_id", "");

                JSONObject jsonBody = new JSONObject();
                try {

                    jsonBody.put("SeatID", "0");
                    jsonBody.put("RegistrationID", registration_id);
                    jsonBody.put("PlanTypeID", values.indexOf(shift));
                    jsonBody.put("SeatNo", seat);
                    jsonBody.put("UserID", UserID);
                    jsonBody.put("Result", "");


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Seat_Insert_Update, jsonBody,
                        response -> {


                            progressDialog.dismiss();

                            Toast.makeText(this, "Seat Allotted Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddSeat.this, ViewStudents.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                        }, error -> {
                    // Handle errors
                    Log.e("Volley Error", error.toString());
                    progressDialog.dismiss();
                    Toast.makeText(AddSeat.this, error.toString(), Toast.LENGTH_SHORT).show();
                });


                requestQueue.add(jsonObjectRequest);
            }
        });


    }

    private void validateName() {
        String name = seatno.getText().toString().trim();
        if (name.isEmpty()) {
            nameErrorText.setText("Enter Seat No ");
            nameErrorText.setVisibility(View.VISIBLE);
        } else {
            nameErrorText.setVisibility(View.GONE);
        }
    }

    private void validateType() {
        String type = spinner.getSelectedItem().toString().trim();
        if (type.equalsIgnoreCase("-Select Shift-")) {
            typeErrorText.setText("Please select plan type");
            typeErrorText.setVisibility(View.VISIBLE);

        } else {
            typeErrorText.setVisibility(View.GONE);
        }
    }

    private boolean isFormValid() {
        return nameErrorText.getVisibility() == View.GONE;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AddSeat.this, ViewStudents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }


}