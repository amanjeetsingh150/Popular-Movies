package com.developers.popularmovies.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developers.popularmovies.R;
import com.developers.popularmovies.util.Constants;

import java.util.prefs.PreferenceChangeListener;

public class SettingActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFrag()).commit();
    }

    public static class PrefFrag extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private SharedPreferences sharedPreferences;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_activity);
            sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES,
                    Context.MODE_PRIVATE);
            bindPreference(findPreference(getString(R.string.sort_key)));
        }

        private void bindPreference(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext())
                    .getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String stringValue = o.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list (since they have separate labels/values).
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    sharedPreferences.edit().putString(getString(R.string.sort_key)
                            , String.valueOf(prefIndex)).apply();
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    }
}
