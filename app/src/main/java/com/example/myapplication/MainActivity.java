package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.room.Room;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.AWSDataStorePlugin;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskDao taskDao;
    private AppDataBase appDataBase;
    private List<Task> taskList = new ArrayList<>();
    private List<Team>teams=new ArrayList<>();
    private List<com.amplifyframework.datastore.generated.model.Task> taskList1 = new ArrayList<>();

    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
            }
        });


        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllTask.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.settings).setOnClickListener(view -> {
            Intent settingIntent = new Intent(MainActivity.this, Settings.class);
            startActivity(settingIntent);
        });


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String instName = sharedPreferences.getString("username", "Go and set the Instructor Name");

        TextView welcome = findViewById(R.id.welcomeMsg);
        welcome.setText(instName + " : Task");

        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        SharedPreferences sharedPreferences1=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String team=sharedPreferences1.getString("team","team");


        RecyclerView AllTasks = findViewById(R.id.taskRecycler);
        AllTasks.setLayoutManager(new LinearLayoutManager(this));

        AllTasks.setAdapter(new TaskAdapter(taskList, this));

        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                AllTasks.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team todo : response.getData()) {
//                       Task taskOrg = new Task(todo.getTitle(),todo.getBody(),todo.getState());
//                        Log.i("graph testing", todo.getTitle());
                        teams.add(todo);
                    }
                    for (int i = 0; i < teams.size(); i++) {
                        if (teams.get(i).getTeamName().equals(team)){
                            taskList1.addAll(teams.get(i).getTasks());
                        }
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        AllTasks.setAdapter(new TaskAdapter(taskList,this));
        AllTasks.setLayoutManager(new LinearLayoutManager(this));
        String team1 = sharedPreferences.getString("team", "team");
        TextView teamName = findViewById(R.id.teamHomePage);
        teamName.setText(team1);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String instName = sharedPreferences.getString("username", "Go and set the Instructor Name");
//
//        TextView welcome = findViewById(R.id.welcomeMsg);
//        welcome.setText(instName + " : Task");



    }


}

