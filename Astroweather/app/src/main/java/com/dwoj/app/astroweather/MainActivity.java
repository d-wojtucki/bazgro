package com.dwoj.app.astroweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(3);

        //Clock - 1 second between refreshes
        Thread clockThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setTime();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        clockThread.start();
        //Data - 1 minute between refreshes
        Thread dataThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(60000 * DataHolder.frequencyOfRefreshing);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setSunData();
                                setMoonData();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        dataThread.start();
    }

    public void setTime() {
        TextView mClockView = (TextView) findViewById(R.id.mainClock);

        Calendar cal = Calendar.getInstance();
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);

        if (DateFormat.is24HourFormat(this)) {
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            mClockView.setText((hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds));
        }
        else {
            int hours = cal.get(Calendar.HOUR);
            mClockView.setText(hours + ":" + (minutes < 10 ? "0" + minutes : minutes) + " " + new DateFormatSymbols().getAmPmStrings()[cal.get(Calendar.AM_PM)]);
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return FirstFragment.newInstance("FirstFragment, Instance 1");
                case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");
                case 2: return ThirdFragment.newInstance("Weather fragment");
                default: return ThirdFragment.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void openSettings(View view) {
        startActivityForResult(new Intent(this, SettingsActivity.class), 0xe110);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0xe110) {
            setSunData();
            setMoonData();
        }
    }

    public static AstroCalculator getAstroCalculator(){

        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);

        AstroDateTime astroDateTime = new AstroDateTime(calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH),
                calendar.get(calendar.HOUR_OF_DAY), calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH), 0, true);

        AstroCalculator.Location astroLocation = new AstroCalculator.Location(DataHolder.latitude, DataHolder.longitude);

        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, astroLocation);
        return astroCalculator;
    }

    public void setSunData() {
        AstroCalculator.SunInfo astroCalculator = getAstroCalculator().getSunInfo();

        TextView sun_sunset = (TextView) findViewById(R.id.sun_sunset);
        TextView sun_sunrise = (TextView) findViewById(R.id.sun_sunrise);
        TextView sun_azimuthrise = (TextView) findViewById(R.id.sun_azimuthrise);
        TextView sun_azimuthset = (TextView) findViewById(R.id.sun_azimuthset);
        TextView sun_twilighmorning = (TextView) findViewById(R.id.sun_twilighmorning);
        TextView sun_twilighevening = (TextView) findViewById(R.id.sun_twilighevening);

        sun_sunrise.setText(astroCalculator.getSunrise().toString());
        sun_sunset.setText(astroCalculator.getSunset().toString());
        sun_azimuthrise.setText(String.valueOf(astroCalculator.getAzimuthRise()));
        sun_azimuthset.setText(String.valueOf(astroCalculator.getAzimuthSet()));
        sun_twilighmorning.setText(astroCalculator.getTwilightMorning().toString());
        sun_twilighevening.setText(astroCalculator.getTwilightEvening().toString());
    }

    public void setMoonData() {
        AstroCalculator.MoonInfo astroCalculator = getAstroCalculator().getMoonInfo();

        TextView moon_moonrise = (TextView) findViewById(R.id.moon_moonrise);
        TextView moon_moonset = (TextView) findViewById(R.id.moon_moonset);
        TextView moon_age = (TextView) findViewById(R.id.moon_age);
        TextView moon_illumination = (TextView) findViewById(R.id.moon_illumination);
        TextView moon_nextfullmoon = (TextView) findViewById(R.id.moon_nextfullmoon);
        TextView moon_nextnewmoon = (TextView) findViewById(R.id.moon_nextnewmoon);

        moon_moonrise.setText(astroCalculator.getMoonrise().toString());
        moon_moonset.setText(astroCalculator.getMoonset().toString());
        moon_age.setText(String.valueOf(astroCalculator.getAge()));
        moon_illumination.setText(String.valueOf(astroCalculator.getIllumination()));
        moon_nextfullmoon.setText(astroCalculator.getNextFullMoon().toString());
        moon_nextnewmoon.setText(astroCalculator.getNextNewMoon().toString());
    }
}