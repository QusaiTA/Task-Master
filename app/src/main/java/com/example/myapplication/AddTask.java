package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.room.Room;


public class AddTask extends AppCompatActivity {

    private TaskDao taskDao;
    private AppDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        appDataBase= Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"task").allowMainThreadQueries().build();
        taskDao=appDataBase.taskDao();

        TextView textView = findViewById(R.id.textView6);

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            int count =1;
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                EditText taskTitle =findViewById(R.id.editTextTextPersonName);
                EditText taskBody  =findViewById(R.id.editTextTextPersonName2);
                EditText taskState =findViewById(R.id.editTextTextPersonName3);

                String taskTitleVal =taskTitle.getText().toString();
                String taskBodyVal  =taskBody.getText().toString();
                String taskStateVal =taskState.getText().toString();

                Task task = new Task(taskTitleVal,taskBodyVal,taskStateVal);
                taskDao.insertAll(task);




                textView.setText("Task Count :" + count++);

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent   = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}