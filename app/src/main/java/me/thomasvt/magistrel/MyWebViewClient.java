package me.thomasvt.magistrel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private Context context;

    public MyWebViewClient(Context context){
        this.context = context;
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript: var allLinks = document.getElementsByTagName('a'); if (allLinks) {var i;for (i=0; i<allLinks.length; i++) {var link = allLinks[i];var target = link.getAttribute('target'); if (target && target == '_blank') {link.setAttribute('target','_self');link.href = 'newtab:'+link.href;}}}");
        Log.i("URL", url);
        CookieSyncManager.getInstance().sync();

        if (url.contains(".magister.net/magister/#/vandaag") && url.endsWith("magister/#/vandaag")) {
            Activity a = (Activity) context;
            a.startActivity(new Intent (a, OptionActivity.class));
        }

    }

/*
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("URL", url);
        if (url.contains("/studiewijzers/")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
            view.goBack();
            return false;
        }
        if (url.startsWith("https://segbroek.magister")) {
            Log.i("Loading", "Starts With");
            view.loadUrl(url);
            return true;
        }
        return false;
    }
    */
}