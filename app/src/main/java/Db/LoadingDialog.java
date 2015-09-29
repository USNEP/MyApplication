package Db;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ashok on 9/26/15.
 */
public class LoadingDialog {
private ProgressDialog diaog;
private    Context thiscontext;
  private String msz;
     public LoadingDialog(){}
    public LoadingDialog( String msz, Context thiscontext) {
        this.msz = msz;
        this.thiscontext = thiscontext;
    }
public  ProgressDialog getLoadingDialog() {
    ProgressDialog dialog = new ProgressDialog(thiscontext);
    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    dialog.setMessage(msz);
    dialog.setIndeterminate(true);
    dialog.setCanceledOnTouchOutside(false);
    return dialog;
}
}
