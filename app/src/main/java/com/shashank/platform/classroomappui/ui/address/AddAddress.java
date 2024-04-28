package com.shashank.platform.classroomappui.ui.address;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class AddAddress extends AppCompatActivity {

    EditText addressEditText;
    EditText cityEditText;
    List<String> values, values1;
    EditText pincodeEditText;
    Spinner state, country;
    Button setbutton;
    TextView addressErrorText, cityErrorText, stateErrorText, pincodeErrorText, countryErrorText;
    private
    String UserID;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Address");

        addressEditText = findViewById(R.id.address);
        cityEditText = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pincodeEditText = findViewById(R.id.pincode);
        country = findViewById(R.id.country);
        setbutton = findViewById(R.id.button);

        values = Arrays.asList("-Select State-",
                "Andaman and Nicobar",
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chandigarh",
                "Chhattishgarh",
                "Dadra and N. Haveli",
                "Daman and Diu",
                "Delhi",
                "Goa",
                "Gujrat",
                "Haryana",
                "Himachal Pradesh",
                "Jammu and Kashmir",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Lakshadweep",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghayala",
                "Mizoram",
                "Nagaland",
                "Orrisa",
                "Pondicherry",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttar Pradesh",
                "Uttarakhand",
                "West Bengal");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        state.setAdapter(adapter);


        values1 = Arrays.asList("-Select Country-", "India");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values1);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        country.setAdapter(adapter1);
//        country.setSelection(1);


        addressErrorText = findViewById(R.id.address_error_text);
        cityErrorText = findViewById(R.id.city_error_text);
        stateErrorText = findViewById(R.id.state_error_text);
        pincodeErrorText = findViewById(R.id.pincode_error_text);
        countryErrorText = findViewById(R.id.country_error_text);

        requestQueue = Volley.newRequestQueue(this);
        UserID = getIntent().getStringExtra("UserID");

        JSONObject jsonBody1 = new JSONObject();
        try {


            jsonBody1.put("UserID", UserID);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST,
                Constants.get_Address, jsonBody1, response -> {
            // Handle the response


            try {
                JSONArray jsonArray = new JSONArray(response.getString("d"));


                JSONObject innerJsonObject = jsonArray.getJSONObject(0);

                addressEditText.setText(innerJsonObject.getString("Address"));
                cityEditText.setText(innerJsonObject.getString("City"));
                state.setSelection(values.indexOf(innerJsonObject.getString("State")));
                country.setSelection(values1.indexOf(innerJsonObject.getString("Country")));
                pincodeEditText.setText(innerJsonObject.getString("PostalCode"));


            } catch (JSONException e) {
                progressDialog.dismiss();
                throw new RuntimeException(e);
            }

        }, error -> {
            // Handle errors
            Log.e("Volley Error", error.toString());
            progressDialog.dismiss();
            Toast.makeText(AddAddress.this, error.toString(), Toast.LENGTH_SHORT).show();
        });

        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest1);


        setbutton.setOnClickListener(view -> {

            validateAddress();
            validateCity();
            validateState();
            validatepPincode();
            validateCountry();
            if (isFormValid()) {

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Adding...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String taddress = addressEditText.getText().toString().trim();
                String tcity = cityEditText.getText().toString().trim();
                String tstate = state.getSelectedItem().toString().trim();
                String tcountry = country.getSelectedItem().toString().trim();
                String tpincode = pincodeEditText.getText().toString().trim();
                // Create a JSON object to hold the parameters
                JSONObject jsonBody = new JSONObject();
                try {


                    jsonBody.put("UserID", UserID);
                    jsonBody.put("Address", taddress);
                    jsonBody.put("City", tcity);
                    jsonBody.put("State", tstate);
                    jsonBody.put("PostalCode", tpincode);
                    jsonBody.put("Country", tcountry);
                    jsonBody.put("Result", "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        Constants.Address_Insert_Update, jsonBody, response -> {
                    // Handle the response


                    try {
                        String body = response.getString("d");


                        if (body.substring(1, body.length() - 1).equals("Address has been successfully updated.")) {

                            progressDialog.dismiss();
                            Toast.makeText(this, "Address Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddAddress.this, ViewStudents.class);
                            //intent.putExtra("UserID", body.substring(1, body.length() - 1));
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
                    Toast.makeText(AddAddress.this, error.toString(), Toast.LENGTH_SHORT).show();
                });

                // Add the request to the RequestQueue.
                requestQueue.add(jsonObjectRequest);
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void validateAddress() {
        String address = addressEditText.getText().toString().trim();
        if (address.isEmpty()) {
            addressErrorText.setText("Please enter student address");
            addressErrorText.setVisibility(View.VISIBLE);
        } else if (!address.matches("[a-zA-Z ]+")) {
            addressErrorText.setText("Invalid address format");
            addressErrorText.setVisibility(View.VISIBLE);
        } else {
            addressErrorText.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void validateCity() {
        String city = cityEditText.getText().toString().trim();
        if (city.isEmpty()) {
            cityErrorText.setText("Please enter city");
            cityErrorText.setVisibility(View.VISIBLE);
        } else if (!city.matches("[a-zA-Z ]+")) {
            cityErrorText.setText("Invalid email format");
            cityErrorText.setVisibility(View.VISIBLE);
        } else {
            cityErrorText.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void validateState() {
        String sstate = state.getSelectedItem().toString().trim();
        if (sstate.equalsIgnoreCase("-Select State-")) {
            stateErrorText.setText("Please select state");
            stateErrorText.setVisibility(View.VISIBLE);
        } else {
            stateErrorText.setVisibility(View.GONE);
        }
    }


    @SuppressLint("SetTextI18n")
    private void validatepPincode() {
        String pincode = pincodeEditText.getText().toString().trim();
        if (pincode.isEmpty()) {
            pincodeErrorText.setText("Please enter pinocde");
            pincodeErrorText.setVisibility(View.VISIBLE);
        } else {
            pincodeErrorText.setVisibility(View.GONE);
        }
    }

    private void validateCountry() {
        String scountry = country.getSelectedItem().toString().trim();
        if (scountry.equalsIgnoreCase("-Select country-")) {
            countryErrorText.setText("Please select country");
            countryErrorText.setVisibility(View.VISIBLE);
        } else {
            countryErrorText.setVisibility(View.GONE);
        }
    }

    private boolean isFormValid() {
        return addressErrorText.getVisibility() == View.GONE &&
                cityErrorText.getVisibility() == View.GONE
                && stateErrorText.getVisibility() == View.GONE &&
                pincodeErrorText.getVisibility() == View.GONE
                && countryErrorText.getVisibility() == View.GONE;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AddAddress.this, ViewStudents.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }


}