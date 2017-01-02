package com.effigrity.slidingmenu;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.effigrity.Utilities.CommonData;
import com.effigrity.ayurvedichomemedicine.free.DiseaseDisplay;
import com.effigrity.ayurvedichomemedicine.free.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class MainFragment extends Fragment {
    AdView adView;
    Context c = null;
    ArrayList<String> diseaseList = new ArrayList();
    DiseaseListAdapter diseaseListView;
    boolean isAdShown = false;
    ListView lv;

    class DiseaseListAdapter extends BaseAdapter implements OnClickListener {
        private Context activity;
        ArrayList<String> disease = new ArrayList();
        int flag;
        private LayoutInflater inflater = null;
        String quota;

        public DiseaseListAdapter(Context a, ArrayList<String> d) {
            this.activity = a;
            this.disease = d;
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
            Button diseaseName = (Button) vi.findViewById(R.id.diseasename);
            diseaseName.setText((CharSequence) this.disease.get(position));
            diseaseName.setTypeface(CommonData.typeFace);
            diseaseName.setTag(Integer.valueOf(position));
            diseaseName.setOnClickListener(this);
            return vi;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void onClick(View v) {
            int i = ((Integer) v.getTag()).intValue();
            String choosenDisease = (CommonData.chosenBodyPart + "/" + ((String) this.disease.get(i)).replaceAll(" ", "_")).toLowerCase();
            String choosenDiseasePath = (CommonData.baseUrl + CommonData.chosenBodyPart + "/" + ((String) this.disease.get(i)).replaceAll(" ", "_") + ".html").toLowerCase();
            Bundle b = new Bundle();
            b.putString("path", choosenDiseasePath);
            b.putString("allowed", "X");
            b.putString("choosenDisease", choosenDisease);
            Intent ii = new Intent(this.activity, DiseaseDisplay.class);
            ii.putExtras(b);
            MainFragment.this.startActivity(ii);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.c = getActivity().getApplicationContext();
        View v = inflater.inflate(R.layout.main, container, false);
        this.adView = new AdView(this.c);
        this.adView.setAdUnitId(CommonData.promotionId);
        this.adView.setAdSize(AdSize.BANNER);
        this.adView.loadAd(new Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("TEST_DEVICE_ID").build());
        this.lv = (ListView) v.findViewById(R.id.disease_list);
        String[] split = ((String) CommonData.bodyPartProblems.get(CommonData.chosenBodyPart)).split("##");
        for (String add : split) {
            this.diseaseList.add(add);
        }
        this.diseaseListView = new DiseaseListAdapter(this.c, this.diseaseList);
        this.lv.setAdapter(this.diseaseListView);
        ((RelativeLayout) v.findViewById(R.id.adlayout)).addView(this.adView);
        return v;
    }

    public void onResume() {
        super.onResume();
    }
}
