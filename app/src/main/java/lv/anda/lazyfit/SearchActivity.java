package lv.anda.lazyfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class SearchActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        editor = sharedPref.edit();
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
        TextView text_search = (TextView) findViewById(R.id.text_search);
        final LinearLayout detailsTable = (LinearLayout) findViewById(R.id.table_layout_search);
        ImageButton search = (ImageButton) findViewById(R.id.imageBtn_search);
        String search_default = getRandom();
        if(search_default!=null){

            String URL = "https://www.themealdb.com/api/json/v1/1/filter.php?i="+search_default;
            RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> saveData(response), error -> Log.e("Rest Response error", error.toString()));
            requestQueue.add(objectRequest);
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = text_search.getText().toString();
                if(!(search.isEmpty()) || !(search.equals(""))){
                    if(detailsTable.getChildCount() >0){
                        detailsTable.removeViews(0,  detailsTable.getChildCount());
                    }
                    String URL = "https://www.themealdb.com/api/json/v1/1/filter.php?i="+search;
                    RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> saveData(response), error -> Log.e("Rest Response error", error.toString()));
                    requestQueue.add(objectRequest);
                }

            }

        });


    }

    private void saveData(Object result) {
        try {
            JSONObject object = new JSONObject(result.toString());
            final LinearLayout detailsTable = (LinearLayout) findViewById(R.id.table_layout_search);

                for (int i=0; i<object.getJSONArray("meals").length(); i++) {
                    JSONObject object2 = new JSONObject(object.getJSONArray("meals").getString(i));
                    final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
                    tableRow.setId(i);
                    TextView tv;
                    String url = object2.getString("strMealThumb");
                    ImageView imageview = (ImageView)tableRow.findViewById(R.id.imageView);

                    RequestOptions requestOptions = new RequestOptions();

                    Glide.with(SearchActivity.this)
                            .load(url)
                            .apply(requestOptions)
                            .into(imageview);

                    tv = (TextView) tableRow.findViewById(R.id.tableCell1);
                    tv.setId(i);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setText(object2.getString("strMeal"));
                    tv.setOnClickListener(v -> {

                        int i1 =0;
                        try {
                            String s = object2.getString("idMeal");
                            i1 = Integer.parseInt(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        editor.putInt("ID", i1);
                        editor.apply();
                        Intent intent = new Intent(SearchActivity.this, RecipeActivity.class);
                        startActivity(intent);
                    });
                    detailsTable.addView(tableRow);
                }


        }catch(Exception e)
        {
            e.printStackTrace();
        }


    }
    public String getRandom()
    {
        list.clear();
        int random =0;
        int size = sharedPref.getInt("Products_size", 0);
        if(size!= 0){
             random= new Random().nextInt(size);
            for(int i=0;i<size;i++)
            {
                list.add(sharedPref.getString("Product_" + i, ""));
            }
        }
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(random);
        }
    }

}