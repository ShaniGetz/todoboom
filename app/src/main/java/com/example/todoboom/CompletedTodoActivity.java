package com.example.todoboom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Date;

public class CompletedTodoActivity extends AppCompatActivity implements DeleteTodoItemDialog.DeleteTodoItemDialogListener {

    TextView textViewTodo;
    String todoTaskId;
    int todoTaskPos;
    Todo todoTask;
    Intent intentBack2;
    private int dialogValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todo);
        dialogValue = -1; // Default value
        Intent intentCreatedMe = getIntent();
        todoTaskId = intentCreatedMe.getStringExtra("taskId");
        todoTaskPos = intentCreatedMe.getIntExtra("taskPos", 0);
        todoTask = TodoListManager.getInstance().getTodoFromId(todoTaskId);
        setMyContentView();
        intentBack2 = new Intent();
        intentBack2.putExtra("todoId", todoTaskId);
        setResult(RESULT_OK, intentBack2);
    }

    private void setMyContentView() {
        textViewTodo = findViewById(R.id.todo);
        textViewTodo.setText(todoTask.getDescription());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("dialogValue", dialogValue);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dialogValue = savedInstanceState.getInt("dialogValue");
    }

    public void unmarkAsDoneButtonOnClick(View view){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = ts;
        todoTask.setEditTimestamp(date.toString());
        Todo newTodo = todoTask;
        newTodo.setIsDone(false);
        TodoListManager.getInstance().updateTodo(todoTask, newTodo);
        intentBack2.putExtra("unmarkTodoAsDone", true);
        setResult(RESULT_OK, intentBack2);
        finish();
    }

    public void deleteButtonOnClick(View view){

        dialogValue = todoTaskPos;
        DeleteTodoItemDialog dialog = new DeleteTodoItemDialog();
        dialog.show(getSupportFragmentManager(), "Delete todo item dialog");
    }

    @Override
    public void onDeleteTodoItemClicked() {
        if (dialogValue <= -1) {
            Log.e("Delete_no_position", "Delete was called with no position!");
            return;
        }
        TodoListManager.getInstance().deleteTodoForever(todoTask);
        intentBack2.putExtra("todoIsDelete", true);
        setResult(RESULT_OK, intentBack2);
        dialogValue = -1;
        finish();
    }
}

