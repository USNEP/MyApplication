package com.example.ashok.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import Db.DatabaseHandler;
import model.History;
import model.Type;

/**
 * Created by ashok on 9/12/15.
 */
public class RecordMoneyExpenses extends Fragment implements  View.OnClickListener {
public Button btnAddd;
    public  DatabaseHandler db;
    Context thiscontext;
    Spinner fldOption;
    Spinner fldFrom;
    EditText fldAmount;
    EditText fldDate;
    EditText fldComment;
    RadioButton rb;
    RadioButton rc;
    History history;
    TextView title;
    String [] listvalue;
    List<String> creoptions;
    String m_Text="";
    public RecordMoneyExpenses() {
        history= new History();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_create, container, false);
        thiscontext = container.getContext();
        db = new DatabaseHandler(thiscontext);

        fldOption =(Spinner)rootView.findViewById(R.id.spinnerList1);
       fldFrom =(Spinner)rootView.findViewById(R.id.spinnerList);
        listvalue=getResources().getStringArray(R.array.record_expenses_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_spinner_item,listvalue);
        fldOption.setAdapter(adapter);
        fldOption.setOnItemSelectedListener(spinner_listener);
        creoptions = new ArrayList<String>();
        creoptions.add(getResources().getString(R.string.create_new));
        db.getAllTypes();
        creoptions.addAll(db.getType("1","2","2"));
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_spinner_item,creoptions);
        fldFrom.setAdapter(adapter1);
        fldFrom.setOnItemSelectedListener(spinner_listener);
        TextView lblName=(TextView) rootView.findViewById(R.id.lblName);
        lblName.setText("Expense Head");
        fldAmount = (EditText)rootView.findViewById(R.id.fldAmount);
       rb = (RadioButton)rootView. findViewById(R.id.radioBank);
        rc = (RadioButton)rootView. findViewById(R.id.radioCash);
       fldDate =(EditText) rootView.findViewById(R.id.fldDate);
        title=(TextView) rootView.findViewById(R.id.lblTitle);
        title.setText(getResources().getStringArray(R.array.navigation_drawer_items_array)[1]);
        fldDate.setOnClickListener(date_listener);
        btnAddd = (Button) rootView.findViewById(R.id.btnAdd);
        fldComment=(EditText) rootView.findViewById(R.id.fldComment);
        btnAddd.setOnClickListener(this);
        myCalendar=Calendar.getInstance();
        updateLabel();
        return rootView;
    }


    @Override
    public void onClick(View v) {

        if(validateForm()) {
            db.addHistory(new History(history.get_type(), history.get_sub_type(),history.get_io(),
                    history.get_cb(),history.get_amount(),history.get_date(),history.get_description()));
            clearForm();
        }
        else{
            Toast.makeText(thiscontext, "Invalid Entry", Toast.LENGTH_LONG).show();
        }
    }
    AdapterView.OnItemSelectedListener spinner_listener = new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
            int arg2, long arg3) {
        if(arg2==0 && arg3==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(thiscontext);
            builder.setTitle("Create New?");

            final EditText input = new EditText(thiscontext);
            input.setInputType(InputType.TYPE_CLASS_TEXT );
            builder.setView(input);
            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    Type tp=new Type("1",m_Text,"3");
                    db.addType(tp);
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            }

            }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(thiscontext, "nothing selected", Toast.LENGTH_LONG).show();

        }
    };

    View.OnClickListener date_listener = new View.OnClickListener(){
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(thiscontext, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            //Your Implementaions...
        }
    };

    public boolean validateForm(){


        history.set_type(fldOption.getSelectedItem().toString());
        history.set_sub_type(fldFrom.getSelectedItem().toString());
        history.set_io(false);
        history.set_cb(rc.isChecked() ? true : false);
        history.set_amount(Double.parseDouble(fldAmount.getText().toString()));
        history.set_date(fldDate.getText().toString());
        history.set_description(fldComment.getText().toString());

        return true;
    }
    public void clearForm(){

        fldOption.setSelection(0);
        fldAmount.setText("");
        fldFrom.setSelection(0);
        fldComment.setText("");
        myCalendar=Calendar.getInstance();
        updateLabel();
    }
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fldDate.setText(sdf.format(myCalendar.getTime()));
    }



}
//spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//@Override
//public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//        // your code here
//        }
//
//@Override
//public void onNothingSelected(AdapterView<?> parentView) {
//        // your code here
//        }
//
//        });
//RadioGroup radioGroup = (RadioGroup) findViewById(R.id.yourRadioGroup);
//radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
//        {
//@Override
//public void onCheckedChanged(RadioGroup group, int checkedId) {
//        // checkedId is the RadioButton selected
//        }
//        });