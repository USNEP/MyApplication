
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

        import java.sql.Types;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import Db.DatabaseHandler;
        import global.Global;
        import model.ReportData;
        import model.Type;

/**
 * Created by ashok on 9/28/15.
 */
public class ReportAdapter extends Fragment implements ListView.OnItemClickListener{
    private Context thiscontext;
    private DatabaseHandler db;
    private String currentTpe;
    private String currentDrawer;
    private TextView lblHead;
    private TextView lblTitle;
    List<String> loanItems;
    List<Type> types;
    String tp;

    public ReportAdapter() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.report_editor, container, false);
        thiscontext=container.getContext();
        db = new DatabaseHandler(thiscontext);
        currentTpe =getArguments().getString("arlist");
        currentDrawer=getArguments().getString("current");
        lblHead=(TextView) view.findViewById(R.id.lblHead);
        lblTitle=(TextView) view.findViewById(R.id.lblheader1);
        lblHead.setText(currentDrawer);
        lblTitle.setText(currentTpe);
        String[] drawerItems=getResources().getStringArray(R.array.navigation_drawer_items_array);
        loanItems= Arrays.asList(getResources().getStringArray(R.array.loan_items));
        if(loanItems.contains(currentTpe)){
            types=db.getTypes("All_Loans", "Loan_Items");
        }
        else
        {
            types= db.getTypes(currentDrawer, currentTpe);
        }

        ListView list_view1=(ListView)view.findViewById(R.id.listView1);
        list_view1.setAdapter(new ReportArrayAdapter(getActivity(), getReportArray(types,currentDrawer)));
        list_view1.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment=new IndivReportAdpator();
        Bundle bundle = new Bundle();
        TextView tv=(TextView)view.findViewById(R.id.st_row);
        System.out.println(tv.getText().toString());
        bundle.putString("currentHead", currentDrawer);
        bundle.putString("currentSubType", tv.getText().toString());
        fragment.setArguments(bundle);
        Global.global.changeFragment(fragment);

    }
    public List<ReportData> getReportArray(List<Type> array,String type){
        List<ReportData> rp=new ArrayList<ReportData>();
        for(Type t:array){
        rp.add(db.getReportBySubType(type, t.get_sub_type()));
            }
        return rp;
         }
        }
