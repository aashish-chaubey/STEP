package com.example.sunny.step;

import android.app.ProgressDialog;
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

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText nameText, emailText, passwordtText;
    private Button signUp;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailText = (EditText) findViewById(R.id.input_email);
        nameText = (EditText) findViewById(R.id.input_name);
        passwordtText = (EditText) findViewById(R.id.inout_password);

        signUp = (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signUp() {
        Log.d(TAG, "SignUp");

        if (!validate()) {
            onSignUpFailed();
            return;
        }

        signUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // Implement your own Sign up login here

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // On complete call either onSignUpSuccess or onSignUpFailed
                // depending on the result
                onSignUpSuccess();
                // onSignUpFailed()
                progressDialog.dismiss();
            }
        }, 3000);
    }

    public void onSignUpSuccess() {
        signUp.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getApplicationContext(), "Sign Up successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_SHORT).show();
        signUp.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordtText.getText().toString();

        if (name.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address!");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            emailText.setError("Password length Should be between 4 to 15 alphanumeric characters!");
            valid = false;
        } else {
            emailText.setError(null);
        }
        return valid;
    }
}