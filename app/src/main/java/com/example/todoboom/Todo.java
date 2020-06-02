package com.example.todoboom;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {

    private String description;
    private boolean isDone;
    private String creationTimestamp;
    private String editTimestamp;
    private String id;


    private Todo(Parcel in) {
        description = in.readString();
        isDone = in.readByte() != 0;
        creationTimestamp = in.readString();
        editTimestamp = in.readString();
        id = in.readString();
    }

    public Todo(){
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(isDone ? 1 : 0);
        dest.writeString(creationTimestamp);
        dest.writeString(editTimestamp);
        dest.writeString(id);
    }

    Todo(String description, String creationTimestamp, String editTimestamp){
        this.description = description;
        this.isDone = false;
        this.creationTimestamp = creationTimestamp;
        this.editTimestamp = editTimestamp;
        this.id = null;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDone(){
        return isDone;
    }

    void setIsDone(Boolean bool){
        this.isDone=bool;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getEditTimestamp() {
        return editTimestamp;
    }

    public void setEditTimestamp(String editTimestamp) {
        this.editTimestamp = editTimestamp;
    }
}

