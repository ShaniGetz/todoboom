package com.example.todoboom;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TodoListManager {

    private static TodoListManager single_instance = null;
    private ArrayList<Todo> mTodoList;
    private static final String LOG_TAG = "FirestoreManager";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference todoRef = db.collection("allTodo");


    private TodoListManager(){
        if (mTodoList == null) {
            mTodoList = new ArrayList<>();
        }
        createLiveQuery();
    }

    private void createLiveQuery(){
        todoRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(LOG_TAG, "snapshot exception");
                }
                if(queryDocumentSnapshots == null){
                    Log.d(LOG_TAG, "null value");
                }
                TodoListManager.this.mTodoList.clear();
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Todo newTodo = doc.toObject(Todo.class);
                    mTodoList.add(newTodo);
                }
            }
        });
    }

    public static TodoListManager getInstance() {
        if (single_instance == null) {
            single_instance = new TodoListManager();
        }
        return(single_instance);
    }

    public void addTodo(Todo task) {
        if(mTodoList.contains(task)){
            Log.w(LOG_TAG, "ignoring, task already in local arrayList!");
        }
        mTodoList.add(task);
        DocumentReference document = db.collection("allTodo").document();
        task.setId(document.getId());
        document.set(task);
    }

    public void updateTodo(Todo oldTask, Todo newTask){
        int index = getTodoIdxFromId(oldTask.getId());
        if(index == -1){
            Log.e(LOG_TAG, "can't edit todo: could not find old todo!");
            return;
        }
        mTodoList.remove(oldTask);
        mTodoList.add(index, newTask);
        // update in firebase
        String documentId = oldTask.getId();
        if (documentId == null) {
            Log.e(LOG_TAG, "can't update todo in firestore, no docucment-id!" + oldTask);
            return;
        }
        newTask.setId(documentId);
        todoRef.document(documentId).set(newTask);
    }

    public void deleteTodoForever(final Todo task) {
        mTodoList.remove(task);
        String documentId = task.getId();
        if(documentId == null){
            Log.e(LOG_TAG, "can't delete todo, no docucment-id to delete in firestore!" + task);
            return;
        }
        todoRef.document(documentId).delete();
    }


    public int getSize(){
        return mTodoList.size();
    }

    public ArrayList<Todo> getAllTodos(){
        return mTodoList;
    }

    public Todo getTodoFromId(String id){
            for(Todo todoTask : mTodoList){
            if(todoTask.getId().equals(id)){
                return todoTask;
            }
        }
        return null;
    }

    public int getTodoIdxFromId(String id){
        for (int i = 0; i < mTodoList.size(); i++) {
            if (mTodoList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
