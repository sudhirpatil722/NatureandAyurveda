package com.effigrity.ayurvedichomemedicine.free;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.effigrity.Utilities.CommonData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;

public class DiseaseDisplay extends DashboardActivity {
    AdView adView;
    Bundle bun;
    String choosenDisease;
    TextView goToRemedies;
    MyJavaScriptInterface jsInterface;
    ListView lv;
    ProgressDialog pd;
    String remedyAvailable;
    LinearLayout remedyLayout;
    ArrayList<String> remedyList = new ArrayList();
    RemedyListAdapter remedyListView;
    WebView wv;

    class MyJavaScriptInterface {
        private Context context;
        Thread timer = new Thread() {
            public void run() {
                try {
                    DiseaseDisplay.this.runOnUiThread(new Runnable() {
                        public void run() {
                            String remedyListFromMap = (String) CommonData.remedyItems.get(DiseaseDisplay.this.choosenDisease);
                            if (remedyListFromMap != null) {
                                String[] split = remedyListFromMap.split("##");
                                for (String add : split) {
                                    DiseaseDisplay.this.remedyList.add(add);
                                }
                                DiseaseDisplay.this.remedyListView = new RemedyListAdapter(MyJavaScriptInterface.this.context, DiseaseDisplay.this.remedyList, DiseaseDisplay.this.choosenDisease);
                                DiseaseDisplay.this.lv.setAdapter(DiseaseDisplay.this.remedyListView);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        public MyJavaScriptInterface(Context c) {
            this.context = c;
        }

        public void processHTML(String html) {
            this.timer.start();
            DiseaseDisplay.this.pd.dismiss();
        }
    }

    class RemedyListAdapter extends BaseAdapter implements OnClickListener {
        private Context activity;
        String diseaseUrl = "";
        int flag;
        private LayoutInflater inflater = null;
        String quota;
        ArrayList<String> remedy = new ArrayList();

        public RemedyListAdapter(Context a, ArrayList<String> d, String url) {
            this.activity = a;
            this.remedy = d;
            this.diseaseUrl = url;
            this.inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return this.remedy.size();
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
            if (((String) this.remedy.get(position)).contains(" (")) {
                String remedyNameEng = ((String) this.remedy.get(position)).substring(0, ((String) this.remedy.get(position)).indexOf("(") + 1);
                diseaseName.setText(Html.fromHtml(new StringBuilder(String.valueOf(remedyNameEng)).append("<i>").append(((String) this.remedy.get(position)).substring(((String) this.remedy.get(position)).indexOf("(") + 1, ((String) this.remedy.get(position)).indexOf(")"))).append("</i> )").toString()));
            } else {
                diseaseName.setText((CharSequence) this.remedy.get(position));
            }
            diseaseName.setTypeface(CommonData.typeFace);
            diseaseName.setTag(Integer.valueOf(position));
            diseaseName.setOnClickListener(this);
            return vi;
        }

        public void onClick(View v) {
            int i = ((Integer) v.getTag()).intValue();
            if (DiseaseDisplay.this.isNetworkAvailable()) {
                String remedyName = (String) this.remedy.get(i);
                String remedyUrl = remedyName;
                if (remedyName.contains("(")) {
                    remedyUrl = remedyName.substring(0, remedyName.indexOf(" ("));
                }
                String remedyPath = (CommonData.websiteBaseUrl + this.diseaseUrl + "/" + remedyUrl.replaceAll(" ", "_") + ".html").toLowerCase();
                Bundle b = new Bundle();
                b.putString("path", remedyPath);
                Intent ii = new Intent(DiseaseDisplay.this.getApplicationContext(), RemedyDetails.class);
                ii.putExtras(b);
                DiseaseDisplay.this.startActivity(ii);
                return;
            }
            new Builder(this.activity).setMessage("An Internet connection is required to use this app. Please check your settings and try again.").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            }).show();
        }
    }

    @SuppressLint({"NewApi", "JavascriptInterface"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_details);
        init();
        this.lv = (ListView) findViewById(R.id.remedy_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (VERSION.SDK_INT > 11) {
            getActionBar().setHomeButtonEnabled(true);
        }
        this.goToRemedies = (TextView) findViewById(R.id.remedybelow);
        this.goToRemedies.setTypeface(CommonData.typeFace);
        this.jsInterface = new MyJavaScriptInterface(this);
        this.wv = (WebView) findViewById(R.id.webView1);
        this.bun = getIntent().getExtras();
        String path = this.bun.getString("path");
        this.remedyAvailable = this.bun.getString("allowed");
        this.choosenDisease = this.bun.getString("choosenDisease");
        this.pd = new ProgressDialog(this, 0);
        this.pd.setTitle("Please Wait...");
        this.pd.setMessage("Loading ...");
        this.pd.setCancelable(false);
        this.pd.show();
        this.wv.setInitialScale(100);
        this.wv.getSettings().setBuiltInZoomControls(true);
        this.wv.getSettings().setLoadWithOverviewMode(true);
        this.wv.getSettings().setUseWideViewPort(true);
        this.wv.getSettings().setSupportZoom(true);
        this.wv.getSettings().setJavaScriptEnabled(true);
        this.wv.addJavascriptInterface(this.jsInterface, "HTMLOUT");
        this.wv.setBackgroundColor(0);
        this.wv.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                DiseaseDisplay.this.wv.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(DiseaseDisplay.this);
                myJavaScriptInterface.processHTML("");
            }
        });
        this.wv.loadUrl(path);
        this.adView = new AdView(this);
        this.adView.setAdUnitId(CommonData.promotionId);
        this.adView.setAdSize(AdSize.BANNER);
        this.adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("TEST_DEVICE_ID").build());
        ((RelativeLayout) findViewById(R.id.adlayout)).addView(this.adView);
    }
}
