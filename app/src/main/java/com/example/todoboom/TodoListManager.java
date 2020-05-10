package com.example.todoboom;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TodoListManager {

    private static TodoListManager single_instance = null;
    private ArrayList<Todo> mTodoList;
    private static final String SP_TODO_LIST = "TodoList";

    private TodoListManager(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SP_TODO_LIST, null);
        Type type = new TypeToken<ArrayList<Todo>>() {
        }.getType();
        mTodoList = gson.fromJson(json, type);
        if (mTodoList == null) {
            mTodoList = new ArrayList<>();
        }
    }

    public static TodoListManager getInstance(Context context) {
        if (single_instance == null) {
            single_instance = new TodoListManager(context);
        }
        return(single_instance);
    }

    public static TodoListManager getInstance() {
        return(single_instance);
    }

    private void saveData(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mTodoList);
        editor.putString(SP_TODO_LIST, json);
        editor.apply();
    }

    public void addTodo(Todo task, Context context) {
        mTodoList.add(task);
        saveData(context);
    }

    public void markTodoAsDone(Todo task, Context context) {
        task.setIsDone(true);
        saveData(context);
    }

    public void deleteTodoForever(Todo task, Context context) {
        mTodoList.remove(task);
        saveData(context);
    }

    public int getSize(){
        return mTodoList.size();
    }

    public ArrayList<Todo> getAllTodos(){
        return mTodoList;
    }

//    getTodoFromId();
}
