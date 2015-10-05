package com.example.ashok.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Db.DatabaseHandler;
import Db.LoadingDialog;
import global.Global;
import model.History;
import model.Status;
import model.Type;

/**
 * Created by ashok on 9/12/15.
 */
public class RecordMoneyExpenses extends Fragment implements  View.OnClickListener {
    public Button btnAddd;
    Button btnCreate;
    public  DatabaseHandler db;
    //fields to access view items
    TextView lblSpinner;
    Context thiscontext;
    Spinner fldOption;
    Spinner fldFrom;
    EditText fldAmount;
    EditText fldDate;
    EditText fldComment;
    RadioButton rb;
    TextView lblName;
    RadioButton rc;
    TextView title;
    /// fields for class
    List<Type> creoptions;
    String m_Text="";
    History history;
    List<String> drawerItems;
    String currentDrawer;
    Calendar myCalendar = Calendar.getInstance();
   String[] loan_items;
    ProgressDialog ldDialog;
    public RecordMoneyExpenses() {
        history= new History();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_create, container, false);
        thiscontext = container.getContext();
        db = new DatabaseHandler(thiscontext);
        ldDialog=new LoadingDialog("Loading.....",thiscontext).getLoadingDialog();
        loan_items=getResources().getStringArray(R.array.loan_items);
        drawerItems=getArguments().getStringArrayList("arlist");
        currentDrawer=getArguments().getString("current");
        title=(TextView) rootView.findViewById(R.id.lblTitle);
        title.setText(currentDrawer);
        fldOption =(Spinner)rootView.findViewById(R.id.spinnerList1);
        fldOption.setOnItemSelectedListener(type_listener);
        fldFrom =   (Spinner)rootView.findViewById(R.id.spinnerList);
        lblName=(TextView) rootView.findViewById(R.id.lblName);
        lblSpinner=(TextView) rootView.findViewById(R.id.lblspinner1);
        fldAmount = (EditText)rootView.findViewById(R.id.fldAmount);
        rb = (RadioButton)rootView. findViewById(R.id.radioBank);
        rc = (RadioButton)rootView. findViewById(R.id.radioCash);
        fldDate =(EditText) rootView.findViewById(R.id.fldDate);
        fldDate.setOnClickListener(date_listener);
        btnAddd = (Button) rootView.findViewById(R.id.btnAdd);
        fldComment=(EditText) rootView.findViewById(R.id.fldComment);
        btnCreate = (Button) rootView.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        btnAddd.setOnClickListener(this);
        myCalendar=Calendar.getInstance();
        updateLabel();
        switch(drawerItems.indexOf(currentDrawer)){
            case 0:
                onCreateMoneyInView();
                break;
            case 1:
                onCreateExpenseView();
                break;
            case 2:
                onCreateMoneyOutView();
                break;
            default:
        }



        return rootView;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
        case R.id.btnCreate:
            AlertDialog.Builder builder = new AlertDialog.Builder(thiscontext);
            builder.setTitle("Create New?");
            final EditText input = new EditText(thiscontext);
            input.setInputType(InputType.TYPE_CLASS_TEXT );
            builder.setView(input);
            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean success;
                    m_Text = input.getText().toString();
                    creoptions = new ArrayList<Type>();
                    if (!m_Text.isEmpty()) {
                        if(new ArrayList<String>(Arrays.asList(loan_items)).contains(fldOption.getSelectedItem().toString())){
                            success=  db.addType( new Type("All_Loans","Loan_Items", m_Text));
                        creoptions.addAll(db.getTypes("All_Loans", "Loan_Items"));
                        } else{
                            success=  db.addType( new Type(currentDrawer, fldOption.getSelectedItem().toString(), m_Text));
                            creoptions.addAll(db.getTypes(currentDrawer, fldOption.getSelectedItem().toString()));
                        }

                        updateSpinner(Global.global.getItems(creoptions),fldFrom);
                    } else
                    {                Toast.makeText(thiscontext, "Invalid Entry", Toast.LENGTH_LONG).show();
                    }
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
            break;
        case R.id.btnAdd:



