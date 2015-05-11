package me.thomasvt.magistrel;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import thomasvt.me.magistre.R;

public class MyChromeClient extends WebChromeClient {

    public MyChromeClient(Context context){
        this.context = context;
    }

    private Context context;

    public void onProgressChanged(WebView view, int progress) {
        final String appName = context.getResources().getString(R.string.app_name);
        final Activity activity = (Activity) context;

        activity.setTitle(appName + " Laden... " + progress + "%"); //Make the bar disappear after URL is loaded, and changes string to Loading...
        activity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

        if(progress == 100) //Restore app name
            activity.setTitle(appName);
    }
}
