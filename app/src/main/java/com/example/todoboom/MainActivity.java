package com.example.todoboom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    TextView textView;
    Button button;
    private RecyclerView recyclerViewTasks;
    private LinearLayoutManager layoutManager;
    private TodoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.input_field);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);

        mAdapter = new TodoAdapter();

        recyclerViewTasks = findViewById(R.id.recycler_view_tasks);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewTasks.setLayoutManager(layoutManager);
        recyclerViewTasks.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo task = new Todo(String.valueOf(inputField.getText()));
                if (task.getDescription().equals("")) {
                    toastMessage("you can't create an empty task");
                } else {
                    mAdapter.addItem(task);
                }
                inputField.setText("");
            }
        });

//        if (savedInstanceState != null){
////            task = savedInstanceState.getString("task");
////            textView.setText(task);
//        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("inputFieldText",String.valueOf(inputField.getText()));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inputField.setText(savedInstanceState.getString("inputFieldText"));
    }

    public void toastMessage(String message) {
        Toast newToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        newToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        newToast.show();
    }
}
