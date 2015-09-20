package com.example.ashok.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ashok on 9/19/15.
 */
public class InputDialog {
    String m_Text="";

    public String getM_Text() {
        return m_Text;
    }

    public void setM_Text(String m_Text) {
        this.m_Text = m_Text;
    }

    public View getalertView(Context contxt) {

        AlertDialog.Builder builder = new AlertDialog.Builder(contxt);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(contxt);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
       return this.getalertView(contxt);
    }
}
