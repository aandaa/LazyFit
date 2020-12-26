package lv.anda.lazyfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.ic_recipes) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.ic_calculator) {
                Intent intent1 = new Intent(SearchActivity.this, CalculatorActivity.class);
                startActivity(intent1);
            } else if (id == R.id.ic_search) {
            }else if(id==R.id.ic_account){
                Intent intent3 = new Intent(SearchActivity.this, AccountActivity.class);
                startActivity(intent3);
            }

            return false;
        });
    }


}