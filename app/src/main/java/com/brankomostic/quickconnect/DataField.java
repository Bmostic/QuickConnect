package com.brankomostic.quickconnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataField extends LinearLayout {

    private TextView header;
    private EditText data;
    private String key;
    private String shareString;

    public DataField(Context context, String title, String nKey, String share, int inputType) {
        super(context);
        init(context, title, nKey, share, inputType, false);
    }

    public DataField(Context context, String title, String nKey, String share, int inputType, boolean isOther) {
        super(context);
        init(context, title, nKey, share, inputType, isOther);
    }

    private void init(Context context, String title, String nKey, String share, int inputType, boolean isOther) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.data_entry_layout, this);

        header = (TextView)view.findViewById(R.id.header);
        data = (EditText)view.findViewById(R.id.data);
        data.setInputType(inputType);
        data.clearFocus();
        if(isOther) {
            data.setSingleLine(false);
            data.setHint(R.string.other_hint);
        }

        header.setText(title);
        key = nKey;
        data.setText(PreferenceManager.getDefaultSharedPreferences(context).getString(key, ""));
        shareString = share;
    }

    public void saveData(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, data.getText().toString());
        editor.apply();
    }

    public String getShareString() {
        String result = "";
        if(shareString != null && !shareString.isEmpty() && !getData().isEmpty()) {
            result = shareString + ": " + getData() + "\n";
        } else if(!getData().isEmpty()){
            result = getData() + "\n";
        }
        return result;
    }

    public String getData() {
        data.clearFocus();
        return data.getText().toString();
    }

    public String getTitle() {
        return header.getText().toString();
    }
}
