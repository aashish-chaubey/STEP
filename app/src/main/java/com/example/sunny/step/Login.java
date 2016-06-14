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

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static EditText emailText, passwordText;
    private static TextView signUp;
    private static Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.inout_email);
        passwordText = (EditText) findViewById(R.id.inout_password);

        signUp = (TextView) findViewById(R.id.signup);

        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //Todo : Implement your authentication logic here

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // On Complete either call onLoginSuccess or onLoginFailed
                onLoginSuccess();
                // onLoginFailed()
                progressDialog.dismiss();
            }
        }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                //TODO : Implement Successful Signup logic Here
                // By default we just finish the activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Disable going back to the previous activity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "login Failed!", Toast.LENGTH_SHORT).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // Check the case if email matches or not

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address!");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            passwordText.setError("Password length Should be between 4 to 15 alphanumeric characters!");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
