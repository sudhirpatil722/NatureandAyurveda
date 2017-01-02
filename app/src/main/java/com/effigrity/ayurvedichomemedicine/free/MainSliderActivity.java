package com.effigrity.ayurvedichomemedicine.free;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.effigrity.Utilities.CommonData;
import com.effigrity.slidermenu.adapter.NavDrawerListAdapter;
import com.effigrity.slidermenu.model.NavDrawerItem;
import com.effigrity.slidingmenu.MainFragment;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainSliderActivity extends DashboardActivity {
    public static int count = 0;
    public static String pnrno = "";
    private NavDrawerListAdapter adapter;
    boolean doubleBackToExitPressedOnce = false;
    private InterstitialAd interstitial;
    boolean isAdShown = false;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;
    boolean single_click = false;

    private class SlideMenuClickListener implements OnItemClickListener {
        private SlideMenuClickListener() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            MainSliderActivity.this.displayView(position);
        }
    }

    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonData.intialize();
        CommonData.prepareRemedyList();
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
        }
        CharSequence title = getTitle();
        this.mDrawerTitle = title;
        this.mTitle = title;
        this.navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        this.navDrawerItems = new ArrayList();
        for (int i = 0; i < 4; i++) {
            this.navDrawerItems.add(new NavDrawerItem(this.navMenuTitles[i]));
        }
        this.mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        this.adapter = new NavDrawerListAdapter(getApplicationContext(), this.navDrawerItems);
        this.mDrawerList.setAdapter(this.adapter);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (VERSION.SDK_INT > 11) {
            getActionBar().setHomeButtonEnabled(true);
        }
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, R.drawable.ic_drawer, R.string.choose_part, R.string.choose_part) {
            public void onDrawerClosed(View view) {
                MainSliderActivity.this.getActionBar().setTitle(MainSliderActivity.this.mTitle);
                MainSliderActivity.this.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                MainSliderActivity.this.getActionBar().setTitle(MainSliderActivity.this.mDrawerTitle);
                MainSliderActivity.this.invalidateOptionsMenu();
            }
        };
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
        if (savedInstanceState == null) {
            this.mDrawerLayout.openDrawer(this.mDrawerList);
        }
        this.interstitial = new InterstitialAd(this);
        this.interstitial.setAdUnitId(CommonData.interstitialId);
        this.interstitial.loadAd(new Builder().build());
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            if (this.interstitial.isLoaded()) {
                this.interstitial.show();
                return;
            }
            return;
        }
        if (!this.single_click) {
            if (!this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
                this.mDrawerLayout.openDrawer(this.mDrawerList);
            }
            this.single_click = true;
        }
        this.doubleBackToExitPressedOnce = true;
        toast_short("Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainSliderActivity.this.doubleBackToExitPressedOnce = false;
                MainSliderActivity.this.single_click = false;
            }
        }, 2000);
    }

    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == 8 && menu != null && menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", new Class[]{Boolean.TYPE});
                m.setAccessible(true);
                m.invoke(menu, new Object[]{Boolean.valueOf(true)});
            } catch (NoSuchMethodException e) {
            } catch (Exception e2) {
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.rating /*2131034200*/:
                onClickGiveRating(getCurrentFocus());
                return true;
            case R.id.like /*2131034201*/:
                onClickFacebook(getCurrentFocus());
                return true;
            case R.id.aboutUS /*2131034204*/:
                onClickAbout(getCurrentFocus());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = this.mDrawerLayout.isDrawerOpen(this.mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint({"NewApi"})
    private void displayView(int position) {
        Fragment fragment = new MainFragment();
        CommonData.chosenBodyPart = this.navMenuTitles[position];
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            this.mDrawerList.setItemChecked(position, true);
            this.mDrawerList.setSelection(position);
            setTitle(this.navMenuTitles[position]);
            this.mDrawerLayout.closeDrawer(this.mDrawerList);
            return;
        }
        Log.e("MainActivity", "Error in creating fragment");
    }

    @SuppressLint({"NewApi"})
    public void setTitle(CharSequence title) {
        this.mTitle = title;
        getActionBar().setTitle(this.mTitle);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
