package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by asana341 on 7/10/14.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
