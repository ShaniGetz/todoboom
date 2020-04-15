package com.example.todoboom;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder>{

    private ArrayList<Todo> mTodoList;
    private OnDoneListener mOnDoneListener;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTask;
        CheckBox checkBoxIsDone;

        MyViewHolder(View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.TextViewTask);
            checkBoxIsDone = itemView.findViewById(R.id.CheckBoxIsDone);
        }
    }

    TodoAdapter(ArrayList<Todo> todoList, OnDoneListener listener) {
        this.mTodoList = todoList;
        this.mOnDoneListener = listener;
    }

    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Todo task = mTodoList.get(position);
        holder.checkBoxIsDone.setOnCheckedChangeListener(null);
        holder.checkBoxIsDone.setChecked(task.getIsDone());
        holder.textViewTask.setText(task.getDescription());
        holder.checkBoxIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setEnabled(false);
                    mOnDoneListener.markedAsDone(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    void updateList(ArrayList<Todo> list, int position) {
        mTodoList = list;
        this.notifyItemInserted(position);
    }

    public interface OnDoneListener {
        void markedAsDone(int position);
    }
}
