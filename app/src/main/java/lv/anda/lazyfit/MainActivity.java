package lv.anda.lazyfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set that user has opened main activity
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("notFirstTimee", true);
        editor.apply();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.ic_recipes) {
            } else if (id == R.id.ic_calculator) {
                Intent intent1 = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(intent1);
            } else if (id == R.id.ic_search) {
                Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent2);
            }else if(id==R.id.ic_account){
                Intent intent3 = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent3);
            }

            return false;
        });
    }
}