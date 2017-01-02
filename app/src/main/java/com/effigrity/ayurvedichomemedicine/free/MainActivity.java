package com.effigrity.ayurvedichomemedicine.free;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.effigrity.Utilities.CommonData;
import com.effigrity.apprater.Appirater;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends DashboardActivity {
    AdRequest adRequest = null;
    AdView adView;
    BodyPartListAdapter bodyPartListView;
    Bundle bun;
    String choosenDisease;
    boolean doubleBackToExitPressedOnce = false;
    private InterstitialAd interstitial;
    boolean isAdShown = false;
    ListView lv;
    String action="";

    class BodyPartListAdapter extends BaseAdapter implements OnClickListener {
        private Context activity;
        ArrayList<String> bodyPartList = new ArrayList();
        int flag;
        private LayoutInflater inflater = null;
        String quota;

        public BodyPartListAdapter(Context a, ArrayList<String> d) {
            this.activity = a;
            this.bodyPartList = d;
            this.inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return this.bodyPartList.size();
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null) {
                vi = this.inflater.inflate(R.layout.disease_item, null);
            }
            Button diseaseName = (Button) vi.findViewById(R.id.diseasename);
            diseaseName.setText((CharSequence) this.bodyPartList.get(position));
            diseaseName.setTypeface(CommonData.typeFace);
            diseaseName.setTag(Integer.valueOf(position));
            diseaseName.setOnClickListener(this);
            return vi;
        }

        public void onClick(View v) {
            CommonData.chosenBodyPart = (String) this.bodyPartList.get(((Integer) v.getTag()).intValue());
            MainActivity.this.startActivity(new Intent(this.activity, BodyPartDiseaseActivity.class));
        }
    }

    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        Appirater.appLaunched(this);
        init();
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (VERSION.SDK_INT > 11) {
            getActionBar().setHomeButtonEnabled(true);
        }
        this.lv = (ListView) findViewById(R.id.body_part_list);
        ArrayList<String> bodyPartList = new ArrayList();
        bodyPartList.add("Ankle");
        bodyPartList.add("Babies");
        bodyPartList.add("Body");
        bodyPartList.add("Bones");
        bodyPartList.add("Ear");
        bodyPartList.add("Elbow");
        bodyPartList.add("Eye");
        bodyPartList.add("Face");
        bodyPartList.add("Feet");
        bodyPartList.add("Hair");
        bodyPartList.add("Head");
        bodyPartList.add("Legs");
        bodyPartList.add("Lips");
        bodyPartList.add("Mouth");
        bodyPartList.add("Muscle");
        bodyPartList.add("Neck");
        bodyPartList.add("Nose");
        bodyPartList.add("Skin");
        bodyPartList.add("Stomach");
        bodyPartList.add("Throat");
        bodyPartList.add("Women");

        boolean isAutoNavigate = false;
        if(getIntent()!=null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("notification_action")){
                action = getIntent().getExtras().getString("notification_action");
                for (int index = 0; index <bodyPartList.size();index++){
                    if(bodyPartList.get(index).equalsIgnoreCase(action)){
                        isAutoNavigate  = true;
                        CommonData.chosenBodyPart = (String) bodyPartList.get(index);
                        MainActivity.this.startActivity(new Intent(this, BodyPartDiseaseActivity.class));
                        break;
                    }
                }
                if(!isAutoNavigate){
                    for (Map.Entry<String, String> entry : CommonData.remedyItems.entrySet()) {
                        System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
                        if(entry.getKey().toString().contains(action)){
                            String value = "";
                            try{
                                String[] split = entry.getKey().split("/");
                                if(split.length>0) {
                                    value = split[0];
                                    int listPostition = CommonData.getPagePosition(value);
                                    CommonData.chosenBodyPart = (String) bodyPartList.get(listPostition);
                                    Intent in = new Intent(this, BodyPartDiseaseActivity.class);
                                    if(split.length>1){
                                        in.putExtra("pageaction",action);
                                    }
                                    MainActivity.this.startActivity(in);
                                    break;
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }
        this.bodyPartListView = new BodyPartListAdapter(this, bodyPartList);
        this.lv.setAdapter(this.bodyPartListView);
        this.adView = new AdView(this);
        this.adView.setAdUnitId(CommonData.promotionId);
        this.adView.setAdSize(AdSize.BANNER);
        this.adRequest = new Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("TEST_DEVICE_ID").build();
        this.adView.loadAd(this.adRequest);
        ((RelativeLayout) findViewById(R.id.adlayout)).addView(this.adView);
        this.interstitial = new InterstitialAd(this);
        this.interstitial.setAdUnitId(CommonData.interstitialId);
        this.adRequest = new Builder().build();
        this.interstitial.loadAd(this.adRequest);
    }

    protected void onResume() {
        super.onResume();
        CommonData.intialize();
        CommonData.prepareRemedyList();
        CommonData.typeFace = Typeface.createFromAsset(getAssets(), "arialbd.ttf");
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
        this.doubleBackToExitPressedOnce = true;
        toast_short("Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
