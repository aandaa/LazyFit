package lv.anda.lazyfit;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

public class GlobalActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //if user opens app for the first time open StartActivity, if he has already previously opened the open MainActivity
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean("notFirstTime",false);
        Intent intent;
        if (value)
        {
            //intent = new Intent(this, MainActivity.class);
            intent = new Intent(this, MainActivity.class);
        }
        else {
            intent = new Intent(this, StartActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}