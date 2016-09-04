package com.dwoj.app.astroweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;

/**
 * Created by Lenovo on 2016-06-29.
 */
public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag, container, false);
        moonInitialization(v);
        return v;
    }

    public static SecondFragment newInstance(String text) {
        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void moonInitialization(View v) {
        TextView moon_moonrise = (TextView) v.findViewById(R.id.moon_moonrise);
        TextView moon_moonset = (TextView) v.findViewById(R.id.moon_moonset);
        TextView moon_age = (TextView) v.findViewById(R.id.moon_age);
        TextView moon_illumination = (TextView) v.findViewById(R.id.moon_illumination);
        TextView moon_nextfullmoon = (TextView) v.findViewById(R.id.moon_nextfullmoon);
        TextView moon_nextnewmoon = (TextView) v.findViewById(R.id.moon_nextnewmoon);

        AstroCalculator astroCalculator = MainActivity.getAstroCalculator();
        moon_moonrise.setText(astroCalculator.getMoonInfo().getMoonrise().toString());
        moon_moonset.setText(astroCalculator.getMoonInfo().getMoonset().toString());
        moon_age.setText(String.valueOf(astroCalculator.getMoonInfo().getAge()));
        moon_illumination.setText(String.valueOf(astroCalculator.getMoonInfo().getIllumination()));
        moon_nextfullmoon.setText(astroCalculator.getMoonInfo().getNextFullMoon().toString());
        moon_nextnewmoon.setText(astroCalculator.getMoonInfo().getNextNewMoon().toString());
    }
}