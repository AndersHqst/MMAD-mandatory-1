package com.hqst.bookshelf.app;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by ahkj on 20/03/14.
 */
public class ViewBookActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        long bookId = intent.getLongExtra(BookshelfListFragment.EXTRA_BOOK_ID, -1);
        return ViewBookFragment.newInstance(bookId);
    }
}
