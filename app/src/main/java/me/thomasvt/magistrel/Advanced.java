package me.thomasvt.magistrel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;

import thomasvt.me.magistre.R;

public class Advanced {

    public Advanced(Context context){
        this.activity = (Activity) context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    static SharedPreferences preferences;
    private Activity activity;

    public void fillBlanks() {
        final EditText cijfers = (EditText) activity.findViewById(R.id.adv_cijfers);
        cijfers.setText(preferences.getString("cijfers", "cijfers"));

        final EditText aanwezigheid = (EditText) activity.findViewById(R.id.adv_aanwezigheid);
        aanwezigheid.setText(preferences.getString("aanwezigheid", "aanwezig"));

        final EditText berichten = (EditText) activity.findViewById(R.id.adv_berichten);
        berichten.setText(preferences.getString("berichten", "berichten"));

        final EditText elo = (EditText) activity.findViewById(R.id.adv_elo);
        elo.setText(preferences.getString("elo", "vandaag/ELO"));
    }

    public void saveOptions() {
        final EditText cijfers = (EditText) activity.findViewById(R.id.adv_cijfers);
        final EditText aanwezigheid = (EditText) activity.findViewById(R.id.adv_aanwezigheid);
        final EditText berichten = (EditText) activity.findViewById(R.id.adv_berichten);
        final EditText elo = (EditText) activity.findViewById(R.id.adv_elo);

        preferences.edit().putString("cijfers", cijfers.getText().toString());
        preferences.edit().putString("aanwezigheid", aanwezigheid.getText().toString());
        preferences.edit().putString("berichten", berichten.getText().toString());
        preferences.edit().putString("elo", elo.getText().toString());

        activity.startActivity(new Intent(activity, OptionActivity.class));
    }
}
