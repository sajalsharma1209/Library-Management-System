package com.shashank.platform.classroomappui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.classroomappui.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupScreen extends AppCompatActivity {


    private RequestQueue requestQueue;

    private EditText nameEditText, emailEditText, mobileEditText, passwordEditText, confirmPasswordEditText;
    private TextView nameErrorText, emailErrorText, mobileErrorText, passwordErrorText, confirmPasswordErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);

        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        mobileEditText = findViewById(R.id.mobile_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        nameErrorText = findViewById(R.id.name_error_text);
        emailErrorText = findViewById(R.id.email_error_text);
        mobileErrorText = findViewById(R.id.mobile_error_text);
        passwordErrorText = findViewById(R.id.password_error_text);
        confirmPasswordErrorText = findViewById(R.id.confirm_password_error_text);

//        nameEditText.setText("Sajal");
//        emailEditText.setText("sajal@test.com");
//        mobileEditText.setText("1234567890");
//        passwordEditText.setText("asdfgh");
//        confirmPasswordEditText.setText("asdfgh");


        Button submitButton = findViewById(R.id.sign_up);
        submitButton.setOnClickListener(v -> {
            validateName();
            validateEmail();
            validateMobile();
            validatePassword();
            validateConfirmPassword();
            if (isFormValid()) {

                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Create a JSON object to hold the parameters
                JSONObject jsonBody = new JSONObject();
                try {

                    jsonBody.put("RegistrationID", "0");
                    jsonBody.put("Name", name);
                    jsonBody.put("EmailID", email);
                    jsonBody.put("MobileNo", mobile);
                    jsonBody.put("Password", password);
                    jsonBody.put("Result", "");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Request a JSON response from the provided URL.
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Registration_URL, jsonBody,
                        response -> {
                            // Handle the response


                            try {
                                String body = response.getString("d");
                                Log.e("Volley Error", body);
                                Toast.makeText(SignupScreen.this, body, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                        }, error -> {
                    // Handle errors
                    Log.e("Volley Error", error.toString());
                    Toast.makeText(SignupScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                });

                // Add the request to the RequestQueue.
                requestQueue.add(jsonObjectRequest);


            }
        });

        TextView signInButton = findViewById(R.id.sign_in);
        signInButton.setOnClickListener(v -> {
            startActivity(new Intent(SignupScreen.this, LoginScreen.class));
            finish();
        });


    }

    private void validateName() {
        String name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            nameErrorText.setText("Please enter your name");
            nameErrorText.setVisibility(View.VISIBLE);
        } else if (!name.matches("[a-zA-Z ]+")) {
            nameErrorText.setText("Invalid name format");
            nameErrorText.setVisibility(View.VISIBLE);
        } else {
            nameErrorText.setVisibility(View.GONE);
        }
    }

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

    private void validatePassword() {
        String password = passwordEditText.getText().toString().trim();
        if (password.isEmpty()) {
            passwordErrorText.setText("Please enter a password");
            passwordErrorText.setVisibility(View.VISIBLE);
        } else if (password.length() < 6) {
            passwordErrorText.setText("Password must be at least 6 characters long");
            passwordErrorText.setVisibility(View.VISIBLE);
        } else {
            passwordErrorText.setVisibility(View.GONE);
        }
    }

    private void validateConfirmPassword() {
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            confirmPasswordErrorText.setText("Please confirm your password");
            confirmPasswordErrorText.setVisibility(View.VISIBLE);
        } else if (!confirmPassword.equals(passwordEditText.getText().toString().trim())) {
            confirmPasswordErrorText.setText("Passwords do not match");
            confirmPasswordErrorText.setVisibility(View.VISIBLE);
        } else {
            confirmPasswordErrorText.setVisibility(View.GONE);
        }
    }

    private boolean isFormValid() {
        return nameErrorText.getVisibility() == View.GONE &&
                emailErrorText.getVisibility() == View.GONE &&
                mobileErrorText.getVisibility() == View.GONE &&
                passwordErrorText.getVisibility() == View.GONE &&
                confirmPasswordErrorText.getVisibility() == View.GONE;
    }
}
