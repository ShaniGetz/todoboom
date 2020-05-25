package com.example.todoboom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CompletedTodoActivity extends AppCompatActivity implements DeleteTodoItemDialog.DeleteTodoItemDialogListener {

    TextView textViewTodo;
    int todoTaskId;
    Todo todoTask;
    Intent intentBack2;
    private int dialogValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todo);
        dialogValue = -1; // Default value
        Intent intentCreatedMe = getIntent();
        todoTaskId = intentCreatedMe.getIntExtra("taskId", 0); //pos and not id
//        todoTask = TodoListManager.getInstance().getTodoFromId(todoTaskId);
        todoTask = TodoListManager.getInstance().getAllTodos().get(todoTaskId); //with pos and not id
        setMyContentView();
        intentBack2 = new Intent();
        intentBack2.putExtra("todoId", todoTaskId); //pos and not id
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
        todoTask.setIsDone(false);
        intentBack2.putExtra("unmarkTodoAsDone", true);
        setResult(RESULT_OK, intentBack2);
        TodoListManager.getInstance().saveData(getApplicationContext());
        finish();
    }

    public void deleteButtonOnClick(View view){
        dialogValue = todoTaskId;
        DeleteTodoItemDialog dialog = new DeleteTodoItemDialog();
        dialog.show(getSupportFragmentManager(), "Delete todo item dialog");
    }

    @Override
    public void onDeleteTodoItemClicked() {
        if (dialogValue <= -1) {
            Log.e("Delete_no_position", "Delete was called with no position!");
            return;
        }
        TodoListManager.getInstance().deleteTodoForever(todoTask, getApplicationContext());
        intentBack2.putExtra("todoIsDelete", true);
        setResult(RESULT_OK, intentBack2);
        dialogValue = -1;
        finish();
    }
}

