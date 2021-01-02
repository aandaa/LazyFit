package lv.anda.lazyfit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import lv.anda.lazyfit.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean("notFirstTime", true);
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