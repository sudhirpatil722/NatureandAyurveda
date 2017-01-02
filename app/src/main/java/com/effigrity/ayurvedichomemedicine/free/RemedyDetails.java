package com.effigrity.ayurvedichomemedicine.free;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.effigrity.Utilities.CommonData;
import com.effigrity.apprater.Appirater;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class RemedyDetails extends DashboardActivity {
    AdView adView;
    Bundle bun;
    String choosenDisease;
    ProgressDialog pd;
    String remedyAvailable;
    WebView wv;

    class MyJavaScriptInterface {
        MyJavaScriptInterface() {
        }

        public void processHTML(String html) {
            if (html.contains("temporarily down or it may have moved permanently") || html.contains("ERR_NAME_NOT_RESOLVED")) {
                RemedyDetails.this.goBack();
            }
            RemedyDetails.this.pd.dismiss();
        }
    }

    @SuppressLint({"NewApi", "JavascriptInterface"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedy_details);
        Appirater.significantEvent(this);
        init();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (VERSION.SDK_INT > 11) {
            getActionBar().setHomeButtonEnabled(true);
        }
        this.wv = (WebView) findViewById(R.id.webView1);
        this.bun = getIntent().getExtras();
        String path = this.bun.getString("path");
        this.pd = new ProgressDialog(this, 0);
        this.pd.setTitle("Please Wait...");
        this.pd.setMessage("Loading ...");
        this.pd.show();
        this.wv.loadUrl(path);
        this.wv.getSettings().setBuiltInZoomControls(true);
        this.wv.getSettings().setLoadWithOverviewMode(true);
        this.wv.getSettings().setUseWideViewPort(true);
        this.wv.getSettings().setSupportZoom(true);
        this.wv.getSettings().setJavaScriptEnabled(true);
        this.wv.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        this.wv.setBackgroundColor(0);
        this.wv.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                RemedyDetails.this.wv.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface();
                myJavaScriptInterface.processHTML("");
            }
        });
        this.wv.setBackgroundColor(0);
        if (VERSION.SDK_INT >= 11) {
            this.wv.setLayerType(1, new Paint());
        }
        this.adView = new AdView(this);
        this.adView.setAdUnitId(CommonData.promotionId);
        this.adView.setAdSize(AdSize.BANNER);
        this.adView.loadAd(new Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("TEST_DEVICE_ID").build());
        ((RelativeLayout) findViewById(R.id.adlayout)).addView(this.adView);
    }

    private void goBack() {
        runOnUiThread(new Runnable() {
            public void run() {
                RemedyDetails.this.wv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                new AlertDialog.Builder(RemedyDetails.this).setMessage("An Internet connection is required to use this app. Please check your settings and try again.").setCancelable(false).setPositiveButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        RemedyDetails.this.onBackPressed();
                    }
                }).show();
            }
        });
    }
}
