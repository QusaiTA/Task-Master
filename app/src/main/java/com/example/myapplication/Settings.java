package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
public class Settings extends AppCompatActivity {
    RadioGroup submitRadio;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        findViewById(R.id.button4).setOnClickListener(view -> {
            TextView textView = findViewById(R.id.userNameLabel);
            String name = textView.getText().toString();
            editor.putString("username",name);
            editor.apply();
        });

        submitRadio=findViewById(R.id.apply);
//        teamText=findViewById(R.id.textView10);
        Button applyTeam=findViewById(R.id.teamApply);
        applyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                RadioButton One = findViewById(R.id.radioButton4);
                RadioButton Two = findViewById(R.id.radioButton5);
                RadioButton Three = findViewById(R.id.radioButton6);

                if (One.isChecked()){
                    sharedPreferencesEditor.putString("team", One.getText().toString());
                }else if(Two.isChecked()){
                    sharedPreferencesEditor.putString("team", Two.getText().toString());
                }else if(Three.isChecked()){
                    sharedPreferencesEditor.putString("team", Three.getText().toString());
                }

                sharedPreferencesEditor.apply();


                Intent toHome = new Intent(Settings.this,MainActivity.class);
                startActivity(toHome);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent   = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}