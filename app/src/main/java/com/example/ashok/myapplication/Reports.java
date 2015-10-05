package com.example.ashok.myapplication;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Db.DatabaseHandler;
import global.Global;
import model.ReportData;

/**
 * Created by ashok on 9/14/15.
 */
public class Reports extends Fragment implements ListView.OnItemClickListener{

    private Context thiscontext;
    Bundle bundle;
    DatabaseHandler db ;
    String[] loan={"Loan"};
    String[] expns={"Expenses"};
    String[] Income={""};
    String[] Investment={""};
    ListView list_view1;
    ListView list_view2;
    ListView list_view3;
    ListView list_view4;
    ListView list_view5;
    ListView list_view6;
    String[] monInArray;
    String[] expensesArray;
    String[] monOutArray;
    String[] drawerArray;
    String[] loanItems;
    EditText from;
    EditText to;
    Calendar myCalendar = Calendar.getInstance();
    Calendar myDte= Calendar.getInstance();
    Boolean frm=true;
    String myFormat = "yyyy-MM-dd"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    Button btnLoad;
    String fromDate;
    String toDate;
    public Reports() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.report_multi_list, container, false);
        thiscontext=container.getContext();
        db = new DatabaseHandler(thiscontext);
        list_view1=(ListView)view.findViewById(R.id.listView1);
        list_view2=(ListView)view.findViewById(R.id.listView2);
        list_view3=(ListView)view.findViewById(R.id.listView3);
        list_view4=(ListView)view.findViewById(R.id.listView4);
        list_view5=(ListView)view.findViewById(R.id.listView5);
        list_view6=(ListView)view.findViewById(R.id.listView6);
        monInArray=(getResources().getStringArray(R.array.money_in_array));
        monOutArray=(getResources().getStringArray(R.array.money_out_array));
        expensesArray=(getResources().getStringArray(R.array.record_expenses_array));
        drawerArray=(getResources().getStringArray(R.array.navigation_drawer_items_array));
        loanItems=(getResources().getStringArray(R.array.loan_items));
        Income[0]=monInArray[0];
        Investment[0]=monOutArray[2];
        list_view3.setVisibility(View.GONE);
        list_view5.setVisibility(View.GONE);

       // list_view1.setOnItemClickListener(this);
        list_view2.setOnItemClickListener(this);
        list_view4.setOnItemClickListener(this);
        list_view1.setOnItemClickListener(this);
        list_view3.setOnItemClickListener(this);
        list_view5.setOnItemClickListener(this);
        list_view6.setOnItemClickListener(this);


        from=(EditText)view.findViewById(R.id.editDate1);
        to=(EditText)view.findViewById(R.id.editDate2);
        myDte.set(Calendar.DATE, myDte.getActualMinimum(Calendar.DATE));
        from.setText(sdf.format(myDte.getTime()));
        myDte= Calendar.getInstance();
        myDte.set(Calendar.DATE, myDte.getActualMaximum(Calendar.DATE));
        to.setText(sdf.format(myDte.getTime()));
        myDte= Calendar.getInstance();
        from.setOnClickListener(date_listener);
        to.setOnClickListener(date_listener);
        refreshView();
        btnLoad=(Button)view.findViewById(R.id.btnShow);
        btnLoad.setOnClickListener(loadListener);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.listView2:
                if(list_view3.getVisibility()==View.VISIBLE){
                list_view3.setVisibility(View.GONE);}
                else{
                    list_view3.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.listView4:
                if(list_view5.getVisibility()==View.VISIBLE){
                    list_view5.setVisibility(View.GONE);}
                else{
                    list_view5.setVisibility(View.VISIBLE);
                }
                break;
            default:
                Fragment fragment=new ReportAdapter();
                bundle = new Bundle();
                TextView tv=(TextView)view.findViewById(R.id.st_row);
                bundle.putString("arlist", tv.getText().toString());
                bundle.putString("startDate", fromDate);
                bundle.putString("endDate", toDate);
                fragment.setArguments(bundle);
                Global.global.changeFragment(fragment);
        }



    }
    public List<ReportData> getReportArray(String[] array,String startDate,String endDate){
        int n=array.length;
        List<ReportData> rp=new ArrayList<ReportData>();
        for(int i=0;i<n;i++){
            rp.add(db.getIndvReport(array[i],startDate,endDate));
        }
        return rp;
   }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setListAdapter(new MobileArrayAdapter(getActivity(), Arrays.asList(getResources().getStringArray(R.array.loan_items))));
//
//}
View.OnClickListener date_listener = new View.OnClickListener(){
    public void onClick(View v) {
        String d=((EditText)v).getText().toString();
        try {
            myDte.setTime(sdf.parse(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(v.getId()==R.id.editDate1){frm=true;}else{frm=false;}
        new DatePickerDialog(thiscontext, date, myDte
                .get(Calendar.YEAR), myDte.get(Calendar.MONTH),
                myDte.get(Calendar.DAY_OF_MONTH)).show();
    }
};
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
        if(frm)
            from.setText(sdf.format(myCalendar.getTime()));
        else
            to.setText(sdf.format(myCalendar.getTime()));

    }
    View.OnClickListener loadListener = new View.OnClickListener(){
        public void onClick(View v) {
        refreshView();
        }
    };
   public void refreshView(){
        fromDate=from.getText().toString();
        toDate=to.getText().toString();
       List<ReportData> exp=getReportArray(expensesArray, fromDate, toDate);
       List<ReportData> ln=getReportArray(loanItems, fromDate, toDate);
       list_view1.setAdapter(new ReportArrayAdapter(getActivity(), getReportArray(Income,fromDate,toDate)));
       list_view3.setAdapter(new ReportArrayAdapter(getActivity(),exp));
       list_view5.setAdapter(new ReportArrayAdapter(getActivity(),ln));
       list_view6.setAdapter(new ReportArrayAdapter(getActivity(), getReportArray(Investment,fromDate,toDate)));
       list_view2.setAdapter(new ReportArrayAdapter(getActivity(), getTotal(expns[0],exp)));
       list_view4.setAdapter(new ReportArrayAdapter(getActivity(), getTotal(loan[0],exp)));


   }
    public List<ReportData> getTotal(String item,List<ReportData> rp){
        double total=0;
        for(ReportData r:rp){
            total=total+r.getAmount();
        }
        rp=new ArrayList<ReportData>();
        rp.add(new ReportData(item,total));
        System.out.println("Total values=     "+total );
        return rp;

    }


}
