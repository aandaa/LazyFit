package lv.anda.lazyfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class SettingsActivity extends AppCompatActivity {
    String name, gender;
    int age, calories;
    float weight, height;
    Button btn_continue;
    EditText mName, mAge, mWeight, mHeight, mCalories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

         mName   = findViewById(R.id.editText_name);
         mAge   = findViewById(R.id.editText_age);
         mWeight   = findViewById(R.id.editText_weight);
         mHeight   = findViewById(R.id.editText_height);
         mCalories   = findViewById(R.id.editText_calorie_goal);
        Spinner spinner =  findViewById(R.id.spinner_gender);

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            validateFields();
        }
    };
        mName.addTextChangedListener(textWatcher);
        mCalories.addTextChangedListener(textWatcher);
        mAge.addTextChangedListener(textWatcher);
        mHeight.addTextChangedListener(textWatcher);
        mWeight.addTextChangedListener(textWatcher);

         btn_continue = findViewById(R.id.btn_continue);
         btn_continue.setEnabled(false);
         btn_continue.setOnClickListener(v -> {
             name = mName.getText().toString();
             age = Integer.parseInt(mAge.getText().toString());
             weight = Float.parseFloat(mWeight.getText().toString());
             height = Float.parseFloat(mHeight.getText().toString());
             calories = Integer.parseInt(mCalories.getText().toString());
             gender = spinner.getSelectedItem().toString();

             editor.putString("Name", name);
         editor.putInt("Age", age);
         editor.putFloat("Weight", weight);
         editor.putFloat("Weight", height);
         editor.putInt("Calories", calories);
         editor.putString("Gender", gender);
         editor.apply();
         Intent act_main_intent = new Intent(getApplicationContext(), MainActivity.class);
         startActivity(act_main_intent);
        });
    }
    protected void validateFields() {
        btn_continue.setEnabled(mName.getText().length() > 0 && mAge.getText().length() > 0 && mCalories.getText().length() > 0 && mHeight.getText().length() > 0 && mWeight.getText().length() > 0);
    }
}