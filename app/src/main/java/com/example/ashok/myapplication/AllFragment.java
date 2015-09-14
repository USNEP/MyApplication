package com.example.ashok.myapplication;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import Db.DatabaseHandler;
import model.Contact;

/**
 * Created by ashok on 9/12/15.
 */
public class AllFragment extends ListFragment {
    public Button btnAddd;
    public  DatabaseHandler db;
    Context thiscontext;
    TextView fldName;
    EditText fldPh_no;
    private List<Contact> mItems;
    public AllFragment() {
    }
    @Override

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            db=new DatabaseHandler(getActivity());
            List<Contact> all=db.getAllContacts();
        System.out.println("///////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println(String.valueOf(all.size()));
        System.out.println(String.valueOf(all.get(1).getID()));

        setListAdapter(new MobileArrayAdapter(getActivity(), all));

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        thiscontext = container.getContext();
//
//
//        db=new DatabaseHandler(thiscontext);
//        List<Contact> all=db.getAllContacts();
//        MobileArrayAdapter ma= new MobileArrayAdapter(thiscontext, all);
//       setListAdapter(ma);
//    }

}





