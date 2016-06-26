package com.brankomostic.quickconnect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ArrayList<DataField> fields;

    public ListFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final int NAME = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
        final int TEXT = InputType.TYPE_CLASS_TEXT;
        final int PHONE = InputType.TYPE_CLASS_PHONE;
        final int EMAIL = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        final int URL = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI;
        final int CAP_SENTENCE = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
        final int CAP_WORDS = InputType.TYPE_TEXT_FLAG_CAP_WORDS;
        //ADD NEW INPUT TYPES HERE

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        LinearLayout list = (LinearLayout)rootView.findViewById(R.id.linear_layout);
        Context context = getActivity();

        fields = new ArrayList<>();
        fields.add(new DataField(context, "Name", "name", "", NAME));
        fields.add(new DataField(context, "Position", "position", "", TEXT | CAP_WORDS));
        fields.add(new DataField(context, "Organization/School", "organization", "", TEXT | CAP_WORDS));
        fields.add(new DataField(context, "Phone Number", "phone", "Phone", PHONE));
        fields.add(new DataField(context, "Email", "email", "Email", EMAIL));
        fields.add(new DataField(context, "Website", "website", "Website", URL));
        fields.add(new DataField(context, "Linkedin Link", "linkedin", "Linkedin", URL));
        fields.add(new DataField(context, "GitHub Account", "github", "GitHub", TEXT));
        fields.add(new DataField(context, "Facebook Link", "facebook", "Facebook", URL));
        fields.add(new DataField(context, "Twitter Handle", "twitter", "Twitter", TEXT));
        //ADD NEW FIELDS HERE TODO add fields
        fields.add(new DataField(getActivity(), "Other", "other", "", TEXT | CAP_SENTENCE, true));

        for (DataField field : fields) {
            list.addView(field);
        }
        list.requestFocus();
        return rootView;
    }

    private void save() {
        for(DataField field : fields) {
            field.saveData(getActivity());
        }
    }

    public void share(boolean [] pass) {
        save();
        String data = shareString(pass);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }

    public String shareString(boolean [] s) {
        String toShare = "";
        String promote = ""; //TODO add string

        for(int i = 0; i < fields.size(); i++) {
            if(s[i]) {
                toShare = toShare + (fields.get(i)).getShareString();
            }
        }

        toShare = toShare + promote;

        return toShare;
    }

    public void dialog(Context context) {
        if(fields != null) {
            final boolean[] checked = new boolean[fields.size()];
            for (int i = 0; i < checked.length; i++) {
                checked[i] = false;
            }

            String[] entries = new String[fields.size()];
            for (int i = 0; i < fields.size(); i++) {
                entries[i] = fields.get(i).getTitle();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Set the dialog title
            builder.setTitle("Choose What to Share")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(entries, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    checked[which] = isChecked;
                                }
                            })
                            // Set the action buttons
                    .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            share(checked);
                        }
                    })
                    .setNeutralButton("Share All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            for (int i = 0; i < checked.length; i++) {
                                checked[i] = true;
                            }
                            share(checked);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
        }
    }
}