            if(validateForm()) {
                System.out.println("cd------------------"+history.get_head());
                db.addHistory(new History(history.get_type(), history.get_sub_type(),history.get_head(),history.get_io(),
                        history.get_cb(),history.get_amount(),history.get_date(),history.get_description()));

            updateStatus(history.get_amount(),rb.isChecked(),fldOption.getSelectedItem().toString());
                System.out.println("which checked");
                System.out.println("kkkkkkkkkk" + rb.isChecked());
                clearForm();
            }
            else{
                Toast.makeText(thiscontext, "Invalid Entry", Toast.LENGTH_LONG).show();
            }
            break;
        default:
    }

    }

    AdapterView.OnItemSelectedListener type_listener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int arg2, long arg3) {
            if(currentDrawer==drawerItems.get(0) ){
                if(arg2==0 && arg3==0){
                    lblName.setText("Title");
                }else{
                    lblName.setText("From");
                }
            }
            else if(currentDrawer==drawerItems.get(2)){
                if((arg2==0 && arg3==0) ||(arg2==1 && arg3==1)){
                    lblName.setText("To");
                }else{
                    lblName.setText("Title");
                }
            }
            creoptions = new ArrayList<Type>();
            try {
                if(new ArrayList<String>(Arrays.asList(loan_items)).contains(fldOption.getSelectedItem().toString())) {
                    System.out.println(fldOption.getSelectedItem().toString());
                    if(fldOption.getSelectedItem().toString().equals(loan_items[1]))
                    {
                        btnCreate.setVisibility(View.GONE);
                    }
                    else{
                        System.out.println("made visible");
                        System.out.println( fldOption.getSelectedItem().toString());
                        btnCreate.setVisibility(View.VISIBLE);
                    }
                    creoptions.addAll(db.getTypes("All_Loans", "Loan_Items"));
                }else{
                    btnCreate.setVisibility(View.VISIBLE);
                    creoptions.addAll(db.getTypes(currentDrawer, fldOption.getSelectedItem().toString()));
                }                }catch (Exception e){
                e.printStackTrace();
            }
            updateSpinner(Global.global.getItems(creoptions), fldFrom);

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
        try {
            System.out.println("/////////////////"+currentDrawer);
            history.set_type(fldOption.getSelectedItem().toString());
            history.set_sub_type(fldFrom.getSelectedItem().toString());
            history.set_head(currentDrawer);
            history.set_io(false);
            System.out.println("........."+history.get_head()+".............");
            history.set_cb(rc.isChecked() ? true : false);
            history.set_amount(Double.parseDouble(fldAmount.getText().toString()));
            history.set_date(fldDate.getText().toString());
            history.set_description(fldComment.getText().toString());
        }catch(Exception e)
        {
            return false;
        }
        if(history.get_sub_type().isEmpty()){
            return false;
        }

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
    public int updateStatus(double amt,boolean bank,String optn){

        Status st=db.getStatus();
        if(optn.equals(loan_items[1]) || optn.equals(loan_items[2])){
            st.set_loan(String.valueOf(Double.parseDouble(st.get_loan())-amt));
        }
        if( optn.equals(loan_items[0])){
            st.set_loan(String.valueOf(Double.parseDouble(st.get_loan())+amt));
        }
       amt= currentDrawer.equals(drawerItems.get(0))?amt:(-1)*amt;
       if(bank)
           st.set_bank(String.valueOf(Double.parseDouble(st.get_bank())+amt));
       else
           st.set_cash(String.valueOf(Double.parseDouble(st.get_cash()) + amt));

        return db.updateStatus(st);
    }

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

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fldDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void onCreateExpenseView() {
        lblSpinner.setText("Expense Type :");
        lblName.setText("Expense Head :");
        updateSpinner(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.record_expenses_array))), fldOption);
        creoptions = new ArrayList<Type>();

        try {
            if(new ArrayList<String>(Arrays.asList(loan_items)).contains(fldOption.getSelectedItem().toString())) {
                creoptions.addAll(db.getTypes("All_Loans", "Loan_Items"));
            }else{
                creoptions.addAll(db.getTypes(currentDrawer, fldOption.getSelectedItem().toString()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        updateSpinner(Global.global.getItems(creoptions), fldFrom);

    }
    public void onCreateMoneyInView(){
        lblSpinner.setText("Source:");
        lblName.setText("Title :");
        updateSpinner(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.money_in_array))), fldOption);
        creoptions = new ArrayList<Type>();
        try {
            if(new ArrayList<String>(Arrays.asList(loan_items)).contains(fldOption.getSelectedItem().toString())) {
                creoptions.addAll(db.getTypes("All_Loans", "Loan_Items"));
            }else{
                creoptions.addAll(db.getTypes(currentDrawer, fldOption.getSelectedItem().toString()));
            }        }catch(Exception e){
            e.printStackTrace();
        }
        updateSpinner(Global.global.getItems(creoptions), fldFrom);

    }
    public void onCreateMoneyOutView(){
        lblName.setText("To :");
        lblSpinner.setText("Out For :");
        updateSpinner(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.money_out_array))),fldOption);
        creoptions = new ArrayList<Type>();
        try {
            if(new ArrayList<String>(Arrays.asList(loan_items)).contains(fldOption.getSelectedItem().toString())) {
                if(fldOption.getSelectedItem().toString().equals(loan_items[1]))
                {
                    btnCreate.setVisibility(View.GONE);
                }
                creoptions.addAll(db.getTypes("All_Loans", "Loan_Items"));
            }else{
                creoptions.addAll(db.getTypes(currentDrawer, fldOption.getSelectedItem().toString()));
            }        }catch(Exception e){
            e.printStackTrace();
        }
        updateSpinner(Global.global.getItems(creoptions), fldFrom);

    }
    public void updateSpinner(List<String> listValue,Spinner spinner){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_spinner_item,listValue);
        adapter    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.invalidate();
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