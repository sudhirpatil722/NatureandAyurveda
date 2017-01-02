package com.effigrity.ayurvedichomemedicine.free;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.effigrity.Utilities.CommonData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;

public class BodyPartDiseaseActivity extends DashboardActivity {
    AdView adView;
    Bundle bun;
    String choosenDisease;
    ArrayList<String> diseaseList = new ArrayList();
    DiseaseListAdapter diseaseListView;
    ArrayList<Boolean> isLocked = new ArrayList();
    ListView lv;

    class DiseaseListAdapter extends BaseAdapter implements OnClickListener {
        private Context activity;
        ArrayList<String> disease = new ArrayList();
        int flag;
        private LayoutInflater inflater = null;
        ArrayList<Boolean> isLocked = new ArrayList();
        String quota;

        public DiseaseListAdapter(Context a, ArrayList<String> d, ArrayList<Boolean> l) {
            this.activity = a;
            this.disease = d;
            this.isLocked = l;
            this.inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return this.disease.size();
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
            ImageView lockImage = (ImageView) vi.findViewById(R.id.lock);
            Button diseaseName = (Button) vi.findViewById(R.id.diseasename);
            diseaseName.setText((CharSequence) this.disease.get(position));
            diseaseName.setTypeface(CommonData.typeFace);
            if (((Boolean) this.isLocked.get(position)).booleanValue()) {
                lockImage.setVisibility(View.GONE);
            } else {
                lockImage.setVisibility(View.GONE);
            }
            diseaseName.setTag(Integer.valueOf(position));
            diseaseName.setOnClickListener(this);
            return vi;
        }

        public void onClick(View v) {
            int i = ((Integer) v.getTag()).intValue();
            if (((Boolean) this.isLocked.get(i)).booleanValue()) {
                new Builder(this.activity).setMessage("The remedies for locked diseases are available in paid app. Do you want to buy?").setCancelable(false).setPositiveButton("Buy Now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        String appName = "com.effigrity.ayurvedichomemedicine.paid";
                        try {
                            BodyPartDiseaseActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.effigrity.ayurvedichomemedicine.paid")));
                        } catch (ActivityNotFoundException e) {
                            BodyPartDiseaseActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.effigrity.ayurvedichomemedicine.paid")));
                        }
                    }
                }).setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
                return;
            }
            String diseaseName = ((String) this.disease.get(i)).replaceAll(" ", "_");
            if (diseaseName.contains("_(")) {
                diseaseName = diseaseName.substring(0, diseaseName.indexOf("_("));
            }
            String choosenDisease = (CommonData.chosenBodyPart + "/" + diseaseName).toLowerCase().replaceAll("&", "");
            String choosenDiseasePath = (CommonData.baseUrl + "/" + diseaseName + ".html").toLowerCase().replaceAll("&", "");
            Bundle b = new Bundle();
            b.putString("path", choosenDiseasePath);
            b.putString("allowed", "X");
            b.putString("choosenDisease", choosenDisease);
            Intent ii = new Intent(this.activity, DiseaseDisplay.class);
            ii.putExtras(b);
            BodyPartDiseaseActivity.this.startActivity(ii);
        }
    }

    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diseaselistscreen);
        init();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (VERSION.SDK_INT > 11) {
            getActionBar().setHomeButtonEnabled(true);
        }
        this.lv = (ListView) findViewById(R.id.disease_list);
        String[] split = ((String) CommonData.bodyPartProblems.get(CommonData.chosenBodyPart)).split("##");
        boolean locked = false;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("0")) {
                locked = true;
            } else {
                this.diseaseList.add(split[i]);
                this.isLocked.add(false);
            }
        }
        this.diseaseListView = new DiseaseListAdapter(this, this.diseaseList, this.isLocked);
        this.lv.setAdapter(this.diseaseListView);
        this.adView = new AdView(this);
        this.adView.setAdUnitId(CommonData.promotionId);
        this.adView.setAdSize(AdSize.BANNER);
        this.adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("TEST_DEVICE_ID").build());
        ((RelativeLayout) findViewById(R.id.adlayout)).addView(this.adView);

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("pageaction")) {
                String action = getIntent().getExtras().getString("pageaction");
                for (int index = 0 ;index <this.diseaseList.size();index++){
                    if(action.equalsIgnoreCase(this.diseaseList.get(index))){
                        String diseaseName = action;
                        if (diseaseName.contains("_(")) {
                            diseaseName = diseaseName.substring(0, diseaseName.indexOf("_("));
                        }
                        String choosenDisease = (CommonData.chosenBodyPart + "/" + diseaseName).toLowerCase().replaceAll("&", "");
                        String choosenDiseasePath = (CommonData.baseUrl + "/" + diseaseName + ".html").toLowerCase().replaceAll("&", "");
                        Bundle b = new Bundle();
                        b.putString("path", choosenDiseasePath);
                        b.putString("allowed", "X");
                        b.putString("choosenDisease", choosenDisease);
                        Intent ii = new Intent(this, DiseaseDisplay.class);
                        ii.putExtras(b);
                        BodyPartDiseaseActivity.this.startActivity(ii);
                        break;
                    }
                }
            }
        }
    }
}
