package lv.anda.lazyfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        final TextView textViewToChange = findViewById(R.id.textView);
        String value = "Hello, "+sharedPref.getString("Name",null)+ "!";
        textViewToChange.setText(value);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.ic_recipes) {
                Intent intent = new Intent(AccountActivity.this, TabbedActivity.class);
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