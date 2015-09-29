package com.example.ashok.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Db.DatabaseHandler;
import model.Status;

/**
 * Created by ashok on 9/14/15.
 */
public class Home extends Fragment {


    public Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         Context thiscontext = container.getContext();
        DatabaseHandler db = new DatabaseHandler(thiscontext);
       Status st= db.getStatus();
       View rootView = inflater.inflate(R.layout.home_page, container, false);
        TextView bank=(TextView)rootView.findViewById(R.id.value_bank);
        bank.setText(st.get_bank());
        TextView cash=(TextView)rootView.findViewById(R.id.value_cash);
        cash.setText(st.get_cash());
        TextView loan=(TextView)rootView.findViewById(R.id.value_loan);
        loan.setText(st.get_loan());
        TextView status=(TextView)rootView.findViewById(R.id.valueStatus);
        status.setText(String.valueOf(Math.abs(Double.parseDouble(st.get_bank())+Double.parseDouble(st.get_cash()) - Double.parseDouble(st.get_loan()))));
        if((Double.parseDouble(st.get_bank())+Double.parseDouble(st.get_cash()) - Double.parseDouble(st.get_loan()))<0){
          status.setTextColor(Color.RED);
        }
        return rootView;
    }

}
