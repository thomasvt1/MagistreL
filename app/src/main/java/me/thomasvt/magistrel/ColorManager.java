package me.thomasvt.magistrel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import thomasvt.me.magistre.R;

public class ColorManager {

    public ColorManager(Context context){
        this.activity = (Activity) context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    static SharedPreferences preferences;
    private Activity activity;

    public void colorChosen() {
        RadioGroup g = (RadioGroup) activity.findViewById(R.id.colorGroup);
        int selected = g.getCheckedRadioButtonId();
        RadioButton b = (RadioButton) activity.findViewById(selected);
        String color = b.getText().toString().toLowerCase();
        Log.i("ColorChooser", "" + color);

        preferences.edit().putString("color", color).apply();
        applyColor(true, null);
    }

    void applyColor(boolean close, String color) {
        if (color == null)
            color = preferences.getString("color", "blauw");

        if (color.matches("blauw") || color.matches("blue")) {
            activity.setTheme(R.style.BlueTheme);
        }
        else if (color.matches("paars") || color.matches("purple")) {
            activity.setTheme(R.style.PurpleTheme);
        }
        else if (color.matches("rood") || color.matches("red")) {
            activity.setTheme(R.style.RedTheme);
        }
        else if (color.matches("groen") || color.matches("green")) {
            activity.setTheme(R.style.GreenTheme);
        }
        else if (color.matches("grijsblauw") || color.matches("grey blue")) {
            activity.setTheme(R.style.BlueGreyTheme);
        }
        else if (color.matches("lichtblauw") || color.matches("light blue")) {
            activity.setTheme(R.style.LightBlueTheme);
        }
        else if (color.matches("indigo") || color.matches("indigo")) {
            activity.setTheme(R.style.IndigoTheme);
        }
        else if (color.matches("geel") || color.matches("yellow")) {
            activity.setTheme(R.style.YellowTheme);
        }
        else if (color.matches("oranje") || color.matches("orange")) {
            activity.setTheme(R.style.OrangeTheme);
        }

        if (close)
            activity.startActivity(new Intent(activity, OptionActivity.class));
    }
}
