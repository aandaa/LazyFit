package lv.anda.lazyfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int id = item.getItemId();
            if (id == R.id.ic_recipes) {
                Intent intent = new Intent(CalculatorActivity.this, TabbedActivity.class);
                startActivity(intent);
            } else if (id == R.id.ic_calculator) {
            } else if (id == R.id.ic_search) {

                Intent intent2 = new Intent(CalculatorActivity.this, SearchActivity.class);
                startActivity(intent2);
            }else if(id==R.id.ic_account){
                Intent intent3 = new Intent(CalculatorActivity.this, AccountActivity.class);
                startActivity(intent3);
            }


            return false;
        });
    }
}