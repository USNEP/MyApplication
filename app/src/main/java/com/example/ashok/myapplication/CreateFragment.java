package com.example.ashok.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Db.DatabaseHandler;
import model.Contact;

/**
 * Created by ashok on 9/12/15.
 */
public class CreateFragment extends Fragment implements  View.OnClickListener {
public Button btnAddd;
    public  DatabaseHandler db;
    Context thiscontext;
    Spinner fldName;
    EditText fldPh_no;
    String name;
    String phNo;
    public CreateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create, container, false);
       fldName=(Spinner)rootView.findViewById(R.id.spinnerList);
       fldPh_no=(EditText)rootView.findViewById(R.id.fldPh_no);

        btnAddd = (Button) rootView.findViewById(R.id.btnAdd);
        thiscontext = container.getContext();
        btnAddd.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        if(validateForm()) {
            db = new DatabaseHandler(thiscontext);
            db.addContact(new Contact(name, phNo));
            clearForm();
        }
        else{
            Toast.makeText(thiscontext, "Invalid Entry", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validateForm(){

        try {
             name = fldName.getSelectedItem().toString();
             phNo = fldPh_no.getText().toString();
        }catch (Exception e)

        {
            e.printStackTrace();
          return false;
        }
        if(name.isEmpty() || phNo.isEmpty())
        {
            return false;
        }
        db = new DatabaseHandler(thiscontext);
        if(!db.getContactByPhNo(phNo))
        {return false;}
        return true;
    }
    public void clearForm(){

        fldName.setSelection(0);
        fldPh_no.setText("");
    }


}
