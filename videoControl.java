package com.example.home.fagprojektstart;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class videoControl extends AppCompatActivity {
    Button sendComp, sendFrames, sendRes;
    EditText editComp, editFrames, editRes;
    BluetoothSocket btSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_control);
        Intent intent = getIntent();
        btSocket = ((bluetooth) intent.getExtras().getSerializable(controlScreen.EXTRA_ADDRESS)).getSocket(); //receive the address of the bluetooth device

        sendComp = (Button) findViewById(R.id.sendComp);
        sendFrames = (Button) findViewById(R.id.sendFrames);
        sendRes = (Button) findViewById(R.id.sendRes);

        editComp = (EditText) findViewById(R.id.editComp);
        editFrames = (EditText) findViewById(R.id.editFrames);
        editRes = (EditText) findViewById(R.id.editRes);

        sendComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Compression;
                Compression = editComp.getText().toString();
                try {
                    int CompressionInt = Integer.parseInt(Compression);
                    if (CompressionInt > 0 && CompressionInt <= 100 ) {
                        if (btSocket != null) {
                            try {
                                Compression = "{VCL:"+Compression+"}";
                                btSocket.getOutputStream().write(Compression.getBytes());
                                msg("Control signal sent");
                                editComp.setText("");
                            } catch (IOException e) {
                                System.out.println("Error");
                            }
                        }
                    } else {
                        System.out.print("Failure in input");
                        editComp.setText("");
                        msg("Wrong input, follow instruction");
                    }

                } catch (NumberFormatException e){
                    e.printStackTrace();
                    msg("Wrong input, follow instruction");
                }
            }
        });

        sendFrames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Frames;
                Frames = editFrames.getText().toString();
                try {
                    int FramesInt = Integer.parseInt(Frames);

                    if (FramesInt > 0 && FramesInt <= 60 ) {
                        if (btSocket != null) {
                            try {
                                Frames = "{FPS:"+Frames+"}";
                                btSocket.getOutputStream().write(Frames.getBytes());
                                msg("Control signal sent");
                                editFrames.setText("");
                            } catch (IOException e) {
                                System.out.println("Error");
                            }
                        }
                    } else {
                        System.out.print("Failure in input");
                        editFrames.setText("");
                        msg("Wrong input, follow instruction");
                    }

                } catch (NumberFormatException e){
                    e.printStackTrace();
                    msg("Wrong input, follow instruction");
                }
            }
        });

        sendRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Resolution;
                Resolution = editRes.getText().toString();
                try {
                    int ResolutionInt = Integer.parseInt(Resolution);

                    if (ResolutionInt == 144 || ResolutionInt == 480 || ResolutionInt == 720 || ResolutionInt == 1080) {
                        if (btSocket != null) {
                            try {
                                Resolution = "{RES:"+Resolution+"}";
                                btSocket.getOutputStream().write(Resolution.getBytes());
                                msg("Control signal sent");
                                editRes.setText("");
                            } catch (IOException e) {
                                System.out.println("Error");
                            }
                        }
                    } else {
                        System.out.print("Failure in input");
                        editRes.setText("");
                        msg("Wrong input, follow instruction");
                    }

                } catch (NumberFormatException e){
                    e.printStackTrace();
                    msg("Wrong input, follow instruction");
                }
            }
        });
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
