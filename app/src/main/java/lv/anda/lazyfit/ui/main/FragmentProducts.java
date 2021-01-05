package lv.anda.lazyfit.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import lv.anda.lazyfit.R;


public class FragmentProducts extends Fragment {

    SharedPreferences sharedPref;

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static FragmentProducts instance = null;

    public static FragmentProducts getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_products, container, false);
        instance = this;

        loadArray();
        ListView lv = vi.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        Button but = vi.findViewById(R.id.btnProd);
        but.setOnClickListener(v -> {

            DialogFragment dialog = FragmentDialog.newInstance();
            dialog.show(getFragmentManager(), "dialog");
        });
        return vi;
    }

    public ArrayList loadArray()
    {
        sharedPref = this.getActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE);
        list.clear();
        int size = sharedPref.getInt("Products_size", 0);
        if(size!= 0){
            for(int i=0;i<size;i++)
            {
                list.add(sharedPref.getString("Product_" + i, ""));
            }
        }
        return list;
    }

    public void refresh(){
        loadArray();
        adapter.notifyDataSetChanged();
    }
}