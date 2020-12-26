package lv.anda.lazyfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        EditText mName   = findViewById(R.id.editText_name);
        EditText mAge   = findViewById(R.id.editText_age);
        EditText mWeight   = findViewById(R.id.editText_weight);
        EditText mHeight   = findViewById(R.id.editText_height);
        EditText mCalories   = findViewById(R.id.editText_calorie_goal);
        Spinner spinner =  findViewById(R.id.spinner_gender);



        Button btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(v -> {
            String name = mName.getText().toString().trim();
            editor.putString("Name", name);

            int age = Integer.parseInt(mAge.getText().toString());
            editor.putInt("Age", age);

            float weight = Float.parseFloat(mWeight.getText().toString());
            editor.putFloat("Weight", weight);

            float height = Float.parseFloat(mHeight.getText().toString());
            editor.putFloat("Weight", height);

            int calories = Integer.parseInt(mCalories.getText().toString());
            editor.putInt("Calories", calories);

            String gender = spinner.getSelectedItem().toString();
            editor.putString("Gender", gender);

            editor.apply();
            Intent act_main_intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(act_main_intent);
        });
    }
}