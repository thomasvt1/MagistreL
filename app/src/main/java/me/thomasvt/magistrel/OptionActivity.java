package me.thomasvt.magistrel;

import android.app.Activity;
import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import me.thomasvt.magistrel.model.OptionManager;
import thomasvt.me.magistre.R;


public class OptionActivity extends Activity {

    static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ColorManager(this).applyColor(false, null);
        setContentView(R.layout.activity_country);

        pageOpen = false;

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        OptionAdapter mAdapter = new OptionAdapter(OptionManager.getInstance().getOpties(), R.layout.row_country, this);
        mRecyclerView.setAdapter(mAdapter);

        checkFirstTime();
        requestBackup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.getItemId() == R.id.action_settings) {
            //startBrowser(actionUrl("firsttime"), true);
            setContentView(R.layout.activity_welcome);
            return true;
        }
        if (item.getItemId() == R.id.action_reload) {
            if (mWebView != null)
                mWebView.reload();
            else
                showError( getResources().getString(R.string.open_page));
            return true;
        }
        if (item.getItemId() == R.id.action_clear) {
            if (mWebView != null) {
                mWebView.clearCache(true);
                mWebView.reload();
            } else
                showError( getResources().getString(R.string.open_page));
            return true;
        }
        if (item.getItemId() == R.id.action_turbo) {
            setContentView(R.layout.activity_speed);
            final Switch turbo = (Switch) findViewById(R.id.turboSwitch);
            turbo.setChecked(preferences.getBoolean("turboMode", false));
            return true;
        }
        if (item.getItemId() == R.id.action_about) {
            setContentView(R.layout.activity_about);
            return true;
        }
        if (item.getItemId() == R.id.action_color) {
            setContentView(R.layout.activity_color);
            return true;
        }
        if (item.getItemId() == R.id.action_advanced) {
            setContentView(R.layout.activity_advanced);
            new Advanced(this).fillBlanks();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    void showError(String error) {
        Toast errorToast;
        errorToast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
        errorToast.show();
    }

    @Override
    public void onBackPressed() {
        if (mWebView == null) {
            Log.i("BackPressed", "3");
            startActivity(new Intent(getApplication(), OptionActivity.class));
        }
        else if (mWebView.canGoBack()) {
            if (mWebView.getUrl().contains("agenda" + "/20")) {
                startActivity(new Intent (getApplication(), OptionActivity.class));
                pageOpen = false;
            }

            Log.i("BackPressed", "2");
            mWebView.goBack();
        }
        else if (pageOpen) {
            Log.i("BackPressed", "1");
            pageOpen = false;
            startActivity(new Intent(getApplication(), OptionActivity.class));
        }
        else {
            Log.i("BackPressed", "4");
            super.onBackPressed();
        }
    }

    public void clickEvent(View v) {
        TextView t = (TextView) v.findViewById(R.id.countryName);

        Log.i("App", "App, " + t.getText().toString());
        startBrowser(actionUrl(t.getText().toString()), false);
    }

    public void requestBackup() {
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
    }

    private WebView mWebView;
    boolean pageOpen = false;

    String actionUrl(String action) {
        Log.i("urlAction", action);
        String school = preferences.getString("schoolURL", "Segbroek");
        String url = "https://" + school + ".magister.net/magister/#/";
        Log.i("urlAction", url);
        if (action.toLowerCase().matches("agenda"))
            url = url + "agenda";

        else if (action.toLowerCase().matches("huiswerk"))
            url = url + "agenda/huiswerk";

        else if (action.toLowerCase().matches("cijfers"))
            url = url + preferences.getString("cijfers", "cijfers"); //Advanced settings for *some* schools

        else if (action.toLowerCase().matches("aanwezigheid"))
            url = url + preferences.getString("aanwezigheid", "aanwezig"); //Advanced settings for *some* schools

        else if (action.toLowerCase().matches("berichten"))
            url = url + preferences.getString( "berichten", "berichten"); //Advanced settings for *some* schools

        else if (action.toLowerCase().matches("elo"))
            url = url + preferences.getString("elo", "vandaag/ELO"); //Advanced settings for *some* schools

        else if (action.toLowerCase().matches("firsttime"))
            url = "https://" + school + ".magister.net/";
        return url;
    }

    void checkFirstTime() {
        boolean firstStart = preferences.getBoolean("firstStart", true);
        if (firstStart) {
            Log.i("firstStart", "This is the first start!");
            setContentView(R.layout.activity_welcome);
        }
    }

    public void saveTurbo(View v) {
        final Switch turbo = (Switch) findViewById(R.id.turboSwitch);

        Log.i("TurboSwitch", "isChecked: " + turbo.isChecked());
        preferences.edit().putBoolean("turboMode", turbo.isChecked()).apply();

        startActivity(new Intent(this, OptionActivity.class));
        //setContentView(R.layout.activity_country);
    }

    public void colorChosen(View v) {
        new ColorManager(this).colorChosen();
    }

    public void saveAdvanced(View v) {
        new Advanced(this).saveOptions();
    }

    public void welcomeFinished(View v) {
        final EditText text = (EditText) findViewById(R.id.schoolName);

        String schoolName = text.getEditableText().toString();
        if (schoolName.length() <= 3)
            return;
        Log.i("schoolName", schoolName);
        preferences.edit().putBoolean("firstStart", false).apply();
        preferences.edit().putString("schoolURL", schoolName).apply();

        startBrowser(actionUrl("firstTime"), true);
    }

    void startBrowser(String url, boolean firstTime) {
        pageOpen = true;
        boolean turboMode = preferences.getBoolean("turboMode", false);
        mWebView = new WebView(this);

        setContentView(R.layout.activity_browser);

        mWebView = (WebView) findViewById(R.id.webView);

        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.setAcceptFileSchemeCookies(true);

        mWebView.setVerticalScrollBarEnabled(true);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        if (turboMode || !firstTime)
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);


        mWebView.setWebViewClient(new MyWebViewClient(this));
        mWebView.setWebChromeClient(new MyChromeClient(this));

        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mWebView.goBack();
                startActivity(i);
            }
        });

        mWebView.loadUrl(url);
    }
}
