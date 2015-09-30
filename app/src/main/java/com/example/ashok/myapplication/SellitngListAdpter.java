package com.example.ashok.myapplication;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Db.DatabaseHandler;
import global.Global;
import model.History;
import model.Type;

public class SellitngListAdpter extends ArrayAdapter<String> {
    private Context thisContext;
    private ViewHolder viewHolder;
    TextView tv;
    String type;
    List<String> items;
    DatabaseHandler db;
    String   m_Text="";
    List<Type>  types;
    List<String> loanItems;

    public SellitngListAdpter(Context context, List<String> items) {
        super(context, R.layout.setting_list, items);
        this.items=items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        thisContext=getContext();
        db=new DatabaseHandler(thisContext);
        loanItems=Arrays.asList(thisContext.getResources().getStringArray(R.array.loan_items));

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.setting_list, parent, false);
            // initialize the view holder
            viewHolder = new ViewHolder();
             tv=(TextView)convertView.findViewById(R.id.item_st);
            viewHolder.SerNo = (TextView) convertView.findViewById(R.id.sn);
            viewHolder.ivIcon = (TextView) convertView.findViewById(R.id.item_st);
           viewHolder.btnEdit = (ImageButton) convertView.findViewById(R.id.btnEdit);

            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
                    builder.setTitle("Update?");
                    final RelativeLayout layout=(RelativeLayout) v.getParent();
                    final EditText input = new EditText(thisContext);
                    final TextView text=(TextView)layout.findViewById(R.id.item_st);

                    input.setInputType(InputType.TYPE_CLASS_TEXT );
                    builder.setView(input);
                    input.setText(text.getText().toString());
                    input.setSelection(0,text.getText().toString().length());

                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            if(!m_Text.isEmpty()) {
                                if (updateType(text.getText().toString(), m_Text)){
                                    text.setText(m_Text);

                                }
                            }

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

            });
            viewHolder.btnDelete = (ImageButton) convertView.findViewById(R.id.btnDelete);
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener()
            { @Override
              public void onClick(View v)
                {   final RelativeLayout layout=(RelativeLayout) v.getParent();
                    final TextView tv=(TextView)layout.findViewById(R.id.item_st);
                    final ListView listv=(ListView)layout.getParent();
                    final RelativeLayout pl=(RelativeLayout) listv.getParent();
                    final TextView lblHead=(TextView)pl.findViewById(R.id.lblHead);
                    final TextView lblTitle=(TextView)pl.findViewById(R.id.lblheader1);
                    final String head=lblHead.getText().toString();
                    final String title=lblTitle.getText().toString();
                    System.out.println("...."+head+".....+title=;  "+title);
                    for (String a:loanItems)
                    System.out.println(a);
                    new AlertDialog.Builder(thisContext)
                            .setTitle("Delete Type?")
                            .setMessage("Are you sure to delete "+items.get(position)+ " type?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteType(tv.getText().toString());

                                    if(loanItems.contains(title)){
                                        types=db.getTypes("All_Loans", "Loan_Items");
                                    }
                                    else
                                    {
                                        types= db.getTypes(head, title);
                                    }
                                    listv.setAdapter(new SellitngListAdpter(thisContext,Global.global.getItems(types)));
                                }

                            })
                            .setNegativeButton("No", null).show();

                }

            } );
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        String s = getItem(position);
        viewHolder.SerNo.setText(String.valueOf(position+1)+".");
        viewHolder.ivIcon.setText(s);
       type=s;

        return convertView;
    }
    public void deleteType(String value){
         db.deleteType(value);

    }
    public boolean updateType(String  firstValue,String updatValue){
       return db.updateType(firstValue,updatValue)!=0?true:false;

    }
    public void updateView( View v,String s){
        RelativeLayout layout=(RelativeLayout) v.getParent();
        TextView text=(TextView)layout.findViewById(R.id.item_st);
        text.setText(s);
    }



    private static class ViewHolder {
        TextView ivIcon;
        TextView SerNo;
        ImageButton btnEdit;
        ImageButton btnDelete;
    }
}