package com.example.todoboom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteTodoItemDialog extends AppCompatDialogFragment {
    private DeleteTodoItemDialogListener listener;
    private int taskPosition;

    DeleteTodoItemDialog(int taskPosition){
        this.taskPosition = taskPosition;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attention!")
                .setMessage("Are You Sure to delete?")
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDeleteTodoItemClicked(taskPosition);
                    }
                });
        return builder.create();
    }

    public interface DeleteTodoItemDialogListener {
        void onDeleteTodoItemClicked(int taskPosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteTodoItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement DeleteTodoItemDialogListener");
        }
    }
}
