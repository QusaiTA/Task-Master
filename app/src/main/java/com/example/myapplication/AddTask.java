package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.room.Room;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;


public class AddTask extends AppCompatActivity {

    private TaskDao taskDao;
    private AppDataBase appDataBase;

    RadioGroup submitRadio;
    RadioButton radioButton;
    RadioButton One;
    RadioButton Two;
    RadioButton Three;
    List<Team> teams=new ArrayList<>();
    TextView teamText;
    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Team.class),
                response -> {
                    for (Team todo : response.getData()) {
                        teams.add(todo);
                        System.out.println(teams);
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

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



//                 team = Team.builder().teamName("QusaiTeam").build();


                textView.setText("Task Count :" + count++);
                com.amplifyframework.datastore.generated.model.Task todo = Task.builder().title(taskTitleVal)
                        .team(team)
                        .body(taskBodyVal)
                        .state(taskStateVal)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );


            }
        });
        submitRadio=findViewById(R.id.groub);
//        teamText=findViewById(R.id.teamHomePage);
        Button applyTeam=findViewById(R.id.addTeam);
        applyTeam.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
//                int radioGroup = submitRadio.getCheckedRadioButtonId();
                One = findViewById(R.id.radioButton);
                Two = findViewById(R.id.radioButton2);
                Three = findViewById(R.id.radioButton3);
//                teamText.setText("You Choice is: " + radioButton.getText());

                String name="";

                if(One.isChecked()){
                    name="One";
                }else if (Two.isChecked()){
                    name="Two";
                }else if (Three.isChecked()){
                    name="Three";
                }

                team=null;
                for(int i=0;i<teams.size();i++){
                    if(teams.get(i).getTeamName().equals(name)){
                        team=teams.get(i);
                    }
                }

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