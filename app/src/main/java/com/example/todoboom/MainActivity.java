package com.example.todoboom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTaskEventListener,
        DeleteTodoItemDialog.DeleteTodoItemDialogListener{

    private ArrayList<Todo> mTodoList;
    EditText inputField;
    TextView textView;
    Button button;
    private TodoAdapter mAdapter;
    private int dialogValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoList = TodoListManager.getInstance().getAllTodos();

        dialogValue = -1; // Default value

        inputField = findViewById(R.id.input_field);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);
        RecyclerView recyclerViewTasks = findViewById(R.id.recycler_view_tasks);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTasks.setLayoutManager(layoutManager);

        mAdapter = new TodoAdapter(mTodoList,this);
        recyclerViewTasks.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo task = new Todo(String.valueOf(inputField.getText()), TodoListManager.getIdCounter());
                if (task.getDescription().length() == 0) {
                    toastMessage("you can't create an empty task");
                } else {
                    addItem(task);
                    TodoListManager.addByOneIdCounter();
                }
                inputField.setText("");
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("inputFieldText",String.valueOf(inputField.getText()));
        outState.putInt("dialogValue", dialogValue);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        inputField.setText(savedInstanceState.getString("inputFieldText"));
        dialogValue = savedInstanceState.getInt("dialogValue");
    }

    public void toastMessage(String message) {
        Toast newToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        newToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        ViewGroup group = (ViewGroup) newToast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(16);
        newToast.show();
    }

    private void addItem(Todo task) {
        TodoListManager.getInstance().addTodo(task, getApplicationContext());
        mAdapter.updateListAddItem(mTodoList, mTodoList.size() - 1);
    }

    @Override
    public void markedAsDone (int position) {
        Todo task = mTodoList.get(position);
        TodoListManager.getInstance().markTodoAsDone(task, getApplicationContext());
        String doneMessage = "TODO " + task.getDescription() +  " is now DONE. BOOM!";
        toastMessage(doneMessage);
    }

    @Override
    public void onTodoLongClick(int position) {
        dialogValue = position;
        DeleteTodoItemDialog dialog = new DeleteTodoItemDialog();
        dialog.show(getSupportFragmentManager(), "Delete todo item dialog");
    }

    @Override
    public void onDeleteTodoItemClicked() {
        if (dialogValue <= -1) {
            Log.e("Delete_no_position", "Delete was called with no position!");
            return;
        }
        Todo task = mTodoList.get(dialogValue);
        TodoListManager.getInstance().deleteTodoForever(task, getApplicationContext());
        mAdapter.updateListRemoveItem(mTodoList, dialogValue);
        dialogValue = -1;
    }

}
