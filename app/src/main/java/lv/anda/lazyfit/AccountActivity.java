package lv.anda.lazyfit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lv.anda.lazyfit.ui.main.SectionsPagerAdapterAcc;

public class AccountActivity extends AppCompatActivity {
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SectionsPagerAdapterAcc sectionsPagerAdapter = new SectionsPagerAdapterAcc(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);

        List<String> listItems = new ArrayList<String>();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.ic_recipes) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
            } else if (id == R.id.ic_calculator) {
                Intent intent1 = new Intent(AccountActivity.this, CalculatorActivity.class);
                startActivity(intent1);
                this.finish();
            } else if (id == R.id.ic_search) {
                Intent intent2 = new Intent(AccountActivity.this, SearchActivity.class);
                startActivity(intent2);
                this.finish();
            }else if(id==R.id.ic_account){
            }

            return false;
        });
    }
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}