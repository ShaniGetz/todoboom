package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
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
        DeleteTodoItemDialog.DeleteTodoItemDialogListener {

    ArrayList<Todo> mTodoList;
    EditText inputField;
    TextView textView;
    Button button;
    private TodoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.input_field);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);
        RecyclerView recyclerViewTasks = findViewById(R.id.recycler_view_tasks);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTasks.setLayoutManager(layoutManager);

        loadData();
        logTodoListSize();
        mAdapter = new TodoAdapter(mTodoList,this);
        recyclerViewTasks.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo task = new Todo(String.valueOf(inputField.getText()));
                if (task.getDescription().length() == 0) {
                    toastMessage("you can't create an empty task");
                } else {
                    addItem(task);
                }
                inputField.setText("");
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mTodoList);
        editor.putString("TodoList", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("TodoList", null);
        Type type = new TypeToken<ArrayList<Todo>>() {}.getType();
        mTodoList = gson.fromJson(json, type);
        if (mTodoList == null) {
            mTodoList = new ArrayList<>();
        }
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
        mTodoList.add(task);
        mAdapter.updateListAddItem(mTodoList, mTodoList.size() - 1);
        saveData();
    }

    @Override
    public void markedAsDone (int position) {
        Todo task = mTodoList.get(position);
        task.setIsDone(true);
        String doneMessage = "TODO " + task.getDescription() +  " is now DONE. BOOM!";
        toastMessage(doneMessage);
        saveData();
    }

    @Override
    public void onTodoLongClick(int position) {
        DeleteTodoItemDialog dialog = new DeleteTodoItemDialog(position);
        dialog.show(getSupportFragmentManager(), "Delete todo item dialog");
    }

    @Override
    public void onDeleteTodoItemClicked(int position) {
        Todo task = mTodoList.get(position);
        mTodoList.remove(task);
        mAdapter.updateListRemoveItem(mTodoList, position);
        saveData();
    }

    private void logTodoListSize() {
        Log.e("Todo list Size is ", Integer.toString(mTodoList.size()));
    }
}
