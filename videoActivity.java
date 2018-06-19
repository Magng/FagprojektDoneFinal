package com.example.home.fagprojektstart;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckedTextView;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import android.os.AsyncTask;

import org.w3c.dom.Text;


public class videoActivity extends AppCompatActivity {
    public TextView dak, SetTextFrames, textViewCompression, textViewResolution;
    private static final String host = "35.197.211.160";
    private static final String database = "quickstartdb";
    private static final String user = "root";
    private static final String password = "Dapolicex1";
    Connection connection = null;
    CheckedTextView connectedView;
    public String Frames;
    public String Compression;
    public String Resolution;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_video);
        dak = (TextView) findViewById(R.id.textView5);
        SetTextFrames = (TextView) findViewById(R.id.textView6);
        textViewCompression = (TextView) findViewById(R.id.textViewCompression);
        textViewResolution = (TextView) findViewById(R.id.textViewResolution);



        connectedView = (CheckedTextView) findViewById(R.id.connectedView);
        new MyTask().execute();


    }

    private class MyTask extends AsyncTask<Void,Void,Void>{
        private String fname="", lname="";
        String query = "SELECT * FROM inventory";


        protected Void doInBackground(Void... arg0){
            try {
                String url = String.format("jdbc:mysql://%s:3306/%s", host, database);
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url,user,password);
                System.out.println("Connection established...");
                if (connection != null) {
                    connectedView.setChecked(true);
                    Thread.sleep(1000);
                    while (connection != null) {
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        while (rs.next()) {
                            if (rs.getString(2).equals("Frames")){
                                Frames = rs.getString(3);
                            }
                            else if (rs.getString(2).equals("Compression")){
                                Compression = rs.getString(3);
                            }
                            else if (rs.getString(2).equals("Resolution")){
                                Resolution = rs.getString(3);
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    SetTextFrames.setText(Frames);
                                    textViewCompression.setText(Compression);
                                    textViewResolution.setText(Resolution);
                                }

                            });
                        }
                    }
                }
                System.out.println("Connection Stopped...");
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }
    }

    @Override
    public void onBackPressed() {
        connectedView.setChecked(false);
        connection = null;
        finish();
    }
}
