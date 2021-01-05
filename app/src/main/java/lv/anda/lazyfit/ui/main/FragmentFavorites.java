package lv.anda.lazyfit.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lv.anda.lazyfit.R;
import lv.anda.lazyfit.RecipeActivity;
import lv.anda.lazyfit.SearchActivity;


public class FragmentFavorites extends Fragment {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayList<String> favourites = new ArrayList<>();
    int row;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        sharedPref = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String URL;
        for(int i=0; i<4 ; i++) {
            switch (i) {
                default:
                case 0:
                    URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Breakfast";
                    break;
                case 1:
                    URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Vegetarian";
                    break;
                case 2:
                    URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Side";
                    break;
                case 3:
                    URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Dessert";
                    break;
            }
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this::saveData, error -> Log.e("Rest Response error", error.toString()));
            requestQueue.add(objectRequest);
        }
        return view;
    }

    private void saveData(Object result) {

        try {
            JSONObject object = new JSONObject(result.toString());
            final LinearLayout detailsTable = (LinearLayout) getView().findViewById(R.id.table_layout_fragFav);
            row = 0;

            for (int i=0; i<object.getJSONArray("meals").length(); i++) {
                JSONObject object2 = new JSONObject(object.getJSONArray("meals").getString(i));

                if(loadArray().contains(object2.getString("idMeal"))){
                    final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
                    tableRow.setId(row);
                    TextView tv;
                    String url = object2.getString("strMealThumb");
                    ImageView imageview = (ImageView)tableRow.findViewById(R.id.imageView);

                    RequestOptions requestOptions = new RequestOptions();

                    Glide.with(FragmentFavorites.this)
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
                        Intent intent = new Intent(getActivity(), RecipeActivity.class);
                        startActivity(intent);
                    });
                    detailsTable.addView(tableRow);
                    row++;
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList loadArray()
    {
        sharedPref = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
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