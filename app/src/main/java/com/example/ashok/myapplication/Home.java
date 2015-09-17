package com.example.ashok.myapplication;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ashok on 9/14/15.
 */
public class Home extends Fragment {


    public Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        double b=5000,c=4000,l=10000;
        View rootView = inflater.inflate(R.layout.home_page, container, false);
        TextView bank=(TextView)rootView.findViewById(R.id.value_bank);
        bank.setText(String.valueOf(b));
        TextView cash=(TextView)rootView.findViewById(R.id.value_cash);
        cash.setText(String.valueOf(c));
        TextView loan=(TextView)rootView.findViewById(R.id.value_loan);
        loan.setText(String.valueOf(l));
        TextView status=(TextView)rootView.findViewById(R.id.valueStatus);
        status.setText(String.valueOf(Math.abs(b + c - l)));
        if((b+c-l)<0){
          status.setTextColor(Color.RED);
        }
        return rootView;
    }

}
