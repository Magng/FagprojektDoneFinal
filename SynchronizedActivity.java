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



public class SynchronizedActivity extends AppCompatActivity {
    public TextView dak, SetText;
    private static final String host = "35.197.211.160";
    private static final String database = "quickstartdb";
    private static final String user = "root";
    private static final String password = "Dapolicex1";
    Connection connection = null;
    CheckedTextView connectedView;
    public String Synchronized;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_synchronized);
        dak = (TextView) findViewById(R.id.textView11);
        SetText = (TextView) findViewById(R.id.textView12);
        connectedView = (CheckedTextView) findViewById(R.id.connectedView);
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void,Void,Void>{
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
                            if (rs.getString(2).equals("Synchronization")){
                                Synchronized = rs.getString(3);
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    SetText.setText(Synchronized);
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
