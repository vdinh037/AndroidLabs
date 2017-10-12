package com.example.vdinh037.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected static final String MY_TAG = "ChatWindow";
    private Button sendButton;
    private ListView listView;
    private EditText editText;
    private TextView msgText;
    private ArrayList<String> listItems;
    private ChatAdapter adapter;
    //Lab5
    private static final String ACTIVITY_NAME = "Query";
    private Cursor cursor;
    private SQLiteDatabase database;
    private ChatDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        sendButton = findViewById(R.id.sendButton);
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);

        listItems = new ArrayList<>();
        adapter = new ChatAdapter(this);
        listView.setAdapter(adapter);

        //Lab 5
        helper = new ChatDatabaseHelper(this);
        database = helper.getWritableDatabase();

         //lab5
        String[] allColumns = {ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.MESSAGE};
        cursor = database.query(ChatDatabaseHelper.TABLE_NAME, allColumns, null, null,
                null, null, null);
        cursor.moveToFirst();

        //while the cursor is not pointing to the position after the last row (
        while (!cursor.isAfterLast()) {
            String newMessage = cursor.getString(cursor.getColumnIndex( ChatDatabaseHelper.MESSAGE));

            //once we get the cursor's MESSAGE value, we add it to the msgs ArrayList.
            listItems.add(newMessage);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(
                    ChatDatabaseHelper.MESSAGE)));
            cursor.moveToNext();
        }

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i("Cursor column name", cursor.getColumnName(i));
            }
            Log.i(ACTIVITY_NAME, "Cursors column count =" + cursor.getColumnCount());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String empty_message = editText.getText().toString();
                if (TextUtils.isEmpty(empty_message)) {
                } else {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ChatDatabaseHelper.MESSAGE, editText.getText().toString());
                    database.insert(ChatDatabaseHelper.TABLE_NAME, "null", contentValues);
                    listItems.add(editText.getText().toString());
                    editText.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        private ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount(){ return listItems.size();}

        @Override
        public String getItem(int position){
            return listItems.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            msgText = result.findViewById(R.id.message_Text);
            msgText.setText(getItem(position));
            return result;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
        cursor.close();
        database.close();
    }
}
