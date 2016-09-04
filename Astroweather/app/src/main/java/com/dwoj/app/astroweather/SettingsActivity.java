package com.dwoj.app.astroweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getData();
    }

    public void save(View v) {
        EditText longitude = (EditText) findViewById(R.id.input_lon);
        EditText latitude = (EditText) findViewById(R.id.input_lat);
        EditText frequency = (EditText) findViewById(R.id.input_freq);
        try {
            Double newLat = Double.parseDouble(latitude.getText().toString());
            Double newLon = Double.parseDouble(longitude.getText().toString());
            int newFreq = Integer.parseInt(frequency.getText().toString());
            if (Math.abs(newLat) > 90 || Math.abs(newLon) > 180) {
                Toast.makeText(this, "Wrong values!", Toast.LENGTH_LONG).show();
            } else {
                DataHolder.longitude = newLon;
                DataHolder.latitude = newLat;
                DataHolder.frequencyOfRefreshing = newFreq;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Wrong format!",
                    Toast.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "Unknown error!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void getData() {
        TextView data_lat = (TextView) findViewById(R.id.dataLat);
        TextView data_lon = (TextView) findViewById(R.id.dataLon);
        TextView data_freq = (TextView) findViewById(R.id.dataFreq);
        String lat = "Current latitude: " + DataHolder.getLatitude() + " degree(s).";
        String lon = "Current longitude: " + DataHolder.getLongitude() + " degree(s).";
        String freq = "Current frequency of refreshing astronomic data: " + DataHolder.getFrequency() + " minute(s).";
        data_lat.setText(lat);
        data_lon.setText(lon);
        data_freq.setText(freq);
    }
}
