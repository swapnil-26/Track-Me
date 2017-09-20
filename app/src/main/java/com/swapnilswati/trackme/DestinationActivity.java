package com.swapnilswati.trackme;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DestinationActivity extends AppCompatActivity {

    Button latlongbutton;
    TextView addtv;
    TextView latlongtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        addtv= (TextView) findViewById(R.id.textView3);
        latlongtv= (TextView) findViewById(R.id.textView4);
        latlongbutton= (Button) findViewById(R.id.button2);
        latlongbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText= (EditText) findViewById(R.id.editText);
                String address=editText.getText().toString();

                GeocodingLocation locationAddress=new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,getApplicationContext(),new GeocoderHandler());

            }
        });
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            latlongtv.setText(locationAddress);
        }
    }




}
