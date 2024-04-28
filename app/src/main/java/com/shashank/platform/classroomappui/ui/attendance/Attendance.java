package com.shashank.platform.classroomappui.ui.attendance;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Attendance extends AppCompatActivity {


    String UserID, name;
    ArrayList<AttendanceModel> datalist;
    AttendanceAdapter adapter;
    private EditText dateEditText;
    private Button button;
    private RecyclerView recview;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private DatePickerDialog pickerdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Attendance");

        dateEditText = findViewById(R.id.date_edit_text);
        button = findViewById(R.id.submit);
        recview = findViewById(R.id.recview);

        requestQueue = Volley.newRequestQueue(this);

        UserID = getIntent().getStringExtra("UserID");
        name = getIntent().getStringExtra("Name");


        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.setOnClickListener(view -> {


            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            pickerdate = new DatePickerDialog(Attendance.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> dateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            pickerdate.show();
        });


        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        getAttendance(UserID, name);

        button.setOnClickListener(click -> {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Marking Attendance...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            JSONObject jsonBody1 = new JSONObject();
            try {


                jsonBody1.put("UserID", UserID);
                jsonBody1.put("ADate", convertDate(dateEditText.getText().toString()));
                jsonBody1.put("Result", "");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST,
                    Constants.Attendance_Insert_Update, jsonBody1, response -> {
                // Handle the response
                String body;
                try {
                    body = response.getString("d");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                if (body.substring(1, body.length() - 1).equals("Attendance already marked for selected Member.")) {
                    Toast.makeText(this, "Attendance already marked for this date", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Marked Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Attendance.this, ViewStudents.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                progressDialog.dismiss();
            }, error -> {
                // Handle errors
                Log.e("Volley Error", error.toString());
                progressDialog.dismiss();
                Toast.makeText(Attendance.this, error.toString(), Toast.LENGTH_SHORT).show();
            });

            // Add the request to the RequestQueue.
            requestQueue.add(jsonObjectRequest1);


        });
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

    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(Attendance.this, ViewStudents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }


    private void getAttendance(String UserID, String name) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Attendance...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Create a JSON object to hold the parameters
        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("UserID", UserID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.get_Attendance, jsonBody,
                response -> {
                    try {
                        progressDialog.dismiss();

                        // Get the JSON array from the response object
                        JSONArray jsonArray = new JSONArray(response.getString("d"));

                        if (jsonArray.length() < 1) {
                            // If empty, show the ImageView

                            recview.setVisibility(View.GONE);
                        } else {
                            // If not empty, hide the ImageView

                            recview.setVisibility(View.VISIBLE);
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject innerJsonObject = jsonArray.getJSONObject(i);
                            AttendanceModel obj = new AttendanceModel(name, innerJsonObject.getString("AttendanceDate"));
                            datalist.add(obj);
                            adapter = new AttendanceAdapter(datalist);
                            recview.setAdapter(adapter);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        Log.e("Error Json", Objects.requireNonNull(e.getMessage()));
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Errorhi " + error.getMessage(), Toast.LENGTH_SHORT).show();    // Handle error
                });


        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);

        adapter = new AttendanceAdapter(datalist);
        recview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}