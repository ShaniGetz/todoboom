package com.example.todoboom;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    public TodoListManager info;

    @Override
    public void onCreate() {
        super.onCreate();
        info = TodoListManager.getInstance(this);
        logTodoListSize();
    }

    private void logTodoListSize() {
        Log.e("Todo list Size is ", Integer.toString(info.getSize()));
    }
}