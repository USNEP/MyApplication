package com.example.ashok.myapplication;

import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import Db.DatabaseHandler;

public class MainActivity extends AppCompatActivity {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.icon_save, mNavigationDrawerItemTitles[0]);
        drawerItem[1] = new ObjectDrawerItem(R.drawable.icon_save, mNavigationDrawerItemTitles[1]);
        drawerItem[2] = new ObjectDrawerItem(R.drawable.icon_save,mNavigationDrawerItemTitles[2]);
        drawerItem[3] = new ObjectDrawerItem(R.drawable.icon_save, mNavigationDrawerItemTitles[3]);
        drawerItem[4] = new ObjectDrawerItem(R.drawable.icon_save,mNavigationDrawerItemTitles[4]);
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.icon_save,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_save);

                /// getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                // getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectItem(-1);
       // getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_save);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch ((id)){
            case android.R.id.home:
                selectItem(-1);

                break;
            case R.id.action_settings:
                selectItem(-2);
                break;
            case R.id.action_search:
                selectItem(-3);
                break;
            default:
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                return super.onOptionsItemSelected(item);


        }
        return true;

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case -2:
                fragment=new Setting();
                break;
            case -1:
                fragment=new Home();
                break;
            case 0:
                fragment = new CreateFragment();
                break;
            case 1:
                fragment = new AllFragment();
                break;
            case 2:
                fragment = new CreateFragment();
                break;
            case 3:
                fragment = new AllFragment();
                break;
            case 4:
                fragment = new CreateFragment();
                break;


            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
if(position>-1) {
    mDrawerList.setItemChecked(position, true);
    mDrawerList.setSelection(position);
    getSupportActionBar().setTitle(mNavigationDrawerItemTitles[position]);
    mDrawerLayout.closeDrawer(mDrawerList);
}

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
