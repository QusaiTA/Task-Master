package com.example.myapplication;


import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> taskList = new ArrayList<>();


    public TaskAdapter(List<Task> taskList,Context context) {
        this.taskList = taskList;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        public Task task;

        View itemView;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

        }
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {

        taskViewHolder.task = taskList.get(position);
        TextView title = taskViewHolder.itemView.findViewById(R.id.titleTextView);
        TextView body = taskViewHolder.itemView.findViewById(R.id.bodyTextView);
        TextView state = taskViewHolder.itemView.findViewById(R.id.stateTextView);

        title.setText(taskViewHolder.task.title);
        body.setText(taskViewHolder.task.body);
        state.setText(taskViewHolder.task.state);


        taskViewHolder.itemView.findViewById(R.id.layout).setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),TaskDetails.class);
            intent.putExtra("title",taskList.get(position).title);
            intent.putExtra("body",taskList.get(position).body);
            intent.putExtra("state",taskList.get(position).state);
            view.getContext().startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
