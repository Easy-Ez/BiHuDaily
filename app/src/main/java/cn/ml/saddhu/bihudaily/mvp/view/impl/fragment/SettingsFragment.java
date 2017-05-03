package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/2/27.
 * Email static.sadhu@gmail.com
 * Describe: 设置fragment
 */
public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
    SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                    Logger.i("key %s:", key);
                    // listener implementation
                    if (key.equals(getString(R.string.preference_key_clear_cache))) {
                        Preference connectionPref = findPreference(key);
                        // Set summary to be the user-description for the selected value
                        //connectionPref.setSummary(sharedPreferences.getString(key, ""));
                    }
                }
            };


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        Preference checkUpdatePref = findPreference(getString(R.string.preference_key_check_update));
        Preference clearCachePref = findPreference(getString(R.string.preference_key_clear_cache));
        checkUpdatePref.setSummary("xxxxxxx");
        clearCachePref.setOnPreferenceClickListener(this);
        checkUpdatePref.setOnPreferenceClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(listener);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.preference_key_check_update))) {
            Logger.i("check update click");
            Toast.makeText(getActivity(), "check update click", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (preference.getKey().equals(getString(R.string.preference_key_clear_cache))) {
            Logger.i("clear cache click");
            Toast.makeText(getActivity(), "clear cache click", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


}
