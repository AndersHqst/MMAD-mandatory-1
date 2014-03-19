package com.hqst.bookshelf.app;

import android.support.v4.app.Fragment;

/**
 * Created by ahkj on 19/03/14.
 */
public class CreateBookActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CreateBookFragment();
    }
}
