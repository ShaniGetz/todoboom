package com.example.todoboom;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {

    private String description;
    private boolean isDone;

    private Todo(Parcel in) {
        description = in.readString();
        isDone = in.readByte() != 0;
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
    }

    Todo(String description){
        this.description = description;
        this.isDone = false;
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
}

