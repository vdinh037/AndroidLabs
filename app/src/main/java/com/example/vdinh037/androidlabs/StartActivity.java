package com.example.vdinh037.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String MY_TAG = "StartActivity";
    private Button button;
    private Button chatButton;
    private static final int SECOND_ACTIVITY_RESULT_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(MY_TAG,"In onCreate");

        chatButton = findViewById(R.id.chatButton);
        button = findViewById(R.id.button);

        //click to start listItemsActivity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
                Log.i(MY_TAG,"New chat window started");
            }
        });

        //click start chat button to display msg
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
                Log.i(MY_TAG,"New chat window started");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String str_intent = data.getStringExtra("Response");
                CharSequence text = "ListItemsActivity passed: " + str_intent;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(StartActivity.this, text, duration);
                toast.show();
                Log.i(MY_TAG, "Returned to StartActivity.onActivityResult");
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(MY_TAG, "In onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(MY_TAG, "In onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MY_TAG, "In onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(MY_TAG, "In onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(MY_TAG, "In onDestroy");
    }


}
