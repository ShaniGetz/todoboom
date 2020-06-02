package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTaskEventListener{

    private ArrayList<Todo> mTodoList;
    EditText inputField;
    TextView textView;
    Button button;
    private TodoAdapter mAdapter;
    RecyclerView recyclerViewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoList = TodoListManager.getInstance().getAllTodos();

        inputField = findViewById(R.id.input_field);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);
        recyclerViewTasks = findViewById(R.id.recycler_view_tasks);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTasks.setLayoutManager(layoutManager);

        mAdapter = new TodoAdapter(mTodoList,this);
        recyclerViewTasks.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskDescription = String.valueOf(inputField.getText());
                if (taskDescription.length() == 0) {
                    toastMessage("you can't create an empty task");
                } else {
                    addItem(taskDescription);
                }
                inputField.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewTasks.setAdapter(mAdapter);
    }

    public void toastMessage(String message) {
        Toast newToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        newToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        ViewGroup group = (ViewGroup) newToast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(16);
        newToast.show();
    }

    private void addItem(String taskDescription) {
        Date ts = new Timestamp(System.currentTimeMillis());
        String timestampStr = ts.toString();
        Todo task = new Todo(taskDescription, timestampStr, timestampStr);
        TodoListManager.getInstance().addTodo(task);
        mAdapter.updateListAddItem(mTodoList, mTodoList.size() - 1);
    }

    @Override
    public void onTodoClick(int position) {
        Todo task = mTodoList.get(position);
        if(!task.getIsDone()){
            openNotCompletedTodoActivity(task.getId());
        }
        else {
            openCompletedTodoActivity(task.getId(),position);
        }
    }

    private void openNotCompletedTodoActivity(String taskId) {
        Intent intent = new Intent(this, NotCompletedTodoActivity.class);
        intent.putExtra("taskId", taskId);
        startActivityForResult(intent, 1);
    }

    private void openCompletedTodoActivity(String taskId, int position) {
        Intent intent = new Intent(this, CompletedTodoActivity.class);
        intent.putExtra("taskId", taskId);
        intent.putExtra("taskPos", position);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String todoId = data.getStringExtra("todoId");
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                boolean isUpdate = data.getBooleanExtra("updateTodoDescription", false);
                boolean isMarkAsDone = data.getBooleanExtra("markTodoAsDone", false);
                if(isUpdate){
                    updateTodoDescription(todoId);
                }
                if (isMarkAsDone) {
                    markAsDone(todoId);
                }
            }
        }if (requestCode == 2){
            if(resultCode == RESULT_OK){
                boolean isUnmarkAsDone = data.getBooleanExtra("unmarkTodoAsDone", false);
                boolean todoIsDelete = data.getBooleanExtra("todoIsDelete", false);
                if(isUnmarkAsDone){
                    unmarkAsDone(todoId);
                }
                if(todoIsDelete){
                    deleteTodo(todoId);
                }
            }
        }
    }

    private void updateTodoDescription(String taskId){
        Todo task = TodoListManager.getInstance().getTodoFromId(taskId);
        String updateMessage = "TODO " + task.getDescription() +  " is now updated!";
        toastMessage(updateMessage);
    }

    private void markAsDone(String taskId){
        Todo task = TodoListManager.getInstance().getTodoFromId(taskId);
        String doneMessage = "TODO " + task.getDescription() +  " is now done. BOOM!";
        toastMessage(doneMessage);
    }

    private void unmarkAsDone(String taskId){
        Todo task = TodoListManager.getInstance().getTodoFromId(taskId);
        String doneMessage = "TODO " + task.getDescription() +  " is NOT DONE!";
        toastMessage(doneMessage);
    }

    private void deleteTodo(String taskId){
        Todo task = TodoListManager.getInstance().getTodoFromId(taskId);
        String doneMessage = "TODO task is DELETE";
        toastMessage(doneMessage);
    }

    @Override
    public void onCheckedChangedClick(int position){
        Todo task = mTodoList.get(position);
        String id = task.getId();
        Date ts = new Timestamp(System.currentTimeMillis());
        task.setEditTimestamp(ts.toString());
        if(!task.getIsDone()){
            markAsDone(id);
            Todo newTodo = task;
            newTodo.setIsDone(true);
            TodoListManager.getInstance().updateTodo(task, newTodo);
        }
        else {
            task.setIsDone(false);
            unmarkAsDone(id);
            Todo newTodo = task;
            newTodo.setIsDone(false);
            TodoListManager.getInstance().updateTodo(task, newTodo);
        }
    }
}
