package lv.anda.lazyfit;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class GlobalActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean("notFirstTime",false);
        Intent intent;
        if (value)
        {
            intent = new Intent(this, MainActivity.class);
        }
        else {
            intent = new Intent(this, StartActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}