package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toolbar;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String body=intent.getStringExtra("body");
        String state=intent.getStringExtra("state");

        TextView textView= findViewById(R.id.titleFinish);
        textView.setText(title);
        TextView textView2= findViewById(R.id.bodyFinish);
        textView2.setText(body);
        TextView textView3= findViewById(R.id.stateFinish);
        textView3.setText(state);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent   = new Intent(TaskDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}