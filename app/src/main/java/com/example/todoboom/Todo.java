package com.example.todoboom;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {

    private String description;
    private boolean isDone;
//    String creation_timestamp;
//    String editTimestamp;
    private int id;


    private Todo(Parcel in) {
        description = in.readString();
        isDone = in.readByte() != 0;
        id = in.readInt();
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
        dest.writeInt(id);
    }

    Todo(String description, int id){
        this.description = description;
        this.isDone = false;
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    boolean getIsDone(){
        return isDone;
    }

    void setIsDone(Boolean bool){
        this.isDone=bool;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

