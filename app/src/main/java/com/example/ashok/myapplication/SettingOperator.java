package com.example.ashok.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import Db.DatabaseHandler;
import global.Global;
import model.Type;

/**
 * Created by ashok on 9/28/15.
 */
public class SettingOperator extends Fragment implements ListView.OnItemClickListener{
private Context thiscontext;
private DatabaseHandler db;
    private String drawerItems;
    private String currentDrawer;
    private TextView lblHead;
    private TextView lblTitle;
    List<String> loanItems;
    List<Type> types;

    public SettingOperator() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.setting_editor, container, false);
        thiscontext=container.getContext();
        db = new DatabaseHandler(thiscontext);
        drawerItems=getArguments().getString("arlist");
        currentDrawer=getArguments().getString("current");
        lblHead=(TextView) view.findViewById(R.id.lblHead);
        lblTitle=(TextView) view.findViewById(R.id.lblheader1);
        lblHead.setText(currentDrawer);
        lblTitle.setText(drawerItems);
        loanItems=Arrays.asList(getResources().getStringArray(R.array.loan_items));
        if(loanItems.contains(drawerItems)){
            types=db.getTypes("All_Loans", "Loan_Items");
        }
        else
        {
           types= db.getTypes(currentDrawer, drawerItems);
        }
        ListView list_view1=(ListView)view.findViewById(R.id.listView1);
        list_view1.setAdapter(new SellitngListAdpter(getActivity(), Global.global.getItems(types)));
        list_view1.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
