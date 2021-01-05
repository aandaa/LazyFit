package lv.anda.lazyfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayList<String> favourites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        int i = sharedPref.getInt("ID", 0);
        String s = i+ "";

        Button add = (Button) findViewById(R.id.btn_add);

        if(loadArray().contains(i+"")){
            add.setText("-");
        }

        add.setOnClickListener(v -> {
            if(add.getText().equals("+")){
                if(!favourites.contains(i+"")){
                    favourites.add(i+"");
                    add.setText("-");
                    saveArray();
                    Log.d("Added: ", i+"");
                }
            } else if(add.getText().equals("-")) {
                if(favourites.contains(i+"")){
                    favourites.remove(favourites.indexOf(i+""));
                    add.setText("+");
                    saveArray();
                    Log.d("Removed: ", i+"");
                }
            } else{
                Log.e("Favourites array", "Favourites array has encountered an error");
            }
        });

        String URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="+ s;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this::saveData, error -> Log.e("Error", error.toString()));
        requestQueue.add(objectRequest);
    }
    private void saveData(Object result) {
        try {
            JSONObject object = new JSONObject(result.toString());
            JSONObject object2 = new JSONObject(object.getJSONArray("meals").getString(0));
            //text.setText(object2.getString("strMeal"));

           TextView textView2 = (TextView) findViewById(R.id.recipe_name);
            textView2.setText(object2.getString("strMeal"));
            //Log.e("Instructions", object2.getString("strInstructions"));

            TextView textView5 = (TextView) findViewById(R.id.recipe_instructions);
            textView5.setText(object2.getString("strInstructions"));

            String url = object2.getString("strMealThumb");
            ImageView imageview = (ImageView)findViewById(R.id.recipe_image);


            RequestOptions requestOptions = new RequestOptions();

            Glide.with(RecipeActivity.this)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageview);

            final TableLayout detailsTable = (TableLayout) findViewById(R.id.table_layout1);
            TableRow tableRow;

            for(int i=1; i<21; i++){
                if(object2.getString("strIngredient"+i).equals("") ||object2.getString("strIngredient"+i).equals("null") ){
                    break;
                }

                tableRow = (TableRow) getLayoutInflater().inflate(R.layout.ingredientsrows, null);
                    TextView tv;
                    tv = (TextView) tableRow.findViewById(R.id.recipe_ingredients);
                    tv.setText(object2.getString("strIngredient"+i));
                    TextView tv1;
                    tv1 = (TextView) tableRow.findViewById(R.id.recipe_amount);
                    tv1.setText(object2.getString("strMeasure"+i));
                    detailsTable.addView(tableRow);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveArray()
    {
        sharedPref = this.getSharedPreferences("myKey", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("Favourites_size", favourites.size());

        for(int i=0;i<favourites.size();i++)
        {
            editor.remove("Fav_" + i);
            editor.putString("Fav_" + i, favourites.get(i));
        }
        editor.apply();
        return;
    }

    public ArrayList loadArray()
    {
        sharedPref = this.getSharedPreferences("myKey", Context.MODE_PRIVATE);
        favourites.clear();
        int size = sharedPref.getInt("Favourites_size", 0);
        if (size!=0){
            for(int i=0;i<size;i++)
            {
                favourites.add(sharedPref.getString("Fav_" + i, ""));
            }
        }
        return favourites;
    }
}