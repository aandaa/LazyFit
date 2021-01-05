package lv.anda.lazyfit.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import lv.anda.lazyfit.R;


public class FragmentDialog extends DialogFragment {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayList<String> listItems = new ArrayList<>();

    public FragmentDialog() {
        // Required empty public constructor
    }

    static FragmentDialog newInstance() {
        FragmentDialog f = new FragmentDialog();
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, null);

        Dialog builder = new Dialog(getActivity());
        builder.setContentView(view);

        return builder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPref = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        String URL = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this::saveData, error -> Log.e("Rest Response error", error.toString()));
        requestQueue.add(objectRequest);

        Button save = (Button) view.findViewById(R.id.btnSave);
        save.setOnClickListener(v -> {
            saveArray();
            dismiss();
            FragmentProducts.getInstance().refresh();
        });
        return view;
    }

    private void saveData(Object result) {
        try {
            JSONObject object = new JSONObject(result.toString());

            final TableLayout detailsTable = getView().findViewById(R.id.table_layout_fragProd);

            // i<object.getJSONArray("meals").length() - var likt arī šo, bet metās errors "Skipped x frames", jo par daudz jānolasa un aizņem pārāk ilgi
            for (int i=0; i<50; i++) {
                JSONObject object2 = new JSONObject(object.getJSONArray("meals").getString(i));
                final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_narrow, null);
                tableRow.setId(i);

                CheckBox cb;
                cb = tableRow.findViewById(R.id.checkBox);
                cb.setId(i);
                cb.setGravity(Gravity.CENTER_VERTICAL);
                cb.setText(object2.getString("strIngredient"));

                if(loadArray().contains(cb.getText().toString())){
                    cb.setChecked(true);
                }

                cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked){
                        if (!listItems.contains(cb.getText().toString())){
                            listItems.add(cb.getText().toString());
                        }
                    } else {
                        if (listItems.contains(cb.getText().toString())){
                            listItems.remove(listItems.indexOf(cb.getText().toString()));
                        }
                    }
                }
                );
                detailsTable.addView(tableRow);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveArray()
    {
        sharedPref = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("Products_size", listItems.size());

        for(int i=0;i<listItems.size();i++)
        {
            editor.remove("Product_" + i);
            editor.putString("Product_" + i, listItems.get(i));
        }
        editor.apply();
        return;
    }

    public ArrayList loadArray()
    {
        sharedPref = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
        listItems.clear();
        int size = sharedPref.getInt("Products_size", 0);
        if (size!=0){
            for(int i=0;i<size;i++)
            {
                listItems.add(sharedPref.getString("Product_" + i, ""));
            }
        }

        return listItems;
    }
}
