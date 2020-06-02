package com.example.todoboom;

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
    private OnTaskEventListener mOnTaskEventListener;

    static class MyViewHolder extends RecyclerView.ViewHolder implements    View.OnClickListener {

        TextView textViewTask;
        CheckBox checkBoxIsDone;
        OnTaskEventListener mOnTaskEventListener;

        MyViewHolder(View itemView, OnTaskEventListener mOnTaskEventListener) {
            super(itemView);
            this.textViewTask = itemView.findViewById(R.id.TextViewTask);
            this.checkBoxIsDone = itemView.findViewById(R.id.CheckBoxIsDone);
            this.mOnTaskEventListener = mOnTaskEventListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mOnTaskEventListener.onTodoClick(getAdapterPosition());
        }
    }

    TodoAdapter(ArrayList<Todo> todoList, OnTaskEventListener listener) {
        this.mTodoList = todoList;
        this.mOnTaskEventListener = listener;
    }

    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_task, parent, false);
        return new MyViewHolder(v, mOnTaskEventListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Todo task = mTodoList.get(position);
        holder.checkBoxIsDone.setOnCheckedChangeListener(null);
        holder.checkBoxIsDone.setChecked(task.getIsDone());
        holder.textViewTask.setText(task.getDescription());
        holder.checkBoxIsDone.setEnabled(!task.getIsDone());
        holder.checkBoxIsDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOnTaskEventListener.onCheckedChangedClick(position);
//                if (isChecked) {
//                    buttonView.setEnabled(false);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    void updateListAddItem(ArrayList<Todo> list, int position) {
        mTodoList = list;
        this.notifyItemInserted(position);
    }

    public interface OnTaskEventListener {
        void onTodoClick(int position);
        void onCheckedChangedClick(int position);
    }
}
