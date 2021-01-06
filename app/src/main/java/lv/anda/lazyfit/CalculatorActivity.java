package lv.anda.lazyfit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalculatorActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    //TextView inputText;
    public static final String mypreference = "mypref";
    public static final String Name = "inputTextKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        //sharedPreferences
        sharedpreferences = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        String name = sharedpreferences.getString("textKey","0");
        TextView label = (TextView) findViewById(R.id.textViewCounter);
        if(name==""|| name==null){
            label.setText("0");
        }else {label.setText(name);}



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int id = item.getItemId();
            if (id == R.id.ic_recipes) {
                Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
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

        Button calculate= findViewById(R.id.btnCalculate);
        calculate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                TextView inputText = (TextView) findViewById(R.id.textViewCounter); //all calories from products
                EditText Protein= (EditText) findViewById(R.id.editTextProtein);
                EditText Grains= (EditText) findViewById(R.id.editTextGrains);
                EditText Vegetables= (EditText) findViewById(R.id.editTextVegetables);
                EditText Fruits= (EditText) findViewById(R.id.editTextFruits);
                EditText Sugar= (EditText) findViewById(R.id.editTextSugar);
                EditText Dairy= (EditText) findViewById(R.id.editTextDairy);
                EditText Fat= (EditText) findViewById(R.id.editTextFat);
                String ProteinString= Protein.getText().toString();
                String GrainsString = Grains.getText().toString();
                String VegetablesString = Vegetables.getText().toString();
                String FruitsString = Fruits.getText().toString();
                String SugarString = Sugar.getText().toString();
                String DairyString = Dairy.getText().toString();
                String FatString = Fat.getText().toString();

                try {

                    Integer ProteinInt = Integer.parseInt(ProteinString.replaceAll("[\\D]",""));
                    Integer GrainInt = Integer.parseInt(GrainsString.replaceAll("[\\D]",""));
                    Integer VegetableInt = Integer.parseInt(VegetablesString.replaceAll("[\\D]",""));
                    Integer FruitsInt = Integer.parseInt(FruitsString.replaceAll("[\\D]",""));
                    Integer SugarInt = Integer.parseInt(SugarString.replaceAll("[\\D]",""));
                    Integer DairyInt = Integer.parseInt(DairyString.replaceAll("[\\D]",""));
                    Integer FatInt = Integer.parseInt(FatString.replaceAll("[\\D]",""));

                    Integer ProteinSum= ProteinInt*4; //1g proteīna = 4 kcal (16.7 kJ)
                    Integer GrainSum= GrainInt*2; //1g šķiedrvielu= 2 kcal
                    Integer VegetablesSum = VegetableInt*2;
                    Integer FruitsSum = FruitsInt*4;
                    Integer SugarSum = SugarInt*4; //1g ogļhidrātu = 4 kcal (17.2 kJ)
                    Integer DairySum =DairyInt*4;
                    Integer FatSum= FatInt*9; //1g tauku = 9 kcal (38.9 kJ)

                    Integer result= ProteinSum+GrainSum+VegetablesSum+FruitsSum+SugarSum+DairySum+FatSum;
                    sharedpreferences = getSharedPreferences("prefID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("textKey", result.toString());
                    // editor.putInt("textKey",value);
                    editor.apply();
                    inputText.setText(result+"");

                }catch (Exception e){
                    inputText.setText("0");
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid characters entered", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        Button remove= findViewById(R.id.btnRemove);
        remove.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView inputText = (TextView) findViewById(R.id.textViewCounter); //all calories from products
                SharedPreferences preferences =getSharedPreferences("prefID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                inputText.setText("0");
                //finish();
            }
        });
    }
}