package com.shashank.platform.classroomappui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity {


    private RequestQueue requestQueue;

    private EditText emailEditText, passwordEditText;
    private TextView emailErrorText, passwordErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);


        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);


        emailErrorText = findViewById(R.id.email_error_text);
        passwordErrorText = findViewById(R.id.password_error_text);


        emailEditText.setText("L592748");
        passwordEditText.setText("qwerty");


        Button submitButton = findViewById(R.id.sign_in);
        submitButton.setOnClickListener(v -> {
            //validateName();
            validateEmail();
            // validateMobile();
            validatePassword();
            // validateConfirmPassword();
            if (isFormValid()) {

                // String mobile = mobileEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Create a JSON object to hold the parameters
                JSONObject jsonBody = new JSONObject();
                try {


//
                    jsonBody.put("UserName", email);
                    jsonBody.put("Password", password);
                    jsonBody.put("Result", "");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Login_URL, jsonBody,
                        response -> {
                            // Handle the response


                            try {
                                // Parse the JSON string to a JSONObject


                                // Check if the key "d" is null or empty
                                String dValue = response.getString("d");
                                if (dValue != null && !dValue.isEmpty()) {
                                    // "d" is not null or empty, handle it accordingly
                                    JSONArray jsonArray = new JSONArray(dValue);
                                    if (jsonArray.length() > 0) {
                                        // "d" is not an empty array
                                        JSONObject innerJsonObject = jsonArray.getJSONObject(0);

                                        // Extracting values
                                        int registrationID = innerJsonObject.getInt("RegistrationID");
                                        Toast.makeText(this, "" + registrationID, Toast.LENGTH_SHORT).show();


                                    } else {
                                        // "d" is an empty array
                                        Toast.makeText(this, "Invalid User Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // "d" is null or empty
                                    //Toast.makeText(this, "Invalid User Credentials", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // JSON parsing error
                                Log.e("Error", "JSON parsing error: " + e.getMessage());
                            }

                        }, error -> {
                    // Handle errors
                    Log.e("Volley Error", error.toString());
                    Toast.makeText(LoginScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                });

                // Add the request to the RequestQueue.
                requestQueue.add(jsonObjectRequest);


            }
        });

        TextView signUpButton = findViewById(R.id.sign_up);
        signUpButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, SignupScreen.class));
            finish();
        });


    }


    private void validateEmail() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            emailErrorText.setText("Please enter your email");
            emailErrorText.setVisibility(View.VISIBLE);
        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            emailErrorText.setText("Invalid email format");
//            emailErrorText.setVisibility(View.VISIBLE);
//        }
        else {
            emailErrorText.setVisibility(View.GONE);
        }
    }


    private void validatePassword() {
        String password = passwordEditText.getText().toString().trim();
        if (password.isEmpty()) {
            passwordErrorText.setText("Please enter a password");
            passwordErrorText.setVisibility(View.VISIBLE);
        }
//        else if (password.length() < 6) {
//            passwordErrorText.setText("Password must be at least 6 characters long");
//            passwordErrorText.setVisibility(View.VISIBLE);
//        }
        else {
            passwordErrorText.setVisibility(View.GONE);
        }
    }


    private boolean isFormValid() {
        return
                emailErrorText.getVisibility() == View.GONE &&
                        passwordErrorText.getVisibility() == View.GONE;
    }
}
