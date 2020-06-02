package com.example.todoboom;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    public TodoListManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dbManager = TodoListManager.getInstance();
        logTodoListSize();
    }

    private void logTodoListSize() {
        Log.e("Todo list Size is ", Integer.toString(dbManager.getSize()));
    }
}