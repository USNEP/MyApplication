package com.example.ashok.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by ashok on 9/14/15.
 */
public class Setting extends Fragment {


    public Setting() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.setting_page, container, false);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);
        spinner.setEnabled(true);
        return rootView;
    }

}
