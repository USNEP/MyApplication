package com.example.ashok.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import model.History;

public class MobileArrayAdapter extends ArrayAdapter<History> {

    public MobileArrayAdapter(Context context, List<History> items) {
        super(context, R.layout.list_entries, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_entries, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (TextView) convertView.findViewById(R.id.entry_id);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.entry_name);
            viewHolder.tvDescription = (Button) convertView.findViewById(R.id.entry_phno);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        History item = getItem(position);

        System.out.println(".......................................................................");
        System.out.println(String.valueOf(item.get_type()));
        viewHolder.ivIcon.setText(String.valueOf(item.get_type()));
        viewHolder.tvTitle.setText(item.get_sub_type());
        viewHolder.tvDescription.setText(String.valueOf(item.get_amount()));

        return convertView;
    }


    private static class ViewHolder {
        TextView ivIcon;
        TextView tvTitle;
        Button tvDescription;
    }
}