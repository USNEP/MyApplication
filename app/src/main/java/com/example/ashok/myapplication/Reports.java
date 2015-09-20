package com.example.ashok.myapplication;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import Db.DatabaseHandler;
import model.History;

/**
 * Created by ashok on 9/12/15.
 */
public class Reports extends ListFragment {
    public Button btnAddd;
    public  DatabaseHandler db;
    Context thiscontext;
    TextView fldName;
    EditText fldPh_no;
    private List<History> mItems;
    public Reports() {
    }
    @Override

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            db=new DatabaseHandler(getActivity());
            List<History> all=db.getAllContacts();
        System.out.println("///////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println(String.valueOf(all.size()));
        System.out.println(String.valueOf(all.get(0).get_id()));

        setListAdapter(new MobileArrayAdapter(getActivity(), all));

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        thiscontext = container.getContext();
//
//
//        db=new DatabaseHandler(thiscontext);
//        List<History> all=db.getAllContacts();
//        MobileArrayAdapter ma= new MobileArrayAdapter(thiscontext, all);
//       setListAdapter(ma);
//    }

}





