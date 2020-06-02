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
    String todoTaskId;
    Todo todoTask;
    Intent intentBack1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_completed_todo);
        Intent intentCreatedMe = getIntent();
        todoTaskId = intentCreatedMe.getStringExtra("taskId");
        todoTask = TodoListManager.getInstance().getTodoFromId(todoTaskId);
        setMyContentView();
        intentBack1 = new Intent();
        intentBack1.putExtra("todoId", todoTaskId);
        setResult(RESULT_OK, intentBack1);
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
        textViewTodo.setText(newDescription);
        inputFieldEdit.setText(newDescription);
        Date ts = new Timestamp(System.currentTimeMillis());
        textViewLastModified.setText(ts.toString());
        Todo newTodo = new Todo(newDescription, todoTask.getCreationTimestamp(), ts.toString());
        TodoListManager.getInstance().updateTodo(todoTask, newTodo);
        todoTask = newTodo;
        intentBack1.putExtra("updateTodoDescription", true);
        setResult(RESULT_OK, intentBack1);
    }

    public void markAsDoneButtonOnClick(View view){
        Date ts = new Timestamp(System.currentTimeMillis());
        todoTask.setEditTimestamp(ts.toString());
        Todo newTodo = todoTask;
        newTodo.setIsDone(true);
        TodoListManager.getInstance().updateTodo(todoTask, newTodo);
        intentBack1.putExtra("markTodoAsDone", true);
        setResult(RESULT_OK, intentBack1);
        finish();
    }
}
