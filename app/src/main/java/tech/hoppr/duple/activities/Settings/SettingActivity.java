package tech.hoppr.duple.activities.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.preference.RingtonePreference;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.hoppr.duple.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new WeatherPreferenceFragment())
                .commit();
    }

    public static class WeatherPreferenceFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }




    @Override
    protected void onStart() {
        Log.d("LIFECYCLE", "onStart");
        super.onStart();

    }

    @Override
    protected void onResume() {
        Log.d("LIFECYCLE", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("LIFECYCLE", "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d("LIFECYCLE", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d("LIFECYCLE", "onRestart");
        super.onRestart();
    }


}