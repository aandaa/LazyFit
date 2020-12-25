package lv.anda.lazyfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn_main_act = findViewById(R.id.btn_start);
        btn_main_act.setOnClickListener(v -> {
            Intent act_main_intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(act_main_intent);
        });

        Button btn_about = findViewById(R.id.btn_about);
        btn_about.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);

            builder.setTitle("About us");
            builder.setMessage(R.string.app_text)
                    .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawableResource(R.color.light_orange);
        });

    }
}