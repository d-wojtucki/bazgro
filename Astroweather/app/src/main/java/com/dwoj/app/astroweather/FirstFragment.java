package com.dwoj.app.astroweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;


public class FirstFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);
        sunInitialization(v);
        return v;
    }

    public static FirstFragment newInstance(String text) {

        FirstFragment f = new FirstFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public void sunInitialization(View v) {
        TextView sun_sunset = (TextView) v.findViewById(R.id.sun_sunset);
        TextView sun_sunrise = (TextView) v.findViewById(R.id.sun_sunrise);
        TextView sun_azimuthrise = (TextView) v.findViewById(R.id.sun_azimuthrise);
        TextView sun_azimuthset = (TextView) v.findViewById(R.id.sun_azimuthset);
        TextView sun_twilighmorning = (TextView) v.findViewById(R.id.sun_twilighmorning);
        TextView sun_twilighevening = (TextView) v.findViewById(R.id.sun_twilighevening);

        AstroCalculator astroCalculator = MainActivity.getAstroCalculator();
        sun_sunset.setText(astroCalculator.getSunInfo().getSunset().toString());
        sun_sunrise.setText(astroCalculator.getSunInfo().getSunrise().toString());
        sun_azimuthrise.setText(String.valueOf(astroCalculator.getSunInfo().getAzimuthRise()));
        sun_azimuthset.setText(String.valueOf(astroCalculator.getSunInfo().getAzimuthSet()));
        sun_twilighmorning.setText(astroCalculator.getSunInfo().getTwilightMorning().toString());
        sun_twilighevening.setText(astroCalculator.getSunInfo().getTwilightEvening().toString());
    }


}