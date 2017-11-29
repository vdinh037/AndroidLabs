package com.example.vdinh037.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String MY_TAG = "ChatWindow";
    private ImageView weatherImage;
    private TextView minTemp;
    private TextView maxTemp;
    private ProgressBar progressBar;
    private TextView currentTemp;
    private String urlString;
    private String urlIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImage = findViewById(R.id.weatherImage);
        currentTemp = findViewById(R.id.currentTemp);
        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        urlIcon = "http://openweathermap.org/img/w/";
        new ForecastQuery().execute();
        Log.i(MY_TAG, "In onCreate");
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String min_Temp;
        private String max_Temp;
        private String current_Temp;
        private String weather_icon;
        private Bitmap bitmap;

        @Override
        protected String doInBackground(String... params) {

            Log.i(MY_TAG, "In doInBackground");
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    if (parser.getName().equals("temperature")) {
                        current_Temp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min_Temp = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max_Temp = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    if (parser.getName().equals("weather")) {
                        weather_icon = parser.getAttributeValue(null, "icon");
                        String imageFile = weather_icon + ".png";
                        if (fileExistance(imageFile)) {
                            FileInputStream fis = null;
                            try {
                                fis = openFileInput(imageFile);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            bitmap = BitmapFactory.decodeStream(fis);
                            Log.i(MY_TAG, "Image already exists");
                        } else {
                            URL iconUrl = new URL(urlIcon + weather_icon + ".png");
                            bitmap = getImage(iconUrl);
                            FileOutputStream fos = openFileOutput(weather_icon + ".png", Context.MODE_PRIVATE);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                            fos.flush();
                            fos.close();
                            Log.i(MY_TAG, "Adding new image");
                        }
                        Log.i(MY_TAG, "file name=" + imageFile);
                        publishProgress(100);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.i(MY_TAG, "In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(MY_TAG, "In onPostExecute");
            progressBar.setVisibility(View.INVISIBLE);
            currentTemp.setText("Current: " + current_Temp + "C");
            minTemp.setText("Min: " + min_Temp + "C");
            maxTemp.setText("Max: " + max_Temp + "C");
            weatherImage.setImageBitmap(bitmap);
        }
    }

    protected static Bitmap getImage(URL url) {
        Log.i(MY_TAG, "In getImage");
        HttpURLConnection iconConn = null;
        try {
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            if (response == 200) {
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (iconConn != null) {
                iconConn.disconnect();
            }
        }
    }

    public boolean fileExistance(String fileName) {
        Log.i(MY_TAG, "In fileExistance");
        Log.i(MY_TAG, getBaseContext().getFileStreamPath(fileName).toString());
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }
}



