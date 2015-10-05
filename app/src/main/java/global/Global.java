package global;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import com.example.ashok.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import model.Type;

/**
 * Created by ashok on 9/28/15.
 */
public class Global {

    private FragmentManager fragmentManager;
    public static Global global;

    public ActionBar getActionBar() {
        return actionBar;
    }

    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;

    }
    public void setActionBarMode(){
        this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    public void setActionBarStandard(){    this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

    }

    ActionBar actionBar;

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

        private Global(){ }
    public static Global getInstance( ) {
        if (global==null)
        { global=new Global();

        }
        return global;
    }
 public void   changeFragment( Fragment fragment){

     fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
 }
    public List<String> getItems(List<Type> types){
        List<String> s=new ArrayList<String>();
        for(Type t:types)
        {
            s.add(t.get_sub_type());
        }
        return s;
    }

}
