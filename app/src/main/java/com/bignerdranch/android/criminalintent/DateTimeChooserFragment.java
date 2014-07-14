package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import java.util.Date;

/**
 * Created by asana341 on 7/14/14.
 */
public class DateTimeChooserFragment extends DialogFragment {
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";

    private static final int REQUEST_DATE = 1;
    private static final int REQUEST_TIME = 2;

    private Date mDate;

    public static final String EXTRA_DATETIME = "com.bignerdranch.android.criminalintent.datetime";

    public static DateTimeChooserFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATETIME, date);
        DateTimeChooserFragment fragment = new DateTimeChooserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult() {
        Intent i = new Intent();
        i.putExtra(EXTRA_DATETIME, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDate.setYear(date.getYear());
            mDate.setMonth(date.getMonth());
            mDate.setDate(date.getDate());
            sendResult();
        } else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mDate.setHours(date.getHours());
            mDate.setMinutes(date.getMinutes());
            sendResult();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATETIME);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose, null);

        Button dateButton = (Button)v.findViewById(R.id.dialog_choose_dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(DateTimeChooserFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
        Button timeButton = (Button)v.findViewById(R.id.dialog_choose_timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(DateTimeChooserFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_time_chooser_title)
                .create();
    }
}
