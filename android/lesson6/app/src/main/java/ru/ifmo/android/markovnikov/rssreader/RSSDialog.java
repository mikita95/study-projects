package ru.ifmo.android.markovnikov.rssreader;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class RSSDialog extends DialogFragment {
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getResources().getString(R.string.dialogTitle));
        final View view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.rss_dialog, null);
        builder.setView(view);
        builder.setPositiveButton(getActivity().getResources().getString(R.string.buttonAdd), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("url", ((EditText) view.findViewById(R.id.editTextUrl)).getText().toString());
                getActivity().getContentResolver().insert(Uri.parse("content://ru.ifmo.android.markovnikov.rssreader.feeds/channels"), contentValues);
                dismiss();
            }
        });

        builder.setNegativeButton(getActivity().getResources().getString(R.string.buttonCancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        return builder.create();
    }
}
