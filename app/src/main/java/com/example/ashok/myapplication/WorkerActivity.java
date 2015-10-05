package com.example.ashok.myapplication;

        import android.app.AlertDialog;
        import android.support.v4.app.Fragment;
        import android.content.DialogInterface;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ListView;
        import android.support.v4.app.ActionBarDrawerToggle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;

        import java.util.ArrayList;
        import java.util.Arrays;

        import Db.DatabaseHandler;
        import global.Global;

public class WorkerActivity extends AppCompatActivity {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private boolean drawerstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Global.getInstance();
        DatabaseHandler db = new DatabaseHandler(this);
        System.out.println("Database on start calling");
        db.onStart();
        System.out.println("Database on start called");
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];
        Global.global.setActionBar(getSupportActionBar());
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
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_save);
                drawerstatus=true;
                /// getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_save);
                drawerstatus=false;

                // getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_save);
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
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
                else{
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.action_settings:
                selectItem(-2);
                break;
            case R.id.action_search:
                new AlertDialog.Builder(this)
                        .setTitle("Exit KYP?")
                        .setMessage("Are you sure to exit KYP?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);                            }

                        })
                        .setNegativeButton("No", null).show();

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
    public void onBackPressed() {
        selectItem(-1);

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
        Bundle bundle = new Bundle();

        switch (position) {
            case -2:
                fragment=new Setting();
                Global.global.setActionBarStandard();
                break;
            case -1:
                fragment=new Home();
                Global.global.setActionBarStandard();
                break;
            case 0:
                fragment = new RecordMoneyExpenses();
                bundle = new Bundle();
                bundle.putString("current", mNavigationDrawerItemTitles[0]);
                bundle.putStringArrayList("arlist", new ArrayList<String>(Arrays.asList(mNavigationDrawerItemTitles)));
                fragment.setArguments(bundle);
                Global.global.setActionBarStandard();

                break;
            case 1:
                fragment = new RecordMoneyExpenses();
                bundle = new Bundle();
                bundle.putString("current", mNavigationDrawerItemTitles[1]);
                bundle.putStringArrayList("arlist", new ArrayList<String>(Arrays.asList(mNavigationDrawerItemTitles)));
                fragment.setArguments(bundle);
                Global.global.setActionBarStandard();


                break;
            case 2:
                fragment = new RecordMoneyExpenses();
                bundle = new Bundle();
                bundle.putString("current", mNavigationDrawerItemTitles[2]);
                bundle.putStringArrayList("arlist", new ArrayList<String>(Arrays.asList(mNavigationDrawerItemTitles)));
                fragment.setArguments(bundle);
                Global.global.setActionBarStandard();

                break;
            case 3:
                fragment = new Reports();
                Global.global.setActionBarStandard();

                break;
            case 4:
                fragment = new TabbedFragment();
                break;


            default:
                Global.global.setActionBarStandard();
                break;
        }

        if (fragment != null) {
            Global.global.setFragmentManager(getSupportFragmentManager());
            Global.global.changeFragment(fragment);
            //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
