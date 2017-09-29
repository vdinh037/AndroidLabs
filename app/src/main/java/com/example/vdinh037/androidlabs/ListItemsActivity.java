package com.example.vdinh037.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String MY_TAG = "ListItemsActivity";
    private ImageButton b1;
    private Switch sw1;
    private CheckBox cb1;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        b1 = (ImageButton)findViewById(R.id.imageButton1);
        sw1 = (Switch)findViewById(R.id.switch1);
        cb1 = (CheckBox)findViewById(R.id.checkBox1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
            });

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton switchView, boolean isChecked) {
                if (isChecked) {
                    CharSequence text = "Switch is On";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ListItemsActivity.this,text,duration);
                    toast.show();
                } else {
                    CharSequence text = "Switch is Off";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(ListItemsActivity.this,text,duration);
                    toast.show();
                }
            }
        });

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkBoxView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setTitle(R.string.dialog_title);
                builder.setMessage(R.string.dialog_message);

                if (isChecked) {
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "Here is my response");
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            cb1.setChecked(false);
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap image = (Bitmap) extras.get("data");
                b1.setImageBitmap(image);
            }
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
