package com.queens.delivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.util.Log;
import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;



import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

import com.queens.delivery.R;
import com.queens.delivery.helpers.RequestQueueHelper;
import com.queens.delivery.shared.SessionHandler;
import com.queens.delivery.helpers.InputValidation;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    //Email
    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText textInputEditTextEmail;
    @BindView(R.id.textInputLayoutEmail)
    TextInputLayout textInputLayoutEmail;

    //Password
    @BindView(R.id.textInputEditTextPassword)
    TextInputEditText textInputEditTextPassword;
    @BindView(R.id.textInputLayoutPassword)
    TextInputLayout textInputLayoutPassword;

    //Button
    @BindView(R.id.appCompatButtonLogin)
    AppCompatButton appCompatButtonLogin;

    private final AppCompatActivity activity = LoginActivity.this;
    SharedPreferences shrd;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";

    //private String username;
    private String email;
    private String password;
    private ProgressDialog pDialog;
    private String login_url = "https://delivery.queensclassycollections.com/api/member/login.php";
    private SessionHandler session;
    private InputValidation inputValidation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        inputValidation = new InputValidation(getApplicationContext());
        if(session.isLoggedIn()){
            loadDashboard();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, LoginActivity.this);
    }

    @OnClick(R.id.appCompatButtonLogin)
    public void loginAction(){
        email=textInputEditTextEmail.getText().toString().trim();
        password=textInputEditTextPassword.getText().toString().trim();
        if(validateInputs()){
            login();
        }
    }

    private boolean validateInputs(){
        if(!inputValidation.isInputEditTextFilled(textInputEditTextEmail,textInputLayoutEmail,getString(R.string.error_message_email))){
            return false;
        }
        if(!inputValidation.isInputEditTextEmail(textInputEditTextEmail,textInputLayoutEmail,getString(R.string.error_message_email))){
            return false;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))){
            return false;
        }
        return true;
    }

    private void loadDashboard(){
        Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayLoader(){
        pDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        pDialog.setMessage("Authenticating ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void login(){
        Log.d(TAG, "Login");
        displayLoader();
        JSONObject request = new JSONObject();
        try{
            request.put(KEY_EMAIL, email);
            request.put(KEY_PASSWORD, password);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                try {
                    if (response.getInt(KEY_STATUS) == 0) {
                        session.loginUser(email, response.getString(KEY_FULL_NAME));
                        loadDashboard();
                    } else {
                        Snackbar.make(nestedScrollView, response.getString(KEY_MESSAGE), Snackbar.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            pDialog.dismiss();
            Snackbar.make(nestedScrollView, error.getMessage(), Snackbar.LENGTH_LONG).show();
        });
        RequestQueueHelper.getInstance(this).addToRequestQueue(jsArrayRequest);
    }


}
