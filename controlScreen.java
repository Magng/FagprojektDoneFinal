package com.example.home.fagprojektstart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public class controlScreen extends AppCompatActivity {
    private Button controlVideo, controlModulation, buttonShow, controlLED;
    String address;
    public static String EXTRA_ADDRESS = "device_address";
    private ProgressDialog progress;
    BluetoothSocket btSocket;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    bluetooth object = new bluetooth();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_screen);
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        try {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
            BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
            btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
            object.setSocket(btSocket);
            object.setAddress(address);
        } catch(IOException e) {

        }

        new ConnectBT().execute(); //Call the class to connect

        controlLED = (Button) findViewById(R.id.controlLED);
        controlVideo = (Button) findViewById(R.id.controlVideo);
        controlModulation = (Button) findViewById(R.id.controlModulation);
        buttonShow = (Button) findViewById(R.id.buttonShow);

        controlLED.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(controlScreen.this, ledControl.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_ADDRESS, object);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        controlVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(controlScreen.this, videoControl.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_ADDRESS, object);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        controlModulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(controlScreen.this, modulationControl.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_ADDRESS, object);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(controlScreen.this, showdata.class);
                Disconnect();
                startActivity(intent);
            }
        });
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(controlScreen.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (!isBtConnected)
                {
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout
    }

    @Override
    public void onBackPressed() {
        Disconnect();
        finish();
    }
}
