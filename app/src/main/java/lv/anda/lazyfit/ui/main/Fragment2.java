package lv.anda.lazyfit.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import lv.anda.lazyfit.R;
import lv.anda.lazyfit.RecipeActivity;

public class Fragment2 extends Fragment {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPref = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Vegetarian";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this::saveData, error -> Log.e("Rest Response error", error.toString()));
        requestQueue.add(objectRequest);

        return inflater.inflate(R.layout.fragment2_layout, container, false);
    }

    private void saveData(Object result) {
        try {
            JSONObject object = new JSONObject(result.toString());
            //text.setText(object2.getString("strMeal"));
            //Log.e("Instructions", object2.getString("strInstructions"));

            final TableLayout detailsTable = (TableLayout) getView().findViewById(R.id.table_layout_frag2);

            for (int i=0; i<object.getJSONArray("meals").length(); i++) {
                JSONObject object2 = new JSONObject(object.getJSONArray("meals").getString(i));
                final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
                tableRow.setId(i);

                TextView tv;
                String url = object2.getString("strMealThumb");
                ImageView imageview = (ImageView)tableRow.findViewById(R.id.imageView);

                RequestOptions requestOptions = new RequestOptions();

                Glide.with(getActivity())
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

            }


        }catch(Exception e)
        {
            e.printStackTrace();
        }



    }
}
