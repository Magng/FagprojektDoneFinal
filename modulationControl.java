package com.example.home.fagprojektstart;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class modulationControl extends AppCompatActivity {
    Button controlPam2, controlPam4, controlPam8;
    BluetoothSocket btSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulation_control);

        Intent intent = getIntent();
        btSocket = ((bluetooth) intent.getExtras().getSerializable(controlScreen.EXTRA_ADDRESS)).getSocket(); //receive the address of the bluetooth device

        controlPam2 = (Button) findViewById(R.id.controlPam2);
        controlPam4 = (Button) findViewById(R.id.controlPam4);
        controlPam8 = (Button) findViewById(R.id.controlPam8);

        controlPam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("{MOD:0}".toString().getBytes());
                        msg("2 PAM MODULATION");
                    } catch (IOException e) {
                        System.out.println("Error");
                    }
                }
            }
        });

        controlPam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("{MOD:1}".toString().getBytes());
                        msg("4 PAM MODULATION");
                    } catch (IOException e) {
                        System.out.println("Error");
                    }
                }
            }
        });

        controlPam8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write("{MOD:2}".toString().getBytes());
                        msg("8 PAM MODULATION");
                    } catch (IOException e) {
                        System.out.println("Error");
                    }
                }
            }
        });

    }




    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
