package com.swapnilswati.trackme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView tv;
    LocationManager lm;
    String mProvider;
    Location loc;
    double lat, lon, alt;
    float acc;
   Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        button= (Button) findViewById(R.id.button);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //to get location service
        Criteria c = new Criteria();
        mProvider = lm.getBestProvider(c, false); //false i.e either disble aur enable then also return the best provider
        //to return the best provider for lattitudes and longitudes..i.e network or gps

        if (mProvider.equals("gps")) {
            if (!lm.isProviderEnabled(mProvider)) //if gps is disabled then go to settings
            {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }

        }
        Toast.makeText(this, "Provider-" + mProvider, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v)
    {
        Intent i=new Intent(this,DestinationActivity.class);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        lm.requestLocationUpdates(mProvider, 1000, 1, this); //registration of source of event with location manager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = lm.getLastKnownLocation(mProvider); //all these are behaviours i.e methods of locationmanager object and are non static methods
        if (loc != null) {
            lat = loc.getLatitude();
            lon = loc.getLongitude();
            alt = loc.getAltitude();
            acc = loc.getAccuracy();

            String time = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()).format(new Date());
            //for time stamp and locale is used so as to get the local time according to the country and getDefault is used so that default time is set
            tv.setText("Altitude-" + alt + "\n" + "Latitude-" + lat + "\n" + "Longitude-" + lon + "\n" + "Accuracy-" + acc + "\n" + "Time-" + time);
        } else {
            Toast.makeText(this, "This app couldn't find location..Please keep track", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.removeUpdates(this);
    }

    public void mShowMapView(View v)
    {
        if(loc!=null)
        {
            Uri u= Uri.parse("geo:"+lat+","+lon);
            Intent i=new Intent(Intent.ACTION_VIEW,u);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "This app couldn't find location..Please keep track", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLocationChanged(Location location)
    {
        loc=location;
        if(loc!=null)
        {
            lat=loc.getLatitude();
            lon=loc.getLongitude();
            alt=loc.getAltitude();
            acc=loc.getAccuracy();

            String time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            //for time stamp and locale is used so as to get the local time according to the country and getDefault is used so that default time is set
            tv.setText("Altitude-"+alt+"\n"+"Latitude-"+lat+"\n"+"Longitude-"+lon+"\n"+"Accuracy-"+acc+"\n"+"Time-"+time);
        }
        else
        {
            Toast.makeText(this, "This app couldn't find loacation..Please keep track", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }
}
