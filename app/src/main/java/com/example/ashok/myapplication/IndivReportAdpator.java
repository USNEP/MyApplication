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
    Calendar myCalendar = Calendar.getInstance();
    Calendar myDte= Calendar.getInstance();
    Boolean frm=true;
    String myFormat = "yyyy-MM-dd"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    Button btnLoad;
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
        lblHead=(TextView) view.findViewById(R.id.lblHead);
        lblTitle=(TextView) view.findViewById(R.id.lblheader1);
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
        lblHead.setText(currentHead);
        lblTitle.setText(currentsubType);
        list_view1=(ListView)view.findViewById(R.id.listView1);
        hist=db.getistoryByDate(from.getText().toString(),to.getText().toString(),currentsubType,currentHead);
        list_view1.setAdapter(new IndvRepArryAdaptor(getActivity(),hist ));
        //list_view1.setAdapter(new IndvRepArryAdaptor(getActivity(), db.getAllContacts()));
        list_view1.setOnItemClickListener(this);
        btnLoad=(Button)view.findViewById(R.id.btnShow);
        btnLoad.setOnClickListener(loadListener);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //  Toast.makeText(thiscontext,hist.get(position).get_description() , Toast.LENGTH_SHORT).show();
    }
    View.OnClickListener loadListener = new View.OnClickListener(){
        public void onClick(View v) {

            hist=db.getistoryByDate(from.getText().toString(),to.getText().toString(),currentsubType,currentHead);
            list_view1.setAdapter(new IndvRepArryAdaptor(getActivity(),hist ));
        }
    };
    public List<ReportData> getReportArray(List<Type> array,String type){
        List<ReportData> rp=new ArrayList<ReportData>();
        for(Type t:array){
            rp.add(db.getReportBySubType(type, t.get_sub_type()));
        }
        return rp;
    }
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
    };DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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

    public  Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

}
