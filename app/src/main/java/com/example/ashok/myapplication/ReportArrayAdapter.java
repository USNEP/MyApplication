
package com.example.ashok.myapplication;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.List;

        import model.History;
        import model.ReportData;

public class ReportArrayAdapter extends ArrayAdapter<ReportData> {
    private Context thisContext;
    private ViewHolder viewHolder;
    public ReportArrayAdapter(Context context, List<ReportData> items) {
        super(context, R.layout.report_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        thisContext=getContext();
        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.report_row, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (TextView) convertView.findViewById(R.id.st_row);
            viewHolder.SerNo = (TextView) convertView.findViewById(R.id.sn_setting);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.st_amount);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ReportData s = getItem(position);

        viewHolder.ivIcon.setText(s.getType());
        viewHolder.SerNo.setText(String.valueOf(position+1)+".");
        viewHolder.amount.setText(String.valueOf(s.getAmount()));

        return convertView;
    }





    private static class ViewHolder {
        TextView ivIcon;
        TextView SerNo;
        TextView amount;
    }
}