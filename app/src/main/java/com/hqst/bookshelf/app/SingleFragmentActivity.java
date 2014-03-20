package com.hqst.bookshelf.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ahkj on 22/02/14.
 * Code snippet is taken from the big nerd ranch book.
 */
public abstract class SingleFragmentActivity extends FragmentActivity{
    protected abstract Fragment createFragment();

    protected int getResLayoutId(){
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayoutId());

        // FragmentManager from the support library
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        // will be null the first time, after this the FragmentManager saves
        // the fragment list between view cycles
        if(fragment == null){
            // Create and commit a Fragment transaction
            // Transactions are used to add, remove, attach, detach, or replace fragments
            // in the fragment list.
            fragment = createFragment();
            // The following two lines are normally one lined it seems
            FragmentTransaction ft = fm.beginTransaction();
            // ft.add|replace|attach|...
            // Provide container id for the FragmentManager to know where to put the
            // fragment. Also serves as a unique id in the FragmentManager's
            // fragment list.
            ft.add(R.id.fragmentContainer, fragment).commit();
        }
    }
}
