package com.example.ashok.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Db.DatabaseHandler;
import model.Status;

/**
 * Created by ashok on 9/19/15.
 */
public class About extends Fragment{

    public About() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context thiscontext = container.getContext();
        View rootView = inflater.inflate(R.layout.about, container, false);
        TextView bank=(TextView)rootView.findViewById(R.id.about);
        bank.setText(getResources().getString(R.string.about));
        return rootView;
    }
}
