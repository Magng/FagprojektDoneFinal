package com.example.home.fagprojektstart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseScreen extends AppCompatActivity {
    private Button pickShow, pickControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_screen);
        pickControls = (Button) findViewById(R.id.pickControl);
        pickShow = (Button) findViewById(R.id.pickShow);


        pickShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseScreen.this, showdata.class);
                startActivity(intent);
            }
        });

        pickControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseScreen.this, DeviceList.class);
                startActivity(intent);
            }
        });
    }
}
