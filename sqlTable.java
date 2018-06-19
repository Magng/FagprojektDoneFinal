package com.example.home.fagprojektstart;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sqlTable extends AppCompatActivity {
    ListView listView;
    private ArrayList<String> arrayNames;
    private ArrayList<String> arrayValues;
    private ArrayAdapter<String> adapter;
    private static final String host = "35.197.211.160";
    private static final String database = "quickstartdb";
    private static final String user = "root";
    private static final String password = "Dapolice1";
    Connection connection = null;
    CheckedTextView connectedView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_table);
        String[] names = {"Video", "Framing", "Compression", "Encryption","WORKING","WORKING","WORKING","WORKING"};
        String[] values = {"1234","1234","1234","1234","1234","1234"};
        arrayNames = new ArrayList<>(Arrays.asList(names));
        arrayValues = new ArrayList<>(Arrays.asList(values));
        adapter = new ArrayAdapter<String>(this, R.layout.adapter_view_layout, R.id.firstItem1);
        listView.setAdapter(adapter);
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
                    while (connection != null) {
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        if (rs.next()) {
                            final String result = rs.getString(3);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    System.out.println("HI MAN");


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
        connection = null;
        finish();
    }
}