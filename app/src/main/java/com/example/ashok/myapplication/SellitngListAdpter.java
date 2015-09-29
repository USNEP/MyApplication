package com.example.ashok.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import model.History;

public class SellitngListAdpter extends ArrayAdapter<String> {
    private Context thisContext;
    private ViewHolder viewHolder;
    TextView tv;
    String type;
    List<String> items;
    public SellitngListAdpter(Context context, List<String> items) {
        super(context, R.layout.setting_list, items);
        this.items=items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        thisContext=getContext();
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

                   updateType(position);

                }

            });
            viewHolder.btnDelete = (ImageButton) convertView.findViewById(R.id.btnDelete);
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener()
            { @Override
              public void onClick(View v)
                {
                    Toast.makeText(getContext(), v.toString(), Toast.LENGTH_LONG).show();

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
    public boolean deleteType(int position){
        return true;
    }
    public boolean updateType(int position){

        return true;
    }




    private static class ViewHolder {
        TextView ivIcon;
        TextView SerNo;
        ImageButton btnEdit;
        ImageButton btnDelete;
    }
}