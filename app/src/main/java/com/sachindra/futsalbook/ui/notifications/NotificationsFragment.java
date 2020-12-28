package com.sachindra.futsalbook.ui.notifications;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sachindra.futsalbook.MyBooking;
import com.sachindra.futsalbook.MyItems;
import com.sachindra.futsalbook.adapter.ViewPagerAdapter;
import com.sachindra.futsalbook.ui.booking.BookingFragment;
import com.sachindra.futsalbook.ui.home.HomeFragment;
import com.sachindra.futsalbook.R;

public class NotificationsFragment extends Fragment {
    public static ViewPager viewPager;
    static TabLayout tabLayout;
    Sensor sensor;
    SensorManager sensorManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        viewPager = root.findViewById( R.id.Viewpager );
        tabLayout = root.findViewById( R.id.tbLayout );
        ViewPagerAdapter viewR = new ViewPagerAdapter( getChildFragmentManager() );
        viewR.addfragments( new MyBooking(), "Booking " );
        viewR.addfragments( new MyItems(), "Shopping" );
        viewPager.setAdapter(viewR);
        tabLayout.setupWithViewPager(viewPager);
        sensorManager = (SensorManager) getContext().getSystemService( getContext().SENSOR_SERVICE );
        sensor = sensorManager.getDefaultSensor( Sensor.TYPE_GYROSCOPE );
        home();
        return root;
    }
    void home() {
        SensorEventListener gyo = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.values[1] < 0) {
                    if (viewPager.getCurrentItem() != 0)
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);



                } else if (event.values[1] > 0) {
                    if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount())
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };
        if (sensor != null) {
            sensorManager.registerListener( gyo, sensor, sensorManager.SENSOR_DELAY_NORMAL );

        } else {
        }
    }
}