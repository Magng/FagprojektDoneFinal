package com.example.home.fagprojektstart;
import android.bluetooth.BluetoothSocket;
import java.io.Serializable;

/**
 * Created by Home on 13/06/2018.
 */


public class bluetooth implements Serializable{
    private static BluetoothSocket socket;
    private String address = "";

    public bluetooth(){
        super();
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setSocket(BluetoothSocket socket){
        this.socket = socket;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }
}
