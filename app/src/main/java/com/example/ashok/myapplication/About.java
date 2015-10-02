package com.example.ashok.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import Db.DatabaseHandler;
import model.Status;

/**
 * Created by ashok on 9/19/15.
 */
public class About extends Fragment {
    TextView bank;
    public About() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context thiscontext = container.getContext();
        View rootView = inflater.inflate(R.layout.about, container, false);
         bank=(TextView)rootView.findViewById(R.id.about);
        bank.setText(getResources().getString(R.string.about));
        new RequestTask().execute();
        return rootView;
    }

    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            String responseString = null;
            HttpURLConnection conn=null;
            try {
                String myurl="https://www.google.com.np";
                URL url = new URL(myurl);
                try {
                     conn = (HttpURLConnection) url.openConnection();
                }catch( Exception en){
                    en.printStackTrace();
                    responseString="Unable to connect to network";
                }
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    // Do normal input or output stream reading
                    System.out.println("Connected  hurreyyyyyyyyyyyyyyyyyyy");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    responseString = in.toString();
                }
                else {
                    System.out.println("connection fail bhayo lau kha");
                    responseString = " Connection FAILED"; // See documentation for more info on response handling
                }
            }  catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception po fal6 ta hai ...");
                //TODO Handle problems..
                responseString="connection error.......";
            }
            System.out.println("Return samma aayo hai");
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("lau bhayo hai aba ta ...");
            bank.setText(result);
        }
    }
}
