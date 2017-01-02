package com.effigrity.apprater;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.effigrity.ayurvedichomemedicine.free.R;

public class Appirater {
    private static final String PREF_APP_VERSION_CODE = "versioncode";
    private static final String PREF_DATE_FIRST_LAUNCHED = "date_firstlaunch";
    private static final String PREF_DATE_REMINDER_PRESSED = "date_reminder_pressed";
    private static final String PREF_DONT_SHOW = "dontshow";
    private static final String PREF_EVENT_COUNT = "event_count";
    private static final String PREF_LAUNCH_COUNT = "launch_count";
    private static final String PREF_RATE_CLICKED = "rateclicked";

    static  class AnonymousClass1 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ Editor val$editor;
        private final /* synthetic */ Context val$mContext;

        AnonymousClass1(Context context, Editor editor, Dialog dialog) {
            this.val$mContext = context;
            this.val$editor = editor;
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            Appirater.rateApp(this.val$mContext, this.val$editor);
            this.val$dialog.dismiss();
        }
    }

    static class AnonymousClass2 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ Editor val$editor;

        AnonymousClass2(Editor editor, Dialog dialog) {
            this.val$editor = editor;
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            if (this.val$editor != null) {
                this.val$editor.putLong(Appirater.PREF_DATE_REMINDER_PRESSED, System.currentTimeMillis());
                this.val$editor.commit();
            }
            this.val$dialog.dismiss();
        }
    }

    static class AnonymousClass3 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ Editor val$editor;

        AnonymousClass3(Editor editor, Dialog dialog) {
            this.val$editor = editor;
            this.val$dialog = dialog;
        }

        public void onClick(View v) {
            if (this.val$editor != null) {
                this.val$editor.putBoolean(Appirater.PREF_DONT_SHOW, true);
                this.val$editor.commit();
            }
            this.val$dialog.dismiss();
        }
    }

    public static void appLaunched(Context mContext) {
        boolean testMode = mContext.getResources().getBoolean(R.bool.appirator_test_mode);
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName() + ".appirater", 0);
        if (testMode || !(prefs.getBoolean(PREF_DONT_SHOW, false) || prefs.getBoolean(PREF_RATE_CLICKED, false))) {
            Editor editor = prefs.edit();
            if (testMode) {
                showRateDialog(mContext, editor);
                return;
            }
            long launch_count = prefs.getLong(PREF_LAUNCH_COUNT, 0);
            long event_count = prefs.getLong(PREF_EVENT_COUNT, 0);
            long date_firstLaunch = prefs.getLong(PREF_DATE_FIRST_LAUNCHED, 0);
            long date_reminder_pressed = prefs.getLong(PREF_DATE_REMINDER_PRESSED, 0);
            try {
                int appVersionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
                if (prefs.getInt(PREF_APP_VERSION_CODE, 0) != appVersionCode) {
                    launch_count = 0;
                    event_count = 0;
                    editor.putLong(PREF_EVENT_COUNT, 0);
                }
                editor.putInt(PREF_APP_VERSION_CODE, appVersionCode);
            } catch (Exception e) {
            }
            launch_count++;
            editor.putLong(PREF_LAUNCH_COUNT, launch_count);
            if (date_firstLaunch == 0) {
                date_firstLaunch = System.currentTimeMillis();
                editor.putLong(PREF_DATE_FIRST_LAUNCHED, date_firstLaunch);
            }
            if (launch_count >= ((long) mContext.getResources().getInteger(R.integer.appirator_launches_until_prompt))) {
                if (System.currentTimeMillis() >= date_firstLaunch + (((long) (((mContext.getResources().getInteger(R.integer.appirator_days_until_prompt) * 24) * 60) * 60)) * 1000) || event_count >= ((long) mContext.getResources().getInteger(R.integer.appirator_events_until_prompt))) {
                    if (date_reminder_pressed == 0) {
                        showRateDialog(mContext, editor);
                    } else {
                        if (System.currentTimeMillis() >= (((long) (((mContext.getResources().getInteger(R.integer.appirator_days_before_reminding) * 24) * 60) * 60)) * 1000) + date_reminder_pressed) {
                            showRateDialog(mContext, editor);
                        }
                    }
                }
            }
            editor.commit();
        }
    }

    public static void rateApp(Context mContext) {
        rateApp(mContext, mContext.getSharedPreferences(mContext.getPackageName() + ".appirater", 0).edit());
    }

    public static void significantEvent(Context mContext) {
        boolean testMode = mContext.getResources().getBoolean(R.bool.appirator_test_mode);
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName() + ".appirater", 0);
        if (testMode || !(prefs.getBoolean(PREF_DONT_SHOW, false) || prefs.getBoolean(PREF_RATE_CLICKED, false))) {
            prefs.edit().putLong(PREF_EVENT_COUNT, prefs.getLong(PREF_EVENT_COUNT, 0) + 1).apply();
        }
    }

    private static void rateApp(Context mContext, Editor editor) {
        //mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format(mContext.getString(R.string.appirator_market_url), new Object[]{mContext.getPackageName()}))));
        mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(mContext.getString(R.string.appirator_market_url))));
        if (editor != null) {
            editor.putBoolean(PREF_RATE_CLICKED, true);
            editor.commit();
        }
    }


    private static void showRateDialog(Context mContext, Editor editor) {
        String appName = mContext.getString(R.string.appirator_app_title);
        Dialog dialog = new Dialog(mContext);
        if (VERSION.RELEASE.startsWith("1.") || VERSION.RELEASE.startsWith("2.0") || VERSION.RELEASE.startsWith("2.1")) {
            dialog.requestWindowFeature(1);
        } else if (mContext.getResources().getDisplayMetrics().densityDpi == 120 || mContext.getResources().getDisplayMetrics().densityDpi == 160) {
            int rotation = 0;//((WindowManager) mContext.getSystemService("window")).getDefaultDisplay().getRotation();
            if (rotation == 90 || rotation == 270) {
                dialog.requestWindowFeature(1);
            } else {
                dialog.setTitle(String.format(mContext.getString(R.string.rate_title), new Object[]{appName}));
            }
        } else {
            dialog.setTitle(String.format(mContext.getString(R.string.rate_title), new Object[]{appName}));
        }
        LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.appirater, null);
        //((TextView) layout.findViewById(R.id.message)).setText(String.format(mContext.getString(R.string.rate_message), new Object[]{appName}));
        ((TextView) layout.findViewById(R.id.message)).setText(mContext.getString(R.string.rate_message));
        Button rateButton = (Button) layout.findViewById(R.id.rate);
        rateButton.setText(mContext.getString(R.string.rate));
        rateButton.setOnClickListener(new AnonymousClass1(mContext, editor, dialog));
        Button rateLaterButton = (Button) layout.findViewById(R.id.rateLater);
        rateLaterButton.setText(mContext.getString(R.string.rate_later));
        rateLaterButton.setOnClickListener(new AnonymousClass2(editor, dialog));
        Button cancelButton = (Button) layout.findViewById(R.id.cancel);
        cancelButton.setText(mContext.getString(R.string.rate_cancel));
        cancelButton.setOnClickListener(new AnonymousClass3(editor, dialog));
        dialog.setContentView(layout);
        dialog.show();
    }
}
