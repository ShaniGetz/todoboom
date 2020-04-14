package com.example.todoboom;

public class Todo {
    private String description;
    private boolean isDone;

    public Todo(String description){
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsDone(){
        return isDone;
    }

    public void setIsDone(Boolean bool){
        this.isDone=bool;
    }
}

