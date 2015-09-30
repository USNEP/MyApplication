package com.example.ashok.myapplication;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    ListView list_view1;
    ListView list_view2;
    ListView list_view3;
    String[] monInArray;
    String[] expensesArray;
    String[] monOutArray;
    String[] drawerArray;

    public Reports() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.report_multi_list, container, false);
        thiscontext=container.getContext();
        db = new DatabaseHandler(thiscontext);
        list_view1=(ListView)view.findViewById(R.id.listView1);
        list_view2=(ListView)view.findViewById(R.id.listView2);
        list_view3=(ListView)view.findViewById(R.id.listView3);
        monInArray=(getResources().getStringArray(R.array.money_in_array));
        monOutArray=(getResources().getStringArray(R.array.money_out_array));
        expensesArray=(getResources().getStringArray(R.array.record_expenses_array));
        drawerArray=(getResources().getStringArray(R.array.navigation_drawer_items_array));

        list_view1.setAdapter(new ReportArrayAdapter(getActivity(), getReportArray(monInArray)));
        list_view2.setAdapter(new ReportArrayAdapter(getActivity(), getReportArray(expensesArray)));
        list_view3.setAdapter(new ReportArrayAdapter(getActivity(),getReportArray(monOutArray) ));
        list_view1.setOnItemClickListener(this);
        list_view2.setOnItemClickListener(this);
        list_view3.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment=new ReportAdapter();
        bundle = new Bundle();
        String value;
        if(parent.getId()==list_view1.getId())
        { value=drawerArray[0];
            bundle.putString("arlist", monInArray[position]);
        }
        else if(parent.getId()==list_view2.getId()){
            value=drawerArray[1];
            bundle.putString("arlist", expensesArray[position]);
        }
        else{
            value=drawerArray[2];
            bundle.putString("arlist", monOutArray[position]);
        }
        bundle.putString("current", value);
        fragment.setArguments(bundle);
        Global.global.changeFragment(fragment);
    }
    public List<ReportData> getReportArray(String[] array){
        int n=array.length;
        List<ReportData> rp=new ArrayList<ReportData>();
        for(int i=0;i<n;i++){
            rp.add(db.getIndvReport(array[i]));
        }
        return rp;
   }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setListAdapter(new MobileArrayAdapter(getActivity(), Arrays.asList(getResources().getStringArray(R.array.loan_items))));
//
//}


}
