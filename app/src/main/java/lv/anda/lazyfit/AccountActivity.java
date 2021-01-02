package lv.anda.lazyfit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import lv.anda.lazyfit.ui.main.SectionsPagerAdapterAcc;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SectionsPagerAdapterAcc sectionsPagerAdapter = new SectionsPagerAdapterAcc(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs2);
        tabs.setupWithViewPager(viewPager);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.ic_recipes) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.ic_calculator) {
                Intent intent1 = new Intent(AccountActivity.this, CalculatorActivity.class);
                startActivity(intent1);
            } else if (id == R.id.ic_search) {
                Intent intent2 = new Intent(AccountActivity.this, SearchActivity.class);
                startActivity(intent2);
            }else if(id==R.id.ic_account){
            }

            return false;
        });
    }
}