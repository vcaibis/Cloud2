package com.example.vincent.afinal.Activities;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.example.vincent.afinal.R;

/**
 * Created by Vincent on 23.11.2016.
 */

public class LanguageFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de la feuille XML préférences
        addPreferencesFromResource(R.xml.preferences);
    }
}
