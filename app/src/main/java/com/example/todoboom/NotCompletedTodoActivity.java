package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.sql.Timestamp;
import java.util.Date;


public class NotCompletedTodoActivity extends AppCompatActivity {

    TextView textViewTodo;
    EditText inputFieldEdit;
    TextView textViewLastModified;
    TextView textViewWasCreated;
    int todoTaskId;
    Todo todoTask;
    Intent intentBack1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_completed_todo);
        Intent intentCreatedMe = getIntent();
        todoTaskId = intentCreatedMe.getIntExtra("taskId", 0); //pos and not id
//        todoTask = TodoListManager.getInstance().getTodoFromId(todoTaskId);
        todoTask = TodoListManager.getInstance().getAllTodos().get(todoTaskId); //with pos and not id
        setMyContentView();
        intentBack1 = new Intent();
        intentBack1.putExtra("todoId", todoTaskId); //pos and not id
    }

    private void setMyContentView() {
        textViewTodo = findViewById(R.id.todo);
        textViewTodo.setText(todoTask.getDescription());
        textViewWasCreated = findViewById(R.id.was_created);
        textViewWasCreated.setText(todoTask.getCreationTimestamp());
        textViewLastModified = findViewById(R.id.last_modified);
        textViewLastModified.setText(todoTask.getEditTimestamp());
        inputFieldEdit = findViewById(R.id.input_field_edit);
        inputFieldEdit.setText(todoTask.getDescription());
    }

    public void applyEditButtonOnClick(View view){
        String newDescription = String.valueOf(inputFieldEdit.getText());
        todoTask.setDescription(newDescription);
        textViewTodo.setText(newDescription);
        inputFieldEdit.setText(newDescription);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = ts;
        todoTask.setEditTimestamp(date.toString());
        textViewLastModified.setText(date.toString());
        intentBack1.putExtra("updateTodoDescription", true);
        setResult(RESULT_OK, intentBack1);
        TodoListManager.getInstance().saveData(getApplicationContext());
    }

    public void markAsDoneButtonOnClick(View view){
        todoTask.setIsDone(true);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = ts;
        todoTask.setEditTimestamp(date.toString());
        intentBack1.putExtra("markTodoAsDone", true);
        setResult(RESULT_OK, intentBack1);
        TodoListManager.getInstance().saveData(getApplicationContext());
        finish();
    }
}
