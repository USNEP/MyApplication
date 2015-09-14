package com.example.ashok.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import model.Contact;

public class MobileArrayAdapter extends ArrayAdapter<Contact> {

    public MobileArrayAdapter(Context context, List<Contact> items) {
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
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.entry_phno);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        Contact item = getItem(position);

        System.out.println(".......................................................................");
        System.out.println(String.valueOf(item.getName()));
        viewHolder.ivIcon.setText(String.valueOf(item.getID()));
        viewHolder.tvTitle.setText(item.getName());
        viewHolder.tvDescription.setText(item.getPhoneNumber());

        return convertView;
    }


    private static class ViewHolder {
        TextView ivIcon;
        TextView tvTitle;
        TextView tvDescription;
    }
}