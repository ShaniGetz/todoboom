package com.example.todoboom;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    private ArrayList<Todo> mTodoList;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTask;
        CheckBox checkBoxIsDone;

        MyViewHolder(View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.TextViewTask);
            checkBoxIsDone = itemView.findViewById(R.id.CheckBoxIsDone);
        }
    }

    TodoAdapter() {
        this.mTodoList = new ArrayList<>();
    }

    TodoAdapter(ArrayList<Todo> todoList) {
        this.mTodoList = todoList;
    }

    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Todo task = mTodoList.get(position);
        holder.checkBoxIsDone.setOnCheckedChangeListener(null);
        holder.checkBoxIsDone.setChecked(task.getIsDone());
        holder.textViewTask.setText(task.getDescription());
        holder.checkBoxIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTodoList.get(holder.getAdapterPosition()).setIsDone(isChecked);
                    buttonView.setEnabled(false);

//                    MainActivity.
//                    ;final Toast toastTaskIsDone = Toast.makeText(, "", Toast.LENGTH_SHORT);
                    // todo: Notify by toast "TODO <user's string> is now DONE. BOOM!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    void addItem(Todo item) {
        mTodoList.add(item);
        this.notifyItemInserted(mTodoList.size() - 1);
    }

    public ArrayList<Todo> getmTodoList(){
        return mTodoList;
    }

//    private void callEditCompletedTodoItemActivity(int position) {
//        Intent intent = new Intent(getApplicationContext(), EditCompletedTodoItemActivity.class);
//        intent.putExtra("position", position);
//        startActivity(intent);
//    }
}
