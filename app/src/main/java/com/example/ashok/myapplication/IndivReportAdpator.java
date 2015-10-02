package com.example.ashok.myapplication;

        import android.app.DatePickerDialog;
        import android.app.Fragment;
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
        import android.widget.TextView;
        import android.widget.Toast;

        import java.sql.Types;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

        import Db.DatabaseHandler;
        import global.Global;
        import model.History;
        import model.ReportData;
        import model.Type;

/**
 * Created by ashok on 9/28/15.
 */
public class IndivReportAdpator extends Fragment implements ListView.OnItemClickListener{
    private Context thiscontext;
    private DatabaseHandler db;
    private String currentHead;
    private String currentsubType;
    private TextView lblHead;
    private TextView lblTitle;
    List<String> loanItems;
    List<Type> types;
    String tp;
    EditText from;
    EditText to;
String startDate="";
    String endDate="";
    ListView list_view1;
    List<History> hist;
    public IndivReportAdpator() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.report_final, container, false);
        thiscontext=container.getContext();
        db = new DatabaseHandler(thiscontext);
        currentHead =getArguments().getString("currentHead");
        currentsubType=getArguments().getString("currentSubType");
        startDate=getArguments().getString("startDate");
        endDate=getArguments().getString("endDate");

        lblHead=(TextView) view.findViewById(R.id.lblHead);
        lblTitle=(TextView) view.findViewById(R.id.lblheader1);

        lblHead.setText(currentHead);
        lblTitle.setText(currentsubType);
        list_view1=(ListView)view.findViewById(R.id.listView1);
        hist=db.getistoryByDate(startDate,endDate,currentsubType,currentHead);
        list_view1.setAdapter(new IndvRepArryAdaptor(getActivity(),hist ));
        //list_view1.setAdapter(new IndvRepArryAdaptor(getActivity(), db.getAllContacts()));
        list_view1.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //  Toast.makeText(thiscontext,hist.get(position).get_description() , Toast.LENGTH_SHORT).show();
    }
    View.OnClickListener loadListener = new View.OnClickListener(){
        public void onClick(View v) {

            hist=db.getistoryByDate(startDate,endDate,currentsubType,currentHead);
            list_view1.setAdapter(new IndvRepArryAdaptor(getActivity(),hist ));
        }
    };



}
