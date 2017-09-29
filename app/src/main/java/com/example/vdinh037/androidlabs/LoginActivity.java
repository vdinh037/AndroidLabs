package com.example.vdinh037.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String MY_TAG = "LoginActivity";
    private SharedPreferences prefs;
    protected static final String MY_PREFS = "MY PREFERENCES";
    private EditText emailField;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(MY_TAG, "In onCreate");

        b1 = (Button)findViewById(R.id.button1);
        emailField = (EditText)findViewById(R.id.login1);

        prefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        emailField.setText(prefs.getString("DefaultEmail","email@domain.com"));

        // Start the FirstActivity
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSavedPref();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showSavedPref(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DefaultEmail",emailField.getText().toString());
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(MY_TAG, "In onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MY_TAG, "In onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MY_TAG, "In onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(MY_TAG, "In onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(MY_TAG, "In onDestroy");
    }

}