package me.thomasvt.magistrel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import thomasvt.me.magistre.R;

public class ColorManager {

    public ColorManager(Context context){
        this.context = context;
        this.activity = (Activity) context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    static SharedPreferences preferences;
    private Context context;
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

    void previewClicked(View v) {
        RadioGroup g = (RadioGroup) activity.findViewById(R.id.colorGroup);
        int selected = g.getCheckedRadioButtonId();
        RadioButton b = (RadioButton) activity.findViewById(selected);
        String color = b.getText().toString().toLowerCase();
        Log.i("ColorChooser", "" + color);

        applyColor(false, color);

        //activity.setContentView(R.layout.activity_color);
        View vg = activity.findViewById (R.id.action_color);
        vg.invalidate();
    }

    void applyColor(boolean close, String color) {
        if (color == null)
            color = preferences.getString("color", "blauw");

        if (color.matches("blauw")) {
            activity.setTheme(R.style.BlueTheme);
        }
        else if (color.matches("paars")) {
            activity.setTheme(R.style.PurpleTheme);
        }
        else if (color.matches("rood")) {
            activity.setTheme(R.style.RedTheme);
        }
        else if (color.matches("groen")) {
            activity.setTheme(R.style.GreenTheme);
        }
        else if (color.matches("grijsblauw")) {
            activity.setTheme(R.style.BlueGreyTheme);
        }
        else if (color.matches("lichtblauw")) {
            activity.setTheme(R.style.LightBlueTheme);
        }
        else if (color.matches("indigo")) {
            activity.setTheme(R.style.IndigoTheme);
        }
        else if (color.matches("geel")) {
            activity.setTheme(R.style.YellowTheme);
        }
        else if (color.matches("oranje")) {
            activity.setTheme(R.style.OrangeTheme);
        }

        if (close)
            activity.startActivity(new Intent(activity, OptionActivity.class));
    }
}
