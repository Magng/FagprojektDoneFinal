package com.example.home.fagprojektstart;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.nfc.Tag;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import android.support.v7.app.AppCompatActivity;



public class ledControl extends AppCompatActivity {
    Button btnOn, btnOff;
    TextView lumn;
    String address;
    BluetoothSocket btSocket = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket socket;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        btSocket = ((bluetooth) intent.getExtras().getSerializable(controlScreen.EXTRA_ADDRESS)).getSocket(); //receive the address of the bluetooth device
        //Log.e("SOCKET TEST", ""+socket);

        //view of the ledControl
        setContentView(R.layout.activity_led_control);

        //call the widgtes
        btnOn = (Button)findViewById(R.id.button11);
        btnOff = (Button)findViewById(R.id.button12);

        //commands to be sent to bluetooth
        btnOn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                turnOnLed();      //method to turn on
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                turnOffLed();   //method to turn off
            }
        });

    }
    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("LEDOFF}".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("LEDON}".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


}
